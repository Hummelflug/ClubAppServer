package de.hummelflug.clubapp.server.facade;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.DepartmentDAO;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.UserRole;

public class DepartmentNewsFacade extends AbstractSuperNewsFacade {
	
	private final ClubDAO clubDAO;
	private final DepartmentDAO departmentDAO;

	public DepartmentNewsFacade(ClubDAO clubDAO, DepartmentDAO departmentDAO, ImageFileFacade imageFileFacade,
			NewsDAO newsDAO) {
		super(imageFileFacade, newsDAO);
		
		this.clubDAO = clubDAO;
		this.departmentDAO = departmentDAO;
	}
	
	private boolean checkDepartmentPermission(User user, Department department) {
		if (user.getUserRoles().contains(UserRole.ADMIN) || department.getMembers().contains(user.getId())) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidNewsId(Department department, Long newsId) {
		return department.getNews().contains(newsId);
	}
	
	public News addDepartmentNewsReader(User user, Long departmentId, Long newsId) {
		if (user != null && departmentId != null && newsId != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department) && checkValidNewsId(department, newsId)
					&& !user.getUserRoles().contains(UserRole.ADMIN)) {
				return addNewsReader(user, newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News addDepartmentNewsReaderByClubId(User user, Long clubId, Long newsId) {
		if (user != null && clubId != null && newsId != null) {
			Optional<Department> departmentOptional = departmentDAO.findByNewsId(newsId);
			if (departmentOptional.isPresent()) {
				Department department = departmentOptional.get();
				Club club = getClubById(clubId);
				if (checkDepartmentPermission(user, department) && checkValidNewsId(department, newsId)
						&& !user.getUserRoles().contains(UserRole.ADMIN) 
						&& club.getDepartments().contains(department.getId())) {
					return addNewsReader(user, newsId);
				} else {
					throw new WebApplicationException(401);
				}
			}
		}
		throw new WebApplicationException(400);
	}
	
	public News addDepartmentNewsImage(User user, Long departmentId, Long newsId, InputStream fileInputStream) {
		if (user != null && departmentId != null && newsId != null && fileInputStream != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department) && checkValidNewsId(department, newsId)) {
				return this.addNewsImage(user, newsId, fileInputStream);
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
	
	public File downloadImageFile(User user, Long departmentId, Long newsId) {
		if (user != null && departmentId != null && newsId != null) {
			Department department = getDepartmentById(departmentId);
			if (checkDepartmentPermission(user, department) && checkValidNewsId(department, newsId)) {
				return this.downloadNewsImage(newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	

	public File downloadImageFileByNewsId(User user, Long newsId) {
		if (user != null && newsId != null) {
			Department department = getDepartmentByNewsId(newsId);
			if (checkDepartmentPermission(user, department) && checkValidNewsId(department, newsId)) {
				return this.downloadNewsImage(newsId);
			} else {
				throw new WebApplicationException(401);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public List<News>findDepartmentNewsByDepartmentIds(User user, Long clubId, List<Long> departmentIds,
			NewsFilterOption newsFilterOption) {
		if (user != null && departmentIds != null && newsFilterOption != null) {
			Set<Long> news = new HashSet<Long>();
			Club club = getClubById(clubId);
			for (Long departmentId : departmentIds) {
				if (club.getDepartments().contains(departmentId)) {
					Department department = getDepartmentById(departmentId);
					if (checkDepartmentPermission(user, department)) {
						news.addAll(department.getNews());
					} else {
						throw new WebApplicationException(401);
					}
				}
			}
			return findNews(news, newsFilterOption);
		}
		throw new WebApplicationException(400);
	}
	
	public List<News> findDepartmentNewsById(User user, Long departmentId, NewsFilterOption newsFilterOption) {
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
	
	private Club getClubById(Long clubId) {
		if (clubId != null) {
			Optional<Club> clubOptional = clubDAO.findById(clubId);
			if (clubOptional.isPresent()) {
				return clubOptional.get();
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
	
	private Department getDepartmentByNewsId(Long newsId) {
		if (newsId != null) {
			Optional<Department> departmentOptional = departmentDAO.findByNewsId(newsId);
			if (departmentOptional.isPresent()) {
				return departmentOptional.get();
			}
		}
		throw new WebApplicationException(400);
	}
	
}
