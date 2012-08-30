/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.activation.MimetypesFileTypeMap;
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

public class RPFileServiceServlet extends HttpServlet {
	/** log4j category */
	private static Logger log = Logger.getLogger(RPFileServiceServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {

		String idString = req.getPathInfo();
		String[] pathInfo = idString.split("/", 2);		
		
		String folder = pathInfo[1];
		String fileName = req.getParameter("filename");
		Context context = null;
		
		try {
			context = new Context();

			
				File image = new File(
						ConfigurationManager
								.getProperty("researcherpage.file.path")
								+ File.separatorChar
								+ folder
								+ File.separatorChar + fileName);

				InputStream is = null;
				try {
					is = new FileInputStream(image);
					response.setContentType(new MimetypesFileTypeMap().getContentType(image));

					// Response length
					response.setHeader("Content-Length",
							String.valueOf(image.length()));

					Utils.bufferedCopy(is, response.getOutputStream());
					is.close();
					response.getOutputStream().flush();
				} finally {
					if (is != null) {
						is.close();
					}
				}
			
		} catch (SQLException se) {
			// For database errors, we log the exception and show a suitably
			// apologetic error
			log.warn(
					LogManager.getHeader(context, "database_error",
							se.toString()), se);

			// Also email an alert
			UIUtil.sendAlert(req, se);

			JSPManager.showInternalError(req, response);
		} finally {
			// Abort the context if it's still valid
			if ((context != null) && context.isValid()) {
				context.abort();
			}
		}

	}
}
