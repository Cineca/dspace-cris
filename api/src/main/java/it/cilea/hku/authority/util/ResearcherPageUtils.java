/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.VisibilityConstants;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.jdyna.web.Tab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Utils;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class provides some static utility methods to extract information from
 * the rp identifier quering the RPs database.
 * 
 * @author cilea
 * 
 */
public class ResearcherPageUtils
{
    public static final String DIRECTORY_TAB_ICON = "icon";

	public static final String PREFIX_TAB_ICON = "tab_";

	/**
     * Formatter to build the rp identifier
     */
    private static DecimalFormat persIdentifierFormat = new DecimalFormat(
            "00000");

    /**
     * the applicationService to query the RP database, injected by Spring IoC
     */
    private static ApplicationService applicationService;

    /**
     * Constructor use by Spring IoC
     * 
     * @param applicationService
     *            the applicationService to query the RP database, injected by
     *            Spring IoC
     */
    public ResearcherPageUtils(ApplicationService applicationService)
    {
        ResearcherPageUtils.applicationService = applicationService;
    }

    /**
     * Build the rp identifier of the supplied ResearcherPage
     * 
     * @param rp
     *            the researcher page
     * @return the rp identifier of the supplied ResearhcerPage
     */
    public static String getPersistentIdentifier(ResearcherPage rp)
    {
        return "rp" + persIdentifierFormat.format(rp.getId());
    }

    /**
     * Build the rp identifier starting from the db internal primary key
     * 
     * @param rp
     *            the internal db primary key of the researcher page
     * @return the rp identifier of the supplied ResearhcerPage
     */
    public static String getPersistentIdentifier(Integer rp)
    {
        return "rp" + persIdentifierFormat.format(rp);
    }

    /**
     * Extract the db primary key from a rp identifier
     * 
     * @param authorityKey
     *            the rp identifier
     * @return the db primary key
     */
    public static Integer getRealPersistentIdentifier(String authorityKey) 
    {
        try
        {
            return Integer.parseInt(authorityKey.substring(2));
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Return a label to use with a specific form of the name of the researcher.
     * 
     * @param alternativeName
     *            the form of the name to use
     * @param rp
     *            the researcher page
     * @return the label to use
     */
    public static String getLabel(String alternativeName, ResearcherPage rp)
    {
        if (alternativeName.equals(rp.getFullName()))
        {
            return rp.getFullName() + " " + rp.getChineseName().getValue() + " - "
                    + rp.getDept().getValue();
        }
        else
        {
            return alternativeName + " See \"" + rp.getFullName() + "\" - "
                    + rp.getDept().getValue();
        }
    }

    /**
     * Return a label to use with a specific form of the name of the researcher.
     * 
     * @param alternativeName
     *            the form of the name to use
     * @param rpKey
     *            the rp identifier of the ResearcherPage
     * @return the label to use
     */
    public static String getLabel(String alternativeName, String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    Integer.parseInt(rpkey.substring(2)));
            return getLabel(alternativeName, rp);
        }
        return alternativeName;
    }
    
    /**
     * Check if the supplied form is the fullname of the ResearcherPage
     * 
     * @param alternativeName
     *            the string to check
     * @param rpkey
     *            the rp identifier
     * @return true, if the form is the fullname of the ResearcherPage with the
     *         supplied rp identifier
     */
    public static boolean isFullName(String alternativeName, String rpkey)
    {
        if (alternativeName != null && rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return alternativeName.equals(rp.getFullName());
        }
        return false;
    }

    /**
     * Check if the supplied form is the Chinese name of the ResearcherPage
     * 
     * @param alternativeName
     *            the string to check
     * @param rpkey
     *            the rp identifier
     * @return true, if the form is the Chinese name of the ResearcherPage with
     *         the supplied rp identifier
     */
    public static boolean isChineseName(String alternativeName, String rpkey)
    {
        if (alternativeName != null && rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return alternativeName.equals(rp.getChineseName().getValue());
        }
        return false;
    }
    
    /**
     * Get the fullname of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the fullname of the ResearcherPage
     */
    public static String getFullName(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return rp.getFullName();
        }
        return null;
    }
    
    
    /**
     * Get the department of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the department of the ResearcherPage
     */
    public static String getDepartment(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return VisibilityConstants.PUBLIC == rp.getDept().getVisibility()?
                    rp.getDept().getValue():null;
        }
        return null;
    }

    /**
     * Get the list of publicly visible interests of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the fullname of the ResearcherPage
     */
    public static List<String> getInterests(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            List<String> results = new ArrayList<String>();            
            for (RestrictedField rf : rp.getInterests())
            {
              if (rf.getVisibility() == VisibilityConstants.PUBLIC)
              {
                  results.add(rf.getValue());
              }
            }
            return results;
        }
        return null;
    }
    
    /**
     * Get the staff number of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the staff number of the ResearcherPage
     */
    public static String getStaffNumber(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return rp != null?rp.getStaffNo():null;
        }
        return null;
    }

    /**
     * Get the rp identifier of the ResearcherPage with a given staffno
     * 
     * @param staffno
     *            the staffno
     * @return the rp identifier of the ResearcherPage or null
     */
    public static String getRPIdentifierByStaffno(String staffno)
    {
        if (staffno != null)
        {
            ResearcherPage rp = applicationService.getResearcherPageByStaffNo(staffno);
            if (rp != null)
            {
            	return getPersistentIdentifier(rp);
            }
        }
        return null;
    }
    
    /**
     * Get the ChineseName of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the Chinese name of the ResearcherPage
     */
    public static String getChineseName(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return VisibilityConstants.PUBLIC == rp.getChineseName().getVisibility()?
                    rp.getChineseName().getValue():"";
        }
        return null;
    }
    
	/**
	 * Remove curriculum vitae on server by researcher get from parameter method.
	 * 
	 * @param researcher
	 */
	public static void removeCVFiles(ResearcherPage researcher) {
		researcher.getCv().setValue(null);
		researcher.getCv().setMimeType(null);
		File directory = new File(
				ConfigurationManager.getProperty("researcherpage.file.path")
						+ File.separatorChar
						+ ResearcherPageUtils
								.getPersistentIdentifier(researcher));

		if (directory.exists()) {
			Collection<File> files = FileUtils.listFiles(directory,
					new WildcardFilter("*-CV.*"), null);

			for (File cv : files) {
				cv.delete();
			}
		}
	}
	
	/**
	 * Remove researcher personal image from the server.
	 * 
	 * @param researcher
	 */
	public static void removePicture(ResearcherPage researcher) {
		File image = new File(ConfigurationManager
		        .getProperty("researcherpage.file.path")
		        + File.separatorChar
		        + ResearcherPageUtils.getPersistentIdentifier(researcher)
		        + File.separatorChar + ResearcherPageUtils.getPersistentIdentifier(researcher) + "." + researcher.getPict().getValue());
		image.delete();   
		researcher.setPict(null);
	}
	
	/**
	 * 
	 * Load curriculum and copy to default directory.
	 * 
	 * @param researcher
	 * @param rp
	 * @param itemCV
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void loadCv(ResearcherPage researcher, String rp,
			MultipartFile itemCV) throws IOException, FileNotFoundException {
		String pathCV = ConfigurationManager
		        .getProperty("researcherpage.file.path");
		String ext = itemCV.getOriginalFilename().substring(
		        itemCV.getOriginalFilename().lastIndexOf(".") + 1);
		File dir = new File(pathCV + File.separatorChar + rp);
		dir.mkdir();
		File file = new File(dir, rp + "-CV." + ext);
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		Utils.bufferedCopy(itemCV.getInputStream(), out);
		out.close();
		researcher.getCv().setValue(ext);
		researcher.getCv().setMimeType(itemCV.getContentType());
	}
	
	/**
	 * 
	 * Load curriculum and copy to default directory.
	 * 
	 * @param researcher
	 * @param rp
	 * @param itemCV
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void loadCv(ResearcherPage researcher, String rp,
			File itemCV) throws IOException, FileNotFoundException {
		String pathCV = ConfigurationManager
		        .getProperty("researcherpage.file.path");
		String ext = itemCV.getName().substring(
		        itemCV.getName().lastIndexOf(".") + 1);
		File dir = new File(pathCV + File.separatorChar + rp);
		dir.mkdir();
		File file = new File(dir, rp + "-CV." + ext);
		file.createNewFile();
		FileInputStream in = new FileInputStream(itemCV);
		FileOutputStream out = new FileOutputStream(file);
		Utils.bufferedCopy(in, out);
		out.close();
		researcher.getCv().setValue(ext);
		researcher.getCv().setMimeType(new MimetypesFileTypeMap().getContentType(itemCV));
	}
	

	/**
	 * 
	 * Load image and copy to default directory.
	 * 
	 * @param researcher
	 * @param rp
	 * @param itemImage MultipartFile to use in webform
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void loadImg(ResearcherPage researcher, String rp,
			MultipartFile itemImage) throws IOException, FileNotFoundException {
		String pathImage = ConfigurationManager
		        .getProperty("researcherpage.file.path");
		String ext = itemImage.getOriginalFilename().substring(
		        itemImage.getOriginalFilename().lastIndexOf(".") + 1);
		File dir = new File(pathImage + File.separatorChar + rp);
		dir.mkdir();
		File file = new File(dir, rp + "." + ext);
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		Utils.bufferedCopy(itemImage.getInputStream(), out);
		out.close();
		researcher.getPict().setValue(ext);
		researcher.getPict().setMimeType(itemImage.getContentType());
	}

	/**
	 * 
	 * Load image and copy to default directory.
	 * 
	 * @param researcher
	 * @param rp
	 * @param itemImage java.util.File to use out web form
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void loadImg(ResearcherPage researcher, String rp,
			File itemImage) throws IOException, FileNotFoundException {
		String pathImage = ConfigurationManager
		        .getProperty("researcherpage.file.path");
		String ext = itemImage.getName().substring(
		        itemImage.getName().lastIndexOf(".") + 1);
		File dir = new File(pathImage + File.separatorChar + rp);
		dir.mkdir();
		File file = new File(dir, rp + "." + ext);
		file.createNewFile();
		FileInputStream in = new FileInputStream(itemImage);
		FileOutputStream out = new FileOutputStream(file);
		Utils.bufferedCopy(in, out);
		out.close();
		researcher.getPict().setValue(ext);
		researcher.getPict().setMimeType(new MimetypesFileTypeMap().getContentType(itemImage));
	}
	
	/**
	 * 
	 * Load tab icon and copy to default directory.
	 * 
	 * @param researcher
	 * @param rp
	 * @param itemImage MultipartFile to use in webform
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void loadTabIcon(Tab tab, String iconName,
			MultipartFile itemImage) throws IOException, FileNotFoundException {
		String pathImage = ConfigurationManager
		        .getProperty("researcherpage.file.path");
		String ext = itemImage.getOriginalFilename().substring(
		        itemImage.getOriginalFilename().lastIndexOf(".") + 1);
		File dir = new File(pathImage + File.separatorChar + DIRECTORY_TAB_ICON);
		dir.mkdir();
		File file = new File(dir, PREFIX_TAB_ICON + iconName + "." + ext);
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		Utils.bufferedCopy(itemImage.getInputStream(), out);
		out.close();
		tab.setExt(ext);
		tab.setMime(itemImage.getContentType());
	}
	
	/**
	 * Remove tab icon from the server.
	 * 
	 * @param researcher
	 */
	public static void removeTabIcon(Tab tab) {
		File image = new File(ConfigurationManager
		        .getProperty("researcherpage.file.path")
		        + File.separatorChar
		        + DIRECTORY_TAB_ICON
		        + File.separatorChar + PREFIX_TAB_ICON + tab.getId() + "." + tab.getExt());
		image.delete();   
		tab.setExt(null);
		tab.setMime(null);
	}
}
