package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.db.UserScheduleDAO;

public class UserScheduleFacade {

	private final UserScheduleDAO userScheduleDAO;
	
	public UserScheduleFacade(UserScheduleDAO userScheduleDAO) {
		this.userScheduleDAO = userScheduleDAO;
	}
	
	public UserSchedule addEventsToUserSchedule(Long scheduleId, Set<Long> eventIds) {
		Optional<UserSchedule> userScheduleOptional = findUserScheduleById(scheduleId);
		if (userScheduleOptional.isPresent()) {
			UserSchedule userSchedule = userScheduleOptional.get();
			for (Long eventId : eventIds) {
				userSchedule.getEvents().add(eventId);
			}
			return userSchedule;
		}
		return null;
	}
	
	public UserSchedule createUserSchedule(UserSchedule userSchedule) {
		UserSchedule newUserSchedule = userScheduleDAO.insert(new UserSchedule(userSchedule.getUserId()));
		
		for (Long eventId : userSchedule.getEvents()) {
			newUserSchedule.getEvents().add(eventId);
		}
		
		userScheduleDAO.commit();
		userScheduleDAO.refresh(newUserSchedule);
		
		return newUserSchedule;
	}
	
	public List<UserSchedule> findAllUserSchedules() {
		return userScheduleDAO.findAll();
	}
	
	public Optional<UserSchedule> findUserScheduleById(Long id) {
		return userScheduleDAO.findById(id);
	}
	
}
