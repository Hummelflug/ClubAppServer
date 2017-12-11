package de.hummelflug.clubapp.server.facade;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.BugReport;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.BugReportDAO;

public class BugReportFacade {
	
	private final BugReportDAO bugReportDAO;
	
	public BugReportFacade(BugReportDAO bugReportDAO) {
		this.bugReportDAO = bugReportDAO;
	}
	
	public BugReport createBugReport(User user, BugReport bugReport) {
		if (user != null && bugReport != null) {
			return bugReportDAO.insert(new BugReport(user.getId(), bugReport.getTitle(), bugReport.getDescription()));
		}
		
		throw new WebApplicationException(400);
	}
	
	public List<BugReport> findAllBugReports() {
		return bugReportDAO.findAll();
	}
	
	public List<BugReport> findAllOpenBugReports() {
		return bugReportDAO.findAllOpen();
	}
	
}
