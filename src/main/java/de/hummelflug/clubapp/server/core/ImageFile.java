package de.hummelflug.clubapp.server.core;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "image")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.ImageFile.findAll",
            query = "select i from ImageFile i"),
})
public class ImageFile extends AbstractModel {
	
	@Column(name = "creator_user_id", nullable = false)
	private Long creatorUserId;
	
	@Column(name = "image_path")
	private String imagePath;
	
	/**
	 * A no-argument constructor
	 */
	public ImageFile() {
		
	}
	
	/**
	 * 
	 * @param creatorUserId user id of creator
	 * @param imagePath	path of image on server
	 */
	public ImageFile(@Nonnull Long creatorUserId, String imagePath) {
		this.creatorUserId = checkNotNull(creatorUserId, "creator user id cannot be null");
		this.imagePath = imagePath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((creatorUserId == null) ? 0 : creatorUserId.hashCode());
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
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
		ImageFile other = (ImageFile) obj;
		if (creatorUserId == null) {
			if (other.creatorUserId != null)
				return false;
		} else if (!creatorUserId.equals(other.creatorUserId))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
