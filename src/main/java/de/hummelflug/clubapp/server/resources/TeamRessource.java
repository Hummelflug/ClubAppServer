package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.db.TeamDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/team")
@Produces(MediaType.APPLICATION_JSON)
public class TeamRessource {

private TeamDAO teamDAO;
	
	public TeamRessource(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Team> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return teamDAO.findByName(name.get());
        } else {
            return teamDAO.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Team> findById(@PathParam("id") LongParam id) {
        return teamDAO.findById(id.get());
    }
	
}
