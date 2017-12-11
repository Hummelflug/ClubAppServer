package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.db.UserScheduleDAO;
import de.hummelflug.clubapp.server.utils.UserRole;

public class UserScheduleFacade {

	private final UserScheduleDAO userScheduleDAO;
	
	public UserScheduleFacade(UserScheduleDAO userScheduleDAO) {
		this.userScheduleDAO = userScheduleDAO;
	}
	
	public UserSchedule addEventsToUserSchedule(Long scheduleId, Set<Long> eventIds) {
		if (scheduleId != null && eventIds != null) {
			Optional<UserSchedule> userScheduleOptional = findUserScheduleById(scheduleId);
			if (userScheduleOptional.isPresent()) {
				UserSchedule userSchedule = userScheduleOptional.get();
				for (Long eventId : eventIds) {
					userSchedule.getEvents().add(eventId);
				}
				return userSchedule;
			}
		}
		throw new WebApplicationException(400);
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
		if (id != null) {
			return userScheduleDAO.findById(id);
		}
		throw new WebApplicationException(400);
	}
	
	public UserSchedule findUserSchedule(User user, Long id) {
		if (user != null && id != null) {
			Optional<UserSchedule> userScheduleOptional = findUserScheduleById(id);
			if (userScheduleOptional.isPresent()) {
				UserSchedule userSchedule = userScheduleOptional.get();
				if (userSchedule.getUserId() == user.getId() || user.getUserRoles().contains(UserRole.ADMIN)) {
					return userSchedule;
				} else {
					throw new WebApplicationException(401);
				}
			}
		}
		throw new WebApplicationException(400);
	}
	
}
