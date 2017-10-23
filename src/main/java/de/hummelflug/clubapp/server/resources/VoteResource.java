package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.db.VoteDAO;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/vote")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class VoteResource {

	private VoteDAO voteDAO;
	
	public VoteResource(VoteDAO voteDAO) {
		this.voteDAO = voteDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Vote> findAll() {
        return voteDAO.findAll();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Vote> findById(@PathParam("id") LongParam id) {
        return voteDAO.findById(id.get());
    }
}
