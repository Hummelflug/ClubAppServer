package de.hummelflug.clubapp.server.resources;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.core.Schedule;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.ScheduleFacade;
import de.hummelflug.clubapp.server.utils.EventType;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/schedule")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {

	private ScheduleFacade scheduleFacade;
	
	public ScheduleResource(ScheduleFacade scheduleFacade) {
		this.scheduleFacade = scheduleFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Schedule> findAll() {
		return scheduleFacade.findAllSchedules();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Schedule> findById(@PathParam("id") LongParam id) {
        return scheduleFacade.findScheduleById(id.get());
    }
    
    @GET
    @Path("/{id}/event")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Event> findEvents(@Auth User user, @PathParam("id") LongParam scheduleId) {
    	return scheduleFacade.findAllEventsByType(user, scheduleId.get(),
    			new HashSet<EventType>(Arrays.asList(new EventType[] { EventType.GAME, EventType.TRAINING })));
    }
    
    @GET
    @Path("/{id}/game")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Event> findGames(@Auth User user, @PathParam("id") LongParam scheduleId) {
    	return scheduleFacade.findAllEventsByType(user, scheduleId.get(),
    			new HashSet<EventType>(Arrays.asList(new EventType[] { EventType.GAME })));
    }
    
    @GET
    @Path("/{id}/training")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Event> findTrainings(@Auth User user, @PathParam("id") LongParam scheduleId) {
    	return scheduleFacade.findAllEventsByType(user, scheduleId.get(),
    			new HashSet<EventType>(Arrays.asList(new EventType[] { EventType.TRAINING })));
    }
	
}
