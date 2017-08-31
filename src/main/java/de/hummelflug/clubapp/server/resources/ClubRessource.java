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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.facade.ClubFacade;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/club")
@Produces(MediaType.APPLICATION_JSON)
public class ClubRessource {

	private ClubFacade clubFacade;
	
	public ClubRessource(ClubFacade clubFacade) {
		this.clubFacade = clubFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Club> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return clubFacade.findClubByName(name.get());
        } else {
            return clubFacade.findAllClubs();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Club> findById(@PathParam("id") LongParam id) {
        return clubFacade.findClubById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Club add(@Valid Club club) {
        return clubFacade.createClub(club);
    }
	
}
