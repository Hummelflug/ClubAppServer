package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.db.EventDAO;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/event")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

	private EventDAO eventDAO;
	
	public EventResource(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Event> findAll() {
		return eventDAO.findAll();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Event> findById(@PathParam("id") LongParam id) {
        return eventDAO.findById(id.get());
    }
    
}
