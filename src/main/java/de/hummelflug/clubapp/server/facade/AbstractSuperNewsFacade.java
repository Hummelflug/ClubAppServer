package de.hummelflug.clubapp.server.facade;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.io.IOUtils;

import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;

public abstract class AbstractSuperNewsFacade {
	
	public static final Integer NEWSCOUNT = AbstractSuperNewsContentFacade.NEWSCOUNT;
	
	private final NewsDAO newsDAO;
	
	public AbstractSuperNewsFacade(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}
	
	protected News addNewsReader(User user, Long newsId) {
		if (user != null && newsId != null) {
			Optional<News> newsOptional = findNewsById(newsId);
			if (newsOptional.isPresent()) {
				News news = newsOptional.get();
				news.getNewsReaders().add(user.getId());
				return newsDAO.update(news);
			}
		}
		throw new WebApplicationException(400);
	}
	
	protected News addNewsImage(Long newsId, InputStream fileInputStream) {
		if (newsId != null && fileInputStream != null) {
			Optional<News> newsOptional = findNewsById(newsId);
			if (newsOptional.isPresent()) {
				
				/** Receive image **/
				byte[] image = null;
				try {
					image = IOUtils.toByteArray(fileInputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				News news = newsOptional.get();
				news.setImage(image);
				return newsDAO.update(news);
			}
		}
		throw new WebApplicationException(400); 
	}
	
	protected News createNews(User user, News news) {
		if (user != null && news != null && news.getTitle() != null && news.getDescription() != null) {
			return newsDAO.insert(new News(user.getId(), news.getTitle(), news.getDescription()));
		}
		throw new WebApplicationException(400);
	}

	public List<News> findAllNews() {
		return newsDAO.findAll();
	}
	
	public List<News> findNews(Set<Long> news, NewsFilterOption newsFilterOption) {
		if (news != null && newsFilterOption != null) {
			if (newsFilterOption.equals(NewsFilterOption.All)) {
				return newsDAO.findNewsByIds(news);
			} else if (newsFilterOption.equals(NewsFilterOption.RECENT)) {
				return newsDAO.findRecentNewsByIds(news, NEWSCOUNT);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public Optional<News> findNewsById(Long id) {
		return newsDAO.findById(id);
	}

}
