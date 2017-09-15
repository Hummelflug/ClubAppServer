package de.hummelflug.clubapp.server.auth;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

public class UserAuthenticator implements Authenticator<BasicCredentials, User> {

	private final UserDAO userDAO;
	
	public UserAuthenticator(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
	@UnitOfWork
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		List<User> userList = userDAO.findByEmail(credentials.getUsername().toLowerCase());
		
		if (userList.size() == 1) {
			User user = userList.get(0);
			if (user != null) {
				if (credentials.getPassword().equals(user.getPassword())) {
					return Optional.of(user);
				}
			}
		}
		return Optional.empty();
	}

}
