package org.church.rss.feed.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.church.rss.feed.domain.RSSFeed;
import org.church.rss.feed.domain.dao.GenericDao;
import org.church.rss.file.writer.RSSFeedWriter;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.HttpRequestHandler;

public class RSSFeedXMLServlet implements Serializable, HttpRequestHandler
{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RSSFeedXMLServlet.class);
	
	@Autowired
	private GenericDao<RSSFeed, Integer> rssFeedDao;

	//pattern rss/feed/name
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Integer id = -1;
		
		try
		{
			if(request.getParameter("id") == null)
			{
				response.setStatus(404);
				return;
			}
			
			id = Integer.parseInt(request.getParameter("id"));
			RSSFeed feed = rssFeedDao.load(id);
		
			feed.getFeedMessages().size();
			RSSFeedWriter writer = new RSSFeedWriter();
			response.setContentType("application/rss+xml;charset=utf-8");
			response.setHeader("Content-disposition", "attachment; filename="+feed.getTitle()+".xml");
			writer.write(response.getOutputStream(), feed);
			
		}
		catch(Exception e)
		{
			logger.error("RSSFeedXMLServlet.handleRequest()- Failed to output the rss feed: "+id+" to client.", e);
			response.setStatus(500);
		}
	}
	
	public GenericDao<RSSFeed, Integer> getRSSFeedDao() {
		return rssFeedDao;
	}

	public void setRSSFeedDao(GenericDao<RSSFeed, Integer> feedDao) {
		this.rssFeedDao = feedDao;
	}
}
