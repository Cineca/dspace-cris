package org.dspace.app.cris.discovery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;
import org.dspace.content.Bitstream;
import org.dspace.content.Bundle;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.discovery.SolrServiceIndexPlugin;

public class BitstreamSolrIndexer implements SolrServiceIndexPlugin
{
    private static final Logger log = Logger
            .getLogger(BitstreamSolrIndexer.class);

    @Override
    public void additionalIndex(Context context, DSpaceObject dso,
            SolrInputDocument document)
    {
        if (!(dso instanceof Item))
            return;
        Item item = (Item) dso;
        try
        {
            Bundle[] bb = item.getBundles();
            for (Bundle b : bb)
            {

                List<String> bitstreams = new ArrayList<String>();
                for (Bitstream bitstream : b.getBitstreams())
                {
                    bitstreams.add(bitstream.getType() + "-"
                            + bitstream.getID());
                }
                document.addField(b.getName() + "_mvuntokenized", bitstreams);

            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

}
