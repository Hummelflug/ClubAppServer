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

import de.hummelflug.clubapp.server.core.TeamSchedule;
import de.hummelflug.clubapp.server.facade.TeamScheduleFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/team_schedule")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class TeamScheduleRessource {

	private TeamScheduleFacade teamScheduleFacade;
	
	public TeamScheduleRessource(TeamScheduleFacade teamScheduleFacade) {
		this.teamScheduleFacade = teamScheduleFacade;
	}
	
	@GET
    @UnitOfWork
    public List<TeamSchedule> findAll() {
		return teamScheduleFacade.findAllTeamSchedules();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<TeamSchedule> findById(@PathParam("id") LongParam id) {
        return teamScheduleFacade.findTeamScheduleById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public TeamSchedule add(@Valid TeamSchedule teamSchedule) {
    	return teamScheduleFacade.createTeamSchedule(teamSchedule);
    }
	
}
