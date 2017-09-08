package de.hummelflug.clubapp.server.core;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.hummelflug.clubapp.server.utils.ScheduleType;

@Entity
@DiscriminatorValue("USER")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.UserSchedule.findAll",
            query = "select u from UserSchedule u")
})
public class UserSchedule extends Schedule {
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	/**
	 * A no-argument constructor
	 */
	public UserSchedule() {
		super(ScheduleType.USER);
	}
	
	/**
	 * 
	 * @param userId user whose schedule is defined
	 */
	public UserSchedule(Long userId) {
		super(ScheduleType.USER);
		
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserSchedule other = (UserSchedule) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
