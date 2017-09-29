package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.UserFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/user")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private UserFacade userFacade;
	
	public UserResource(UserFacade userFacade) {
		this.userFacade = userFacade;
	}
	
	@GET
    @UnitOfWork
    public List<User> findByName(@QueryParam("email") Optional<String> email,
    		@QueryParam("name") Optional<String> name) {
		if (email.isPresent()) {
			return userFacade.findByEmail(email.get());
		}
		if (name.isPresent()) {
            return userFacade.findByName(name.get());
        } else {
            return userFacade.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<User> findById(@PathParam("id") LongParam id) {
        return userFacade.findById(id.get());
    }

}
