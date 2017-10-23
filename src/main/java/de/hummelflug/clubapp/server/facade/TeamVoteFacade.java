package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.db.VoteDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class TeamVoteFacade extends AbstractSuperVoteFacade {
	
	private final TeamDAO teamDAO;

	public TeamVoteFacade(TeamDAO teamDAO, VoteDAO voteDAO) {
		super(voteDAO);

		this.teamDAO = teamDAO;
	}
	
	private boolean checkTeamPermission(User user, Team team) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || team.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public Vote closeTeamVote(User user, Long teamId, Long voteId) {
		if (user != null && teamId != null && voteId != null) {
			Team team = getTeamById(teamId);
			if (checkTeamPermission(user, team)) {
				return closeVote(voteId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public Vote createTeamVote(User user, Long teamId, Vote vote) {
		if (user != null && teamId != null && vote != null && vote.getQuestionText() != null &&
				vote.getAnswers() != null && vote.getVoteParticipants() != null) {
			Team team = getTeamById(teamId);
			if (checkTeamPermission(user, team)) {
				Vote newVote = createVote(user, vote);
			
				team.getNews().add(newVote.getId());
				teamDAO.update(team);
				
				return newVote;
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<Vote> findTeamVotes(User user, Long teamId, NewsFilterOption filter) {
		if (user != null && teamId != null && filter != null) {
			Team team = getTeamById(teamId);
			if (checkTeamPermission(user, team)) {
				return findVotes(team.getNews(), filter);
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
