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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.facade.PlayerFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/player")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerRessource {

	private PlayerFacade playerFacade;
	
	public PlayerRessource(PlayerFacade playerFacade) {
		this.playerFacade = playerFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Player> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return playerFacade.findPlayerByName(name.get());
        } else {
            return playerFacade.findAllPlayers();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Player> findById(@PathParam("id") LongParam id) {
        return playerFacade.findPlayerById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Player add(@Valid Player player) {
        return playerFacade.createPlayer(player);
    }
	
}
