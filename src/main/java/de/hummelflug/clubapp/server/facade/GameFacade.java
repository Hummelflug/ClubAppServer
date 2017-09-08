package de.hummelflug.clubapp.server.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.db.GameDAO;

public class GameFacade {

	private final GameDAO gameDAO;
	private final TeamScheduleFacade teamScheduleFacade;
	
	public GameFacade(GameDAO gameDAO, TeamScheduleFacade teamScheduleFacade) {
		this.gameDAO = gameDAO;
		this.teamScheduleFacade = teamScheduleFacade;
	}
	
	public Game createGame(Game game) {
		//Create game to get the game id
		Game newGame = gameDAO.insert(new Game(game.getStartTime(), game.getEndTime(), game.getHostTeamId(),
				game.getGuestTeamId()));
		
		//Add organizers
		for (Long organizerId : game.getOrganizers()) {
			newGame.getOrganizers().add(organizerId);
		}
		
		//Add event in team schedule
		Set<Long> events = new HashSet<Long>();
		events.add(newGame.getId());
		teamScheduleFacade.addEventsByTeamId(game.getHostTeamId(), events);
		teamScheduleFacade.addEventsByTeamId(game.getGuestTeamId(), events);
		
		gameDAO.commit();
		gameDAO.refresh(newGame);
		
		return newGame;
	}
	
	public List<Game> findAllGames() {
		return gameDAO.findAll();
	}
	
	public Optional<Game> findGameById(Long id) {
		return gameDAO.findById(id);
	}
	
}
