package de.hummelflug.clubapp.server.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.core.Schedule;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.db.EventDAO;
import de.hummelflug.clubapp.server.db.ScheduleDAO;
import de.hummelflug.clubapp.server.utils.EventType;
import de.hummelflug.clubapp.server.utils.ScheduleType;
import de.hummelflug.clubapp.server.utils.UserRole;

public class ScheduleFacade {

	private final EventDAO eventDAO;
	private final ScheduleDAO scheduleDAO;
	
	public ScheduleFacade(EventDAO eventDAO, ScheduleDAO scheduleDAO) {
		this.eventDAO = eventDAO;
		this.scheduleDAO = scheduleDAO;
	}
	
	public List<Event> findAllEventsByType(User user, Long scheduleId, Set<EventType> eventTypes) {
		Optional<Schedule> scheduleOptional = scheduleDAO.findById(scheduleId);
		if (scheduleOptional.isPresent() && eventTypes != null) {
			Schedule schedule = scheduleOptional.get();
			
			/** Check permission **/
			if (schedule.getScheduleType().equals(ScheduleType.USER)) {
				if (!(user.getUserRoles().contains(UserRole.ADMIN) 
						|| user.getId() == ((UserSchedule) schedule).getUserId())) {
					throw new WebApplicationException(401);
				}		
			}
			
			/** Create event list **/
			List<Event> events = new ArrayList<Event>();
			for (Long eventId : schedule.getEvents()) {
				Optional<Event> eventOptional = eventDAO.findById(eventId);
				if (eventOptional.isPresent()) {
					if (eventTypes.contains(eventOptional.get().getEventType())) {
						events.add(eventOptional.get());
					}
				} else {
					throw new WebApplicationException(400);
				}
			}
			return events;
		}
		throw new WebApplicationException(400);
	}

	public List<Schedule> findAllSchedules() {
		return scheduleDAO.findAll();
	}

	public Optional<Schedule> findScheduleById(Long scheduleId) {
		return scheduleDAO.findById(scheduleId);
	}
	
}
