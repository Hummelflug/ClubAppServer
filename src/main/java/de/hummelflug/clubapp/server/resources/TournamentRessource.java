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

import de.hummelflug.clubapp.server.core.Tournament;
import de.hummelflug.clubapp.server.facade.TournamentFacade;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/tournament")
@Produces(MediaType.APPLICATION_JSON)
public class TournamentRessource {

	private TournamentFacade tournamentFacade;
	
	public TournamentRessource(TournamentFacade tournamentFacade) {
		this.tournamentFacade = tournamentFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Tournament> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return tournamentFacade.findTournamentByName(name.get());
        } else {
            return tournamentFacade.findAllTournaments();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Tournament> findById(@PathParam("id") LongParam id) {
        return tournamentFacade.findTournamentById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Tournament add(@Valid Tournament tournament) {
    	return tournamentFacade.createTournament(tournament);
    }
	
}
