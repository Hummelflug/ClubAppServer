package de.hummelflug.clubapp.server.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.PlayerDAO;
import de.hummelflug.clubapp.server.db.UserDAO;
import de.hummelflug.clubapp.server.utils.UserRole;

public class ClubFacade {

	private final ClubDAO clubDAO;
	private final CoachDAO coachDAO;
	private final PlayerDAO playerDAO;
	private final UserDAO userDAO;
	
	public ClubFacade(ClubDAO clubDAO, CoachDAO coachDAO, PlayerDAO playerDAO, UserDAO userDAO) {
		this.clubDAO = clubDAO;
		this.coachDAO = coachDAO;
		this.playerDAO = playerDAO;
		this.userDAO = userDAO;
	}
	
	public Club addBoardToClub(User user, Long clubId, Long boardId) {
		if (user != null && clubId != null && boardId != null) {
			Optional<Club> clubOptional = findClubById(clubId);
			if (clubOptional.isPresent() && user.getId() != null) {
				Club club = clubOptional.get();
				
				if (club.getBoard().contains(user.getId()) || user.getUserRoles().contains(UserRole.ADMIN)) {
					addBoardToClubSet(club, boardId);
					return clubDAO.update(club);
				} else {
					throw new WebApplicationException(401);
				}
			}
		} 
		
		throw new WebApplicationException(400);
	}
	
	private void addBoardToClubSet(Club club, Long boardId) {
		Optional<User> userOptional = userDAO.findById(boardId);
		if (userOptional.isPresent()) {
			if (userOptional.get().getUserRoles().contains(UserRole.BOARD)) {
				club.getBoard().add(boardId);
			} else {
				throw new WebApplicationException(401);
			}
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public Club addCoachToClub(User user, Long clubId, Long coachId) {
		if (user != null && clubId != null && coachId != null) {
			Optional<Club> clubOptional = findClubById(clubId);
			if (clubOptional.isPresent() && user.getId() != null) {
				Club club = clubOptional.get();
				
				if (club.getBoard().contains(user.getId()) || club.getCoaches().contains(user.getId()) 
						|| user.getUserRoles().contains(UserRole.ADMIN)) {
					addCoachToClubSet(club, coachId);
					return clubDAO.update(club);
				} else {
					throw new WebApplicationException(401);
				}
			}
		} 
		
		throw new WebApplicationException(400);
	}
	
	private void addCoachToClubSet(Club club, Long coachId) {
		Optional<Coach> coachOptional = coachDAO.findById(coachId);
		if (coachOptional.isPresent()) {
			Coach coach = coachOptional.get();
			
			coach.getCurrentClubsAsCoach().add(club.getId());
			coach.getClubHistoryAsCoach().add(club.getId());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public Club addDepartmentHeadToClub(User user, Long clubId, Long departmentHeadUserId) {
		if (user != null && clubId != null && departmentHeadUserId != null) {
			Optional<Club> clubOptional = findClubById(clubId);
			if (clubOptional.isPresent() && user.getId() != null) {
				Club club = clubOptional.get();
				
				if (club.getBoard().contains(user.getId()) || user.getUserRoles().contains(UserRole.ADMIN)) {
					addDepartmentHeadToClubSet(club, departmentHeadUserId);
					return clubDAO.update(club);
				} else {
					throw new WebApplicationException(401);
				}
			}
		} 
		
		throw new WebApplicationException(400);
	}
	
	private void addDepartmentHeadToClubSet(Club club, Long userId) {
		Optional<User> userOptional = userDAO.findById(userId);
		if (userOptional.isPresent()) {
			if (userOptional.get().getUserRoles().contains(UserRole.DEP_HEAD)) {
				club.getDepartmentHeadUsers().add(userId);
			} else {
				throw new WebApplicationException(401);
			}
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public Club addPlayerToClub(User user, Long clubId, Long playerId) {
		if (user != null && clubId != null && playerId != null) {
			Optional<Club> clubOptional = findClubById(clubId);
			if (clubOptional.isPresent() && user.getId() != null) {
				Club club = clubOptional.get();
				
				if (club.getBoard().contains(user.getId()) || club.getCoaches().contains(user.getId()) 
						|| user.getUserRoles().contains(UserRole.ADMIN)) {
					addPlayerToClubSet(club, playerId);
					return clubDAO.update(club);
				} else {
					throw new WebApplicationException(401);
				}
			}
		} 
		
		throw new WebApplicationException(400);
	}
	
	private void addPlayerToClubSet(Club club, Long playerId) {
		Optional<Player> playerOptional = playerDAO.findById(playerId);
		if (playerOptional.isPresent()) {
			Player player = playerOptional.get();
			
			player.getCurrentClubsAsPlayer().add(club.getId());
			player.getClubHistoryAsPlayer().add(club.getId());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public Club createClub(Long userId, Club club) {
		
		/** Create club to get club id **/
		Club newClub = clubDAO.insert(new Club(userId, club.getName(), club.getFoundationDate()));
		
		/** Add provided sportTypes **/
		if (club.getProvidedSportTypes() != null) {
			for (Long sportTypeId : club.getProvidedSportTypes()) {
				newClub.getProvidedSportTypes().add(sportTypeId);
			}
		}
		
		/** Add departments to club **/
		if (club.getDepartments() != null) {
			for (Long departmentId : club.getDepartments()) {
				newClub.getDepartments().add(departmentId);
			}
		}
		
		/** Add department head to club **/
		if (club.getDepartmentHeadUsers() != null) {
			for (Long depHeadId : club.getDepartmentHeadUsers()) {
				newClub.getDepartmentHeadUsers().add(depHeadId);
			}
		}
		
		/** Add teams to club **/
		if (club.getTeams() != null) {
			for (Long teamId : club.getTeams()) {
				newClub.getTeams().add(teamId);
			}
		}
		
		/** Add board to club **/
		if (club.getBoard() != null) {
			Set<Long> boardIds = club.getBoard();
			if (boardIds.size() == 0) {
				addBoardToClubSet(newClub, userId);
			} else {
				for (Long boardId : boardIds) {
					addBoardToClubSet(newClub, boardId);
				}
			}
		}
			
		/** Change current clubs and history of coach **/
		if (club.getPlayers() != null) {
			Set<Long> coachIds = club.getCoaches();
			for (Long coachId : coachIds) {
				addCoachToClubSet(newClub, coachId);
			}
		}

		/** Change current clubs and history of players **/
		if (club.getPlayers() != null) {
			Set<Long> playerIds = club.getPlayers();
			for (Long playerId : playerIds) {
				addPlayerToClubSet(newClub, playerId);
			}
		}
		
		clubDAO.commit();
		clubDAO.refresh(newClub);
		
		return newClub;
	}
	
	public List<Club> findAllClubs() {
		return clubDAO.findAll();
	}
	
	public Optional<Club> findClubById(Long id) {
		return clubDAO.findById(id);
	}
	
	public List<Club> findClubByName(String name) {
		return clubDAO.findByName(name);
	}
	
	public List<User> findClubBoard(Long clubId) {
		Optional<Club> clubOptional = findClubById(clubId);
		if (clubOptional.isPresent()) {
			return findClubUsers(clubOptional.get().getBoard());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public List<User> findClubCoaches(Long clubId) {
		Optional<Club> clubOptional = findClubById(clubId);
		if (clubOptional.isPresent()) {
			return findClubUsers(clubOptional.get().getCoaches());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public List<User> findClubDepartmentHead(Long clubId) {
		Optional<Club> clubOptional = findClubById(clubId);
		if (clubOptional.isPresent()) {
			return findClubUsers(clubOptional.get().getDepartmentHeadUsers());
		} else {
			throw new WebApplicationException(400);
		}
	}

	public List<User> findClubPlayers(Long clubId) {
		Optional<Club> clubOptional = findClubById(clubId);
		if (clubOptional.isPresent()) {
			return findClubUsers(clubOptional.get().getPlayers());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	private List<User> findClubUsers(Set<Long> userIds) {
		if (userIds != null) {
			List<User> users = new ArrayList<User>();
			for (Long userId : userIds) {
				Optional<User> userOptional = userDAO.findById(userId);
				if (userOptional.isPresent()) {
					users.add(userOptional.get());
				} else {
					throw new WebApplicationException(400);
				}
			}
			return users;
		} else {
			throw new WebApplicationException(400);
		}
	}
}
