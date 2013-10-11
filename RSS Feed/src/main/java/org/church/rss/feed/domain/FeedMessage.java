package org.church.rss.feed.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


@Entity
@Table(name="feed_messages")
public class FeedMessage implements Serializable, org.church.management.interfaces.entity.Entity<Integer>
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	//Required. Defines the title of the item
	@Column(name="title", length=100, nullable=false)
	private String title;
	
	//Required. Describes the item
	@Column(name="description", length=500, nullable=false)
	private String description;
	
	//Required. Defines the hyperlink to the item
	@Column(name="link", length=300, nullable=false)
	private String link;
	
	//Allows a media file to be included with the item
	@ManyToOne(fetch=FetchType.EAGER)
	private Enclosure enclosure;
	
	//Optional. Specifies the e-mail address to the author of the item
	@Column(name="author", length=50, nullable=true)
	private String author;
	
	//Optional. Defines one or more categories the item belongs to
	@Column(name="category", length=50, nullable=true)
	private String category;
	
	//Optional. Allows an item to link to comments about that item
	@Column(name="comment", length=1000, nullable=true)
	private String comment;
	
	//Defines the last-publication date for the item
	@Column(name="publication_date", nullable=true)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date publicationDate;
	
	public FeedMessage()
	{
		title = "";
		comment = "";
		author = "";
		category = "";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comments) {
		this.comment = comments;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Enclosure getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(Enclosure enclosure) {
		this.enclosure = enclosure;
	}

	public boolean equals(Object object)
	{
		if(object instanceof FeedMessage)
		{
			FeedMessage that = (FeedMessage) object;
			
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
		return new HashCodeBuilder().append(title).append(description).append(link).toHashCode();
	}
}
