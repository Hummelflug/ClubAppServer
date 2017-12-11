package de.hummelflug.clubapp.server.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.TemporalType;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.utils.EventType;

public class EventDAO extends AbstractSuperDAO<Event> {

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
	
	/**
     * Method returns 10 upcoming events whose id is element of passed parameter.
     * 
     * @param ids set of ids whose events should be returned
     * @param types set of event type
     * @return list of 10 upcoming whose id is element of passed parameter
     */
	public List<Event> findUpcomingByIds(Set<Long> ids, Set<EventType> types) {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Event.findUpcomingByIds")
				.setParameter("ids", ids)
				.setParameter("types", types)
				.setParameter("currentTime", new Date(), TemporalType.DATE)
				.setMaxResults(10));
	}

}
