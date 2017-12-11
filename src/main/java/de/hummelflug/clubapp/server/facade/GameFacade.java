package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.GameDAO;

public class GameFacade {

	private final GameDAO gameDAO;
	private final TeamScheduleFacade teamScheduleFacade;
	
	public GameFacade(GameDAO gameDAO, TeamScheduleFacade teamScheduleFacade) {
		this.gameDAO = gameDAO;
		this.teamScheduleFacade = teamScheduleFacade;
	}
	
	public Game createGame(User user, Game game) {
		/** Create game to get the game id **/
		Game newGame = gameDAO.insert(new Game(user.getId(), game.getMeetingTime(), game.getStartTime(),
				game.getEndTime(), game.getHostTeamId(), game.getGuestTeamId(), game.getTitle(),
				game.getSportTypeId()));
		
		/** Add organizers **/
		for (Long organizerId : game.getOrganizers()) {
			newGame.getOrganizers().add(organizerId);
		}
		
		/** Add event in team schedule **/
		teamScheduleFacade.addEventByTeamId(user, game.getHostTeamId(), newGame.getId());
		teamScheduleFacade.addEventByTeamId(user, game.getGuestTeamId(), newGame.getId());
		
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
