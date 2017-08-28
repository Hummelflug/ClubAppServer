package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.TeamSchedule;
import de.hummelflug.clubapp.server.db.TeamScheduleDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/team_schedule")
@Produces(MediaType.APPLICATION_JSON)
public class TeamScheduleRessource {

	private TeamScheduleDAO teamScheduleDAO;
	
	public TeamScheduleRessource(TeamScheduleDAO teamScheduleDAO) {
		this.teamScheduleDAO = teamScheduleDAO;
	}
	
	@GET
    @UnitOfWork
    public List<TeamSchedule> findAll() {
		return teamScheduleDAO.findAll();
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<TeamSchedule> findById(@PathParam("id") LongParam id) {
        return teamScheduleDAO.findById(id.get());
    }
	
}
