package org.church.rss.feed.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


@Entity
@Table(name="enclosures")
public class Enclosure implements org.church.management.interfaces.entity.Entity<Integer>
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name="file_name", length=100, nullable=false)
	private String fileName;
	
	@Column(name="file_path", length=300, nullable=false)
	private String filePath;
	
	@Column(name="mine_type", length=50, nullable=false)
	private String mineType;
	
	@Column(name="url", length=300, nullable=false)
	private String url;
	
	public Enclosure()
	{
		this.fileName = "";
		this.filePath = "";
		this.mineType = "";
		this.url = "";
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;	
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMineType() {
		return mineType;
	}

	public void setMineType(String mineType) {
		this.mineType = mineType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean equals(Object object)
	{
		if(object instanceof Enclosure)
		{
			Enclosure that = (Enclosure) object;
			
			if(that == this)
			{
				return true;
			}
			
			return new EqualsBuilder().append(that.id, this.id).isEquals();
		}
		
		return false;
	}

	public int hashCode()
	{
		return new HashCodeBuilder().append(fileName).append(url).toHashCode();
	}
}
