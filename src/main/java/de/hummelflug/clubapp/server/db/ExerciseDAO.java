package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Exercise;
import de.hummelflug.clubapp.server.utils.ExerciseType;

public class ExerciseDAO extends AbstractSuperDAO<Exercise> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public ExerciseDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all exercises stored in the database.
     * 
     * @return list of all exercises stored in the database
     */
	public List<Exercise> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Exercise.findAll"));
	}
	
	/**
     * Looks for exercises whose exerciseType equals the passed exerciseType
     * 
     * @param exerciseType query parameter
     * @return list of exercises whose exerciseType equals the passed exerciseType
     */
	public List<Exercise> findByType(ExerciseType exerciseType) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(exerciseType).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Exercise.findByExerciseType")
				.setParameter("exerciseType", builder.toString()));
	}
	
	/**
     * Method looks for a exercise by id.
     * 
     * @param id the id of a exercise we are looking for.
     * @return Optional containing the found exercise or an empty Optional
     * otherwise
     */
	public Optional<Exercise> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
