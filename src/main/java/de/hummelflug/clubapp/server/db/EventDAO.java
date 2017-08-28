package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Event;
import io.dropwizard.hibernate.AbstractDAO;

public class EventDAO extends AbstractDAO<Event> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public EventDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all events stored in the database.
     * 
     * @return list of all events stored in the database
     */
	public List<Event> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Event.findAll"));
	}
	
	/**
     * Method looks for a event by id.
     * 
     * @param id the id of a event we are looking for.
     * @return Optional containing the found event or an empty Optional
     * otherwise
     */
	public Optional<Event> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
