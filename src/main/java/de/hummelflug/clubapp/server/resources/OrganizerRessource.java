package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.Organizer;
import de.hummelflug.clubapp.server.db.OrganizerDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/organizer")
@Produces(MediaType.APPLICATION_JSON)
public class OrganizerRessource {

	private OrganizerDAO organizerDAO;
	
	public OrganizerRessource(OrganizerDAO organizerDAO) {
		this.organizerDAO = organizerDAO;
	}
	
	@GET
    @UnitOfWork
    public List<Organizer> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return organizerDAO.findByName(name.get());
        } else {
            return organizerDAO.findAll();
        }
	}
	
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Organizer> findById(@PathParam("id") LongParam id) {
        return organizerDAO.findById(id.get());
    }
	
}
