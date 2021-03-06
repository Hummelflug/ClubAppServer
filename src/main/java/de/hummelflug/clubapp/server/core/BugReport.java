package de.hummelflug.clubapp.server.core;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.hummelflug.clubapp.server.utils.BugReportStatus;

@Entity
@Table(name = "bug_report")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.BugReport.findAll",
            query = "select b from BugReport b"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.BugReport.findAllOpen",
    		query = "select b from BugReport b where b.status = 'CREATED'")
})
public class BugReport extends AbstractModel {
	
	@Column(name = "creator_user_id", nullable = false)
	private Long creatorUserId;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private BugReportStatus status;
	
	/**
	 * A no-argument constuctor
	 */
	public BugReport() {
		this.status = BugReportStatus.CREATED;
	}
	
	/**
	 * A constructor to create a bug report. id, creationTime, lastModification is not passed, cause it's
     * auto-generated by RDBMS.
	 * 
	 * @param creatorUserId user id of the creator
	 * @param title	of bug report
	 * @param description of bug report
	 */
	public BugReport(@Nonnull Long creatorUserId, @Nonnull String title, @Nonnull String description) {
		this.status = BugReportStatus.CREATED;
		
		this.creatorUserId = checkNotNull(creatorUserId, "creator user id cannot be null");
		this.title = checkNotNull(title, "title cannot be null");
		this.description = checkNotNull(description, "description cannot be null");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((creatorUserId == null) ? 0 : creatorUserId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		BugReport other = (BugReport) obj;
		if (creatorUserId == null) {
			if (other.creatorUserId != null)
				return false;
		} else if (!creatorUserId.equals(other.creatorUserId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * @return the creatorUserId
	 */
	public Long getCreatorUserId() {
		return creatorUserId;
	}

	/**
	 * @param creatorUserId the creatorUserId to set
	 */
	public void setCreatorUserId(Long creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public BugReportStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(BugReportStatus status) {
		this.status = status;
	}
	
}
