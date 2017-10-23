package de.hummelflug.clubapp.server.facade;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.DepartmentDAO;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class DepartmentNewsFacade extends AbstractSuperNewsFacade {
	
	private final DepartmentDAO departmentDAO;

	public DepartmentNewsFacade(DepartmentDAO departmentDAO, NewsDAO newsDAO) {
		super(newsDAO);
		
		this.departmentDAO = departmentDAO;
	}
	
	private boolean checkDepartmentPermission(User user, Department department) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || department.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	public News addDepartmentNewsReader(User user, Long departmentId, Long newsId) {
		if (user != null && departmentId != null && newsId != null) {
			if (checkDepartmentPermission(user, getDepartmentById(departmentId)) 
					&& !user.getUserRoles().contains(UserRole.ADMIN)) {
				return addNewsReader(user, newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News addDepartmentNewsImage(User user, Long departmentId, Long newsId, InputStream fileInputStream) {
		if (user != null && departmentId != null && newsId != null && fileInputStream != null) {
			if (checkDepartmentPermission(user, getDepartmentById(departmentId))) {
				return this.addNewsImage(newsId, fileInputStream);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News createDepartmentNews(User user, Long departmentId, News news) {
		if (user != null && departmentId != null && news != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department)) {
				News newNews = createNews(user, news);
				
				department.getNews().add(newNews.getId());
				departmentDAO.update(department);
				
				return newNews;
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<News> findDepartmentNews(User user, Long departmentId, NewsFilterOption newsFilterOption) {
		if (user != null && departmentId != null && newsFilterOption != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department)) {
				return findNews(department.getNews(), newsFilterOption);
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
