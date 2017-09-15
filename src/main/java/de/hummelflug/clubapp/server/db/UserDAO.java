package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.User;

public class UserDAO extends AbstractSuperDAO<User> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all users stored in the database.
     * 
     * @return list of all users stored in the database
     */
	public List<User> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.User.findAll"));
	}
	
	/**
     * Looks for user whose email equals the passed parameter.
     * 
     * @param email query parameter
     * @return List containing the found user
     */
	public List<User> findByEmail(String email) {
		return list(namedQuery("de.hummelflug.clubapp.server.core.User.findByEmail")
				.setParameter("email", email));
	}
	
	/**
     * Looks for users whose first or last name contains the passed
     * parameter as a substring.
     * 
     * @param name query parameter
     * @return list of users whose first or last name contains the passed
     * parameter as a substring.
     */
	public List<User> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.User.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
     * Method looks for a user by id.
     * 
     * @param id the id of a user we are looking for.
     * @return Optional containing the found user or an empty Optional
     * otherwise
     */
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
