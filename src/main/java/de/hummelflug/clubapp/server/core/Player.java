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

import de.hummelflug.clubapp.server.utils.GenderType;
import de.hummelflug.clubapp.server.utils.UserRole;

@Entity
@Table(name = "player")
@PrimaryKeyJoinColumn(name = "user_id")
@SecondaryTable(name = "user_schedule",
	pkJoinColumns=@PrimaryKeyJoinColumn(name="user_id", referencedColumnName="user_id"))
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Player.findAll",
            query = "select p from Player p"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Player.findByName",
            query = "select p from Player p "
            + "where p.firstName like :name "
            + "or p.lastName like :name")
})
public class Player extends User {
	
	@Column(name = "user_id", nullable = false)
	private Long id;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "shirt_number")
	private Integer shirtNumber;
	
	@ElementCollection
	@CollectionTable(name = "user_sport_type", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "sport_type_id", nullable = false)
	private Set<Long> sportTypes;
	
	@ElementCollection
	@CollectionTable(name = "player_current_club", joinColumns = @JoinColumn(name = "player_id"))
	@Column(name = "club_id", nullable = false)
	private Set<Long> currentClubs;
	
	@ElementCollection
	@CollectionTable(name = "player_current_team", joinColumns = @JoinColumn(name = "player_id"))
	@Column(name = "team_id", nullable = false)
	private Set<Long> currentTeams;
	
	@ElementCollection
	@CollectionTable(name = "player_club_history", joinColumns = @JoinColumn(name = "player_id"))
	@Column(name = "club_id", nullable = false)
	private Set<Long> clubHistory;
	
	@ElementCollection
	@CollectionTable(name = "player_team_history", joinColumns = @JoinColumn(name = "player_id"))
	@Column(name = "team_id", nullable = false)
	private Set<Long> teamHistory;
	
	@Column(table = "user_schedule", name = "schedule_id")
	private Long scheduleId;
	
	/**
	 * A no-argument constructor
	 */
	public Player() {
		super(UserRole.PLAYER);
		clubHistory = new HashSet<Long>();
		teamHistory = new HashSet<Long>();
		sportTypes = new HashSet<Long>();
	}
	
	/**
	 * @param lastName player last name
     * @param firstName player first name
     * @param birthday player birthday
     * @param email player email
     * @param password player password
     * @param gender player gender
	 * @param position of player
	 * @param shirtNumber of player
	 */
	public Player(@Nonnull String lastName, @Nonnull String firstName, @Nonnull Date birthday, @Nonnull String email, 
			@Nonnull String password, GenderType gender, String position, Integer shirtNumber) {
		super(lastName, firstName, birthday, email, password, gender, UserRole.PLAYER);
		this.position = position;
		this.shirtNumber = shirtNumber;
		clubHistory = new HashSet<Long>();
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
		result = prime * result + ((shirtNumber == null) ? 0 : shirtNumber.hashCode());
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
		Player other = (Player) obj;
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
		if (shirtNumber == null) {
			if (other.shirtNumber != null)
				return false;
		} else if (!shirtNumber.equals(other.shirtNumber))
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
	 * @return the shirtNumber
	 */
	public Integer getShirtNumber() {
		return shirtNumber;
	}

	/**
	 * @param shirtNumber the shirtNumber to set
	 */
	public void setShirtNumber(Integer shirtNumber) {
		this.shirtNumber = shirtNumber;
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
