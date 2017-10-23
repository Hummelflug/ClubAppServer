package de.hummelflug.clubapp.server.resources;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/news")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class NewsResource {
	
	private NewsDAO newsDAO;
	
	public NewsResource(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}
	
	@GET
    @UnitOfWork
    public List<News> findAll() {
        return newsDAO.findAll();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<News> findById(@PathParam("id") LongParam id) {
        return newsDAO.findById(id.get());
    }

}
