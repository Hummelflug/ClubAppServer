package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Player;
import io.dropwizard.hibernate.AbstractDAO;

public class PlayerDAO extends AbstractDAO<Player> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public PlayerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
     * Method returns all players stored in the database.
     * 
     * @return list of all players stored in the database
     */
	public List<Player> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Player.findAll"));
	}
	
	/**
     * Looks for players whose first or last name contains the passed
     * parameter as a substring.
     * 
     * @param name query parameter
     * @return list of players whose first or last name contains the passed
     * parameter as a substring.
     */
	public List<Player> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Player.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
     * Method looks for a player by id.
     * 
     * @param id the id of a player we are looking for.
     * @return Optional containing the found player or an empty Optional
     * otherwise
     */
	public Optional<Player> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
