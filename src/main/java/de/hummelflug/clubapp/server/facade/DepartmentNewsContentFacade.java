package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.DepartmentDAO;
import de.hummelflug.clubapp.server.db.NewsContentDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class DepartmentNewsContentFacade extends AbstractSuperNewsContentFacade {

	private final DepartmentDAO departmentDAO;
	
	public DepartmentNewsContentFacade(DepartmentDAO departmentDAO, NewsContentDAO newsContentDAO) {
		super(newsContentDAO);
		
		this.departmentDAO = departmentDAO;
	}
	
	private boolean checkDepartmentPermission(User user, Department department) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || department.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public List<NewsContent> findDepartmentNewsContent(User user, Long departmentId,
			NewsFilterOption newsFilterOption) {
		if (user != null && departmentId != null && newsFilterOption != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department)) {
				return findNewsContent(department.getNews(), newsFilterOption);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	private Department getDepartmentById(Long departmentId) {
		if (departmentId != null) {
			Optional<Department> departmentOptional = departmentDAO.findById(departmentId);
			if (departmentOptional.isPresent()) {
				return departmentOptional.get();
			}
		}
		throw new WebApplicationException(400);
	}

}
