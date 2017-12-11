package de.hummelflug.clubapp.server.db;

import java.util.List;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.BugReport;

public class BugReportDAO extends AbstractSuperDAO<BugReport> {

	/**
     * Constructor.
     *
     * @param sessionFactory Hibernate session factory.
     */
	public BugReportDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all bug reports stored in the database.
     * 
     * @return list of all bug reports stored in the database
     */
	public List<BugReport> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.BugReport.findAll"));
	}
	
	/**
     * Method returns all open bug reports stored in the database.
     * 
     * @return list of all open bug reports stored in the database
     */
	public List<BugReport> findAllOpen() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.BugReport.findAllOpen"));
	}
	
	
}
