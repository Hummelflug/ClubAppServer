package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserRessource {
	
	private UserDAO userDAO;
	
	public UserRessource(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@GET
    @UnitOfWork
    public List<User> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return userDAO.findByName(name.get());
        } else {
            return userDAO.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<User> findById(@PathParam("id") LongParam id) {
        return userDAO.findById(id.get());
    }

}
