/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.model.dynamicfield.widget;


import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.osd.jdyna.model.AValue;
import it.cilea.osd.jdyna.value.MultiValue;
import it.cilea.osd.jdyna.widget.WidgetCombo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="model_rp_jdyna_widgetcombo")
@NamedQueries( {  
	@NamedQuery(name = "WidgetComboRPAdditional.findAll", query = "from WidgetComboRPAdditional order by id")
 } )
public class WidgetComboRPAdditional extends
		WidgetCombo<RPProperty, RPPropertiesDefinition> {
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinTable(name="model_rp_jdyna_widgetcombo2subtipprop")	
	private List<RPPropertiesDefinition> sottoTipologie;

	@Override
	public AValue getInstanceValore() {
		return new MultiValue();
	}
	
	@Override
	public Class getValoreClass() {
		return MultiValue.class;
	}

	@Override
	public List<RPPropertiesDefinition> getSottoTipologie() {
		//NOTE: Don't add sort here (error on save from WEBUI side-effect)
		return sottoTipologie;
	}
	
	public WidgetComboRPAdditional() {
		sottoTipologie = new LinkedList<RPPropertiesDefinition>();
	}
}
