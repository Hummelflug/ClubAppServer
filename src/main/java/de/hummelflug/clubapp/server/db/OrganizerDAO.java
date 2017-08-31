package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Organizer;

public class OrganizerDAO extends AbstractSuperDAO<Organizer> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public OrganizerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all organizers stored in the database.
     * 
     * @return list of all organizers stored in the database
     */
	public List<Organizer> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Organizer.findAll"));
	}
	
	/**
     * Looks for organizers whose first or last name contains the passed
     * parameter as a substring.
     * 
     * @param name query parameter
     * @return list of organizers whose first or last name contains the passed
     * parameter as a substring.
     */
	public List<Organizer> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Organizer.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
     * Method looks for a organizer by id.
     * 
     * @param id the id of a organizer we are looking for.
     * @return Optional containing the found organizer or an empty Optional
     * otherwise
     */
	public Optional<Organizer> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
