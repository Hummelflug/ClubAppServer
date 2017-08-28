package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.UserSchedule;
import io.dropwizard.hibernate.AbstractDAO;

public class UserScheduleDAO extends AbstractDAO<UserSchedule> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public UserScheduleDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all user schedules stored in the database.
     * 
     * @return list of all user schedules stored in the database
     */
	public List<UserSchedule> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.UserSchedule.findAll"));
	}
	
	/**
     * Method looks for a user schedule by id.
     * 
     * @param id the id of a user schedule we are looking for.
     * @return Optional containing the found user schedule or an empty Optional
     * otherwise
     */
	public Optional<UserSchedule> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
