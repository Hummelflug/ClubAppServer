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

import de.hummelflug.clubapp.server.core.Training;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.TrainingFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/training")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class TrainingResource {

	private TrainingFacade trainingFacade;
	
	public TrainingResource(TrainingFacade trainingFacade) {
		this.trainingFacade = trainingFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Training> findAll() {
		return trainingFacade.findAllTrainings();
	}
	
	@GET
	@Path("/{id}")
    @UnitOfWork
	public Optional<Training> findById(@PathParam("id") LongParam id) {
		return trainingFacade.findTrainingById(id.get());
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Training add(@Auth User user, @Valid Training training) {
		return trainingFacade.createTraining(user, training);
	}
    
}
