package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Game;

public class GameDAO extends AbstractSuperDAO<Game> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public GameDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all games stored in the database.
     * 
     * @return list of all games stored in the database
     */
	public List<Game> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Game.findAll"));
	}
	
	/**
     * Method looks for a game by id.
     * 
     * @param id the id of a game we are looking for.
     * @return Optional containing the found game or an empty Optional
     * otherwise
     */
	public Optional<Game> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
