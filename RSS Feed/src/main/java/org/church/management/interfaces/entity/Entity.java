package org.church.management.interfaces.entity;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable
{
	public ID getId();
	public void setId(ID id);

}
