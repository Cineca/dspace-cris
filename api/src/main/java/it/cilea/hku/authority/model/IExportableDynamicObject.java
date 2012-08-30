/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model;

import it.cilea.osd.jdyna.model.AnagraficaObject;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

public interface IExportableDynamicObject<TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>>
{

    String getNamePublicIDAttribute();

    String getValuePublicIDAttribute();

    String getNameIDAttribute();

    String getValueIDAttribute();

    String getNameBusinessIDAttribute();

    String getValueBusinessIDAttribute();

    String getNameTypeIDAttribute();

    String getValueTypeIDAttribute();

    AO getDynamicField();

    String getNameSingleRowElement();

}
