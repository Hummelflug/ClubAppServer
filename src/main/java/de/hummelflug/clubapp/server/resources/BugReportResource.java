package de.hummelflug.clubapp.server.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hummelflug.clubapp.server.core.BugReport;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.facade.BugReportFacade;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/bug_report")
@RolesAllowed(UserRole.Constants.ADMIN_VALUE)
@Produces(MediaType.APPLICATION_JSON)
public class BugReportResource {

	private BugReportFacade bugReportFacade;
	
	public BugReportResource(BugReportFacade bugReportFacade) {
		this.bugReportFacade = bugReportFacade;
	}
	
	@GET
    @UnitOfWork
    public List<BugReport> findAll() {
        return bugReportFacade.findAllBugReports();
	}
	
	@GET
	@Path("/open")
    @UnitOfWork
    public List<BugReport> findAllOpen() {
        return bugReportFacade.findAllOpenBugReports();
	}
	
	@POST
	@PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public BugReport add(@Auth User user, @Valid BugReport bugReport) {
		return bugReportFacade.createBugReport(user, bugReport);
	}
	
}
