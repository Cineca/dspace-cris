/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;
import org.hibernate.HibernateException;
import org.hibernate.annotations.common.util.ReflectHelper;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

public class UpdateSchemaTool extends MatchingTask {

    private List fileSets = new LinkedList();
    private File propertiesFile = null;
    private File configurationFile = null;
    private File outputFile = null;
    private boolean quiet = false;
    private boolean text = true;
    private boolean haltOnError = false;
    private String delimiter = null;
    private String namingStrategy = null;
    

    public void addFileset(FileSet set) {
        fileSets.add(set);
    }

    /**
     * Set a properties file
     * @param propertiesFile the properties file name
     */
    public void setProperties(File propertiesFile) {
        if ( !propertiesFile.exists() ) {
            throw new BuildException("Properties file: " + propertiesFile + " does not exist.");
        }

        log("Using properties file " + propertiesFile, Project.MSG_DEBUG);
        this.propertiesFile = propertiesFile;
    }

    /**
     * Set a <literal>.cfg.xml</literal> file
     * @param configurationFile the file name
     */
    public void setConfig(File configurationFile) {
        this.configurationFile = configurationFile;
    }

    /**
     * Enable "text-only" mode. The schema will not
     * be updated in the database.
     * @param text true to enable text-only mode
     */
    public void setText(boolean text) {
        this.text = text;
    }

    /**
     * Enable "quiet" mode. The schema will not be
     * written to standard out.
     * @param quiet true to enable quiet mode
     */
    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            log("Running Hibernate Core SchemaUpdate."); 
            log("###CILEA HACK to support Hibernate annotation");
            Configuration cfg = getConfiguration();
            getSchemaUpdate(cfg).execute(!quiet, !text);
        }
        catch (HibernateException e) {
            throw new BuildException("Schema text failed: " + e.getMessage(), e);
        }
        catch (FileNotFoundException e) {
            throw new BuildException("File not found: " + e.getMessage(), e);
        }
        catch (IOException e) {
            throw new BuildException("IOException : " + e.getMessage(), e);
        }
        catch (Exception e) {
            throw new BuildException(e);
        }
    }

    private String[] getFiles() {

        List files = new LinkedList();
        for ( Iterator i = fileSets.iterator(); i.hasNext(); ) {

            FileSet fs = (FileSet) i.next();
            DirectoryScanner ds = fs.getDirectoryScanner( getProject() );

            String[] dsFiles = ds.getIncludedFiles();
            for (int j = 0; j < dsFiles.length; j++) {
                File f = new File(dsFiles[j]);
                if ( !f.isFile() ) {
                    f = new File( ds.getBasedir(), dsFiles[j] );
                }

                files.add( f.getAbsolutePath() );
            }
        }

        return ArrayHelper.toStringArray(files);
    }

    private Configuration getConfiguration() throws Exception {
        AnnotationConfiguration cfg = new AnnotationConfiguration();
        if (namingStrategy!=null) {
            cfg.setNamingStrategy(
                    (NamingStrategy) ReflectHelper.classForName(namingStrategy).newInstance()
                );
        }
        if (configurationFile!=null) {
            cfg.configure( configurationFile );
        }

        String[] files = getFiles();
        for (int i = 0; i < files.length; i++) {
            String filename = files[i];
            if ( filename.endsWith(".jar") ) {
                cfg.addJar( new File(filename) );
            }
            else {
                cfg.addFile(filename);
            }
        }
        return cfg;
    }

    private SchemaUpdate getSchemaUpdate(Configuration cfg) throws HibernateException, IOException {
        Properties properties = new Properties();
        properties.putAll( cfg.getProperties() );
        if (propertiesFile == null) {
            properties.putAll( getProject().getProperties() );
        }
        else {
            properties.load( new FileInputStream(propertiesFile) );
        }
        cfg.setProperties(properties);
        SchemaUpdate su = new SchemaUpdate(cfg);
        su.setOutputFile( outputFile.getPath() );
        su.setDelimiter(delimiter);
        su.setHaltOnError(haltOnError);
        return su;
    }

    public void setNamingStrategy(String namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public boolean isHaltOnError() {
        return haltOnError;
    }

    public void setHaltOnError(boolean haltOnError) {
        this.haltOnError = haltOnError;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }


}
