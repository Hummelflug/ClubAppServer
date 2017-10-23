package de.hummelflug.clubapp.server.tasks;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;

import com.google.common.collect.ImmutableMultimap;

import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.db.VoteDAO;
import de.hummelflug.clubapp.server.utils.VoteStatus;
import io.dropwizard.servlets.tasks.Task;

public class VoteExpirationCheckTask extends Task {

    /** task runs every 2 minutes **/
    public static final long SLEEP_TIME_MINUTES = 1;

    private SessionFactory sessionFactory;
    private VoteDAO voteDAO;
	
    public VoteExpirationCheckTask(SessionFactory sessionFactory, VoteDAO voteDAO) {
		super("check-expired-votes");
		this.sessionFactory = sessionFactory;
		this.voteDAO = voteDAO;
	}
    
    public void executeNow() throws Exception {
        execute(new ImmutableMultimap.Builder<String, String>().build(), new PrintWriter(System.out));
    }
    
    @Override
	public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
		run(parameters, output);
	}

    private void run(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
    	new Thread(new VoteExpirationCheck()).start();
    }

    class VoteExpirationCheck implements Runnable {
    	
        @Override
        public void run() {
            while(true) {
            	/** Start transaction **/
            	Session session = sessionFactory.openSession();
                try {
                    ManagedSessionContext.bind(session);
                    Transaction transaction = session.beginTransaction();
                    try {
                    	List<Vote> votes = voteDAO.findExpiredVotes();
                        for (Vote vote : votes) {
                        	vote.setStatus(VoteStatus.EXPIRED);
                        }
                        transaction.commit();
                        
                        /** Logging **/
                        System.out.println("\n====================== Expire job done at: " + 
                   			 new Date(System.currentTimeMillis()).toString() + ", " + votes.size() + 
                   			 " votes updated ======================\n");
                    }
                    catch (Exception e) {
                        transaction.rollback();
                        throw new RuntimeException(e);
                    }
                } finally {
                    session.close();
                    ManagedSessionContext.unbind(sessionFactory);
                } 
            	
                /** Sleep **/
                try {
                    Thread.sleep(TimeUnit.MINUTES.toMillis(SLEEP_TIME_MINUTES));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

	

}
