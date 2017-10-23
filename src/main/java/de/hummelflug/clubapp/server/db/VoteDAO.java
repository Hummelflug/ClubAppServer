package de.hummelflug.clubapp.server.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.utils.VoteStatus;

public class VoteDAO extends AbstractSuperDAO<Vote> {

	/**
     * Constructor.
     * 
     * @param sessionFactory Hibernate session factory.
     */
	public VoteDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
     * Method returns all votes stored in the database.
     * 
     * @return list of all votes stored in the database
     */
	public List<Vote> findAll() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Vote.findAll"));
	}
	
	/**
     * Method looks for a vote by id.
     * 
     * @param id the id of a vote we are looking for.
     * @return Optional containing the found vote or an empty Optional
     * otherwise
     */
	public Optional<Vote> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

	/**
     * Method returns all votes if ids contains the id of votes sorted by date.
     * 
     * @param ids of votes we are looking for.
     * @return list of all votes stored in the database
     */
	public List<Vote> findVotesByIds(Set<Long> ids) {
		if (ids.size() == 0) {
			return new ArrayList<Vote>();
		}
		return list(namedQuery("de.hummelflug.clubapp.server.core.Vote.findByIds")
				.setParameter("ids", ids));
	}

	/**
     * Method returns last n votes if ids contains the id of votes.
     * 
     * @param ids of votes we are looking for.
     * @param count count of votes which will be return
     * @return list of all votes stored in the database
     */
	public List<Vote> findRecentVotesByIds(Set<Long> ids, Integer count) {
		if (ids.size() == 0) {
			return new ArrayList<Vote>();
		}
		return list(namedQuery("de.hummelflug.clubapp.server.core.Vote.findByIds")
				.setParameter("ids", ids)
				.setFirstResult(0)
				.setMaxResults(count));
	}
	
	/**
	 * Method returns all all open votes which should get status EXPIRED.
	 * 
	 * @return list of all open votes which should get status EXPIRED
	 */
	public List<Vote> findExpiredVotes() {
		return list(namedQuery("de.hummelflug.clubapp.server.core.Vote.findExpiredVotes")
				.setParameter("currentTime", new Date(System.currentTimeMillis()))
				.setParameter("openStatus", VoteStatus.OPEN));
	}
	
}
