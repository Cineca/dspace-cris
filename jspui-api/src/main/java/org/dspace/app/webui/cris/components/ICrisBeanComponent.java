package org.dspace.app.webui.cris.components;

import it.cilea.osd.jdyna.components.IBeanSubComponent;

public interface ICrisBeanComponent extends IBeanSubComponent
{
    String getFacetQuery();
    String getFacetField();
}
