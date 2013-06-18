package org.dspace.app.cris.ws.marshaller;

import java.util.List;
import org.jdom.Element;

public interface Marshaller<T>
{
    public Element buildResponse(List<T> results, long start, long tot, String type,
            String[] splitProjection, boolean showHiddenMetadata, String nameRoot);
}
