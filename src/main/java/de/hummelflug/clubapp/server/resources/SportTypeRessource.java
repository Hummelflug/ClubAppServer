package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.SportType;
import de.hummelflug.clubapp.server.db.SportTypeDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/sport_type")
@Produces(MediaType.APPLICATION_JSON)
public class SportTypeRessource {

	private SportTypeDAO sportTypeDAO;
	
	public SportTypeRessource(SportTypeDAO sportTypeDAO) {
		this.sportTypeDAO = sportTypeDAO;
	}
	
	@GET
    @UnitOfWork
    public List<SportType> findAll() {
        return sportTypeDAO.findAll();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<SportType> findById(@PathParam("id") LongParam id) {
        return sportTypeDAO.findById(id.get());
    }
	
}
