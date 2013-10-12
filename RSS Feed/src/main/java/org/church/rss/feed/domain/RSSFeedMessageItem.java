package org.church.rss.feed.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.church.domain.keys.FeedMessageKey;

@Entity
@Table(name="feed_message_items")
public class RSSFeedMessageItem implements org.church.management.interfaces.entity.Entity<FeedMessageKey>
{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FeedMessageKey id;
	
	public RSSFeedMessageItem()
	{
		id = new FeedMessageKey();
	}

	public FeedMessageKey getId() {
		return id;
	}

	public void setId(FeedMessageKey id) {
		this.id = id;
	}
	
	public boolean equals(Object object)
	{
		if(object instanceof RSSFeedMessageItem)
		{
			RSSFeedMessageItem that = (RSSFeedMessageItem) object;
			
			if(that == this)
			{
				return true;
			}
			
			return new EqualsBuilder().append(this.id, that.id).isEquals();
		}
		
		return false;
	}
	
	public int hashCode()
	{
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
