package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.db.PlayerDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerRessource {

	private PlayerDAO playerDAO;
	
	public PlayerRessource(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Player> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return playerDAO.findByName(name.get());
        } else {
            return playerDAO.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Player> findById(@PathParam("id") LongParam id) {
        return playerDAO.findById(id.get());
    }
	
}
