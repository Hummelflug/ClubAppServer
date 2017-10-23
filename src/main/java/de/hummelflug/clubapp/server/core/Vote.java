package de.hummelflug.clubapp.server.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.hummelflug.clubapp.server.utils.NewsContentType;
import de.hummelflug.clubapp.server.utils.VoteStatus;

@Entity
@Table(name = "vote")
@PrimaryKeyJoinColumn(name = "news_content_id")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Vote.findAll",
            query = "select v from Vote v"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Vote.findByIds",
			query = "select v from Vote v where v.id in :ids order by v.creationTime desc)"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Vote.findExpiredVotes",
		    query = "select v from Vote v where expirationTime < :currentTime and status = :openStatus")
})
public class Vote extends NewsContent {

	@Column(name = "new_content_id", nullable = false)
	private Long id;
	
	@Column(name = "question_text")
	private String questionText;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "vote_answer",
            joinColumns = @JoinColumn(name = "vote_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
	private List<Answer> answers;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private VoteStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiration_date", nullable = false)
	private Date expirationTime;
	
	@ElementCollection
	@CollectionTable(name = "vote_participants", joinColumns = @JoinColumn(name = "vote_id"))
	@Column(name = "user_id", nullable = false)
	private Set<Long> voteParticipants;
	
	@ElementCollection
	@CollectionTable(name = "vote_no_answer", joinColumns = @JoinColumn(name = "vote_id"))
	@Column(name = "user_id", nullable = false)
	private Set<Long> voteNoAnswer;
	
	/**
	 * A no-argument constructor
	 */
	public Vote() {
		super(NewsContentType.VOTE);
		
		answers = new ArrayList<Answer>();
		voteParticipants = new HashSet<Long>();
		voteNoAnswer = new HashSet<Long>();
		
		status = VoteStatus.OPEN;
	}
	
	/**
	 * 
	 * @param creatorUserId user id of creator
	 * @param questionText text of question
	 * @param expirationTime timestamp when vote expires
	 */
	public Vote(@Nonnull Long creatorUserId, @Nonnull String questionText,  @Nonnull Date expirationTime) {
		super(NewsContentType.VOTE, creatorUserId);
		
		answers = new ArrayList<Answer>();
		voteParticipants = new HashSet<Long>();
		voteNoAnswer = new HashSet<Long>();
		
		this.questionText = checkNotNull(questionText, "question text cannot be null");
		this.expirationTime = expirationTime;
		status = VoteStatus.OPEN;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((expirationTime == null) ? 0 : expirationTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((voteNoAnswer == null) ? 0 : voteNoAnswer.hashCode());
		result = prime * result + ((voteParticipants == null) ? 0 : voteParticipants.hashCode());
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
		Vote other = (Vote) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (expirationTime == null) {
			if (other.expirationTime != null)
				return false;
		} else if (!expirationTime.equals(other.expirationTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		if (status != other.status)
			return false;
		if (voteNoAnswer == null) {
			if (other.voteNoAnswer != null)
				return false;
		} else if (!voteNoAnswer.equals(other.voteNoAnswer))
			return false;
		if (voteParticipants == null) {
			if (other.voteParticipants != null)
				return false;
		} else if (!voteParticipants.equals(other.voteParticipants))
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
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	/**
	 * @return the status
	 */
	public VoteStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(VoteStatus status) {
		this.status = status;
	}

	/**
	 * @return the expirationTime
	 */
	public Date getExpirationTime() {
		return expirationTime;
	}

	/**
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	/**
	 * @return the voteParticipants
	 */
	public Set<Long> getVoteParticipants() {
		return voteParticipants;
	}

	/**
	 * @param voteParticipants the voteParticipants to set
	 */
	public void setVoteParticipants(Set<Long> voteParticipants) {
		this.voteParticipants = voteParticipants;
	}

	/**
	 * @return the voteNoAnswer
	 */
	public Set<Long> getVoteNoAnswer() {
		return voteNoAnswer;
	}

	/**
	 * @param voteNoAnswer the voteNoAnswer to set
	 */
	public void setVoteNoAnswer(Set<Long> voteNoAnswer) {
		this.voteNoAnswer = voteNoAnswer;
	}
	
}
