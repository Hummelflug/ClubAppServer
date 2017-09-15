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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.facade.CoachFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/coach")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class CoachRessource {

	private CoachFacade coachFacade;
	
	public CoachRessource(CoachFacade coachFacade) {
		this.coachFacade = coachFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Coach> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return coachFacade.findCoachByName(name.get());
        } else {
            return coachFacade.findAllCoaches();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Coach> findById(@PathParam("id") LongParam id) {
        return coachFacade.findCoachById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Coach add(@Valid Coach coach) {
    	return coachFacade.createCoach(coach);
    }
    
}
