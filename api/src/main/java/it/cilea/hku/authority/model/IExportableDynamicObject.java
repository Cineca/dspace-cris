/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
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
