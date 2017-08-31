package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import de.hummelflug.clubapp.server.core.SportType;
import de.hummelflug.clubapp.server.db.SportTypeDAO;

public class SportTypeFacade {
	
	private final SportTypeDAO sportTypeDAO;

	public SportTypeFacade(SportTypeDAO sportTypeDAO) {
		this.sportTypeDAO = sportTypeDAO;
	};
	
	public SportType createSportType(SportType sportType) {	
		return sportTypeDAO.insert(new SportType(sportType.getName(), sportType.getMaxSquadSize(),
				sportType.getFieldLength(), sportType.getFieldWidth()));
	}
	
	public List<SportType> findAllSportTypes() {
		return sportTypeDAO.findAll();
	}
	
	public Optional<SportType> findSportTypeById(Long id) {
		return sportTypeDAO.findById(id);
	}
	
}
