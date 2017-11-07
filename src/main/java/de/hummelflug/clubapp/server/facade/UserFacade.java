package de.hummelflug.clubapp.server.facade;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.auth.PasswordHashHelper;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.UserDAO;
import de.hummelflug.clubapp.server.utils.UserRole;

public class UserFacade {
	
	private final UserDAO userDAO;
	
	public UserFacade(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public User createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (user != null) {
			if (user.getUserRoles() != null && user.getUserRoles().size() > 0) {
				Iterator<UserRole> userRoleIterator = user.getUserRoles().iterator();
				User newUser = userDAO.insert(new User(user.getLastName(), user.getFirstName(), user.getBirthday(),
						user.getEmail(), PasswordHashHelper.generatePasswordHash(user.getPassword()), user.getGender(),
						user.getPhone(), user.getStreet(), user.getPostcode(), user.getCity(),
						userRoleIterator.next()));
				
				while(userRoleIterator.hasNext()) {
					newUser.getUserRoles().add(userRoleIterator.next());
				}
				return userDAO.update(newUser);
			}
		}
		throw new WebApplicationException(400);
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
