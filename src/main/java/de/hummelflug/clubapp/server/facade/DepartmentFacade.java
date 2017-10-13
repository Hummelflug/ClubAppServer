package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.DepartmentDAO;
import de.hummelflug.clubapp.server.utils.UserRole;

public class DepartmentFacade {

	private final ClubDAO clubDAO;
	private final DepartmentDAO departmentDAO;
	
	public DepartmentFacade(ClubDAO clubDAO, DepartmentDAO departmentDAO) {
		this.clubDAO = clubDAO;
		this.departmentDAO = departmentDAO;
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
			}
		}
		
		/** Add teams **/
		if (department.getTeams() != null) {
			for (Long teamId : department.getTeams()) {
				newDepartment.getTeams().add(teamId);
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
