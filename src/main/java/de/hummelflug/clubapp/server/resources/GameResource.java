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
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.GameFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/game")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

	private GameFacade gameFacade;
	
	public GameResource(GameFacade gameFacade) {
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
    public Game add(@Auth User user, @Valid Game game) {
    	return gameFacade.createGame(user, game);
    }
	
}
