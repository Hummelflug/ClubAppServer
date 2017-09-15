package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.UserDAO;

public class UserFacade {
	
	private final UserDAO userDAO;
	
	public UserFacade(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public List<User> findAll() {
		return userDAO.findAll();
	}
	
	public List<User> findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		return userDAO.findById(id);
	}

	public List<User> findByName(String name) {
		return userDAO.findByName(name);
	}
	
}
