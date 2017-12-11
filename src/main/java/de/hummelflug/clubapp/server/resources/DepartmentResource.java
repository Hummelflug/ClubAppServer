package de.hummelflug.clubapp.server.resources;

import java.io.File;
import java.io.InputStream;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataParam;

import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.facade.DepartmentFacade;
import de.hummelflug.clubapp.server.facade.DepartmentNewsContentFacade;
import de.hummelflug.clubapp.server.facade.DepartmentNewsFacade;
import de.hummelflug.clubapp.server.facade.DepartmentVoteFacade;

import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/department")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

	private DepartmentFacade departmentFacade;
	private DepartmentNewsContentFacade departmentNewsContentFacade;
	private DepartmentNewsFacade departmentNewsFacade;
	private DepartmentVoteFacade departmentVoteFacade;
	
	public DepartmentResource(DepartmentFacade departmentFacade, 
			DepartmentNewsContentFacade departmentNewsContentFacade,DepartmentNewsFacade departmentNewsFacade,
			DepartmentVoteFacade departmentVoteFacade) {
		this.departmentFacade = departmentFacade;
		this.departmentNewsContentFacade = departmentNewsContentFacade;
		this.departmentNewsFacade = departmentNewsFacade;
		this.departmentVoteFacade = departmentVoteFacade;
	}
	
	@GET
    @UnitOfWork
    public List<Department> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return departmentFacade.findDepartmentByName(name.get());
        } else {
            return departmentFacade.findAllDepartments();
        }
	}
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Department> findById(@PathParam("id") LongParam id) {
        return departmentFacade.findDepartmentById(id.get());
    }
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Department add(@Auth User user, @Valid Department department) {
    	return departmentFacade.createDepartment(user, department);
    }
	
	@GET
    @Path("/{id}/news_content")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<NewsContent> findNewsContent(@Auth User user, @PathParam("id") LongParam departmentId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return departmentNewsContentFacade.findDepartmentNewsContent(user, departmentId.get(),
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
	
	@GET
    @Path("/news/{newsid}/image.jpg")
	@Produces("image/jpg")
	@PermitAll
    @UnitOfWork
    public File downloadImageByNewsId(@Auth User user, @PathParam("newsid") LongParam newsId) {
        return departmentNewsFacade.downloadImageFileByNewsId(user, newsId.get());
    }
	
	@GET
    @Path("/{id}/news")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<News> findNews(@Auth User user, @PathParam("id") LongParam departmentId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return departmentNewsFacade.findDepartmentNewsById(user, departmentId.get(),
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
	
	@POST
    @Path("/{id}/news")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE, UserRole.Constants.DEP_HEAD_VALUE })
    @UnitOfWork
    public News addDepartmentNews(@Auth User user, @PathParam("id") LongParam departmentId, @Valid News news) {
    	return departmentNewsFacade.createDepartmentNews(user, departmentId.get(), news);
    }
	
	@GET
    @Path("/{departmentid}/news/{newsid}/image.jpg")
	@Produces("image/jpg")
	@PermitAll
    @UnitOfWork
    public File download(@Auth User user, @PathParam("departmentid") LongParam departmentId,
    		@PathParam("newsid") LongParam newsId) {
        return departmentNewsFacade.downloadImageFile(user, departmentId.get(), newsId.get());
    }
	
	@POST
    @Path("/{departmentid}/news/{newsid}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.BOARD_VALUE })
    @UnitOfWork
    public News addDepartmentNewsImage(@Auth User user, @PathParam("departmentid") LongParam departmentId, 
    		@PathParam("newsid") LongParam newsId, @FormDataParam("file") final InputStream inputStream) {
    	return departmentNewsFacade.addDepartmentNewsImage(user, departmentId.get(), newsId.get(), inputStream);
    }
	
	@POST
    @Path("/{departmentid}/news/{newsid}/reader")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public News addDepartmentNewsReader(@Auth User user, @PathParam("departmentid") LongParam departmentId,
    		@PathParam("newsid") LongParam newsId) {
    	return departmentNewsFacade.addDepartmentNewsReader(user, departmentId.get(), newsId.get());
    }
	
	@GET
    @Path("/{id}/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public List<Vote> findVotes(@Auth User user, @PathParam("id") LongParam departmentId, 
    		@QueryParam("filter") Optional<String> filter) {
    	if (filter.isPresent()) {
    		return departmentVoteFacade.findDepartmentVotes(user, departmentId.get(),
    				NewsFilterOption.fromString(filter.get()));
    	}
    	throw new WebApplicationException(400);
    }
	
	@POST
    @Path("/{id}/vote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.DEP_HEAD_VALUE })
    @UnitOfWork
    public Vote addDepartmentVote(@Auth User user, @PathParam("id") LongParam departmentId, @Valid Vote vote) {
    	return departmentVoteFacade.createDepartmentVote(user, departmentId.get(), vote);
    }
	
	@POST
    @Path("/{departmentid}/vote/{voteid}/close")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRole.Constants.ADMIN_VALUE, UserRole.Constants.DEP_HEAD_VALUE })
    @UnitOfWork
    public Vote closeDepartmentVote(@Auth User user, @PathParam("departmentid") LongParam departmentId, 
    		@PathParam("voteid") LongParam voteId) {
    	return departmentVoteFacade.closeDepartmentVote(user, departmentId.get(), voteId.get());
    }
	
	@POST
    @Path("/vote/{voteid}/answer/{answerid}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @UnitOfWork
    public Vote voteForAnswer(@Auth User user, @PathParam("voteid") LongParam voteId,
    		@PathParam("answerid") LongParam answerId) {
    	return departmentVoteFacade.voteForAnswer(user, voteId.get(), answerId.get());
    }
	
}
