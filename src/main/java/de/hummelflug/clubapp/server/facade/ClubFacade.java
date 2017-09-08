package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.PlayerDAO;

public class ClubFacade {

	private final ClubDAO clubDAO;
	private final CoachDAO coachDAO;
	private final PlayerDAO playerDAO;
	
	public ClubFacade(ClubDAO clubDAO, CoachDAO coachDAO, PlayerDAO playerDAO) {
		this.clubDAO = clubDAO;
		this.coachDAO = coachDAO;
		this.playerDAO = playerDAO;
	}
	
	public Club createClub(Club club) {
		
		//Create club to get club id
		Club newClub = clubDAO.insert(new Club(club.getName(), club.getFoundationDate()));
		
		//Add provided sportTypes & teams
		for (Long sportTypeId : club.getProvidedSportTypes()) {
			newClub.getProvidedSportTypes().add(sportTypeId);
		}
		
		for (Long teamId : club.getTeams()) {
			newClub.getTeams().add(teamId);
		}
				
		//Change current clubs and history of coach
		if (club.getPlayers() != null) {
			Set<Long> coachIds = club.getCoaches();
			for (Long coachId : coachIds) {
				Optional<Coach> coachOptional = coachDAO.findById(coachId);
				if (coachOptional.isPresent()) {
					Coach coach = coachOptional.get();
					
					coach.getCurrentClubs().add(newClub.getId());
					coach.getClubHistory().add(newClub.getId());
				} else {
					return null;
				}
			}
		}

		//Change current clubs and history of players
		if (club.getPlayers() != null) {
			Set<Long> playerIds = club.getPlayers();
			for (Long playerId : playerIds) {
				Optional<Player> playerOptional = playerDAO.findById(playerId);
				if (playerOptional.isPresent()) {
					Player player = playerOptional.get();
					
					player.getCurrentClubs().add(newClub.getId());
					player.getClubHistory().add(newClub.getId());
				} else {
					return null;
				}
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
	
}
