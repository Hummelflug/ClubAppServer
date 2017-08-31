package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.TeamSchedule;
import io.dropwizard.hibernate.AbstractDAO;

public class TeamScheduleDAO extends AbstractDAO<TeamSchedule> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public TeamScheduleDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all team schedules stored in the database.
     * 
     * @return list of all team schedules stored in the database
     */
	public List<TeamSchedule> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.TeamSchedule.findAll"));
	}
	
	/**
     * Method looks for a team schedule by id.
     * 
     * @param id the id of a team schedule we are looking for.
     * @return Optional containing the found team schedule or an empty Optional
     * otherwise
     */
	public Optional<TeamSchedule> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
	public TeamSchedule insert(TeamSchedule ts) {
		return persist(ts);
	}
	
}
