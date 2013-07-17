package org.dspace.app.cris.dao;

import it.cilea.osd.common.dao.PaginableObjectDao;
import it.cilea.osd.jdyna.dao.TypeDaoSupport;
import it.cilea.osd.jdyna.model.AType;
import it.cilea.osd.jdyna.model.PropertiesDefinition;

public interface DynamicObjectTypeDao<T extends AType<PD>, PD extends PropertiesDefinition> extends TypeDaoSupport<T, PD>, PaginableObjectDao<T, Integer>
{

}
