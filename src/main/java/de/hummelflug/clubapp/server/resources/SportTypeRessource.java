package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.SportType;
import de.hummelflug.clubapp.server.facade.SportTypeFacade;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/sport_type")
@Produces(MediaType.APPLICATION_JSON)
public class SportTypeRessource {

	private SportTypeFacade sportTypeFacade;
	
	public SportTypeRessource(SportTypeFacade sportTypeFacade) {
		this.sportTypeFacade = sportTypeFacade;
	}
	
	@GET
    @UnitOfWork
    public List<SportType> findAll() {
        return sportTypeFacade.findAllSportTypes();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<SportType> findById(@PathParam("id") LongParam id) {
        return sportTypeFacade.findSportTypeById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public SportType add(@Valid SportType sportType) {
    	return sportTypeFacade.createSportType(sportType);
    }
	
}
