package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.NewsContentDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class TeamNewsContentFacade extends AbstractSuperNewsContentFacade {

	private final TeamDAO teamDAO;
	
	public TeamNewsContentFacade(NewsContentDAO newsContentDAO, TeamDAO teamDAO) {
		super(newsContentDAO);

		this.teamDAO = teamDAO;
	}
	
	private boolean checkTeamPermission(User user, Team team) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || team.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public List<NewsContent> findTeamNewsContent(User user, Long teamId, NewsFilterOption newsFilterOption) {
		if (user != null && teamId != null && newsFilterOption != null) {
			Team team = getTeamById(teamId);
			if (checkTeamPermission(user, team)) {
				return findNewsContent(team.getNews(), newsFilterOption);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}

	private Team getTeamById(Long teamId) {
		if (teamId != null) {
			Optional<Team> teamOptional = teamDAO.findById(teamId);
			if (teamOptional.isPresent()) {
				return teamOptional.get();
			}
		}
		throw new WebApplicationException(400);
	}
	
}
