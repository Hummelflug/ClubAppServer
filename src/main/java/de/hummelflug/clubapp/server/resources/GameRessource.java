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

import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.facade.GameFacade;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public class GameRessource {

	private GameFacade gameFacade;
	
	public GameRessource(GameFacade gameFacade) {
		this.gameFacade = gameFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Game> findAll() {
        return gameFacade.findAllGames();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Game> findById(@PathParam("id") LongParam id) {
        return gameFacade.findGameById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Game add(@Valid Game game) {
    	return gameFacade.createGame(game);
    }
	
}
