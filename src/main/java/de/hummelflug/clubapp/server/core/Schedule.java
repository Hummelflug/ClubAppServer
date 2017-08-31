package de.hummelflug.clubapp.server.core;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.hummelflug.clubapp.server.utils.ScheduleType;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "schedule")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Schedule.findAll",
            query = "select s from Schedule s")
})
public class Schedule extends AbstractModel {
	
	@Column(name = "schedule_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private ScheduleType scheduleType;
	
	/**
	 * A no-argument constructor
	 */
	public Schedule() {
	}
	
	/**
	 * @param scheduleType type of schedule (team or user schedule)
	 */
	public Schedule(@Nonnull ScheduleType scheduleType) {
		this.scheduleType = checkNotNull(scheduleType, "schedule type cannot be null");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModification == null) ? 0 : lastModification.hashCode());
		result = prime * result + ((scheduleType == null) ? 0 : scheduleType.hashCode());
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
		Schedule other = (Schedule) obj;
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
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
		if (scheduleType != other.scheduleType)
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
	 * @return the scheduleType
	 */
	public ScheduleType getScheduleType() {
		return scheduleType;
	}

	/**
	 * @param scheduleType the scheduleType to set
	 */
	public void setScheduleType(ScheduleType scheduleType) {
		this.scheduleType = scheduleType;
	}
	
}
