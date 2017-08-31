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

import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.facade.TeamFacade;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/team")
@Produces(MediaType.APPLICATION_JSON)
public class TeamRessource {

	private TeamFacade teamFacade;
	
	public TeamRessource(TeamFacade teamFacade) {
		this.teamFacade = teamFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Team> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return teamFacade.findTeamByName(name.get());
        } else {
            return teamFacade.findAllTeams();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Team> findById(@PathParam("id") LongParam id) {
        return teamFacade.findTeamById(id.get());
    }
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Team add(@Valid Team team) {
        return teamFacade.createTeam(team);
    }
    
}
