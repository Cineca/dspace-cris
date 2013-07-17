package org.dspace.app.cris.model.jdyna.widget;

import it.cilea.osd.jdyna.widget.WidgetPointer;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.dspace.app.cris.model.jdyna.value.DOPointer;

@Entity
@Table(name = "cris_do_wpointer")
public class WidgetPointerDO extends WidgetPointer<DOPointer>
{

    @Lob
    private String filterExtended;

    public void setFilterExtended(String filterExtended)
    {
        this.filterExtended = filterExtended;
    }

    public String getFilterExtended()
    {
        return filterExtended;
    }
    
    
    
}
