package org.church.rss.file.reader;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.church.rss.feed.domain.Enclosure;
import org.church.rss.feed.domain.RSSFeed;
import org.church.rss.feed.domain.RSSFeedMessage;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 
 * @author Trae
 *
 * This will read the rss feed version 2.0.
 */
public class RSSFeedReader 
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
	static final String CATEGORY = "category";
	static final String LAST_BUILD_DATE = "lastBuildDate";
	static final String COMMENTS = "comments";
	static final String ENCLOSURE = "enclosure";
	static final String URL = "url";
	static final String LENGTH = "length";
	static final String TYPE = "type";
	
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
	
	public RSSFeedReader()
	{
		
	}
	
	public RSSFeed read(InputStream input) throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException
	{
		RSSFeed feed = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(input);
		
		NodeList channels = document.getElementsByTagName(CHANNEL);
		
		for(int i = 0; i < channels.getLength(); i++)
		{
			Node node = channels.item(i);
			
			if(node instanceof Element)
			{
				Element channel = (Element) node;
				feed = new RSSFeed();
				
				NodeList children  = channel.getChildNodes();
				
				for(int j = 0; j < children.getLength(); j++)
				{
					Node child = children.item(j);
					
					if(child instanceof Element)
					{
						Element element = (Element) child;
						
						if(element.getTagName().equals(TITLE))
						{
							feed.setTitle(element.getTextContent());
						}
						
						else if(element.getTagName().equals(DESCRIPTION))
						{
							feed.setDescription(element.getTextContent());
						}
						
						else if(element.getTagName().equals(LINK))
						{
							feed.setLink(element.getTextContent());
						}
						
						else if(element.getTagName().equals(COPYRIGHT))
						{
							feed.setCopyright(element.getTextContent());
						}
						
						else if(element.getTagName().equals(LANGUAGE))
						{
							feed.setLanguage(element.getTextContent());
						}
						
						else if(element.getTagName().equals(CATEGORY))
						{
							feed.setCategory(element.getTextContent());
						}
						
						else if(element.getTagName().equals(MANAGING_EDITOR))
						{
							feed.setManagingEditor(element.getTextContent());
						}
						
						else if(element.getTagName().equals(PUB_DATE))
						{
							String date = element.getTextContent();
							
							if(date != null && !date.equals(""))
							{
								feed.setPublicationDate(dateFormat.parse(element.getTextContent()));
							}
						}
						
						else if(element.getTagName().equals(LAST_BUILD_DATE))
						{
							String date = element.getTextContent();
							
							if(date != null && !date.equals(""))
							{
								feed.setLastBuildDate(dateFormat.parse(element.getTextContent()));
							}
						}
						
						else if(element.getTagName().equals(ITEM))
						{
							RSSFeedMessage message = new RSSFeedMessage();
							NodeList childrenElements = element.getChildNodes();
							feed.addFeedMessage(message);
									
							for(int m = 0; m < childrenElements.getLength(); m++)
							{
								if(childrenElements.item(m) instanceof Element)
								{
									Element childElement = (Element) childrenElements.item(m);
								
									if(childElement.getTagName().equals(TITLE))
									{
										message.setTitle(childElement.getTextContent());
									}
									
									else if(childElement.getTagName().equals(LINK))
									{
										message.setLink(childElement.getTextContent());
									}
									
									else if(childElement.getTagName().equals(DESCRIPTION))
									{
										message.setDescription(childElement.getTextContent());
									}
									
									else if(childElement.getTagName().equals(COMMENTS))
									{
										message.setComment(childElement.getTextContent());
									}
									
									else if(childElement.getTagName().equals(CATEGORY))
									{
										message.setCategory(childElement.getTextContent());
									}
									
									else if(childElement.getTagName().equals(AUTHOR))
									{
										message.setAuthor(childElement.getTextContent());
									}
									
									else if(childElement.getTagName().equals(PUB_DATE))
									{
										String date = childElement.getTextContent();
										
										if(date != null && !date.equals(""))
										{
											message.setPublicationDate(dateFormat.parse(childElement.getTextContent()));
										}
									}
									
									else if(childElement.getTagName().equals(ENCLOSURE))
									{
										Enclosure enclosure = new Enclosure();
										message.setEnclosure(enclosure);
										
										enclosure.setMineType(childElement.getAttribute(TYPE));
										enclosure.setUrl(childElement.getAttribute(URL));
									}
									
								}
							}
						}
					}
				}
			}
		}
		
		return feed;
	}
}
