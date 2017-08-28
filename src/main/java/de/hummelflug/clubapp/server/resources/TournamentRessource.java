package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Tournament;
import de.hummelflug.clubapp.server.db.TournamentDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/tournament")
@Produces(MediaType.APPLICATION_JSON)
public class TournamentRessource {

	private TournamentDAO tournamentDAO;
	
	public TournamentRessource(TournamentDAO tournamentDAO) {
		this.tournamentDAO = tournamentDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Tournament> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return tournamentDAO.findByName(name.get());
        } else {
            return tournamentDAO.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Tournament> findById(@PathParam("id") LongParam id) {
        return tournamentDAO.findById(id.get());
    }
	
}
