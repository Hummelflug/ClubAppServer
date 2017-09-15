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

import de.hummelflug.clubapp.server.core.Exercise;
import de.hummelflug.clubapp.server.facade.ExerciseFacade;
import de.hummelflug.clubapp.server.utils.ExerciseType;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/exercise")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class ExerciseRessource {

	private ExerciseFacade exerciseFacade;
	
	public ExerciseRessource(ExerciseFacade exerciseFacade) {
		this.exerciseFacade = exerciseFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Exercise> findByName(@QueryParam("type") Optional<ExerciseType> type) {
        if (type.isPresent()) {
            return exerciseFacade.findExerciseByExerciseType(type.get());
        } else {
            return exerciseFacade.findAllExercises();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Exercise> findById(@PathParam("id") LongParam id) {
        return exerciseFacade.findExerciseById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Exercise add(@Valid Exercise exercise) {
        return exerciseFacade.createExercise(exercise);
    }
	
}
