package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Training;

public class TrainingDAO extends AbstractSuperDAO<Training> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public TrainingDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all trainings stored in the database.
     * 
     * @return list of all trainings stored in the database
     */
	public List<Training> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Training.findAll"));
	}
	
	/**
     * Method looks for a training by id.
     * 
     * @param id the id of a training we are looking for.
     * @return Optional containing the found training or an empty Optional
     * otherwise
     */
	public Optional<Training> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
