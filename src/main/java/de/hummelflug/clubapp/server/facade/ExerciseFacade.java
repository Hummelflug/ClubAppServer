package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.Exercise;
import de.hummelflug.clubapp.server.db.ExerciseDAO;
import de.hummelflug.clubapp.server.utils.ExerciseType;

public class ExerciseFacade {

	private final ExerciseDAO exerciseDAO;

	public ExerciseFacade(ExerciseDAO exerciseDAO) {
		this.exerciseDAO = exerciseDAO;
	}
	
	public Exercise createExercise(Exercise exercise) {
		return this.exerciseDAO.insert(new Exercise(exercise.getExerciseType(), exercise.getDifficulty(), 
        		exercise.getAgeClass(), exercise.getImage(), exercise.getTitle(), exercise.getDescription()));
	}
	
	public List<Exercise> findAllExercises() {
		return exerciseDAO.findAll();
	}
	
	public List<Exercise> findExerciseByExerciseType(ExerciseType exerciseType) {
		return exerciseDAO.findByType(exerciseType);
	}
	
	public Optional<Exercise> findExerciseById(Long id) {
		return exerciseDAO.findById(id);
	}
	
}
