package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.Tournament;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.TournamentDAO;

public class TournamentFacade {

	private final TournamentDAO tournamentDAO;

	public TournamentFacade(TournamentDAO tournamentDAO) {
		this.tournamentDAO = tournamentDAO;
	}
	
	public Tournament createTournament(User user, Tournament tournament) {
		Tournament newTournament = tournamentDAO.insert(new Tournament(user.getId(), tournament.getMeetingTime(),
				tournament.getStartTime(), tournament.getEndTime(), tournament.getTitle(), tournament.getMaxNumTeams(),
				tournament.getMaxRosterSize(), tournament.getSportTypeId()));
		
		for (Long sportTypeId : tournament.getSportTypes()) {
			newTournament.getSportTypes().add(sportTypeId);
		}
		
		for (Long organizerId : tournament.getOrganizers()) {
			newTournament.getOrganizers().add(organizerId);
		}
		
		for (Long teamId : tournament.getParticipants()) {
			newTournament.getParticipants().add(teamId);
		}
		
		for (Long gameId : tournament.getGames()) {
			newTournament.getGames().add(gameId);
		}
		
		tournamentDAO.commit();
		tournamentDAO.refresh(newTournament);
		
		return newTournament;
	}
	
	public List<Tournament> findAllTournaments() {
		return tournamentDAO.findAll();
	}
	
	public Optional<Tournament> findTournamentById(Long id) {
		return tournamentDAO.findById(id);
	}

	public List<Tournament> findTournamentByName(String name) {
		return tournamentDAO.findByName(name);
	}
	
}
