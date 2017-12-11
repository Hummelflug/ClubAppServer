package de.hummelflug.clubapp.server.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import de.hummelflug.clubapp.server.utils.NewsContentType;

@Entity
@Table(name = "news")
@PrimaryKeyJoinColumn(name = "news_content_id")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.News.findAll",
            query = "select n from News n"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.News.findByIds",
		query = "select n from News n where n.id in :ids order by n.creationTime desc)")
})
public class News extends NewsContent {
	
	@Column(name = "new_content_id", nullable = false)
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "image_id")
	private Long imageId;
	
	@ElementCollection
	@CollectionTable(name = "news_readers", joinColumns = @JoinColumn(name = "news_id"))
	@Column(name = "user_id", nullable = false)
	private Set<Long> newsReaders;
	
	/**
	 * A no-argument constructor
	 */
	public News() {
		super(NewsContentType.NEWS);
		
		newsReaders = new HashSet<Long>();
	}
	
	/**
	 * @param creatorUserId user id of creator
	 * @param title of news
	 * @param description of news
	 */
	public News(@Nonnull Long creatorUserId, @Nonnull String title, @Nonnull String description) {
		super(NewsContentType.NEWS, creatorUserId);
		
		newsReaders = new HashSet<Long>();
		
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imageId == null) ? 0 : imageId.hashCode());
		result = prime * result + ((newsReaders == null) ? 0 : newsReaders.hashCode());
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
		News other = (News) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageId == null) {
			if (other.imageId != null)
				return false;
		} else if (!imageId.equals(other.imageId))
			return false;
		if (newsReaders == null) {
			if (other.newsReaders != null)
				return false;
		} else if (!newsReaders.equals(other.newsReaders))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
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
	 * @return the imageId
	 */
	public Long getImageId() {
		return imageId;
	}

	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	/**
	 * @return the newsReaders
	 */
	public Set<Long> getNewsReaders() {
		return newsReaders;
	}

	/**
	 * @param newsReaders the newsReaders to set
	 */
	public void setNewsReaders(Set<Long> newsReaders) {
		this.newsReaders = newsReaders;
	}
	
}
