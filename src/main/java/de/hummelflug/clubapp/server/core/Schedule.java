package de.hummelflug.clubapp.server.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.hummelflug.clubapp.server.utils.ScheduleType;

@Entity
@Table(name = "schedule")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="schedule_type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Schedule.findAll",
            query = "select s from Schedule s order by s.id asc"),
})
public class Schedule extends AbstractModel {
	
	@Column(name = "schedule_type", nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private ScheduleType scheduleType;
	
	@ElementCollection
	@CollectionTable(name = "schedule_event", joinColumns = @JoinColumn(name = "schedule_id"))
	@Column(name = "event_id", nullable = false)
	private Set<Long> events;
	
	/**
	 * A no-argument constructor
	 */
	public Schedule() {
		events = new HashSet<Long>();
	}
	
	/**
	 * 
	 * @param scheduleType type of schedule
	 */
	public Schedule(ScheduleType scheduleType) {
		events = new HashSet<Long>();
		
		this.scheduleType = scheduleType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((events == null) ? 0 : events.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (scheduleType != other.scheduleType)
			return false;
		return true;
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

	/**
	 * @return the events
	 */
	public Set<Long> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(Set<Long> events) {
		this.events = events;
	}
	
}
