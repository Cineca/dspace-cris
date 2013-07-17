package org.dspace.app.cris.model.jdyna.widget;

import it.cilea.osd.jdyna.widget.WidgetPointer;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.dspace.app.cris.model.jdyna.value.ProjectPointer;

@Entity
@Table(name = "cris_pj_wpointer")
public class WidgetPointerPJ extends WidgetPointer<ProjectPointer>
{

}
