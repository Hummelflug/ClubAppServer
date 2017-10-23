package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.db.NewsContentDAO;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/news_content")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class NewsContentResource {

	private NewsContentDAO newsContentDAO;
	
	public NewsContentResource(NewsContentDAO newsContentDAO) {
		this.newsContentDAO = newsContentDAO;
	}
	
	@GET
    @UnitOfWork
    public List<NewsContent> findAll() {
        return newsContentDAO.findAll();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<NewsContent> findById(@PathParam("id") LongParam id) {
        return newsContentDAO.findById(id.get());
    }
	
}
