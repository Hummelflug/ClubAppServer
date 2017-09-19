package de.hummelflug.clubapp.server.facade;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hummelflug.clubapp.server.auth.PasswordHashHelper;
import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.PlayerDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.db.UserScheduleDAO;

public class PlayerFacade {

	private final ClubDAO clubDAO;
	private final PlayerDAO playerDAO;
	private final TeamDAO teamDAO;
	private final UserScheduleDAO userScheduleDAO;
	
	public PlayerFacade(ClubDAO clubDAO, PlayerDAO playerDAO, TeamDAO teamDAO, UserScheduleDAO userScheduleDAO) {
		this.clubDAO = clubDAO;
		this.playerDAO = playerDAO;
		this.teamDAO = teamDAO;
		this.userScheduleDAO = userScheduleDAO;
	}
	
	public Player createPlayer(Player player) throws NoSuchAlgorithmException, InvalidKeySpecException {
		/** Create player to get the player id **/
		Player newPlayer = playerDAO.insert(new Player(player.getLastName(), player.getFirstName(), 
				player.getBirthday(), player.getEmail().toLowerCase(), 
				PasswordHashHelper.generatePasswordHash(player.getPassword()), player.getGender(),
				player.getPosition(), player.getShirtNumber()));
		
		/** Add team/club histories & sportTypes **/
		for (Long clubId : player.getClubHistory()) {
			newPlayer.getClubHistory().add(clubId);
		}
		
		for (Long teamId : player.getTeamHistory()) {
			newPlayer.getTeamHistory().add(teamId);
		}
		
		for (Long sportTypeId : player.getSportTypes()) {
			newPlayer.getSportTypes().add(sportTypeId);
		}
		
		/** Add player to teams and clubs if necessary **/
		if (player.getCurrentClubs() != null) {
			Set<Long> clubIds = player.getCurrentClubs();
			for (Long clubId : clubIds) {
				Optional<Club> clubOptional = clubDAO.findById(clubId);
				if (clubOptional.isPresent()) {
					Club club = clubOptional.get();
					club.getPlayers().add(newPlayer.getId());
				} else {
					return null;
				}
			}
		}
		
		if (player.getCurrentTeams() != null) {
			Set<Long> teamIds = player.getCurrentTeams();
			for (Long teamId : teamIds) {
				Optional<Team> teamOptional = teamDAO.findById(teamId);
				if (teamOptional.isPresent()) {
					Team team = teamOptional.get();
					team.getPlayers().add(newPlayer.getId());
				} else {
					return null;
				}
			}
		}
		
		/** Create user schedule **/
		UserSchedule userSchedule = userScheduleDAO.insert(new UserSchedule(newPlayer.getId()));
		newPlayer.setScheduleId(userSchedule.getId());
		
		playerDAO.commit();
		playerDAO.refresh(newPlayer);
		
		return newPlayer;
	}
	
	public List<Player> findAllPlayers() {
		return playerDAO.findAll();
	}
	
	public Optional<Player> findPlayerById(Long id) {
		return playerDAO.findById(id);
	}
	
	public List<Player> findPlayerByName(String name) {
		return playerDAO.findByName(name);
	}
	
}
