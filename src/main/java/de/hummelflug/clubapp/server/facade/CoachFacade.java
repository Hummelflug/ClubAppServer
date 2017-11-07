package de.hummelflug.clubapp.server.facade;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hummelflug.clubapp.server.auth.PasswordHashHelper;
import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.db.UserScheduleDAO;

public class CoachFacade {

	private final ClubDAO clubDAO;
	private final CoachDAO coachDAO;
	private final TeamDAO teamDAO;
	private final UserScheduleDAO userScheduleDAO;
	
	public CoachFacade(ClubDAO clubDAO, CoachDAO coachDAO, TeamDAO teamDAO, UserScheduleDAO userScheduleDAO) {
		this.clubDAO = clubDAO;
		this.coachDAO = coachDAO;
		this.teamDAO = teamDAO;
		this.userScheduleDAO = userScheduleDAO;
	}
	
	public Coach createCoach(Coach coach) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		/** Create coach to get the coach id **/
		Coach newCoach = coachDAO.insert(new Coach(coach.getLastName(), coach.getFirstName(), coach.getBirthday(),
				coach.getEmail().toLowerCase(), PasswordHashHelper.generatePasswordHash(coach.getPassword()), 
				coach.getGender(), coach.getPosition(), coach.getPhone(), coach.getStreet(), coach.getPostcode(),
				coach.getCity()));
		
		/** Add team/club histories & sportTypes **/
		for (Long clubId : coach.getClubHistoryAsCoach()) {
			newCoach.getClubHistoryAsCoach().add(clubId);
		}
		
		for (Long teamId : coach.getTeamHistoryAsCoach()) {
			newCoach.getTeamHistoryAsCoach().add(teamId);
		}
		
		for (Long sportTypeId : coach.getSportTypes()) {
			newCoach.getSportTypes().add(sportTypeId);
		}
		
		coachDAO.update(newCoach);
		
		/** Add coach to teams and clubs if necessary **/
		if (coach.getCurrentClubsAsCoach() != null) {
			Set<Long> clubIds = coach.getCurrentClubsAsCoach();
			for (Long clubId : clubIds) {
				Optional<Club> clubOptional = clubDAO.findById(clubId);
				if (clubOptional.isPresent()) {
					Club club = clubOptional.get();
					club.getCoaches().add(newCoach.getId());
				} else {
					return null;
				}
			}
		}
		
		if (coach.getCurrentTeamsAsCoach() != null) {
			Set<Long> teamIds = coach.getCurrentTeamsAsCoach();
			for (Long teamId : teamIds) {
				Optional<Team> teamOptional = teamDAO.findById(teamId);
				if (teamOptional.isPresent()) {
					Team team = teamOptional.get();
					team.getCoaches().add(newCoach.getId());
				} else {
					return null;
				}
			}
		}
		
		/**Create user schedule **/
		UserSchedule userSchedule = userScheduleDAO.insert(new UserSchedule(newCoach.getId()));
		newCoach.setScheduleId(userSchedule.getId());
		
		coachDAO.commit();
		coachDAO.refresh(newCoach);
		
		return newCoach;
	}
	
	public List<Coach> findAllCoaches() {
		return coachDAO.findAll();
	}
	
	public Optional<Coach> findCoachById(Long id) {
		return coachDAO.findById(id);
	}
	
	public List<Coach> findCoachByName(String name) {
		return coachDAO.findByName(name);
	}
	
}
