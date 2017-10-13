package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Club;

public class ClubDAO extends AbstractSuperDAO<Club> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public ClubDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all clubs stored in the database.
     * 
     * @return list of all clubs stored in the database
     */
	public List<Club> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Club.findAll"));
	}
	
	/**
     * Looks for clubs whose name contains the passed parameter as a substring.
     * 
     * @param name query parameter
     * @return list of clubs whose name contains the passed
     * parameter as a substring.
     */
	public List<Club> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Club.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
     * Method looks for a club by id.
     * 
     * @param id the id of a club we are looking for.
     * @return Optional containing the found club or an empty Optional
     * otherwise
     */
	public Optional<Club> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
