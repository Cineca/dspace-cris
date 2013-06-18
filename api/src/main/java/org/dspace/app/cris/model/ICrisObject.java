package org.dspace.app.cris.model;

import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.common.model.Identifiable;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

public interface ICrisObject<P extends Property<TP>, TP extends PropertiesDefinition>
        extends HasTimeStampInfo, UUIDSupport, Identifiable,
        AnagraficaSupport<P, TP>
{

}
