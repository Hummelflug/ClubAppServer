package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Team;

public class TeamDAO extends AbstractSuperDAO<Team> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public TeamDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all teams stored in the database.
     * 
     * @return list of all teams stored in the database
     */
	public List<Team> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Team.findAll"));
	}
	
	/**
     * Looks for teams whose name contains the passed
     * parameter as a substring.
     * 
     * @param name query parameter
     * @return list of teams whose name contains the passed
     * parameter as a substring.
     */
	public List<Team> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Team.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
     * Method looks for a team by id.
     * 
     * @param id the id of a team we are looking for.
     * @return Optional containing the found team or an empty Optional
     * otherwise
     */
	public Optional<Team> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
