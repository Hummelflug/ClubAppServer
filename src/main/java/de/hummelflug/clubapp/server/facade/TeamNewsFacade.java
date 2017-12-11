package de.hummelflug.clubapp.server.facade;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class TeamNewsFacade extends AbstractSuperNewsFacade {
	
	private final ClubDAO clubDAO;
	private final TeamDAO teamDAO;
	
	public TeamNewsFacade(ClubDAO clubDAO, ImageFileFacade imageFileFacade, NewsDAO newsDAO, TeamDAO teamDAO) {
		super(imageFileFacade, newsDAO);
		
		this.clubDAO = clubDAO;
		this.teamDAO = teamDAO;
	}
	
	private boolean checkTeamPermission(User user, Team team) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || team.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidNewsId(Team team, Long newsId) {
		return team.getNews().contains(newsId);
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
	
	public News addTeamNewsReaderByClubId(User user, Long clubId, Long newsId) {
		if (user != null && clubId != null && newsId != null) {
			Optional<Team> teamOptional = teamDAO.findByNewsId(newsId);
			if (teamOptional.isPresent()) {
				Team team = teamOptional.get();
				Club club = getClubById(clubId);
				if (checkTeamPermission(user, team) && checkValidNewsId(team, newsId)
						&& !user.getUserRoles().contains(UserRole.ADMIN) 
						&& club.getTeams().contains(team.getId())) {
					return addNewsReader(user, newsId);
				} else {
					throw new WebApplicationException(401);
				}
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News addTeamNewsImage(User user, Long teamId, Long newsId, InputStream fileInputStream) {
		if (user != null && teamId != null && newsId != null && fileInputStream != null) {
			if (checkTeamPermission(user, getTeamById(teamId))) {
				return this.addNewsImage(user, newsId, fileInputStream);
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

	public File downloadImageFile(User user, Long teamId, Long newsId) {
		if (user != null && teamId != null && newsId != null) {
			Team team = getTeamById(teamId);
			if (checkTeamPermission(user, team) && checkValidNewsId(team, newsId)) {
				return this.downloadNewsImage(newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public File downloadImageFileByNewsId(User user, Long newsId) {
		if (user != null && newsId != null) {
			Team team = getTeamByNewsId(newsId);
			if (checkTeamPermission(user, team) && checkValidNewsId(team, newsId)) {
				return this.downloadNewsImage(newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<News> findTeamNewsByTeamIds(User user, Long clubId, List<Long> teamIds,
			NewsFilterOption newsFilterOption) {
		if (user != null && teamIds != null && newsFilterOption != null) {
			Set<Long> news = new HashSet<Long>();
			Club club = getClubById(clubId);
			for (Long teamId :teamIds) {
				if (club.getTeams().contains(teamId)) {
					Team team = getTeamById(teamId);
					if (checkTeamPermission(user, team)) {
						news.addAll(team.getNews());
					} else {
						throw new WebApplicationException(401);
					}
				}
			}
			return findNews(news, newsFilterOption);
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
	
	private Club getClubById(Long clubId) {
		if (clubId != null) {
			Optional<Club> clubOptional = clubDAO.findById(clubId);
			if (clubOptional.isPresent()) {
				return clubOptional.get();
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
	
	private Team getTeamByNewsId(Long newsId) {
		if (newsId != null) {
			Optional<Team> departmentOptional = teamDAO.findByNewsId(newsId);
			if (departmentOptional.isPresent()) {
				return departmentOptional.get();
			}
		}
		throw new WebApplicationException(400);
	}	

}
