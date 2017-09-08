package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Tournament;

public class TournamentDAO extends AbstractSuperDAO<Tournament> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public TournamentDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all tournaments stored in the database.
     * 
     * @return list of all tournaments stored in the database
     */
	public List<Tournament> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Tournament.findAll"));
	}
	
	/**
     * Looks for tournaments whose name contains the passed
     * parameter as a substring.
     * 
     * @param name query parameter
     * @return list of tournaments whose name contains the passed
     * parameter as a substring.
     */
	public List<Tournament> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Tournament.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
     * Method looks for a tournament by id.
     * 
     * @param id the id of a tournament we are looking for.
     * @return Optional containing the found tournament or an empty Optional
     * otherwise
     */
	public Optional<Tournament> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
