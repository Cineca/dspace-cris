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
package it.cilea.hku.authority.webui.servlet;

import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dspace.app.webui.util.JSPManager;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.core.LogManager;
import org.dspace.core.Utils;
import org.dspace.storage.rdbms.DatabaseManager;
import org.dspace.storage.rdbms.TableRow;

public class ResearcherTabImageServlet extends HttpServlet
{
    /** log4j category */
    private static Logger log = Logger.getLogger(ResearcherTabImageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException
    {

        String idString = req.getPathInfo();
        String[] pathInfo = idString.split("/", 2);
        String tabId = pathInfo[1];  

        Context context = null;
        String contentType = "";
        String ext = "";
        try
        {
            context = new Context();
            TableRow row = DatabaseManager.find(context,
                    "model_jdyna_tab", Integer.parseInt(tabId));
            ext = row.getStringColumn("ext");
            contentType = row.getStringColumn("mime");
            if (ext != null && !ext.isEmpty() && contentType != null && !contentType.isEmpty())
            {
                File image = new File(ConfigurationManager
                        .getProperty("researcherpage.file.path")
                        + File.separatorChar + ResearcherPageUtils.DIRECTORY_TAB_ICON + File.separatorChar + ResearcherPageUtils.PREFIX_TAB_ICON + tabId + "." + ext);
                
            	InputStream is = null;
				try {
					is = new FileInputStream(image);

                response.setContentType(contentType);

                // Response length
                response.setHeader("Content-Length", String.valueOf(image
                        .length()));

                Utils.bufferedCopy(is, response.getOutputStream());
                is.close();
                response.getOutputStream().flush();
				} finally {
					if (is != null) {
						is.close();
					}
				}				
            }
        }
        catch (SQLException se)
        {
            // For database errors, we log the exception and show a suitably
            // apologetic error
            log.warn(LogManager.getHeader(context, "database_error", se
                    .toString()), se);

            // Also email an alert
            UIUtil.sendAlert(req, se);

            JSPManager.showInternalError(req, response);
        }
        finally
        {
            // Abort the context if it's still valid
            if ((context != null) && context.isValid())
            {
                context.abort();
            }
        }

    }
}
