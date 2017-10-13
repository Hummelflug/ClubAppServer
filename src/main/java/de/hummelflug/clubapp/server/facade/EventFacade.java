package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.EventDAO;

public class EventFacade {
	
	private final EventDAO eventDAO;
	
	public EventFacade(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}
	
	public Event initEvent(Event event, Set<Long> participants) {
		if (event != null && participants != null) {
			for (Long userId : participants) {
				event.getParticipants().add(userId);
				event.getUndecidedUsers().add(userId);
			}
		}
		
		eventDAO.commit();
		eventDAO.refresh(event);
		
		return event;
	}
	
	public Event confirmAbsence(User user, Long eventId) {
		if (user != null && eventId != null) {
			Optional<Event> eventOptional = eventDAO.findById(eventId);
			if (eventOptional.isPresent()) {
				Event event = eventOptional.get();
				if (event.getParticipants().contains(user.getId())) {
					if (event.getUndecidedUsers().contains(user.getId())) {
						event.getUndecidedUsers().remove(user.getId());
					} else if (event.getConfirmedParticipants().contains(user.getId())) {
						event.getConfirmedParticipants().remove(user.getId());
					}
					event.getAbsentUsers().add(user.getId());
					
					eventDAO.commit();
					eventDAO.refresh(event);
					
					return event;
				} else {
					throw new WebApplicationException(401);
				}
			} else {
				throw new WebApplicationException(400);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public Event confirmParticipation(User user, Long eventId) {
		if (user != null && eventId != null) {
			Optional<Event> eventOptional = eventDAO.findById(eventId);
			if (eventOptional.isPresent()) {
				Event event = eventOptional.get();
				if (event.getParticipants().contains(user.getId())) {
					if (event.getUndecidedUsers().contains(user.getId())) {
						event.getUndecidedUsers().remove(user.getId());
					} else if (event.getAbsentUsers().contains(user.getId())) {
						event.getAbsentUsers().remove(user.getId());
					}
					event.getConfirmedParticipants().add(user.getId());
					
					eventDAO.commit();
					eventDAO.refresh(event);
					
					return event;
				} else {
					throw new WebApplicationException(401);
				}
			} else {
				throw new WebApplicationException(400);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<Event> findAllEvents() {
		return eventDAO.findAll();
	}
	
	public Optional<Event> findEventById(Long id) {
		return eventDAO.findById(id);
	}

}
