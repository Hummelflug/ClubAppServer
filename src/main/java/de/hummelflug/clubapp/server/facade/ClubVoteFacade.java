package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.VoteDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class ClubVoteFacade extends AbstractSuperVoteFacade {
	
	private final ClubDAO clubDAO;

	public ClubVoteFacade(ClubDAO clubDAO, VoteDAO voteDAO) {
		super(voteDAO);
		
		this.clubDAO = clubDAO;
	}
	
	private boolean checkClubPermission(User user, Club club) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || club.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public Vote closeClubVote(User user, Long clubId, Long voteId) {
		if (user != null && clubId != null && voteId != null) {
			Club club = getClubById(clubId);
			if (checkClubPermission(user, club)) {
				return closeVote(voteId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public Vote createClubVote(User user, Long clubId, Vote vote) {
		if (user != null && clubId != null && vote != null && vote.getQuestionText() != null &&
				vote.getAnswers() != null && vote.getVoteParticipants() != null) {
			Club club = getClubById(clubId);
			if (checkClubPermission(user, club)) {
				Vote newVote = createVote(user, vote);
			
				club.getNews().add(newVote.getId());
				clubDAO.update(club);
				
				return newVote;
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<Vote> findClubVotes(User user, Long clubId, NewsFilterOption filter) {
		if (user != null && clubId != null && filter != null) {
			Club club = getClubById(clubId);
			if (checkClubPermission(user, club)) {
				return findVotes(club.getNews(), filter);
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
