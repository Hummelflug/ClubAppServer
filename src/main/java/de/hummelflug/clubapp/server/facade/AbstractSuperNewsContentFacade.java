package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.db.NewsContentDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;

public abstract class AbstractSuperNewsContentFacade {

	public static final Integer NEWSCOUNT = 3;

	private final NewsContentDAO newsContentDAO;
	
	public AbstractSuperNewsContentFacade(NewsContentDAO newsContentDAO) {
		this.newsContentDAO = newsContentDAO;
	}
	
	public List<NewsContent> findAllNewsContent() {
		return newsContentDAO.findAll();
	}
	
	protected List<NewsContent> findNewsContent(Set<Long> news, NewsFilterOption newsFilterOption) {
		if (news != null && newsFilterOption != null) {
			if (newsFilterOption.equals(NewsFilterOption.All)) {
				return newsContentDAO.findNewsContentByIds(news);
			} else if (newsFilterOption.equals(NewsFilterOption.RECENT)) {
				return newsContentDAO.findRecentNewsContentByIds(news, NEWSCOUNT);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public Optional<NewsContent> findNewsContentById(Long id) {
		return newsContentDAO.findById(id);
	}
	
}
