package org.church.domain.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.church.rss.feed.domain.RSSFeed;
import org.church.rss.feed.domain.RSSFeedMessage;

@Embeddable
public class FeedMessageKey implements Serializable
{

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="feed_id")
	private RSSFeed feed;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="message_id")
	private RSSFeedMessage message;
	
	public FeedMessageKey()
	{
		this.setFeed(null);
		this.setMessage(null);
	}

	public RSSFeedMessage getMessage() {
		return message;
	}

	public void setMessage(RSSFeedMessage message) {
		this.message = message;
	}

	public RSSFeed getFeed() {
		return feed;
	}

	public void setFeed(RSSFeed feed) {
		this.feed = feed;
	}
	
	public boolean equals(Object object)
	{
		if(object instanceof FeedMessageKey)
		{
			FeedMessageKey that = (FeedMessageKey) object;
			
			if(that == this)
			{
				return true;
			}
			
			return new EqualsBuilder().append(that.message, this.message).append(that.feed, this.feed).isEquals();
		}
		
		return false;
	}
}
