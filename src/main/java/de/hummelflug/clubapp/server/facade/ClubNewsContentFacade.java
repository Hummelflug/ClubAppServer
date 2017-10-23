package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.NewsContentDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class ClubNewsContentFacade extends AbstractSuperNewsContentFacade {

	private final ClubDAO clubDAO;
	
	public ClubNewsContentFacade(ClubDAO clubDAO, NewsContentDAO newsContentDAO) {
		super(newsContentDAO);
		
		this.clubDAO = clubDAO;
	}
	
	private boolean checkClubPermission(User user, Club club) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || club.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public List<NewsContent> findClubNewsContent(User user, Long clubId, NewsFilterOption newsFilterOption) {
		if (user != null && clubId != null && newsFilterOption != null) {
			Club club = getClubById(clubId);
			if (checkClubPermission(user, club)) {
				return findNewsContent(club.getNews(), newsFilterOption);
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
