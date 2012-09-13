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
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.osd.jdyna.model.AnagraficaObject;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;
import it.cilea.osd.jdyna.web.controller.FormAnagraficaController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.Context;

/**
 * This is the base abstract SpringMVC controller for the RPs authority list
 * project. All the other form controller extends this. This abstract controller
 * is responsible to initialize the SpringMVC binder system with the
 * configurated property editors and add common reference data to all the view
 * for the hide/unhide of menus on a role basis.
 * 
 * @author cilea
 * 
 */
public abstract class AFormDynamicRGController<P extends Property<TP>, TP extends PropertiesDefinition, H extends IPropertyHolder<Containable>, T extends Tab<H>, AO extends AnagraficaObject<P, TP>> extends FormAnagraficaController<P, TP, H, T, AO> {

    /**
     * The log4j category
     */
	protected final Log log = LogFactory.getLog(getClass());
	
	private String specificPartPath;

	public String getSpecificPartPath() {
		return specificPartPath;
	}

	public void setSpecificPartPath(String specificPartPath) {
		this.specificPartPath = specificPartPath;
	}

	/**
     * Add common reference data to all the view for the hide/unhide of menus on
     * a role basis.
     */
    protected Map referenceData(HttpServletRequest request) throws Exception
    {
        Map<String, Object> reference = new HashMap<String, Object>();
        
        String id_s = request.getParameter("id");
        Integer id = Integer.parseInt(id_s);
        ResearcherGrant grant = getApplicationService().get(
                    ResearcherGrant.class, id);
        Context context = UIUtil.obtainContext(request);        
        if (AuthorizeManager.isAdmin(context))        
        {
            reference.put("grant_page_menu", new Boolean(true));
            reference.put("grant", grant);         
        }
        
        reference.put("specificPartPath", getSpecificPartPath());      
        return reference;
    }
	

}
