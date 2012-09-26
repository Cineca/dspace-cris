package it.cilea.hku.authority.dao;

import it.cilea.hku.authority.model.dynamicfield.RPNestedObject;
import it.cilea.hku.authority.model.dynamicfield.RPNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPNestedProperty;
import it.cilea.hku.authority.model.dynamicfield.RPTypeNestedObject;
import it.cilea.osd.jdyna.dao.NestedObjectDAO;

public interface RPNestedObjectDAO extends NestedObjectDAO<RPNestedObject, RPNestedProperty, RPNestedPropertiesDefinition, RPTypeNestedObject>
{

}
