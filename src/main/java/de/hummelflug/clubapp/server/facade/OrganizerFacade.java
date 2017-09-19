package de.hummelflug.clubapp.server.facade;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.auth.PasswordHashHelper;
import de.hummelflug.clubapp.server.core.Organizer;
import de.hummelflug.clubapp.server.db.OrganizerDAO;

public class OrganizerFacade {

	private final OrganizerDAO organizerDAO;
	
	public OrganizerFacade(OrganizerDAO organizerDAO) {
		this.organizerDAO = organizerDAO;
	}
	
	public Organizer createOrganizer(Organizer organizer) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return organizerDAO.insert(new Organizer(organizer.getLastName(), organizer.getFirstName(),
				organizer.getBirthday(), organizer.getEmail().toLowerCase(), 
				PasswordHashHelper.generatePasswordHash(organizer.getPassword()), organizer.getGender(),
				organizer.getOrganization()));
	}
	
	public List<Organizer> findAllOrganizers() {
		return organizerDAO.findAll();
	}
	
	public Optional<Organizer> findOrganizerById(Long id) {
		return organizerDAO.findById(id);
	}
	
	public List<Organizer> findOrganizerByName(String name) {
		return organizerDAO.findByName(name);
	}
	
}
