package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.db.ClubDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/club")
@Produces(MediaType.APPLICATION_JSON)
public class ClubRessource {

	private ClubDAO clubDAO;
	
	public ClubRessource(ClubDAO clubDAO) {
		this.clubDAO = clubDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Club> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return clubDAO.findByName(name.get());
        } else {
            return clubDAO.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Club> findById(@PathParam("id") LongParam id) {
        return clubDAO.findById(id.get());
    }
	
}
