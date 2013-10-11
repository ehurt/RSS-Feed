package org.church.rss.file.writer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.church.rss.feed.domain.Enclosure;
import org.church.rss.feed.domain.Feed;
import org.church.rss.feed.domain.FeedMessage;
import org.church.rss.feed.domain.FeedMessageItem;


public class RSSFeedWriter 
{
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String MANAGING_EDITOR = "managingEditor";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";
	static final String CATEGORY = "category";
	static final String LAST_BUILD_DATE = "lastBuildDate";
	static final String COMMENTS = "comments";
	static final String ENCLOSURE = "enclosure";
	static final String URL = "url";
	static final String LENGTH = "length";
	static final String TYPE = "type";
	
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
    
    public RSSFeedWriter()
    {
    	
    }
    
	public void write(OutputStream output, Feed feed) throws IOException, XMLStreamException
	{
	    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	    XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(output);
	    
	    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    XMLEvent end = eventFactory.createDTD("\n");
	    XMLEvent tab = eventFactory.createDTD("\t");
	    
	    StartDocument startDocument = eventFactory.createStartDocument();
	    eventWriter.add(startDocument);

	    eventWriter.add(end);
	    
	    StartElement rssStart = eventFactory.createStartElement("", "", "rss");
	    eventWriter.add(rssStart);
	    eventWriter.add(eventFactory.createAttribute("version", "2.0"));
	    eventWriter.add(end);
	    
	    eventWriter.add(eventFactory.createStartElement("", "", "channel"));
	    eventWriter.add(end);
	    
	    writeChannelTags(eventWriter, feed);
	    
	    int count = 0;
	    for(FeedMessageItem message : feed.getFeedMessages())
	    {
	    	if(count != 0)
	    	{
	    		eventWriter.add(end);
	    	}
	    	
	    	count++;
	    	eventWriter.add(tab);
	    	eventWriter.add(eventFactory.createStartElement("", "", "item"));
	        eventWriter.add(end);
	    	writeFeedItems(eventWriter, message.getId().getMessage());
	    	eventWriter.add(tab);
	    	eventWriter.add(eventFactory.createEndElement("", "", "item"));
	    }
	    
	    eventWriter.add(end);
	    eventWriter.add(eventFactory.createEndElement("", "", "channel"));
	    eventWriter.add(end);
	    eventWriter.add(eventFactory.createEndElement("", "", "rss"));

	    eventWriter.add(end);

	    eventWriter.add(eventFactory.createEndDocument());

	    eventWriter.close();
	}
	
	private void writeChannelTags(XMLEventWriter eventWriter, Feed feed) throws XMLStreamException
	{
		createNode(eventWriter, TITLE, feed.getTitle());
		
		createNode(eventWriter, LINK, feed.getLink());
		
		createNode(eventWriter, DESCRIPTION, feed.getDescription());
		
		if(feed.getManagingEditor() != null && !feed.getManagingEditor().equals(""))
		{
			createNode(eventWriter, MANAGING_EDITOR, feed.getManagingEditor());
		}
		
		if(feed.getCategory() != null && !feed.getCategory().equals(""))
		{
			createNode(eventWriter, CATEGORY, feed.getCategory());
		}
		
		if(feed.getCopyright() != null && !feed.getCopyright().equals(""))
		{
			createNode(eventWriter, COPYRIGHT, feed.getCopyright());
		}
		
		if(feed.getLanguage() != null && !feed.getLanguage().equals(""))
		{
			createNode(eventWriter, LANGUAGE, feed.getLanguage());
		}
		
		if(feed.getPublicationDate() != null)
		{
			createNode(eventWriter, PUB_DATE, dateFormat.format(feed.getPublicationDate()));
		}
		
		if(feed.getLastBuildDate() != null)
		{
			createNode(eventWriter, LAST_BUILD_DATE, dateFormat.format(feed.getLastBuildDate()));
		}
	}
	
	private void writeFeedItems(XMLEventWriter eventWriter, FeedMessage message) throws XMLStreamException
	{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent tab = eventFactory.createDTD("\t");
		XMLEvent end = eventFactory.createDTD("\n");
		
		eventWriter.add(tab);
		createNode(eventWriter, TITLE, message.getTitle());
		
		eventWriter.add(tab);
		createNode(eventWriter, LINK, message.getLink());
		
		eventWriter.add(tab);
		createNode(eventWriter, DESCRIPTION, message.getDescription());
		
		if(message.getAuthor() != null && !message.getAuthor().equals(""))
		{
			eventWriter.add(tab);
			createNode(eventWriter, AUTHOR, message.getAuthor());
		}
		
		if(message.getCategory() != null && !message.getCategory().equals(""))
		{
			eventWriter.add(tab);
			createNode(eventWriter, CATEGORY, message.getCategory());
		}
		
		if(message.getComment() != null && !message.getComment().equals(""))
		{
			eventWriter.add(tab);
			createNode(eventWriter, COMMENTS, message.getComment());
		}
		
		if(message.getPublicationDate() != null)
		{
			eventWriter.add(tab);
			createNode(eventWriter, PUB_DATE, dateFormat.format(message.getPublicationDate()));
		}
		
		if(message.getEnclosure() != null)
		{
			eventWriter.add(tab);
			eventWriter.add(tab);
			Enclosure enclosure = message.getEnclosure();
			File file = new File(enclosure.getFilePath());
			
			StartElement startElement = eventFactory.createStartElement("", "", ENCLOSURE);
			eventWriter.add(startElement);
			
			Attribute url = eventFactory.createAttribute(URL, enclosure.getUrl());
			eventWriter.add(url);
			
			Attribute type = eventFactory.createAttribute(TYPE, enclosure.getMineType());
			eventWriter.add(type);
			
			Attribute length = eventFactory.createAttribute(LENGTH, file.length()+"");
			eventWriter.add(length);
			
			EndElement endElement = eventFactory.createEndElement("", "", ENCLOSURE);
			eventWriter.add(endElement);
			eventWriter.add(end);
		}
	}
	
	private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException
	{
	   XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	   XMLEvent end = eventFactory.createDTD("\n");
	   XMLEvent tab = eventFactory.createDTD("\t");
	   
	   eventWriter.add(tab);
	   StartElement startElement = eventFactory.createStartElement("", "", name);
	   eventWriter.add(startElement);
	   
	   Characters characters = eventFactory.createCharacters(value);
	   eventWriter.add(characters);
	   
	   EndElement endElement = eventFactory.createEndElement("", "", name);
	   eventWriter.add(endElement);
	   eventWriter.add(end);
	}
}
