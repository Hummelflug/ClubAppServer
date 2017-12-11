package de.hummelflug.clubapp.server.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import de.hummelflug.clubapp.server.utils.EventType;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "tournament")
@PrimaryKeyJoinColumn(name = "event_id")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Tournament.findAll",
            query = "select t from Tournament t"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Tournament.findByName",
            query = "select t from Tournament t "
            + "where t.title like :name ")
})
public class Tournament extends Event {
	
	@Id
	@Column(name = "event_id", nullable = false)
	private Long id;
	
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
		super(EventType.TOURNAMENT);
		
		sportTypes = new HashSet<Long>();
		organizers = new HashSet<Long>();
		participants = new HashSet<Long>();
		games = new HashSet<Long>();
	}
	
	/**
	 * A constructor to create a tournament. id, creationTime, lastModification is not passed, cause it's
     * auto-generated by RDBMS.
	 * 
	 * @param creatorUserId user id of creator
	 * @param meetingTime of tournament
	 * @param startTime of tournament
	 * @param endTime of tournament
	 * @param title of tournament
	 * @param maxNumTeams of tournament
	 * @param maxRosterSize of tournament
	 * @param sportTypeId of the game
	 */
	public Tournament(@Nonnull Long creatorUserId, @Nonnull Date meetingTime, @Nonnull Date startTime,
			@Nonnull Date endTime, @Nonnull String title, Integer maxNumTeams, Integer maxRosterSize,
			Long sportTypeId) {
		super(creatorUserId, EventType.TOURNAMENT, meetingTime, startTime, endTime, title, sportTypeId);
		this.maxNumTeams = maxNumTeams;
		this.maxRosterSize = maxRosterSize;
		
		sportTypes = new HashSet<Long>();
		organizers = new HashSet<Long>();
		participants = new HashSet<Long>();
		games = new HashSet<Long>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((games == null) ? 0 : games.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maxNumTeams == null) ? 0 : maxNumTeams.hashCode());
		result = prime * result + ((maxRosterSize == null) ? 0 : maxRosterSize.hashCode());
		result = prime * result + ((organizers == null) ? 0 : organizers.hashCode());
		result = prime * result + ((participants == null) ? 0 : participants.hashCode());
		result = prime * result + ((sportTypes == null) ? 0 : sportTypes.hashCode());
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
		Tournament other = (Tournament) obj;
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
