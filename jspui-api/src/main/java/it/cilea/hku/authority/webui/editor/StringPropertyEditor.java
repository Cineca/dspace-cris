/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.editor;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Property editor needs to the Spring Binder framework to work with the
 * {@link java.lang.String}
 * 
 * @see PropertyEditor 
 * @author cilea
 * 
 */
public class StringPropertyEditor extends PropertyEditorSupport {

	private final static Log log = LogFactory.getLog(StringPropertyEditor.class);

	   /** Model Class */
    private Class clazz;

    public StringPropertyEditor(Class clazz) {
        this.clazz = clazz;
    }
    
	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		log.debug("call StringPropertyEditor - setAsText text: " + text);		
		if (text == null || text.trim().equals("")) {
			setValue(null);
		} else {			
			setValue(text);
		}

	}

	@Override
	public String getAsText() {
		log.debug("chiamato StringPropertyEditor - getAsText");
		Object value = getValue();
		if(value==null) {
			return "";
		}
		return value.toString();
	}


}
