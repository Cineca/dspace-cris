package org.dspace.app.cris.model;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public class RestrictedFieldWithLock extends RestrictedField
{
	private Integer lock;

	public RestrictedFieldWithLock()
	{
		super();
	}

	public Integer getLock()
	{
		return lock;
	}

	public void setLock(Integer lock)
	{
		this.lock = lock == null ? new Integer(0) : lock;
	}
}
