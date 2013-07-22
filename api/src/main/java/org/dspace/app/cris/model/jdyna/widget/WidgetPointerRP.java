package org.dspace.app.cris.model.jdyna.widget;

import it.cilea.osd.jdyna.widget.WidgetPointer;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.dspace.app.cris.model.jdyna.value.RPPointer;

@Entity
@Table(name = "cris_rp_wpointer")
public class WidgetPointerRP extends WidgetPointer<RPPointer>
{

}
