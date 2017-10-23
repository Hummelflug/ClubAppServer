package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.DepartmentDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.utils.UserRole;

public class DepartmentFacade {

	private final ClubDAO clubDAO;
	private final DepartmentDAO departmentDAO;
	private final TeamDAO teamDAO;
	
	public DepartmentFacade(ClubDAO clubDAO, DepartmentDAO departmentDAO, TeamDAO teamDAO) {
		this.clubDAO = clubDAO;
		this.departmentDAO = departmentDAO;
		this.teamDAO = teamDAO;
	}
	
	private void addTeamToDepartment(User user, Department department) {
		for (Long teamId : department.getTeams()) {
			Optional<Team> teamOptional = teamDAO.findById(teamId);
			if (teamOptional.isPresent()) {
				Team team = teamOptional.get();
				if (team.getClubId() == department.getClubId()) {
					department.getTeams().add(teamId);
					department.getMembers().addAll(team.getMembers());
				} else {
					throw new WebApplicationException(400);
				}
			} else {
				throw new WebApplicationException(400);
			}
		}
	}
	
	public Department createDepartment(User user, Department department) {
		
		Department newDepartment = departmentDAO.insert(new Department(user.getId(), department.getName(),
				department.getSportTypeId()));
		
		/** Add club id **/
		if (department.getClubId() != null) {
			Optional<Club> clubOptional = clubDAO.findById(department.getClubId());
			if (clubOptional.isPresent()) {
				Club club = clubOptional.get();
				if (club.getBoard().contains(user.getId()) || user.getUserRoles().contains(UserRole.ADMIN)) {
					club.getDepartments().add(newDepartment.getId());
				} else {
					throw new WebApplicationException(401);
				}
			}
			else {
				throw new WebApplicationException(400);
			}
		} else {
			throw new WebApplicationException(400);
		}
		
		/** Add department head **/
		if (department.getHead() != null) {
			for (Long userId : department.getHead()) {
				newDepartment.getHead().add(userId);
				newDepartment.getMembers().add(userId);
			}
		}
		
		/** Add teams **/
		if (department.getTeams() != null) {
			addTeamToDepartment(user, newDepartment);
		}
		
		/** Add news **/
		if (department.getNews() != null) {
			for (Long newsId : department.getNews()) {
				newDepartment.getNews().add(newsId);
			}
		}
		
		departmentDAO.commit();
		departmentDAO.refresh(newDepartment);
		
		return newDepartment;
	}

	public List<Department> findAllDepartments() {
		return departmentDAO.findAll();
	}
	
	public List<Department> findDepartmentByName(String name) {
		return departmentDAO.findByName(name);
	}
	
	public Optional<Department> findDepartmentById(Long id) {
		return departmentDAO.findById(id);
	}
	
}
