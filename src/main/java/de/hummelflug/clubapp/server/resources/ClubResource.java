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

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.ClubFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/club")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class ClubResource {

	private ClubFacade clubFacade;
	
	public ClubResource(ClubFacade clubFacade) {
		this.clubFacade = clubFacade;
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public Club add(@Auth User user, @Valid Club club) {
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
	
}
