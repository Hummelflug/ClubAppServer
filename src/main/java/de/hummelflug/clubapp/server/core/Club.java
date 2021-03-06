package de.hummelflug.clubapp.server.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "club")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Club.findAll",
            query = "select c from Club c"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Club.findByName",
            query = "select c from Club c "
            + "where c.name like :name ")
})
public class Club extends AbstractModel {
	
	@Column(name = "creator_user_id", nullable = false)
	private Long creatorUserId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "foundation_date")
	@Temporal(TemporalType.DATE)
	private Date foundationDate;
	
	@ElementCollection
	@CollectionTable(name = "club_member", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "member_user_id", nullable = false)
	private Set<Long> members;
	
	@ElementCollection
	@CollectionTable(name = "club_board", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "board_user_id", nullable = false)
	private Set<Long> board;
	
	@ElementCollection
	@CollectionTable(name = "club_department", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "department_id", nullable = false)
	private Set<Long> departments;
	
	@ElementCollection
	@CollectionTable(name = "club_dep_head", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "dep_head_user_id", nullable = false)
	private Set<Long> departmentHeadUsers;
	
	@ElementCollection
	@CollectionTable(name = "club_team", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "team_id", nullable = false)
	private Set<Long> teams;
	
	@ElementCollection
	@CollectionTable(name = "coach_current_club", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "coach_id", nullable = false)
	private Set<Long> coaches;
	
	@ElementCollection
	@CollectionTable(name = "player_current_club", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "player_id", nullable = false)
	private Set<Long> players;
	
	@ElementCollection
	@CollectionTable(name = "club_sport_type", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "sport_type_id", nullable = false)
	private Set<Long> providedSportTypes;
	
	@ElementCollection
	@CollectionTable(name = "club_news_content", joinColumns = @JoinColumn(name = "club_id"))
	@Column(name = "news_content_id", nullable = false)
	private Set<Long> news;

	/**
	 * A no-argument constructor
	 */
	public Club() {
		board = new HashSet<Long>();
		coaches = new HashSet<Long>();
		departments = new HashSet<Long>();
		departmentHeadUsers = new HashSet<Long>();
		members = new HashSet<Long>();
		news = new HashSet<Long>();
		players = new HashSet<Long>();
		providedSportTypes = new HashSet<Long>();	
		teams = new HashSet<Long>();
	}
	
	/**
     * A constructor to create a user. id, creationTime, lastModification is not passed, cause it's
     * auto-generated by RDBMS.
     * 
     * @param creatorUserId user id of creator
     * @param name name of the club
     * @param foundationDate date of club foundation
     */
	public Club(@Nonnull Long creatorUserId, @Nonnull String name, Date foundationDate) {
		board = new HashSet<Long>();
		coaches = new HashSet<Long>();
		departments = new HashSet<Long>();
		departmentHeadUsers = new HashSet<Long>();
		members = new HashSet<Long>();
		news = new HashSet<Long>();
		players = new HashSet<Long>();
		providedSportTypes = new HashSet<Long>();	
		teams = new HashSet<Long>();
		
		this.creatorUserId = checkNotNull(creatorUserId, "creator user id cannot be null");
		this.name = checkNotNull(name, "name cannot be null");
		this.foundationDate = foundationDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((coaches == null) ? 0 : coaches.hashCode());
		result = prime * result + ((creatorUserId == null) ? 0 : creatorUserId.hashCode());
		result = prime * result + ((departmentHeadUsers == null) ? 0 : departmentHeadUsers.hashCode());
		result = prime * result + ((departments == null) ? 0 : departments.hashCode());
		result = prime * result + ((foundationDate == null) ? 0 : foundationDate.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((news == null) ? 0 : news.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + ((providedSportTypes == null) ? 0 : providedSportTypes.hashCode());
		result = prime * result + ((teams == null) ? 0 : teams.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Club other = (Club) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (coaches == null) {
			if (other.coaches != null)
				return false;
		} else if (!coaches.equals(other.coaches))
			return false;
		if (creatorUserId == null) {
			if (other.creatorUserId != null)
				return false;
		} else if (!creatorUserId.equals(other.creatorUserId))
			return false;
		if (departmentHeadUsers == null) {
			if (other.departmentHeadUsers != null)
				return false;
		} else if (!departmentHeadUsers.equals(other.departmentHeadUsers))
			return false;
		if (departments == null) {
			if (other.departments != null)
				return false;
		} else if (!departments.equals(other.departments))
			return false;
		if (foundationDate == null) {
			if (other.foundationDate != null)
				return false;
		} else if (!foundationDate.equals(other.foundationDate))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (news == null) {
			if (other.news != null)
				return false;
		} else if (!news.equals(other.news))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		if (providedSportTypes == null) {
			if (other.providedSportTypes != null)
				return false;
		} else if (!providedSportTypes.equals(other.providedSportTypes))
			return false;
		if (teams == null) {
			if (other.teams != null)
				return false;
		} else if (!teams.equals(other.teams))
			return false;
		return true;
	}

	/**
	 * @return the creatorUserId
	 */
	public Long getCreatorUserId() {
		return creatorUserId;
	}

	/**
	 * @param creatorUserId the creatorUserId to set
	 */
	public void setCreatorUserId(Long creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the foundationDate
	 */
	public Date getFoundationDate() {
		return foundationDate;
	}

	/**
	 * @param foundationDate the foundationDate to set
	 */
	public void setFoundationDate(Date foundationDate) {
		this.foundationDate = foundationDate;
	}

	/**
	 * @return the members
	 */
	public Set<Long> getMembers() {
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(Set<Long> members) {
		this.members = members;
	}

	/**
	 * @return the board
	 */
	public Set<Long> getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Set<Long> board) {
		this.board = board;
	}

	/**
	 * @return the departments
	 */
	public Set<Long> getDepartments() {
		return departments;
	}

	/**
	 * @param departments the departments to set
	 */
	public void setDepartments(Set<Long> departments) {
		this.departments = departments;
	}

	/**
	 * @return the departmentHeadUsers
	 */
	public Set<Long> getDepartmentHeadUsers() {
		return departmentHeadUsers;
	}

	/**
	 * @param departmentHeadUsers the departmentHeadUsers to set
	 */
	public void setDepartmentHeadUsers(Set<Long> departmentHeadUsers) {
		this.departmentHeadUsers = departmentHeadUsers;
	}

	/**
	 * @return the teams
	 */
	public Set<Long> getTeams() {
		return teams;
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(Set<Long> teams) {
		this.teams = teams;
	}

	/**
	 * @return the coaches
	 */
	public Set<Long> getCoaches() {
		return coaches;
	}

	/**
	 * @param coaches the coaches to set
	 */
	public void setCoaches(Set<Long> coaches) {
		this.coaches = coaches;
	}

	/**
	 * @return the players
	 */
	public Set<Long> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Set<Long> players) {
		this.players = players;
	}

	/**
	 * @return the providedSportTypes
	 */
	public Set<Long> getProvidedSportTypes() {
		return providedSportTypes;
	}

	/**
	 * @param providedSportTypes the providedSportTypes to set
	 */
	public void setProvidedSportTypes(Set<Long> providedSportTypes) {
		this.providedSportTypes = providedSportTypes;
	}

	/**
	 * @return the news
	 */
	public Set<Long> getNews() {
		return news;
	}

	/**
	 * @param news the news to set
	 */
	public void setNews(Set<Long> news) {
		this.news = news;
	}
	
}
