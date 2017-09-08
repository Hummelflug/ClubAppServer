package de.hummelflug.clubapp.server.core;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.hummelflug.clubapp.server.utils.ScheduleType;

@Entity
@DiscriminatorValue("TEAM")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.TeamSchedule.findAll",
            query = "select t from TeamSchedule t")
})
public class TeamSchedule extends Schedule {
	
	@Column(name = "team_id", nullable = false)
	private Long teamId;
	
	/**
	 * A no-argument constructor
	 */
	public TeamSchedule() {
		super(ScheduleType.TEAM);
	}
	
	/**
	 * 
	 * @param userId user whose schedule is defined
	 */
	public TeamSchedule(Long userId) {
		super(ScheduleType.TEAM);
		
		this.teamId = userId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		TeamSchedule other = (TeamSchedule) obj;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
			return false;
		return true;
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
	
}
