package de.hummelflug.clubapp.server.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import de.hummelflug.clubapp.server.core.Answer;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.db.VoteDAO;
import de.hummelflug.clubapp.server.utils.NewsFilterOption;
import de.hummelflug.clubapp.server.utils.VoteStatus;

public abstract class AbstractSuperVoteFacade {

public static final Integer NEWSCOUNT = AbstractSuperNewsContentFacade.NEWSCOUNT;
	
	private final VoteDAO voteDAO;
	
	public AbstractSuperVoteFacade(VoteDAO voteDAO) {
		this.voteDAO = voteDAO;
	}

	public List<Vote> findAllVotes() {
		return voteDAO.findAll();
	}
	
	private void addAnswers(User user, Vote vote, List<Answer> answers) {
		if (user != null && vote != null && answers != null) {
			for (Answer answer : answers) {
				vote.getAnswers().add(new Answer(user.getId(), answer.getAnswerText()));
			}
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	private void addParticipants(Vote vote, Set<Long> participants) {
		if (vote != null && participants != null) {
			for (Long userId : participants) {
				vote.getVoteParticipants().add(userId);
				vote.getVoteNoAnswer().add(userId);
			}
		} else {
			throw new WebApplicationException(400);
		}
	}
	
	protected Vote closeVote(Long voteId) {
		if (voteId != null) {
			Optional<Vote> voteOptional = findVoteById(voteId);
			if (voteOptional.isPresent()) {
				Vote vote = voteOptional.get();
				if (vote.getStatus() == VoteStatus.OPEN) {
					vote.setStatus(VoteStatus.CLOSED);
					return voteDAO.update(vote);
				}
			}
		}
		throw new WebApplicationException(400);
	}
	
	protected Vote createVote(User user, Vote vote) {
		if (user != null && vote != null) {
			Vote newVote = voteDAO.insert(new Vote(user.getId(), vote.getQuestionText(), vote.getExpirationTime()));
			addAnswers(user, newVote, vote.getAnswers());	
			addParticipants(newVote, vote.getVoteParticipants());
			
			voteDAO.update(newVote);
			
			return newVote;
		}
		throw new WebApplicationException(400);
	}
	
	public Optional<Vote> findVoteById(Long voteId) {
		return voteDAO.findById(voteId);
	}
	
	protected List<Vote> findVotes(Set<Long> votes, NewsFilterOption filter) {
		if (votes != null && filter != null) {
			if (filter.equals(NewsFilterOption.All)) {
				return voteDAO.findVotesByIds(votes);
			} else if (filter.equals(NewsFilterOption.RECENT)) {
				return voteDAO.findRecentVotesByIds(votes, NEWSCOUNT);
			}
		}
		throw new WebApplicationException(400);
	}
	
	public Vote voteForAnswer(User user, Long voteId, Long answerId) {
		if (user != null && voteId != null && answerId != null) {
			Optional<Vote> voteOptional = findVoteById(voteId);
			if (voteOptional.isPresent()) {
				Vote vote = voteOptional.get();
				if (vote.getStatus().equals(VoteStatus.OPEN) && vote.getVoteParticipants().contains(user.getId())) {
					boolean isValidAnswerOfVote = false;
					for (Answer answer : vote.getAnswers()) {
						if (answer.getId() == answerId) {
							isValidAnswerOfVote = true;
							break;
						}
					}
					if (isValidAnswerOfVote) {
						for (Answer answer : vote.getAnswers()) {
							if (answer.getId() == answerId) {
								answer.getVoters().add(user.getId());
								if (vote.getVoteNoAnswer().contains(user.getId())) {
									vote.getVoteNoAnswer().remove(user.getId());
								}
							} else if (answer.getVoters().contains(user.getId())) {
								answer.getVoters().remove(user.getId());
							}
						}
						return voteDAO.update(vote);
					}
				} else {
					throw new WebApplicationException(401);
				}
			}
		}
		throw new WebApplicationException(400);
	}
	
}
