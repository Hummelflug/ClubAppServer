package de.hummelflug.clubapp.server.resources;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataParam;

import de.hummelflug.clubapp.server.core.ImageFile;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.ImageFileFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/image")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class ImageFileResource {
	
	private ImageFileFacade imageFileFacade;
	
	public ImageFileResource(ImageFileFacade imageFileFacade) {
		this.imageFileFacade = imageFileFacade;
	}
	
	@GET
    @UnitOfWork
    public List<ImageFile> findAll() {
        return imageFileFacade.findAllImageFiles();
    }
	
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public ImageFile addImage(@Auth User user, @FormDataParam("file") final InputStream inputStream) {
    	return imageFileFacade.uploadImageFile(user, inputStream);
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<ImageFile> findById(@PathParam("id") LongParam id) {
        return imageFileFacade.findImageFileById(id.get());
    }
	
	@GET
    @Path("/{id}/download.jpg")
	@Produces("image/jpg")
    @UnitOfWork
    public File download(@PathParam("id") LongParam id) {
        return imageFileFacade.downloadImageFile(id.get());
    }
	
}
