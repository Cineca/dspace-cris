/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.jdyna;

import it.cilea.osd.jdyna.model.ANestedObjectWithTypeSupport;
import it.cilea.osd.jdyna.model.ANestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ANestedProperty;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import javax.persistence.MappedSuperclass;

import org.dspace.app.cris.model.ICrisObject;

@MappedSuperclass
public abstract class ACrisNestedObject<P extends ANestedProperty<TP>, TP extends ANestedPropertiesDefinition, PP extends Property<PTP>, PTP extends PropertiesDefinition>
        extends ANestedObjectWithTypeSupport<P, TP, PP, PTP> implements ICrisObject<P, TP>
{
    public abstract int getType();
    
    public int getID() {
        return super.getId();
    }  

}
