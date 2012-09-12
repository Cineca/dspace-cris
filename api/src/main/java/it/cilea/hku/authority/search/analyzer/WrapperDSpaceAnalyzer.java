/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.search.analyzer;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.LogManager;

/**
 * Wrapper analyzer of the analyzer configurated in dspace.cfg. The purpose is
 * to use the same analyzer of DSpace without the need to change in more than
 * one place the configuration.
 * 
 * @author cilea
 * 
 */
public class WrapperDSpaceAnalyzer extends Analyzer 
{
    /** The log4j logger */
    private static final Log log = LogFactory.getLog(WrapperDSpaceAnalyzer.class);

    /**
     * The wrapped analyzer as configurated in the dspace.cfg file
     */
    private Analyzer analyzer;

    /**
     * Initialization method. Need because the class could be instantiate before
     * that the DSpace context is fully initialized and the configuration read.
     */
    public void init()
    {
        if (analyzer != null)
        {
            // nothing to do, already initialized
            return;
        }
        String analyzerClassName = ConfigurationManager
                .getProperty("search.analyzer");

        if (analyzerClassName == null)
        {
            // Use default
            analyzerClassName = "org.dspace.search.DSAnalyzer";
        }

        try
        {
            Class analyzerClass = Class.forName(analyzerClassName);
            analyzer = (Analyzer) analyzerClass.newInstance();
        }
        catch (Exception e)
        {
            log.fatal(LogManager.getHeader(null, "no_search_analyzer",
                    "search.analyzer=" + analyzerClassName), e);

            throw new IllegalStateException(e.toString());
        }

    }

    /**
     * Wrapper method.
     * 
     * @see Analyzer#close()
     */
    public void close()
    {
        init();
        analyzer.close();
    }

    /**
     * Wrapper method.
     * 
     * @see Analyzer#getPositionIncrementGap(String)
     */
    public int getPositionIncrementGap(String fieldName)
    {   
        init();
        return analyzer.getPositionIncrementGap(fieldName);
    }
    
    /**
     * Wrapper method.
     * 
     * @see Analyzer#tokenStream(String, Reader)
     */
    public TokenStream tokenStream(String fieldName, Reader reader)
    {
        init();
        return analyzer.tokenStream(fieldName, reader);
    }

    /**
     * Wrapper method.
     * 
     * @see Analyzer#reusableTokenStream(String, Reader)
     */
    public TokenStream reusableTokenStream(String fieldName, Reader reader)
            throws IOException
    {
        init();
        return analyzer.reusableTokenStream(fieldName, reader);
    }
}
