package de.hummelflug.clubapp.server.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hummelflug.clubapp.server.core.Training;
import de.hummelflug.clubapp.server.db.TrainingDAO;

public class TrainingFacade {
	
	private final TeamScheduleFacade teamScheduleFacade;
	private final TrainingDAO trainingDAO;
	
	public TrainingFacade(TeamScheduleFacade teamScheduleFacade, TrainingDAO trainingDAO) {
		this.teamScheduleFacade = teamScheduleFacade;
		this.trainingDAO = trainingDAO;
	}
	
	public Training createTraining(Training training) {
		Training newTraining = trainingDAO.insert(new Training(training.getStartTime(), training.getEndTime(),
				training.getTeamId()));
		
		//Add exercises
		for (Long exerciseId : training.getExercises()) {
			newTraining.getExercises().add(exerciseId);
		}
		
		//Add event in team schedule
		Set<Long> events = new HashSet<Long>();
		events.add(newTraining.getId());
		teamScheduleFacade.addEventsByTeamId(training.getTeamId(), events);
		
		return newTraining;
	}
	
	public List<Training> findAllTrainings() {
		return trainingDAO.findAll();
	}
	
	public Optional<Training> findTrainingById(Long id) {
		return trainingDAO.findById(id);
	}
	
}
