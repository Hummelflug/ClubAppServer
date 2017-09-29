package de.hummelflug.clubapp.server.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.TeamSchedule;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.PlayerDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.db.TeamScheduleDAO;
import de.hummelflug.clubapp.server.db.UserDAO;
import de.hummelflug.clubapp.server.utils.UserRole;

public class TeamFacade {
	
	private final CoachDAO coachDAO;
	private final PlayerDAO playerDAO;
	private final TeamDAO teamDAO;
	private final TeamScheduleDAO teamScheduleDAO;
	private final UserDAO userDAO;

	public TeamFacade(CoachDAO coachDAO, PlayerDAO playerDAO, TeamDAO teamDAO, TeamScheduleDAO teamScheduleDAO,
			UserDAO userDAO) {
		this.coachDAO = coachDAO;
		this.playerDAO = playerDAO;
		this.teamDAO = teamDAO;
		this.teamScheduleDAO = teamScheduleDAO;
		this.userDAO = userDAO;
	}
	
	public Team addBoardToTeam(User user, Long teamId, Long boardId) {
		if (user != null && teamId != null && boardId != null) {
			Optional<Team> teamOptional = findTeamById(teamId);
			if (teamOptional.isPresent() && user.getId() != null) {
				Team team = teamOptional.get();
				
				if (team.getBoard().contains(user.getId()) || user.getUserRole().equals(UserRole.ADMIN)) {
					addBoardToTeamSet(team, boardId);
					return teamDAO.update(team);
				} else {
					throw new WebApplicationException(401);
				}
			}
		} 
		
		throw new WebApplicationException(400);
	}
	
	private void addBoardToTeamSet(Team team, Long boardId) {
		Optional<User> userOptional = userDAO.findById(boardId);
		if (userOptional.isPresent()) {
			if (userOptional.get().getUserRole().equals(UserRole.BOARD)) {
				team.getBoard().add(boardId);
			} else {
				throw new WebApplicationException(401);
			}
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public Team addCoachToTeam(User user, Long teamId, Long coachId) {
		if (user != null && teamId != null && coachId != null) {
			Optional<Team> teamOptional = findTeamById(teamId);
			if (teamOptional.isPresent() && user.getId() != null) {
				Team team = teamOptional.get();
				
				if (team.getBoard().contains(user.getId()) || team.getCoaches().contains(user.getId()) 
						|| user.getUserRole().equals(UserRole.ADMIN)) {
					addCoachToTeamSet(team, coachId);
					return teamDAO.update(team);
				} else {
					throw new WebApplicationException(401);
				}
			}
		} 
		
		throw new WebApplicationException(400);
	}
	
	private void addCoachToTeamSet(Team team, Long coachId) {
		Optional<Coach> coachOptional = coachDAO.findById(coachId);
		if (coachOptional.isPresent()) {
			Coach coach = coachOptional.get();
			
			coach.getCurrentTeams().add(team.getId());
			coach.getTeamHistory().add(team.getId());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public Team addPlayerToTeam(User user, Long teamId, Long playerId) {
		if (user != null && teamId != null && playerId != null) {
			Optional<Team> teamOptional = findTeamById(teamId);
			if (teamOptional.isPresent() && user.getId() != null) {
				Team team = teamOptional.get();
				
				if (team.getBoard().contains(user.getId()) || team.getCoaches().contains(user.getId()) 
						|| user.getUserRole().equals(UserRole.ADMIN)) {
					addPlayerToTeamSet(team, playerId);
					return teamDAO.update(team);
				} else {
					throw new WebApplicationException(401);
				}
			}
		}
		throw new WebApplicationException(400);
	}
	
	private void addPlayerToTeamSet(Team team, Long playerId) {
		Optional<Player> playerOptional = playerDAO.findById(playerId);
		if (playerOptional.isPresent()) {
			Player player = playerOptional.get();
			
			player.getCurrentTeams().add(team.getId());
			player.getTeamHistory().add(team.getId());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public Team createTeam(Long userId, Team team) {
		
		/** Create team to get the team id **/
		Team newTeam = teamDAO.insert(new Team(userId, team.getName(), team.getGender(), team.getAgeClass(),
				team.getSportTypeId()));
		
		/** Add board to team **/
		if (team.getBoard() != null) {
			Set<Long> boardIds = team.getBoard();
			if (boardIds.size() == 0) {
				addBoardToTeamSet(newTeam, userId);
			} else {
				for (Long boardId : boardIds) {
					addBoardToTeamSet(newTeam, boardId);
				}
			}
		}
		
		/** Change current teams and history of coach **/
		if (team.getCoaches() != null) {
			Set<Long> coachIds = team.getCoaches();
			for (Long coachId : coachIds) {
				addCoachToTeamSet(newTeam, coachId);
			}
		}

		/** Change current teams and history of players **/
		if (team.getPlayers() != null) {
			Set<Long> playerIds = team.getPlayers();
			for (Long playerId : playerIds) {
				addPlayerToTeamSet(newTeam, playerId);
			}
		}
		
		teamDAO.update(newTeam);
		
		/** Create team schedule **/
		TeamSchedule teamSchedule = teamScheduleDAO.insert(new TeamSchedule(newTeam.getId()));
		newTeam.setTeamScheduleId(teamSchedule.getId());
		
		teamDAO.commit();
		teamDAO.refresh(newTeam);
		
		return newTeam;
	}
	
	public List<Team> findAllTeams() {
		return teamDAO.findAll();
	}
	
	public Optional<Team> findTeamById(Long id) {
		return teamDAO.findById(id);
	}
	
	public List<Team> findTeamByName(String name) {
		return teamDAO.findByName(name);
	}
	
	public List<User> findTeamBoard(Long teamId) {
		Optional<Team> teamOptional = findTeamById(teamId);
		if (teamOptional.isPresent()) {
			return findTeamUsers(teamOptional.get().getBoard());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public List<User> findTeamCoaches(Long teamId) {
		Optional<Team> teamOptional = findTeamById(teamId);
		if (teamOptional.isPresent()) {
			return findTeamUsers(teamOptional.get().getCoaches());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public List<User> findTeamPlayers(Long teamId) {
		Optional<Team> teamOptional = findTeamById(teamId);
		if (teamOptional.isPresent()) {
			return findTeamUsers(teamOptional.get().getPlayers());
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	private List<User> findTeamUsers(Set<Long> userIds) {
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
