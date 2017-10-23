package de.hummelflug.clubapp.server.facade;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class ClubNewsFacade extends AbstractSuperNewsFacade {
	
	private final ClubDAO clubDAO;

	public ClubNewsFacade(ClubDAO clubDAO, NewsDAO newsDAO) {
		super(newsDAO);
		
		this.clubDAO = clubDAO;
	}
	
	private boolean checkClubPermission(User user, Club club) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || club.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public News addClubNewsReader(User user, Long clubId, Long newsId) {
		if (user != null && clubId != null && newsId != null) {
			if (checkClubPermission(user, getClubById(clubId)) && !user.getUserRoles().contains(UserRole.ADMIN)) {
				return addNewsReader(user, newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News addClubNewsImage(User user, Long clubId, Long newsId, InputStream fileInputStream) {
		if (user != null && clubId != null && newsId != null && fileInputStream != null) {
			if (checkClubPermission(user, getClubById(clubId))) {
				return this.addNewsImage(newsId, fileInputStream);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News createClubNews(User user, Long clubId, News news) {
		if (user != null && clubId != null && news != null) {
			Club club = getClubById(clubId);
			if (checkClubPermission(user, club)) {
				News newNews = createNews(user, news);
				
				club.getNews().add(newNews.getId());
				clubDAO.update(club);
				
				return newNews;
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<News> findClubNews(User user, Long clubId, NewsFilterOption filter) {
		if (user != null && clubId != null && filter != null) {
			Club club = getClubById(clubId);
			if (checkClubPermission(user, club)) {
				return findNews(club.getNews(), filter);
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

}
