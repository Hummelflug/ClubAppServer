package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Schedule;
import io.dropwizard.hibernate.AbstractDAO;

public class ScheduleDAO extends AbstractDAO<Schedule> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public ScheduleDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all schedules stored in the database.
     * 
     * @return list of all schedules stored in the database
     */
	public List<Schedule> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Schedule.findAll"));
	}
	
	/**
     * Method looks for a schedule by id.
     * 
     * @param id the id of a schedule we are looking for.
     * @return Optional containing the found schedule or an empty Optional
     * otherwise
     */
	public Optional<Schedule> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
