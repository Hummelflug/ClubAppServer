package de.hummelflug.clubapp.server.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.NewsContent;
import io.dropwizard.hibernate.AbstractDAO;

public class NewsContentDAO extends AbstractDAO<NewsContent> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public NewsContentDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all news contents stored in the database.
     * 
     * @return list of all news contents stored in the database
     */
	public List<NewsContent> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.NewsContent.findAll"));
	}
	
	/**
     * Method returns all news contents if ids contains the id of news content sorted by date.
     * 
     * @param ids of news contents we are looking for.
     * @return list of all news contents stored in the database
     */
	public List<NewsContent> findNewsContentByIds(Set<Long> ids) {
		if (ids.size() == 0) {
			return new ArrayList<NewsContent>();
		}
		return list(namedQuery("de.hummelflug.clubapp.server.core.NewsContent.findByIds")
				.setParameter("ids", ids));
	}
	
	/**
     * Method looks for a news content by id.
     * 
     * @param id the id of a news content we are looking for.
     * @return Optional containing the found news content or an empty Optional
     * otherwise
     */
	public Optional<NewsContent> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
	/**
     * Method returns last n news contents if ids contains the id of news content.
     * 
     * @param ids of news contents we are looking for.
     * @param count count of news contents which will be return
     * @return list of all news contents stored in the database
     */
	public List<NewsContent> findRecentNewsContentByIds(Set<Long> ids, Integer count) {
		if (ids.size() == 0) {
			return new ArrayList<NewsContent>();
		}
		return list(namedQuery("de.hummelflug.clubapp.server.core.NewsContent.findByIds")
				.setParameter("ids", ids)
				.setFirstResult(0)
				.setMaxResults(count));
	}
	
}
