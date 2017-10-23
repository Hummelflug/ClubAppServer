package de.hummelflug.clubapp.server.facade;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class TeamNewsFacade extends AbstractSuperNewsFacade {
	
	private final TeamDAO teamDAO;
	
	public TeamNewsFacade(NewsDAO newsDAO, TeamDAO teamDAO) {
		super(newsDAO);
		
		this.teamDAO = teamDAO;
	}
	
	private boolean checkTeamPermission(User user, Team team) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || team.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public News addTeamNewsReader(User user, Long teamId, Long newsId) {
		if (user != null && teamId != null && newsId != null) {
			if (checkTeamPermission(user, getTeamById(teamId)) && !user.getUserRoles().contains(UserRole.ADMIN)) {
				return addNewsReader(user, newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News addTeamNewsImage(User user, Long teamId, Long newsId, InputStream fileInputStream) {
		if (user != null && teamId != null && newsId != null && fileInputStream != null) {
			if (checkTeamPermission(user, getTeamById(teamId))) {
				return this.addNewsImage(newsId, fileInputStream);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News createTeamNews(User user, Long teamId, News news) {
		if (user != null && teamId != null && news != null) {
			Team team = getTeamById(teamId);
			if (checkTeamPermission(user, team)) {
				News newNews = createNews(user, news);
				
				team.getNews().add(newNews.getId());
				teamDAO.update(team);
				
				return newNews;
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<News> findTeamNews(User user, Long teamId, NewsFilterOption newsFilterOption) {
		if (user != null && teamId != null && newsFilterOption != null) {
			Team team = getTeamById(teamId);
			if (checkTeamPermission(user, team)) {
				return findNews(team.getNews(), newsFilterOption);
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
