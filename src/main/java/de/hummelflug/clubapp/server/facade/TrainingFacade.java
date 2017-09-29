package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.Training;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.TrainingDAO;

public class TrainingFacade {
	
	private final TeamScheduleFacade teamScheduleFacade;
	private final TrainingDAO trainingDAO;
	
	public TrainingFacade(TeamScheduleFacade teamScheduleFacade, TrainingDAO trainingDAO) {
		this.teamScheduleFacade = teamScheduleFacade;
		this.trainingDAO = trainingDAO;
	}
	
	public Training createTraining(User user, Training training) {
		Training newTraining = trainingDAO.insert(new Training(user.getId(), training.getStartTime(),
				training.getEndTime(), training.getTeamId()));
		
		/** Add exercises **/
		for (Long exerciseId : training.getExercises()) {
			newTraining.getExercises().add(exerciseId);
		}
		
		/** Add event in team schedule **/
		teamScheduleFacade.addEventByTeamId(user, training.getTeamId(), newTraining.getId());
		
		return newTraining;
	}
	
	public List<Training> findAllTrainings() {
		return trainingDAO.findAll();
	}
	
	public Optional<Training> findTrainingById(Long id) {
		return trainingDAO.findById(id);
	}
	
}
