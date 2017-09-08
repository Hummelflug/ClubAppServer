package de.hummelflug.clubapp.server.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.hummelflug.clubapp.server.utils.ExerciseType;

@Entity
@Table(name = "exercise")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Exercise.findAll",
            query = "select e from Exercise e"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Exercise.findByExerciseType",
    	query = "select e from Exercise e where e.exerciseType like :exerciseType")
})
public class Exercise extends AbstractModel {

	@Column(name = "exercise_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private ExerciseType exerciseType;
	
	@Column(name = "difficulty")
	private Integer difficulty;
	
	@Column(name = "age_class")
	private Integer ageClass;
	
	@Lob
    @Column(name="image", columnDefinition="mediumblob")
    private byte[] image;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	/**
	 * A no-argument constructor
	 */
	public Exercise() {
	}
	
	public Exercise(@Nonnull ExerciseType exerciseType, Integer difficulty, Integer ageClass, byte[] image, 
			String title, String description) {
		this.exerciseType = checkNotNull(exerciseType, "exercise type cannot be null");
		this.difficulty = difficulty;
		this.ageClass = ageClass;
		this.image = image;
		this.title = title;
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ageClass == null) ? 0 : ageClass.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((exerciseType == null) ? 0 : exerciseType.hashCode());
		result = prime * result + Arrays.hashCode(image);
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
		Exercise other = (Exercise) obj;
		if (ageClass == null) {
			if (other.ageClass != null)
				return false;
		} else if (!ageClass.equals(other.ageClass))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (difficulty == null) {
			if (other.difficulty != null)
				return false;
		} else if (!difficulty.equals(other.difficulty))
			return false;
		if (exerciseType != other.exerciseType)
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * @return the exerciseType
	 */
	public ExerciseType getExerciseType() {
		return exerciseType;
	}

	/**
	 * @param exerciseType the exerciseType to set
	 */
	public void setExerciseType(ExerciseType exerciseType) {
		this.exerciseType = exerciseType;
	}

	/**
	 * @return the difficulty
	 */
	public Integer getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the ageClass
	 */
	public Integer getAgeClass() {
		return ageClass;
	}

	/**
	 * @param ageClass the ageClass to set
	 */
	public void setAgeClass(Integer ageClass) {
		this.ageClass = ageClass;
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
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
	
}
