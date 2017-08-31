package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.SportType;

public class SportTypeDAO extends AbstractSuperDAO<SportType> {

	/**
     * Constructor.
     *
     * @param sessionFactory Hibernate session factory.
     */
	public SportTypeDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all sport types stored in the database.
     * 
     * @return list of all sport types stored in the database
     */
	public List<SportType> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.SportType.findAll"));
	}
	
	/**
     * Method looks for a sport type by id.
     * 
     * @param id the id of a sport type we are looking for.
     * @return Optional containing the found sport type or an empty Optional
     * otherwise
     */
	public Optional<SportType> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
