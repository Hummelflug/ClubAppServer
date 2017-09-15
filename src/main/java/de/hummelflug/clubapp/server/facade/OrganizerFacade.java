package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.Organizer;
import de.hummelflug.clubapp.server.db.OrganizerDAO;

public class OrganizerFacade {

	private final OrganizerDAO organizerDAO;
	
	public OrganizerFacade(OrganizerDAO organizerDAO) {
		this.organizerDAO = organizerDAO;
	}
	
	public Organizer createOrganizer(Organizer organizer) {
		return organizerDAO.insert(new Organizer(organizer.getLastName(), organizer.getFirstName(),
				organizer.getBirthday(), organizer.getEmail().toLowerCase(), organizer.getPassword(),
				organizer.getGender(), organizer.getOrganization()));
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
