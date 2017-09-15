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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Organizer;
import de.hummelflug.clubapp.server.facade.OrganizerFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/organizer")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizerRessource {

	private OrganizerFacade organizerFacade;
	
	public OrganizerRessource(OrganizerFacade organizerFacade) {
		this.organizerFacade = organizerFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Organizer> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return organizerFacade.findOrganizerByName(name.get());
        } else {
            return organizerFacade.findAllOrganizers();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Organizer> findById(@PathParam("id") LongParam id) {
        return organizerFacade.findOrganizerById(id.get());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Organizer add(@Valid Organizer organizer) {
    	return organizerFacade.createOrganizer(organizer);
    }
	
}
