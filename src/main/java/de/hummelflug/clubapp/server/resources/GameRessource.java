package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.db.GameDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public class GameRessource {

	private GameDAO gameDAO;
	
	public GameRessource(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Game> findAll() {
        return gameDAO.findAll();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Game> findById(@PathParam("id") LongParam id) {
        return gameDAO.findById(id.get());
    }
	
}
