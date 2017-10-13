package de.hummelflug.clubapp.server.auth;

import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.utils.UserRole;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.hibernate.UnitOfWork;

public class UserAuthorizer implements Authorizer<User> {

	@Override
	@UnitOfWork
	public boolean authorize(User user, String role) {
		if (user.getUserRoles() == null) {
			return false;
		}
		return user.getUserRoles().contains(UserRole.valueOf(role));
	}

}
