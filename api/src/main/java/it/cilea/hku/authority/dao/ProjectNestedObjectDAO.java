package it.cilea.hku.authority.dao;

import it.cilea.hku.authority.model.dynamicfield.ProjectNestedObject;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedProperty;
import it.cilea.hku.authority.model.dynamicfield.ProjectTypeNestedObject;
import it.cilea.osd.jdyna.dao.NestedObjectDAO;

public interface ProjectNestedObjectDAO extends NestedObjectDAO<ProjectNestedObject, ProjectNestedProperty, ProjectNestedPropertiesDefinition, ProjectTypeNestedObject>
{

}
