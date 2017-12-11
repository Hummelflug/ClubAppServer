package de.hummelflug.clubapp.server.resources;

import java.io.File;
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

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.facade.ClubFacade;
import de.hummelflug.clubapp.server.facade.ClubNewsContentFacade;
import de.hummelflug.clubapp.server.facade.ClubNewsFacade;
import de.hummelflug.clubapp.server.facade.ClubVoteFacade;
import de.hummelflug.clubapp.server.facade.DepartmentNewsFacade;
import de.hummelflug.clubapp.server.facade.DepartmentVoteFacade;
import de.hummelflug.clubapp.server.facade.TeamNewsFacade;
import de.hummelflug.clubapp.server.facade.TeamVoteFacade;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/club")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class ClubResource {

	private ClubFacade clubFacade;
	private ClubNewsContentFacade clubNewsContentFacade;
	private ClubVoteFacade clubVoteFacade;
	private ClubNewsFacade clubNewsFacade;
	private DepartmentNewsFacade departmentNewsFacade;
	private DepartmentVoteFacade departmentVoteFacade;
	private TeamNewsFacade teamNewsFacade;
	private TeamVoteFacade teamVoteFacade;
	
	public ClubResource(ClubFacade clubFacade, ClubNewsContentFacade clubNewsContentFacade,
			ClubNewsFacade clubNewsFacade, ClubVoteFacade clubVoteFacade, DepartmentNewsFacade departmentNewsFacade,
			DepartmentVoteFacade departmentVoteFacade, TeamNewsFacade teamNewsFacade, TeamVoteFacade teamVoteFacade) {
		this.clubFacade = clubFacade;
		this.clubNewsContentFacade = clubNewsContentFacade;
		this.clubNewsFacade = clubNewsFacade;
		this.clubVoteFacade = clubVoteFacade;
		this.departmentNewsFacade = departmentNewsFacade;
		this.departmentVoteFacade = departmentVoteFacade;
		this.teamNewsFacade = teamNewsFacade;
		this.teamVoteFacade = teamVoteFacade;
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Club addClub(@Auth User user, @Valid Club club) {
        return clubFacade.createClub(user.getId(), club);
    }
	
	@GET
    @UnitOfWork
    public List<Club> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return clubFacade.findClubByName(name.get());
        } else {
            return clubFacade.findAllClubs();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Club> findById(@PathParam("id") LongParam id) {
        return clubFacade.findClubById(id.get());
    }
    
    @GET
    @Path("/{id}/board")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<User> findBoard(@PathParam("id") LongParam clubId) {
        return clubFacade.findClubBoard(clubId.get());
    }
    
    @POST
    @Path("/{id}/board")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Club addBoard(@Auth User user, @PathParam("id") LongParam clubId,
    		@QueryParam("boardId") LongParam boardId) {
        return clubFacade.addBoardToClub(user, clubId.get(), boardId.get());
    }
    
    @GET
    @Path("/{id}/coach")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<User> findCoaches(@PathParam("id") LongParam clubId) {
        return clubFacade.findClubCoaches(clubId.get());
    }
    
    @POST
    @Path("/{id}/coach")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Club addCoach(@Auth User user, @PathParam("id") LongParam clubId,
    		@QueryParam("coachId") LongParam coachId) {
        return clubFacade.addCoachToClub(user, clubId.get(), coachId.get());
    }
    
    @GET
    @Path("/{id}/departmentHead")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<User> findDepartmentHead(@PathParam("id") LongParam clubId) {
        return clubFacade.findClubDepartmentHead(clubId.get());
    }
    
    @POST
    @Path("/{id}/departmentHead")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Club addDepartmentHead(@Auth User user, @PathParam("id") LongParam clubId,
    		@QueryParam("departmentHeadId") LongParam departmentHeadId) {
        return clubFacade.addDepartmentHeadToClub(user, clubId.get(), departmentHeadId.get());
    }
    
	@GET
    @Path("/{clubid}/department/news")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<News> findNewsByDepartmentIds(@Auth User user, @PathParam("clubid") LongParam clubId,
    		@QueryParam("departmentids") List<Long> departmentIds, @QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent() && departmentIds != null) {
    		return departmentNewsFacade.findDepartmentNewsByDepartmentIds(user, clubId.get(), departmentIds,
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
	
	@POST
    @Path("/{clubid}/department/news/{newsid}/reader")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public News addDepartmentNewsReader(@Auth User user, @PathParam("clubid") LongParam clubId,
    		@PathParam("newsid") LongParam newsId) {
    	return departmentNewsFacade.addDepartmentNewsReaderByClubId(user, clubId.get(), newsId.get());
    }
	
    @GET
    @Path("/{clubid}/team/news")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<News> findNewsByTeamIds(@Auth User user, @PathParam("clubid") LongParam clubId, 
    		@QueryParam("teamids") List<Long> teamIds, @QueryParam("filter") Optional<String> filter) {
    	if (clubId != null && filter.isPresent() && teamIds != null) {
    		return teamNewsFacade.findTeamNewsByTeamIds(user, clubId.get(), teamIds,
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @POST
    @Path("/{clubid}/team/news/{newsid}/reader")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public News addTeamNewsReader(@Auth User user, @PathParam("clubid") LongParam clubId,
    		@PathParam("newsid") LongParam newsId) {
    	return teamNewsFacade.addTeamNewsReaderByClubId(user, clubId.get(), newsId.get());
    }
    
    @GET
    @Path("/{id}/news_content")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<NewsContent> findNewsContent(@Auth User user, @PathParam("id") LongParam clubId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return clubNewsContentFacade.findClubNewsContent(user, clubId.get(),
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @GET
    @Path("/{id}/news")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<News> findNews(@Auth User user, @PathParam("id") LongParam clubId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return clubNewsFacade.findClubNews(user, clubId.get(), NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @POST
    @Path("/{id}/news")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public News addClubNews(@Auth User user, @PathParam("id") LongParam clubId, @Valid News news) {
    	return clubNewsFacade.createClubNews(user, clubId.get(), news);
    }
    
    @GET
    @Path("/{clubid}/news/{newsid}/image.jpg")
	@Produces("image/jpg")
    @PermitAll
    @UnitOfWork
    public File download(@Auth User user, @PathParam("clubid") LongParam clubId,
    		@PathParam("newsid") LongParam newsId) {
        return clubNewsFacade.downloadImageFile(user, clubId.get(), newsId.get());
    }
    
    @POST
    @Path("/{clubid}/news/{newsid}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public News addClubNewsImage(@Auth User user, @PathParam("clubid") LongParam clubId, 
    		@PathParam("newsid") LongParam newsId, @FormDataParam("file") final InputStream inputStream) {
    	return clubNewsFacade.addClubNewsImage(user, clubId.get(), newsId.get(), inputStream);
    }
    
    @POST
    @Path("/{clubid}/news/{newsid}/reader")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public News addClubNewsReader(@Auth User user, @PathParam("clubid") LongParam clubId,
    		@PathParam("newsid") LongParam newsId) {
    	return clubNewsFacade.addClubNewsReader(user, clubId.get(), newsId.get());
    }
    
    @GET
    @Path("/{id}/player")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<User> findPlayers(@PathParam("id") LongParam clubId) {
        return clubFacade.findClubPlayers(clubId.get());
    }
    
    @POST
    @Path("/{id}/player")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE, UserRole.Constants.COACH_VALUE })
    @UnitOfWork
    public Club addPlayer(@Auth User user, @PathParam("id") LongParam clubId,
    		@QueryParam("playerId") LongParam playerId) {
        return clubFacade.addPlayerToClub(user, clubId.get(), playerId.get());
    }
    
    @GET
    @Path("/{id}/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Vote> findVotes(@Auth User user, @PathParam("id") LongParam clubId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return clubVoteFacade.findClubVotes(user, clubId.get(), NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @GET
    @Path("/{clubid}/department/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Vote> findVotesByDepartmentIds(@Auth User user, @PathParam("clubid") LongParam clubId,
    		@QueryParam("departmentids") List<Long> departmentIds, @QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent() && departmentIds != null) {
    		return departmentVoteFacade.findDepartmentVoteByDepartmentIds(user, clubId.get(), departmentIds,
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
	
    @GET
    @Path("/{clubid}/team/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Vote> findVotesByTeamIds(@Auth User user, @PathParam("clubid") LongParam clubId, 
    		@QueryParam("teamids") List<Long> teamIds, @QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent() && teamIds != null) {
    		System.out.println(teamIds);
    		return teamVoteFacade.findTeamVoteByTeamIds(user, clubId.get(), teamIds,
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
    
    @POST
    @Path("/{id}/vote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Vote addClubVote(@Auth User user, @PathParam("id") LongParam clubId, @Valid Vote vote) {
    	return clubVoteFacade.createClubVote(user, clubId.get(), vote);
    }
    
    @POST
    @Path("/{clubid}/vote/{voteid}/close")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Vote closeClubVote(@Auth User user, @PathParam("clubid") LongParam clubId, 
    		@PathParam("voteid") LongParam voteId) {
    	return clubVoteFacade.closeClubVote(user, clubId.get(), voteId.get());
    }
    
    @POST
    @Path("/vote/{voteid}/answer/{answerid}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public Vote voteForAnswer(@Auth User user, @PathParam("voteid") LongParam voteId,
    		@PathParam("answerid") LongParam answerId) {
    	return clubVoteFacade.voteForAnswer(user, voteId.get(), answerId.get());
    }
	
}
