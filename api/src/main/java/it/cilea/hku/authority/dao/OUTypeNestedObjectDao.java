package it.cilea.hku.authority.dao;

import it.cilea.osd.common.dao.PaginableObjectDao;
import it.cilea.osd.jdyna.dao.TypeDaoSupport;
import it.cilea.osd.jdyna.model.ANestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ATypeNestedObject;

public interface OUTypeNestedObjectDao<NTP extends ANestedPropertiesDefinition, TTP extends ATypeNestedObject<NTP>> extends TypeDaoSupport<TTP, NTP>, PaginableObjectDao<TTP, Integer>
{

}
