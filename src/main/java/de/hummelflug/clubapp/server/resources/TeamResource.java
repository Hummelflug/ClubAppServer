package de.hummelflug.clubapp.server.resources;

import java.io.InputStream;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataParam;

import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.facade.TeamFacade;
import de.hummelflug.clubapp.server.facade.TeamNewsContentFacade;
import de.hummelflug.clubapp.server.facade.TeamNewsFacade;
import de.hummelflug.clubapp.server.facade.TeamVoteFacade;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/team")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {

	private TeamFacade teamFacade;
	private TeamNewsContentFacade teamNewsContentFacade;
	private TeamNewsFacade teamNewsFacade;
	private TeamVoteFacade teamVoteFacade;
	
	public TeamResource(TeamFacade teamFacade, TeamNewsContentFacade teamNewsContentFacade, 
			TeamNewsFacade teamNewsFacade, TeamVoteFacade teamVoteFacade) {
		this.teamFacade = teamFacade;
		this.teamNewsContentFacade = teamNewsContentFacade;
		this.teamNewsFacade = teamNewsFacade;
		this.teamVoteFacade = teamVoteFacade;
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
    @Path("/{id}/news_content")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<NewsContent> findNewsContent(@Auth User user, @PathParam("id") LongParam teamId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return teamNewsContentFacade.findTeamNewsContent(user, teamId.get(),
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @GET
    @Path("/{id}/news")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<News> findNews(@Auth User user, @PathParam("id") LongParam teamId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return teamNewsFacade.findTeamNews(user, teamId.get(), NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @POST
    @Path("/{id}/news")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE, UserRole.Constants.DEP_HEAD_VALUE,
    	UserRole.Constants.COACH_VALUE })
    @UnitOfWork
    public News addTeamNews(@Auth User user, @PathParam("id") LongParam teamId, @Valid News news) {
    	return teamNewsFacade.createTeamNews(user, teamId.get(), news);
    }
    
    @POST
    @Path("/{teamid}/news/{newsid}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public News addTeamNewsImage(@Auth User user, @PathParam("teamid") LongParam teamId, 
    		@PathParam("newsid") LongParam newsId, @FormDataParam("file") final InputStream inputStream) {
    	return teamNewsFacade.addTeamNewsImage(user, teamId.get(), newsId.get(), inputStream);
    }
    
    @POST
    @Path("/{teamid}/news/{newsid}/reader")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public News addTeamNewsReader(@Auth User user, @PathParam("teamid") LongParam teamId,
    		@PathParam("newsid") LongParam newsId) {
    	return teamNewsFacade.addTeamNewsReader(user, teamId.get(), newsId.get());
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
    
    @GET
    @Path("/{id}/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Vote> findVotes(@Auth User user, @PathParam("id") LongParam teamId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return teamVoteFacade.findTeamVotes(user, teamId.get(), NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @POST
    @Path("/{id}/vote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.COACH_VALUE })
    @UnitOfWork
    public Vote addTeamVote(@Auth User user, @PathParam("id") LongParam teamId, @Valid Vote vote) {
    	return teamVoteFacade.createTeamVote(user, teamId.get(), vote);
    }
    
    @POST
    @Path("/{teamid}/vote/{voteid}/close")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.COACH_VALUE })
    @UnitOfWork
    public Vote closeTeamVote(@Auth User user, @PathParam("teamid") LongParam teamId, 
    		@PathParam("voteid") LongParam voteId) {
    	return teamVoteFacade.closeTeamVote(user, teamId.get(), voteId.get());
    }
    
    @POST
    @Path("/{teamid}/vote/{voteid}/answer/{answerid}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public Vote voteForAnswer(@Auth User user, @PathParam("voteid") LongParam voteId,
    		@PathParam("answerid") LongParam answerId) {
    	return teamVoteFacade.voteForAnswer(user, voteId.get(), answerId.get());
    }
    
}
