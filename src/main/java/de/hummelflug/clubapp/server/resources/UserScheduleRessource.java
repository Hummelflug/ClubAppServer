package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.facade.UserScheduleFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/user_schedule")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class UserScheduleRessource {

	private UserScheduleFacade userScheduleFacade;
	
	public UserScheduleRessource(UserScheduleFacade userScheduleFacade) {
		this.userScheduleFacade = userScheduleFacade;
	}
	
	@GET
    @UnitOfWork
    public List<UserSchedule> findAll() {
		return userScheduleFacade.findAllUserSchedules();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<UserSchedule> findById(@PathParam("id") LongParam id) {
        return userScheduleFacade.findUserScheduleById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public UserSchedule add(@Valid UserSchedule userSchedule) {
    	return userScheduleFacade.createUserSchedule(userSchedule);
    }
	
}
