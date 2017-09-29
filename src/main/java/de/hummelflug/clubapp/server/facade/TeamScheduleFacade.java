package de.hummelflug.clubapp.server.facade;

import java.util.Arrays;
import java.util.HashSet;
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
import de.hummelflug.clubapp.server.utils.UserRole;

public class TeamScheduleFacade {

	private final CoachDAO coachDAO;
	private final PlayerDAO playerDAO;
	private final TeamDAO teamDAO;
	private final TeamScheduleDAO teamScheduleDAO;
	private final UserScheduleFacade userScheduleFacade;
	
	public TeamScheduleFacade(CoachDAO coachDAO, PlayerDAO playerDAO, TeamDAO teamDAO, TeamScheduleDAO teamScheduleDAO,
			UserScheduleFacade userScheduleFacade) {
		this.coachDAO = coachDAO;
		this.playerDAO = playerDAO;
		this.teamDAO = teamDAO;
		this.teamScheduleDAO = teamScheduleDAO;
		this.userScheduleFacade = userScheduleFacade;
	}
	
	public TeamSchedule createTeamSchedule(TeamSchedule teamSchedule) {
		Optional<Team> teamOptional = teamDAO.findById(teamSchedule.getTeamId());
		if (teamOptional.isPresent()) {
			TeamSchedule newTeamSchedule = teamScheduleDAO.insert(new TeamSchedule(teamSchedule.getTeamId()));
			
			Team team = teamOptional.get();
			
			addEventsToReferencedSchedules(newTeamSchedule, team, teamSchedule.getEvents());
			
			teamScheduleDAO.commit();
			teamScheduleDAO.refresh(newTeamSchedule);
			
			return newTeamSchedule;
		}
		
		throw new WebApplicationException(400);
	}
	
	public TeamSchedule addEventByTeamId(User user, Long teamId, Long eventId) {
		Optional<Team> teamOptional = teamDAO.findById(teamId);
		if (teamOptional.isPresent() && eventId != null) {
			Team team = teamOptional.get();
			if (team.getCoaches().contains(user.getId()) || team.getBoard().contains(user.getId()) 
					|| user.getUserRole().equals(UserRole.ADMIN)) {
				return addEventsByScheduleId(user, team.getTeamScheduleId(), eventId);
			} else {
				throw new WebApplicationException(401);
			}
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	public TeamSchedule addEventsByScheduleId(User user, Long scheduleId, Long eventId) {
		Optional<TeamSchedule> teamScheduleOptional = findTeamScheduleById(scheduleId);
		
		if (teamScheduleOptional.isPresent() && eventId != null) {
			TeamSchedule teamSchedule = teamScheduleOptional.get();
			
			Optional<Team> teamOptional = teamDAO.findById(teamSchedule.getTeamId());
			if (teamOptional.isPresent()) {
				
				Team team = teamOptional.get();
				
				if (team.getCoaches().contains(user.getId()) || user.getUserRole().equals(UserRole.ADMIN) 
						|| user.getUserRole().equals(UserRole.ORGANIZER)) {
					addEventsToReferencedSchedules(teamSchedule, team,
							new HashSet<Long>(Arrays.asList(new Long[] { eventId })));
					
					return teamScheduleDAO.update(teamSchedule);
				} else {
					throw new WebApplicationException(401);
				}
			}
		}
		throw new WebApplicationException(400);
	}
	
	private void addEventsToReferencedSchedules(TeamSchedule teamSchedule, Team team, Set<Long> events) {
		/** Add events to team schedule **/
		for (Long eventId : events) {
			teamSchedule.getEvents().add(eventId);
		}
		
		/** Add events to coaches schedule **/
		addEventsToCoachSchedules(team.getCoaches(), events);
		
		/** Add events to players schedule **/
		addEventsToPlayerSchedules(team.getPlayers(), events);
	}
	
	private void addEventsToCoachSchedules(Set<Long> coachIds, Set<Long> eventIds) {
		for (Long coachId : coachIds) {
			Optional<Coach> coachOptional = coachDAO.findById(coachId);
			if (coachOptional.isPresent()) {
				Coach coach = coachOptional.get();
				userScheduleFacade.addEventsToUserSchedule(coach.getScheduleId(),eventIds);
			}
		}
	}
	
	private void addEventsToPlayerSchedules(Set<Long> playerIds, Set<Long> eventIds) {
		for (Long playerId : playerIds) {
			Optional<Player> playerOptional = playerDAO.findById(playerId);
			if (playerOptional.isPresent()) {
				Player player = playerOptional.get();
				userScheduleFacade.addEventsToUserSchedule(player.getScheduleId(),eventIds);
			}
		}
	}
	
	public List<TeamSchedule> findAllTeamSchedules() {
		return teamScheduleDAO.findAll();
	}
	
	public Optional<TeamSchedule> findTeamScheduleById(Long id) {
		return teamScheduleDAO.findById(id);
	}
	
}
