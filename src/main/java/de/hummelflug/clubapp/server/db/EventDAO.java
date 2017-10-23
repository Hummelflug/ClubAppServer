package de.hummelflug.clubapp.server.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Event;

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
     * Method returns all events whose id is element of passed parameter.
     * 
     * @param ids set of ids whose events should be returned
     * @return list of all events whose id is element of passed parameter
     */
	public List<Event> findByIds(Set<Long> ids) {
		if (ids != null) {
			List<Event> events = new ArrayList<Event>();
			for (Long id : ids) {
				Optional<Event> eventOptional = findById(id);
				if (eventOptional.isPresent()) {
					events.add(eventOptional.get());
				} else {
					return null;
				}
			}
			return events;
		}
		return null;
	}

}
