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
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class Answer extends AbstractModel {
	
	@Column(name = "creator_user_id", nullable = false)
	private Long creatorUserId;
	
	@Column(name = "answer_text", nullable = false)
	private String answerText;
	
	@ElementCollection
	@CollectionTable(name = "user_vote_answer", joinColumns = @JoinColumn(name = "answer_id"))
	@Column(name = "user_id", nullable = false)
	private Set<Long> voters;
	
	/**
	 * A no-argument constructor
	 */
	public Answer() {
		voters = new HashSet<Long>();
	}
	
	public Answer(@Nonnull Long creatorUserId, @Nonnull String answerText) {
		voters = new HashSet<Long>();
		
		this.creatorUserId = checkNotNull(creatorUserId, "creator user id cannot be null");
		this.answerText = checkNotNull(answerText, "answer text cannot be null");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((answerText == null) ? 0 : answerText.hashCode());
		result = prime * result + ((creatorUserId == null) ? 0 : creatorUserId.hashCode());
		result = prime * result + ((voters == null) ? 0 : voters.hashCode());
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
		Answer other = (Answer) obj;
		if (answerText == null) {
			if (other.answerText != null)
				return false;
		} else if (!answerText.equals(other.answerText))
			return false;
		if (creatorUserId == null) {
			if (other.creatorUserId != null)
				return false;
		} else if (!creatorUserId.equals(other.creatorUserId))
			return false;
		if (voters == null) {
			if (other.voters != null)
				return false;
		} else if (!voters.equals(other.voters))
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
	 * @return the answerText
	 */
	public String getAnswerText() {
		return answerText;
	}

	/**
	 * @param answerText the answerText to set
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	/**
	 * @return the voters
	 */
	public Set<Long> getVoters() {
		return voters;
	}

	/**
	 * @param voters the voters to set
	 */
	public void setVoters(Set<Long> voters) {
		this.voters = voters;
	}

}
