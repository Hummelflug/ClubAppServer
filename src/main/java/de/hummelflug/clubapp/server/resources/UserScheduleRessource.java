package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.db.UserScheduleDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/user_schedule")
@Produces(MediaType.APPLICATION_JSON)
public class UserScheduleRessource {

	private UserScheduleDAO userScheduleDAO;
	
	public UserScheduleRessource(UserScheduleDAO userScheduleDAO) {
		this.userScheduleDAO = userScheduleDAO;
	}
	
	@GET
    @UnitOfWork
    public List<UserSchedule> findAll() {
		return userScheduleDAO.findAll();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<UserSchedule> findById(@PathParam("id") LongParam id) {
        return userScheduleDAO.findById(id.get());
    }
	
}
