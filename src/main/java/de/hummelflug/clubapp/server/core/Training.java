package de.hummelflug.clubapp.server.core;

import static com.google.common.base.Preconditions.checkNotNull;

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

@Entity
@Table(name = "training")
@PrimaryKeyJoinColumn(name = "event_id")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Training.findAll",
            query = "select t from Training t")
})
public class Training extends Event {
	
	@Id
	@Column(name = "event_id", nullable = false)
	private Long id;
	
	@Column(name = "team_id", nullable = false)
	private Long teamId;
	
	@ElementCollection
	@CollectionTable(name = "training_exercise", joinColumns = @JoinColumn(name = "training_id"))
	@Column(name = "exercise_id", nullable = false)
	private Set<Long> exercises;
	
	/**
	 * A no-argument constructor
	 */
	public Training() {
		super(EventType.TRAINING);
		
		exercises = new HashSet<Long>();
	}
	
	/**
	 * 
	 * @param creatorUserId user id of creator
	 * @param meetingTime of training
	 * @param startTime of training
	 * @param endTime of training
	 * @param teamId team whose training is defined
	 * @param title of training
	 * @param sportTypeId of the game
	 */
	public Training(@Nonnull Long creatorUserId, @Nonnull Date meetingTime, @Nonnull Date startTime,
			@Nonnull Date endTime, @Nonnull Long teamId, @Nonnull String title, Long sportTypeId) {
		super(creatorUserId, EventType.TRAINING, meetingTime, startTime, endTime, title, sportTypeId);
		
		this.teamId = checkNotNull(teamId, "team id cannot be null");
		
		exercises = new HashSet<Long>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((exercises == null) ? 0 : exercises.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
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
		Training other = (Training) obj;
		if (exercises == null) {
			if (other.exercises != null)
				return false;
		} else if (!exercises.equals(other.exercises))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
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
	 * @return the teamId
	 */
	public Long getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the exercises
	 */
	public Set<Long> getExercises() {
		return exercises;
	}

	/**
	 * @param exercises the exercises to set
	 */
	public void setExercises(Set<Long> exercises) {
		this.exercises = exercises;
	}
	
}
