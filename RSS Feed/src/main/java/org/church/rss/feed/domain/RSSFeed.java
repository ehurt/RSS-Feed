package org.church.rss.feed.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.church.domain.keys.FeedMessageKey;



/**
 * 
 * @author Trae
 *
 * This class will hold the 
 * information for the rss feed.
 */
//The channel tag is used to describe the feed.
@Entity
@Table(name="feeds")
public class RSSFeed implements Serializable, org.church.management.interfaces.entity.Entity<Integer>
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="title", length=100, nullable=false)
	private String title;
	
	@Column(name="description", length=500, nullable=false)
	private String description;
	
	@Column(name="link", length=300, nullable=false)
	private String link;
	
	@Column(name="publication_date", nullable=true)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date publicationDate;
	
	@Column(name="category", length=50, nullable=true)
	private String category;
	
	@Column(name="language", length=6, nullable=true)
	private String language;
	
	@Column(name="copyright", length=75, nullable=true)
	private String copyright;
	
	@Column(name="last_build_date", nullable=true)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date lastBuildDate;
	
	//email
	@Column(name="managing_editor", nullable=true, length=75)
	private String managingEditor;
	
	//Each <item> element defines an article or "story" in the RSS feed.
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="feed",orphanRemoval=true)
	private List<RSSFeedMessage> feedMessages;
	
	public RSSFeed()
	{
		this.setFeedMessages(new ArrayList<RSSFeedMessage>());
	}
	
	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
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

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public List<RSSFeedMessage> getFeedMessages() {
		return feedMessages;
	}

	public void setFeedMessages(List<RSSFeedMessage> feedMessages) {
		this.feedMessages = feedMessages;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	
	public String getManagingEditor() {
		return managingEditor;
	}

	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public void addFeedMessage(RSSFeedMessage message)
	{
		message.setFeed(this);
		feedMessages.add(message);
	}
	
	public void removeFeedMessage(RSSFeedMessage message){
		this.feedMessages.remove(message);
	}
	
	public boolean equals(Object object)
	{
		if(object instanceof RSSFeed)
		{
			RSSFeed that = (RSSFeed) object;
			
			if(that == this)
			{
				return true;
			}
			
			return new EqualsBuilder().append(this.title, that.title).isEquals();
		}
		
		return false;
	}
	
	public int hashCode()
	{
		return new HashCodeBuilder().append(title).append(description).append(link).toHashCode();
	}
}
