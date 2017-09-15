package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.TeamSchedule;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.PlayerDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.db.TeamScheduleDAO;

public class TeamFacade {
	
	private final CoachDAO coachDAO;
	private final PlayerDAO playerDAO;
	private final TeamDAO teamDAO;
	private final TeamScheduleDAO teamScheduleDAO;

	public TeamFacade(CoachDAO coachDAO, PlayerDAO playerDAO, TeamDAO teamDAO, TeamScheduleDAO teamScheduleDAO) {
		this.coachDAO = coachDAO;
		this.playerDAO = playerDAO;
		this.teamDAO = teamDAO;
		this.teamScheduleDAO = teamScheduleDAO;
	}
	
	public Team createTeam(Team team) {
		
		/** Create team to get the team id **/
		Team newTeam = teamDAO.insert(new Team(team.getName(), team.getGender(), team.getAgeClass(),
				team.getSportTypeId()));
		
		/** Change current teams and history of coach **/
		if (team.getPlayers() != null) {
			Set<Long> coachIds = team.getCoaches();
			for (Long coachId : coachIds) {
				Optional<Coach> coachOptional = coachDAO.findById(coachId);
				if (coachOptional.isPresent()) {
					Coach coach = coachOptional.get();
					
					coach.getCurrentTeams().add(newTeam.getId());
					coach.getTeamHistory().add(newTeam.getId());
				} else {
					return null;
				}
			}
		}

		/** Change current teams and history of players **/
		if (team.getPlayers() != null) {
			Set<Long> playerIds = team.getPlayers();
			for (Long playerId : playerIds) {
				Optional<Player> playerOptional = playerDAO.findById(playerId);
				if (playerOptional.isPresent()) {
					Player player = playerOptional.get();
					
					player.getCurrentTeams().add(newTeam.getId());
					player.getTeamHistory().add(newTeam.getId());
				} else {
					return null;
				}
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

}
