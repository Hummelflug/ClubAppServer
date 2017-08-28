package de.hummelflug.clubapp.server.core;

import java.util.Date;
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

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "game")
@PrimaryKeyJoinColumn(name = "event_id")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Game.findAll",
            query = "select g from Game g"),
})
public class Game extends Event {

	@Id
	@Column(name = "event_id", nullable = false)
	private Long id;
	
	@ElementCollection
	@CollectionTable(name = "game_organizer", joinColumns = @JoinColumn(name = "game_id"))
	@Column(name = "user_id", nullable = false)
	private Set<Long> organizers;

	@Column(name = "host_team_id", nullable = false)
	private Long hostTeamId;
	
	@Column(name = "guest_team_id", nullable = false)
	private Long guestTeamId;
	
	/**
	 * A no-argument constructor
	 */
	public Game() {
	}
	
	/**
	 * A constructor to create a game. id, creationTime, lastModification is not passed, cause it's
     * auto-generated by RDBMS.
	 * 
	 * @param startTime of the game
	 * @param endTime of the game
	 * @param hostTeamId of the game
	 * @param guestTeamId of the game
	 */
	public Game(@Nonnull Date startTime, @Nonnull Date endTime, @Nonnull Long hostTeamId
			, @Nonnull Long guestTeamId) {
		super(startTime, endTime);
		this.hostTeamId = checkNotNull(hostTeamId, "host team id cannot be null");
		this.guestTeamId = checkNotNull(guestTeamId, "guest team id cannot be null");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((guestTeamId == null) ? 0 : guestTeamId.hashCode());
		result = prime * result + ((hostTeamId == null) ? 0 : hostTeamId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((organizers == null) ? 0 : organizers.hashCode());
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
		Game other = (Game) obj;
		if (guestTeamId == null) {
			if (other.guestTeamId != null)
				return false;
		} else if (!guestTeamId.equals(other.guestTeamId))
			return false;
		if (hostTeamId == null) {
			if (other.hostTeamId != null)
				return false;
		} else if (!hostTeamId.equals(other.hostTeamId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (organizers == null) {
			if (other.organizers != null)
				return false;
		} else if (!organizers.equals(other.organizers))
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
	 * @return the hostTeamId
	 */
	public Long getHostTeamId() {
		return hostTeamId;
	}

	/**
	 * @param hostTeamId the hostTeamId to set
	 */
	public void setHostTeamId(Long hostTeamId) {
		this.hostTeamId = hostTeamId;
	}

	/**
	 * @return the guestTeamId
	 */
	public Long getGuestTeamId() {
		return guestTeamId;
	}

	/**
	 * @param guestTeamId the guestTeamId to set
	 */
	public void setGuestTeamId(Long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}
	
}
