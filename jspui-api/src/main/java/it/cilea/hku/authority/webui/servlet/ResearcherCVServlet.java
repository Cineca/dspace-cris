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

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedFieldLocalOrRemoteFile;
import it.cilea.hku.authority.service.ApplicationService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.app.webui.servlet.DSpaceServlet;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.core.Utils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ResearcherCVServlet extends DSpaceServlet {
	/** log4j category */
	private static Logger log = Logger.getLogger(ResearcherCVServlet.class);

	@Override
	protected void doDSGet(Context context, HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		ServletContext ctx = getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(ctx);

		ApplicationService service = (ApplicationService) applicationContext
				.getBean("applicationService");

		String idString = req.getPathInfo();
		String[] pathInfo = idString.split("/", 2);
		String authorityKey = pathInfo[1];

		ResearcherPage rp = service.getResearcherByAuthorityKey(authorityKey);

		if (rp == null // rp doesn't exist
				|| (rp.getCv().getVisibility() == 0 && // cv is hide
				!(AuthorizeManager.isAdmin(context) || // the user logged in is
														// not an admin
				(context.getCurrentUser() != null && rp.getStaffNo().equals(
						context.getCurrentUser().getNetid()) // the user logged
																// in is not the
																// rp owner
				)))) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		String rpName = rp.getFullName();
		Integer id = rp.getId();

		String contentType = "";
		String ext = "";
		String remoteUrl = "";

		boolean found = false;
		RestrictedFieldLocalOrRemoteFile cv = rp.getCv();
		ext = cv.getValue();
		remoteUrl = cv.getRemoteUrl();
		contentType = cv.getMimeType();
		if (StringUtils.isNotEmpty(remoteUrl)) {
			found = true;
			response.sendRedirect(remoteUrl);
		} else if (StringUtils.isNotEmpty(cv.getMimeType())
				&& StringUtils.isNotEmpty(cv.getValue())) {
			File image = new File(
					ConfigurationManager
							.getProperty("researcherpage.file.path")
							+ File.separatorChar
							+ authorityKey
							+ File.separatorChar + authorityKey + "-CV." + ext);
			InputStream is = null;
			try {
				is = new FileInputStream(image);
				context.complete();
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + authorityKey + "-CV-"
								+ rpName + "." + ext + "\"");
				response.setContentType(contentType);

				// Response length
				response.setHeader("Content-Length",
						String.valueOf(image.length()));

				Utils.bufferedCopy(is, response.getOutputStream());
				is.close();
				response.getOutputStream().flush();
				found = true;
			} finally {
				if (is != null) {
					is.close();
				}
			}

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
	}
}
