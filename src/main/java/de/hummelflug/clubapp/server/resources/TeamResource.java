package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.PermitAll;
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

import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.TeamFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/team")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {

	private TeamFacade teamFacade;
	
	public TeamResource(TeamFacade teamFacade) {
		this.teamFacade = teamFacade;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Team add(@Auth User user, @Valid Team team) {
        return teamFacade.createTeam(user, team);
    }
	
	@GET
	@PermitAll
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
    @PermitAll
    @UnitOfWork
    public Optional<Team> findById(@PathParam("id") LongParam id) {
        return teamFacade.findTeamById(id.get());
    }
    
    @GET
    @Path("/{id}/coach")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<User> findCoaches(@PathParam("id") LongParam teamId) {
        return teamFacade.findTeamCoaches(teamId.get());
    }
    
    @POST
    @Path("/{id}/coach")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Team addCoach(@Auth User user, @PathParam("id") LongParam teamId,
    		@QueryParam("coachId") LongParam coachId) {
        return teamFacade.addCoachToTeam(user, teamId.get(), coachId.get());
    }
    
    @GET
    @Path("/{id}/player")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<User> findPlayers(@PathParam("id") LongParam teamId) {
        return teamFacade.findTeamPlayers(teamId.get());
    }
    
    @POST
    @Path("/{id}/player")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE, UserRole.Constants.COACH_VALUE })
    @UnitOfWork
    public Team addPlayer(@Auth User user, @PathParam("id") LongParam teamId,
    		@QueryParam("playerId") LongParam playerId) {
        return teamFacade.addPlayerToTeam(user, teamId.get(), playerId.get());
    }
    
}
