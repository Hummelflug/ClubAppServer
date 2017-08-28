package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Coach;
import io.dropwizard.hibernate.AbstractDAO;

public class CoachDAO extends AbstractDAO<Coach> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public CoachDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all coaches stored in the database.
     * 
     * @return list of all coaches stored in the database
     */
	public List<Coach> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Coach.findAll"));
	}
	
	/**
     * Looks for coaches whose first or last name contains the passed
     * parameter as a substring.
     * 
     * @param name query parameter
     * @return list of coaches whose first or last name contains the passed
     * parameter as a substring.
     */
	public List<Coach> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Coach.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
     * Method looks for a coach by id.
     * 
     * @param id the id of a coach we are looking for.
     * @return Optional containing the found coach or an empty Optional
     * otherwise
     */
	public Optional<Coach> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
