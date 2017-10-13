package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.EventFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/event")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

	private EventFacade eventFacade;
	
	public EventResource(EventFacade eventFacade) {
		this.eventFacade = eventFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Event> findAll() {
		return eventFacade.findAllEvents();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Event> findById(@PathParam("id") LongParam id) {
        return eventFacade.findEventById(id.get());
    }
    
    @POST
    @Path("/{id}/participate")
    @UnitOfWork
    public Event participate(@Auth User user, @PathParam("id") LongParam id) {
        return eventFacade.confirmParticipation(user, id.get());
    }
    
    @POST
    @Path("/{id}/absence")
    @UnitOfWork
    public Event absence(@Auth User user, @PathParam("id") LongParam id) {
        return eventFacade.confirmAbsence(user, id.get());
    }
    
}
