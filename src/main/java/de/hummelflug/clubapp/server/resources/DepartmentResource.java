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

import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.DepartmentFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/department")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

	private DepartmentFacade departmentFacade;
	
	public DepartmentResource(DepartmentFacade departmentFacade) {
		this.departmentFacade = departmentFacade;
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
	
}
