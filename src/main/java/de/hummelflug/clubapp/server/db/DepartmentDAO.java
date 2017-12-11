package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.Department;

public class DepartmentDAO extends AbstractSuperDAO<Department> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public DepartmentDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all departments stored in the database.
     * 
     * @return list of all departments stored in the database
     */
	public List<Department> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Department.findAll"));
	}
	
	/**
     * Looks for departments whose name contains the passed parameter as a substring.
     * 
     * @param name query parameter
     * @return list of departments whose name contains the passed
     * parameter as a substring.
     */
	public List<Department> findByName(String name) {
		StringBuilder builder = new StringBuilder("%");
		builder.append(name).append("%");
		return list(
				namedQuery("de.hummelflug.clubapp.server.core.Department.findByName")
				.setParameter("name", builder.toString()));
	}
	
	/**
	 * Method looks for a department by newsId
	 * 
	 * @param newsId the newsid of a department we are looking for.
	 * @return Optional containing the found department or an empty Optional
     * otherwise
	 */
	public Optional<Department> findByNewsId(Long newsId) {
		return namedQuery("de.hummelflug.clubapp.server.core.Department.findByNewsId")
				.setParameter("newsId", newsId)
				.setMaxResults(1).uniqueResultOptional();
	}
	
	/**
     * Method looks for a department by id.
     * 
     * @param id the id of a department we are looking for.
     * @return Optional containing the found department or an empty Optional
     * otherwise
     */
	public Optional<Department> findById(Long id) {
		return Optional.ofNullable(get(id));
	}
	
}
