package de.hummelflug.clubapp.server.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.hummelflug.clubapp.server.core.ImageFile;

public class ImageFileDAO extends AbstractSuperDAO<ImageFile> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public ImageFileDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
     * Method returns all imageFiles stored in the database.
     * 
     * @return list of all imageFiles stored in the database
     */
	public List<ImageFile> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.ImageFile.findAll"));
	}
	
	/**
     * Method looks for a imageFile by id.
     * 
     * @param id the id of a imageFile we are looking for.
     * @return Optional containing the found imageFile or an empty Optional
     * otherwise
     */
	public Optional<ImageFile> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

}
