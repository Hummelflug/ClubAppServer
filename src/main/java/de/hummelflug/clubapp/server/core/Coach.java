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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import de.hummelflug.clubapp.server.utils.GenderType;
import de.hummelflug.clubapp.server.utils.UserRole;

@Entity
@Table(name = "coach")
@PrimaryKeyJoinColumn(name = "user_id")
@SecondaryTable(name = "user_schedule",
		pkJoinColumns=@PrimaryKeyJoinColumn(name="user_id", referencedColumnName="user_id"))
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Coach.findAll",
            query = "select c from Coach c"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Coach.findByName",
            query = "select c from Coach c "
            + "where c.firstName like :name "
            + "or c.lastName like :name")
})
public class Coach extends User {

	@Column(name = "user_id", nullable = false)
	private Long id;
	
	@Column(name = "position")
	private String position;
	
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	@ElementCollection
	@Column(name = "sport_type_id", nullable = false)
	@CollectionTable(name = "user_sport_type", joinColumns = @JoinColumn(name = "user_id"))
	private Set<Long> sportTypes;
	
	@ElementCollection
	@CollectionTable(name = "coach_current_club", joinColumns = @JoinColumn(name = "coach_id"))
	@Column(name = "club_id", nullable = false)
	private Set<Long> currentClubs;
	
	@ElementCollection
	@CollectionTable(name = "coach_current_team", joinColumns = @JoinColumn(name = "coach_id"))
	@Column(name = "team_id", nullable = false)
	private Set<Long> currentTeams;
	
	@ElementCollection
	@CollectionTable(name = "coach_club_history", joinColumns = @JoinColumn(name = "coach_id"))
	@Column(name = "club_id", nullable = false)
	private Set<Long> clubHistory;
	
	@ElementCollection
	@CollectionTable(name = "coach_team_history", joinColumns = @JoinColumn(name = "coach_id"))
	@Column(name = "team_id", nullable = false)
	private Set<Long> teamHistory;
	
	@Column(table = "user_schedule", name = "schedule_id", nullable = false)
	private Long scheduleId;
	
	/**
	 * A no-argument constructor
	 */
	public Coach() {
		super(UserRole.COACH);
		
		clubHistory = new HashSet<Long>();
		currentClubs = new HashSet<Long>();
		currentTeams = new HashSet<Long>();
		teamHistory = new HashSet<Long>();
		sportTypes = new HashSet<Long>();
	}
	
	/**
	 * @param lastName coach last name
     * @param firstName coach first name
     * @param birthday coach birthday
     * @param email coach email
     * @param password coach password
     * @param gender coach gender
	 * @param position of the coach
	 */
	public Coach(@Nonnull String lastName, @Nonnull String firstName, @Nonnull Date birthday, @Nonnull String email, 
			@Nonnull String password, GenderType gender, String position) {
		super(lastName, firstName, birthday, email, password, gender, UserRole.COACH);
		
		this.position = position;
		
		clubHistory = new HashSet<Long>();
		currentClubs = new HashSet<Long>();
		currentTeams = new HashSet<Long>();
		teamHistory = new HashSet<Long>();
		sportTypes = new HashSet<Long>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clubHistory == null) ? 0 : clubHistory.hashCode());
		result = prime * result + ((currentClubs == null) ? 0 : currentClubs.hashCode());
		result = prime * result + ((currentTeams == null) ? 0 : currentTeams.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((scheduleId == null) ? 0 : scheduleId.hashCode());
		result = prime * result + ((sportTypes == null) ? 0 : sportTypes.hashCode());
		result = prime * result + ((teamHistory == null) ? 0 : teamHistory.hashCode());
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
		Coach other = (Coach) obj;
		if (clubHistory == null) {
			if (other.clubHistory != null)
				return false;
		} else if (!clubHistory.equals(other.clubHistory))
			return false;
		if (currentClubs == null) {
			if (other.currentClubs != null)
				return false;
		} else if (!currentClubs.equals(other.currentClubs))
			return false;
		if (currentTeams == null) {
			if (other.currentTeams != null)
				return false;
		} else if (!currentTeams.equals(other.currentTeams))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (scheduleId == null) {
			if (other.scheduleId != null)
				return false;
		} else if (!scheduleId.equals(other.scheduleId))
			return false;
		if (sportTypes == null) {
			if (other.sportTypes != null)
				return false;
		} else if (!sportTypes.equals(other.sportTypes))
			return false;
		if (teamHistory == null) {
			if (other.teamHistory != null)
				return false;
		} else if (!teamHistory.equals(other.teamHistory))
			return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the sportTypes
	 */
	public Set<Long> getSportTypes() {
		return sportTypes;
	}

	/**
	 * @param sportTypes the sportTypes to set
	 */
	public void setSportTypes(Set<Long> sportTypes) {
		this.sportTypes = sportTypes;
	}

	/**
	 * @return the currentClubs
	 */
	public Set<Long> getCurrentClubs() {
		return currentClubs;
	}

	/**
	 * @param currentClubs the currentClubs to set
	 */
	public void setCurrentClubs(Set<Long> currentClubs) {
		this.currentClubs = currentClubs;
	}

	/**
	 * @return the currentTeams
	 */
	public Set<Long> getCurrentTeams() {
		return currentTeams;
	}

	/**
	 * @param currentTeams the currentTeams to set
	 */
	public void setCurrentTeams(Set<Long> currentTeams) {
		this.currentTeams = currentTeams;
	}

	/**
	 * @return the clubHistory
	 */
	public Set<Long> getClubHistory() {
		return clubHistory;
	}

	/**
	 * @param clubHistory the clubHistory to set
	 */
	public void setClubHistory(Set<Long> clubHistory) {
		this.clubHistory = clubHistory;
	}

	/**
	 * @return the teamHistory
	 */
	public Set<Long> getTeamHistory() {
		return teamHistory;
	}

	/**
	 * @param teamHistory the teamHistory to set
	 */
	public void setTeamHistory(Set<Long> teamHistory) {
		this.teamHistory = teamHistory;
	}

	/**
	 * @return the scheduleId
	 */
	public Long getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

}
