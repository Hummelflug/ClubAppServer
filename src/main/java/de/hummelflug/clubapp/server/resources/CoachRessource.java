package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.db.CoachDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/coach")
@Produces(MediaType.APPLICATION_JSON)
public class CoachRessource {

	private CoachDAO coachRessource;
	
	public CoachRessource(CoachDAO coachRessource) {
		this.coachRessource = coachRessource;
	}
	
	@GET
    @UnitOfWork
    public List<Coach> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return coachRessource.findByName(name.get());
        } else {
            return coachRessource.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Coach> findById(@PathParam("id") LongParam id) {
        return coachRessource.findById(id.get());
    }
    
}
