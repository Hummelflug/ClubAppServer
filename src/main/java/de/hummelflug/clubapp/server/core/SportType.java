package de.hummelflug.clubapp.server.core;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "sport_type")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.SportType.findAll",
            query = "select s from SportType s"),
})
public class SportType extends AbstractModel {
	
	@Column(name = "creator_user_id", nullable = false)
	private Long creatorUserId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "max_squad_size")
	private String maxSquadSize;
	
	@Column(name = "field_length")
	private String fieldLength;
	
	@Column(name = "field_width")
	private String fieldWidth;
	
	/**
	 * A no-argument constructor
	 */
	public SportType() {
	}

	/**
     * A constructor to create a user. id, creationTime, lastModification is not passed, cause it's
     * auto-generated by RDBMS.
     * 
     * @param creatorUserId user id of creator
     * @param name name of sport type
     * @param maxSquadSize maximum size of the squad
     * @param fieldLength length of the playing field
     * @param fieldWidth width of the playing field
     */
	public SportType(@Nonnull Long creatorUserId, @Nonnull String name, String maxSquadSize, String fieldLength,
			String fieldWidth) {
		this.creatorUserId = checkNotNull(creatorUserId, "creator user id cannot be null");
		this.name = checkNotNull(name, "name cannot be null");
		this.maxSquadSize = maxSquadSize;
		this.fieldLength = fieldLength;
		this.fieldWidth = fieldWidth;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((creatorUserId == null) ? 0 : creatorUserId.hashCode());
		result = prime * result + ((fieldLength == null) ? 0 : fieldLength.hashCode());
		result = prime * result + ((fieldWidth == null) ? 0 : fieldWidth.hashCode());
		result = prime * result + ((maxSquadSize == null) ? 0 : maxSquadSize.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		SportType other = (SportType) obj;
		if (creatorUserId == null) {
			if (other.creatorUserId != null)
				return false;
		} else if (!creatorUserId.equals(other.creatorUserId))
			return false;
		if (fieldLength == null) {
			if (other.fieldLength != null)
				return false;
		} else if (!fieldLength.equals(other.fieldLength))
			return false;
		if (fieldWidth == null) {
			if (other.fieldWidth != null)
				return false;
		} else if (!fieldWidth.equals(other.fieldWidth))
			return false;
		if (maxSquadSize == null) {
			if (other.maxSquadSize != null)
				return false;
		} else if (!maxSquadSize.equals(other.maxSquadSize))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
	 * @return the maxSquadSize
	 */
	public String getMaxSquadSize() {
		return maxSquadSize;
	}

	/**
	 * @param maxSquadSize the maxSquadSize to set
	 */
	public void setMaxSquadSize(String maxSquadSize) {
		this.maxSquadSize = maxSquadSize;
	}

	/**
	 * @return the fieldLength
	 */
	public String getFieldLength() {
		return fieldLength;
	}

	/**
	 * @param fieldLength the fieldLength to set
	 */
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	/**
	 * @return the fieldWidth
	 */
	public String getFieldWidth() {
		return fieldWidth;
	}

	/**
	 * @param fieldWidth the fieldWidth to set
	 */
	public void setFieldWidth(String fieldWidth) {
		this.fieldWidth = fieldWidth;
	}
	
}
