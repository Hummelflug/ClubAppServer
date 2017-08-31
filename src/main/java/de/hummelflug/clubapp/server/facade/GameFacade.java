package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.db.GameDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;

public class GameFacade {

	private final GameDAO gameDAO;
	private final TeamDAO teamDAO;
	
	public GameFacade(GameDAO gameDAO, TeamDAO teamDAO) {
		this.gameDAO = gameDAO;
		this.teamDAO = teamDAO;
	}
	
	public Game createGame(Game game) {
		//Create game to get the game id
		Game newGame = gameDAO.insert(new Game(game.getStartTime(), game.getEndTime(), game.getHostTeamId(),
				game.getGuestTeamId()));
		
		//Add organizers
		for (Long organizerId : game.getOrganizers()) {
			newGame.getOrganizers().add(organizerId);
		}
		
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
