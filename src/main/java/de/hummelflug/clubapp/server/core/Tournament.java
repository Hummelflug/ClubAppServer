package de.hummelflug.clubapp.server.core;

import java.util.Date;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "tournament")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Tournament.findAll",
            query = "select t from Tournament t"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Tournament.findByName",
            query = "select t from Tournament t "
            + "where t.name like :name ")
})
public class Tournament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "creation_time", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	
	@Column(name = "last_modification", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModification;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@ElementCollection
	@CollectionTable(name = "tournament_sport_type", joinColumns = @JoinColumn(name = "tournament_id"))
	@Column(name = "sport_type_id", nullable = false)
	private Set<Long> sportTypes;
	
	@Column(name = "max_num_teams")
	private Integer maxNumTeams;
	
	@Column(name = "max_roster_size")
	private Integer maxRosterSize;
	
	@ElementCollection
	@CollectionTable(name = "tournament_organizer", joinColumns = @JoinColumn(name = "tournament_id"))
	@Column(name = "user_id", nullable = false)
	private Set<Long> organizers;
	
	@ElementCollection
	@CollectionTable(name = "tournament_team", joinColumns = @JoinColumn(name = "tournament_id"))
	@Column(name = "team_id", nullable = false)
	private Set<Long> participants;
	
	@ElementCollection
	@CollectionTable(name = "tournament_game", joinColumns = @JoinColumn(name = "tournament_id"))
	@Column(name = "game_event_id", nullable = false)
	private Set<Long> games;
	
	/**
	 * A no-argument constructor
	 */
	public Tournament() {
	}
	
	/**
	 * A constructor to create a tournament. id, creationTime, lastModification is not passed, cause it's
     * auto-generated by RDBMS.
	 * 
	 * @param name of tournament
	 * @param startDate of tournament
	 * @param endDate of tournament
	 * @param maxNumTeams of tournament
	 * @param maxRosterSize of tournament
	 */
	public Tournament(@Nonnull String name, Date startDate, Date endDate, Integer maxNumTeams,
			Integer maxRosterSize) {
		this.name = checkNotNull(name, "name cannot be null");
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxNumTeams = maxNumTeams;
		this.maxRosterSize = maxRosterSize;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((games == null) ? 0 : games.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModification == null) ? 0 : lastModification.hashCode());
		result = prime * result + ((maxNumTeams == null) ? 0 : maxNumTeams.hashCode());
		result = prime * result + ((maxRosterSize == null) ? 0 : maxRosterSize.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((organizers == null) ? 0 : organizers.hashCode());
		result = prime * result + ((participants == null) ? 0 : participants.hashCode());
		result = prime * result + ((sportTypes == null) ? 0 : sportTypes.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tournament other = (Tournament) obj;
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (games == null) {
			if (other.games != null)
				return false;
		} else if (!games.equals(other.games))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastModification == null) {
			if (other.lastModification != null)
				return false;
		} else if (!lastModification.equals(other.lastModification))
			return false;
		if (maxNumTeams == null) {
			if (other.maxNumTeams != null)
				return false;
		} else if (!maxNumTeams.equals(other.maxNumTeams))
			return false;
		if (maxRosterSize == null) {
			if (other.maxRosterSize != null)
				return false;
		} else if (!maxRosterSize.equals(other.maxRosterSize))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (organizers == null) {
			if (other.organizers != null)
				return false;
		} else if (!organizers.equals(other.organizers))
			return false;
		if (participants == null) {
			if (other.participants != null)
				return false;
		} else if (!participants.equals(other.participants))
			return false;
		if (sportTypes == null) {
			if (other.sportTypes != null)
				return false;
		} else if (!sportTypes.equals(other.sportTypes))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
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
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the lastModification
	 */
	public Date getLastModification() {
		return lastModification;
	}

	/**
	 * @param lastModification the lastModification to set
	 */
	public void setLastModification(Date lastModification) {
		this.lastModification = lastModification;
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return the maxNumTeams
	 */
	public Integer getMaxNumTeams() {
		return maxNumTeams;
	}

	/**
	 * @param maxNumTeams the maxNumTeams to set
	 */
	public void setMaxNumTeams(Integer maxNumTeams) {
		this.maxNumTeams = maxNumTeams;
	}

	/**
	 * @return the maxRosterSize
	 */
	public Integer getMaxRosterSize() {
		return maxRosterSize;
	}

	/**
	 * @param maxRosterSize the maxRosterSize to set
	 */
	public void setMaxRosterSize(Integer maxRosterSize) {
		this.maxRosterSize = maxRosterSize;
	}

	/**
	 * @return the organizers
	 */
	public Set<Long> getOrganizers() {
		return organizers;
	}

	/**
	 * @param organizers the organizers to set
	 */
	public void setOrganizers(Set<Long> organizers) {
		this.organizers = organizers;
	}

	/**
	 * @return the participants
	 */
	public Set<Long> getParticipants() {
		return participants;
	}

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(Set<Long> participants) {
		this.participants = participants;
	}

	/**
	 * @return the games
	 */
	public Set<Long> getGames() {
		return games;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(Set<Long> games) {
		this.games = games;
	}
	
}
