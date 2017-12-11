package de.hummelflug.clubapp.server.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.DepartmentDAO;
import de.hummelflug.clubapp.server.db.VoteDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class DepartmentVoteFacade extends AbstractSuperVoteFacade {
	
	private final ClubDAO clubDAO;
	private final DepartmentDAO departmentDAO;

	public DepartmentVoteFacade(ClubDAO clubDAO, DepartmentDAO departmentDAO, VoteDAO voteDAO) {
		super(voteDAO);

		this.clubDAO = clubDAO;
		this.departmentDAO = departmentDAO;
	}
	
	private boolean checkDepartmentPermission(User user, Department department) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || department.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public Vote closeDepartmentVote(User user, Long deparmtentId, Long voteId) {
		if (user != null && deparmtentId != null && voteId != null) {
			Department department = getDepartmentById(deparmtentId);
			if (checkDepartmentPermission(user, department)) {
				return closeVote(voteId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public Vote createDepartmentVote(User user, Long departmentId, Vote vote) {
		if (user != null && departmentId != null && vote != null && vote.getQuestionText() != null &&
				vote.getAnswers() != null && vote.getVoteParticipants() != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department)) {
				Vote newVote = createVote(user, vote);
			
				department.getNews().add(newVote.getId());
				departmentDAO.update(department);
				
				return newVote;
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<Vote> findDepartmentVotes(User user, Long departmentId, NewsFilterOption filter) {
		if (user != null && departmentId != null && filter != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department)) {
				return findVotes(department.getNews(), filter);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<Vote> findDepartmentVoteByDepartmentIds(User user, Long clubId, List<Long> departmentIds,
			NewsFilterOption newsFilterOption) {
		if (user != null && departmentIds != null && newsFilterOption != null) {
			Set<Long> votes = new HashSet<Long>();
			Club club = getClubById(clubId);
			for (Long departmentId : departmentIds) {
				if (club.getDepartments().contains(departmentId)) {
					Department department = getDepartmentById(departmentId);
					if (checkDepartmentPermission(user, department)) {
						votes.addAll(department.getNews());
					} else {
						throw new WebApplicationException(401);
					}
				}
			}
			return findVotes(votes, newsFilterOption);
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
	
	private Department getDepartmentById(Long departmentId) {
		if (departmentId != null) {
			Optional<Department> departmentOptional = departmentDAO.findById(departmentId);
			if (departmentOptional.isPresent()) {
				return departmentOptional.get();
			}
		}
		throw new WebApplicationException(400);
	}

}
