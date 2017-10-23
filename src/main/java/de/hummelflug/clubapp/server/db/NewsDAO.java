package de.hummelflug.clubapp.server.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.News;

public class NewsDAO extends AbstractSuperDAO<News> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public NewsDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
     * Method returns all news stored in the database.
     * 
     * @return list of all news stored in the database
     */
	public List<News> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.News.findAll"));
	}
	
	/**
     * Method looks for a news by id.
     * 
     * @param id the id of a news we are looking for.
     * @return Optional containing the found news or an empty Optional
     * otherwise
     */
	public Optional<News> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
	/**
     * Method returns all news if ids contains the id of news sorted by date.
     * 
     * @param ids of news we are looking for.
     * @return list of all news stored in the database
     */
	public List<News> findNewsByIds(Set<Long> ids) {
		if (ids.size() == 0) {
			return new ArrayList<News>();
		}
		return list(namedQuery("de.hummelflug.clubapp.server.core.News.findByIds")
				.setParameter("ids", ids));
	}

	/**
     * Method returns last n news if ids contains the id of news.
     * 
     * @param ids of news we are looking for.
     * @param count count of news which will be return
     * @return list of all news stored in the database
     */
	public List<News> findRecentNewsByIds(Set<Long> ids, Integer count) {
		if (ids.size() == 0) {
			return new ArrayList<News>();
		}
		return list(namedQuery("de.hummelflug.clubapp.server.core.News.findByIds")
				.setParameter("ids", ids)
				.setFirstResult(0)
				.setMaxResults(count));
	}
}
