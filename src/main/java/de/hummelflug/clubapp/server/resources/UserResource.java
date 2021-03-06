package de.hummelflug.clubapp.server.resources;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MediaType;
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

import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.ImageFileFacade;
import de.hummelflug.clubapp.server.facade.UserFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private ImageFileFacade imageFacade;
	private UserFacade userFacade;
	
	public UserResource(ImageFileFacade imageFacade, UserFacade userFacade) {
		this.imageFacade = imageFacade;
		this.userFacade = userFacade;
	}
	
	@GET
	@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
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
	@Path("/login")
	@PermitAll
	@UnitOfWork
	public User login(@Auth User user) {
		return user;
	}
	
    @GET
    @RolesAllowed(UserRole.Constants.ADMIN_VALUE)
    @Path("/{id}")
    @UnitOfWork
    public Optional<User> findById(@PathParam("id") LongParam id) {
        return userFacade.findById(id.get());
    }
    
    @GET
    @Path("/{id}/profile.jpg")
	@Produces("image/jpg")
    @PermitAll
    @UnitOfWork
    public File download(@Auth User user, @PathParam("id") LongParam userId) {
    	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    	System.out.println(userId);
    	System.out.println(user.getId());
    	System.out.println(user.getImageId());
        if (userId != null) {
        	if (userId.get() == user.getId()) {
        		return this.imageFacade.downloadImageFile(user.getImageId());
        	}
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        throw new WebApplicationException(400);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public User add(@Valid User user) {
        try {
			return userFacade.createUser(user);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new WebApplicationException(400);
		}
    }
    
}
