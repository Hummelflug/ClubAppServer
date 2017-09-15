package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Schedule;
import de.hummelflug.clubapp.server.db.ScheduleDAO;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/schedule")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleRessource {

	private ScheduleDAO scheduleDAO;
	
	public ScheduleRessource(ScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Schedule> findAll() {
		return scheduleDAO.findAll();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Schedule> findById(@PathParam("id") LongParam id) {
        return scheduleDAO.findById(id.get());
    }
	
}
