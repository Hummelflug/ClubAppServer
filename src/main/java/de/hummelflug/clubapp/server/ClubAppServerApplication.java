package de.hummelflug.clubapp.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import de.hummelflug.clubapp.server.auth.UserAuthenticator;
import de.hummelflug.clubapp.server.auth.UserAuthorizer;
import de.hummelflug.clubapp.server.core.Answer;
import de.hummelflug.clubapp.server.core.BugReport;
import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Department;
import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.core.Exercise;
import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.core.ImageFile;
import de.hummelflug.clubapp.server.core.News;
import de.hummelflug.clubapp.server.core.NewsContent;
import de.hummelflug.clubapp.server.core.Organizer;
import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.core.Schedule;
import de.hummelflug.clubapp.server.core.SportType;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.TeamSchedule;
import de.hummelflug.clubapp.server.core.Tournament;
import de.hummelflug.clubapp.server.core.Training;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.core.Vote;
import de.hummelflug.clubapp.server.db.BugReportDAO;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.DepartmentDAO;
import de.hummelflug.clubapp.server.db.EventDAO;
import de.hummelflug.clubapp.server.db.ExerciseDAO;
import de.hummelflug.clubapp.server.db.GameDAO;
import de.hummelflug.clubapp.server.db.ImageFileDAO;
import de.hummelflug.clubapp.server.db.NewsContentDAO;
import de.hummelflug.clubapp.server.db.NewsDAO;
import de.hummelflug.clubapp.server.db.OrganizerDAO;
import de.hummelflug.clubapp.server.db.PlayerDAO;
import de.hummelflug.clubapp.server.db.ScheduleDAO;
import de.hummelflug.clubapp.server.db.SportTypeDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.db.TeamScheduleDAO;
import de.hummelflug.clubapp.server.db.TournamentDAO;
import de.hummelflug.clubapp.server.db.TrainingDAO;
import de.hummelflug.clubapp.server.db.UserDAO;
import de.hummelflug.clubapp.server.db.UserScheduleDAO;
import de.hummelflug.clubapp.server.db.VoteDAO;
import de.hummelflug.clubapp.server.facade.BugReportFacade;
import de.hummelflug.clubapp.server.facade.ClubFacade;
import de.hummelflug.clubapp.server.facade.ClubNewsContentFacade;
import de.hummelflug.clubapp.server.facade.ClubNewsFacade;
import de.hummelflug.clubapp.server.facade.ClubVoteFacade;
import de.hummelflug.clubapp.server.facade.CoachFacade;
import de.hummelflug.clubapp.server.facade.DepartmentFacade;
import de.hummelflug.clubapp.server.facade.DepartmentNewsContentFacade;
import de.hummelflug.clubapp.server.facade.DepartmentNewsFacade;
import de.hummelflug.clubapp.server.facade.DepartmentVoteFacade;
import de.hummelflug.clubapp.server.facade.EventFacade;
import de.hummelflug.clubapp.server.facade.ExerciseFacade;
import de.hummelflug.clubapp.server.facade.GameFacade;
import de.hummelflug.clubapp.server.facade.ImageFileFacade;
import de.hummelflug.clubapp.server.facade.OrganizerFacade;
import de.hummelflug.clubapp.server.facade.PlayerFacade;
import de.hummelflug.clubapp.server.facade.ScheduleFacade;
import de.hummelflug.clubapp.server.facade.SportTypeFacade;
import de.hummelflug.clubapp.server.facade.TeamFacade;
import de.hummelflug.clubapp.server.facade.TeamNewsContentFacade;
import de.hummelflug.clubapp.server.facade.TeamNewsFacade;
import de.hummelflug.clubapp.server.facade.TeamScheduleFacade;
import de.hummelflug.clubapp.server.facade.TeamVoteFacade;
import de.hummelflug.clubapp.server.facade.TournamentFacade;
import de.hummelflug.clubapp.server.facade.TrainingFacade;
import de.hummelflug.clubapp.server.facade.UserFacade;
import de.hummelflug.clubapp.server.facade.UserScheduleFacade;
import de.hummelflug.clubapp.server.resources.BugReportResource;
import de.hummelflug.clubapp.server.resources.ClubResource;
import de.hummelflug.clubapp.server.resources.CoachResource;
import de.hummelflug.clubapp.server.resources.DepartmentResource;
import de.hummelflug.clubapp.server.resources.EventResource;
import de.hummelflug.clubapp.server.resources.ExerciseResource;
import de.hummelflug.clubapp.server.resources.GameResource;
import de.hummelflug.clubapp.server.resources.ImageFileResource;
import de.hummelflug.clubapp.server.resources.NewsContentResource;
import de.hummelflug.clubapp.server.resources.NewsResource;
import de.hummelflug.clubapp.server.resources.OrganizerResource;
import de.hummelflug.clubapp.server.resources.PlayerResource;
import de.hummelflug.clubapp.server.resources.ScheduleResource;
import de.hummelflug.clubapp.server.resources.SportTypeResource;
import de.hummelflug.clubapp.server.resources.TeamResource;
import de.hummelflug.clubapp.server.resources.TeamScheduleResource;
import de.hummelflug.clubapp.server.resources.TournamentResource;
import de.hummelflug.clubapp.server.resources.TrainingResource;
import de.hummelflug.clubapp.server.resources.UserResource;
import de.hummelflug.clubapp.server.resources.UserScheduleResource;
import de.hummelflug.clubapp.server.resources.VoteResource;
import de.hummelflug.clubapp.server.tasks.VoteExpirationCheckTask;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ClubAppServerApplication extends Application<ClubAppServerConfiguration> {
	
	/**
     * Hibernate bundle.
     */
    private final HibernateBundle<ClubAppServerConfiguration> hibernateBundle
            = new HibernateBundle<ClubAppServerConfiguration>(
            		Answer.class,
            		BugReport.class,
            		Club.class,
            		Coach.class,
            		Department.class,
            		Event.class,
            		Exercise.class,
            		Game.class,
            		ImageFile.class,
            		News.class,
            		NewsContent.class,
            		Organizer.class,
            		Player.class,
            		Schedule.class,
                    SportType.class,
                    Team.class,
                    TeamSchedule.class,
                    Tournament.class,
                    Training.class,
            		User.class,
            		UserSchedule.class,
            		Vote.class
            ) {

        @Override
        public DataSourceFactory getDataSourceFactory(
        		ClubAppServerConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }

    };

    public static void main(final String[] args) throws Exception {
        new ClubAppServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "ClubAppServer";
    }

    @Override
    public void initialize(final Bootstrap<ClubAppServerConfiguration> bootstrap) {
    	bootstrap.addBundle(hibernateBundle);
    	
    	/** Enable variable substitution with environment variables **/
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                                                   new EnvironmentVariableSubstitutor()
                )
        );

    }

    @Override
    public void run(final ClubAppServerConfiguration configuration,
                    final Environment environment) throws Exception {
    	/** Init Daos **/
    	final BugReportDAO bugReportDAO = new BugReportDAO(hibernateBundle.getSessionFactory());
    	final ClubDAO clubDAO = new ClubDAO(hibernateBundle.getSessionFactory());
    	final CoachDAO coachDAO = new CoachDAO(hibernateBundle.getSessionFactory());
    	final DepartmentDAO departmentDAO = new DepartmentDAO(hibernateBundle.getSessionFactory());
    	final EventDAO eventDAO = new EventDAO(hibernateBundle.getSessionFactory());
    	final ExerciseDAO exerciseDAO = new ExerciseDAO(hibernateBundle.getSessionFactory());
    	final GameDAO gameDAO = new GameDAO(hibernateBundle.getSessionFactory());
    	final ImageFileDAO imageFileDAO = new ImageFileDAO(hibernateBundle.getSessionFactory());
    	final NewsDAO newsDAO = new NewsDAO(hibernateBundle.getSessionFactory());
    	final NewsContentDAO newsContentDAO = new NewsContentDAO(hibernateBundle.getSessionFactory());
    	final OrganizerDAO organizerDAO = new OrganizerDAO(hibernateBundle.getSessionFactory());
    	final PlayerDAO playerDAO = new PlayerDAO(hibernateBundle.getSessionFactory());
    	final ScheduleDAO scheduleDAO = new ScheduleDAO(hibernateBundle.getSessionFactory());
    	final SportTypeDAO sportTypeDAO = new SportTypeDAO(hibernateBundle.getSessionFactory());
    	final TeamDAO teamDAO = new TeamDAO(hibernateBundle.getSessionFactory());
    	final TeamScheduleDAO teamScheduleDAO = new TeamScheduleDAO(hibernateBundle.getSessionFactory());
    	final TournamentDAO tournamentDAO = new TournamentDAO(hibernateBundle.getSessionFactory());
    	final TrainingDAO trainingDAO = new TrainingDAO(hibernateBundle.getSessionFactory());
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final UserScheduleDAO userScheduleDAO = new UserScheduleDAO(hibernateBundle.getSessionFactory());
        final VoteDAO voteDAO = new VoteDAO(hibernateBundle.getSessionFactory());
        
        /** Initialize Facades **/
        final ImageFileFacade imageFileFacade = new ImageFileFacade(imageFileDAO);
        final UserScheduleFacade userScheduleFacade = new UserScheduleFacade(userScheduleDAO);
        final TeamScheduleFacade teamScheduleFacade = new TeamScheduleFacade(coachDAO, playerDAO, teamDAO,
        		teamScheduleDAO, userScheduleFacade);
        
        final BugReportFacade bugReportFacade = new BugReportFacade(bugReportDAO);
        final ClubFacade clubFacade = new ClubFacade(clubDAO, coachDAO, playerDAO, userDAO);
        final ClubNewsContentFacade clubNewsContentFacade = new ClubNewsContentFacade(clubDAO, newsContentDAO);
        final ClubNewsFacade clubNewsFacade = new ClubNewsFacade(clubDAO, imageFileFacade, newsDAO);
        final ClubVoteFacade clubVoteFacade = new ClubVoteFacade(clubDAO, voteDAO);
        final CoachFacade coachFacade = new CoachFacade(clubDAO, coachDAO, teamDAO, userScheduleDAO);
        final DepartmentFacade departmentFacade = new DepartmentFacade(clubDAO, departmentDAO, teamDAO);
        final DepartmentNewsContentFacade departmentNewsContentFacade = new DepartmentNewsContentFacade(departmentDAO,
        		newsContentDAO);
        final DepartmentNewsFacade departmentNewsFacade = new DepartmentNewsFacade(clubDAO, departmentDAO,
        		imageFileFacade, newsDAO);
        final DepartmentVoteFacade departmentVoteFacade = new DepartmentVoteFacade(clubDAO, departmentDAO, voteDAO);
        final EventFacade eventFacade = new EventFacade(eventDAO);
        final ExerciseFacade exerciseFacade = new ExerciseFacade(exerciseDAO);
        final GameFacade gameFacade = new GameFacade(gameDAO, teamScheduleFacade);
        final OrganizerFacade organizerFacade = new OrganizerFacade(organizerDAO);
        final PlayerFacade playerFacade = new PlayerFacade(clubDAO, playerDAO, teamDAO, userScheduleDAO);
        final ScheduleFacade scheduleFacade = new ScheduleFacade(eventDAO, scheduleDAO);
        final SportTypeFacade sportTypeFacade = new SportTypeFacade(sportTypeDAO);
        final TeamFacade teamFacade = new TeamFacade(clubDAO, coachDAO, departmentDAO,
        		playerDAO, teamDAO, teamScheduleDAO, userDAO);  
        final TeamNewsContentFacade teamNewsContentFacade = new TeamNewsContentFacade(newsContentDAO, teamDAO);
        final TeamNewsFacade teamNewsFacade = new TeamNewsFacade(clubDAO, imageFileFacade, newsDAO, teamDAO);
        final TeamVoteFacade teamVoteFacade = new TeamVoteFacade(clubDAO, teamDAO, voteDAO);  
        final TournamentFacade tournamentFacade = new TournamentFacade(tournamentDAO);
        final TrainingFacade trainingFacade = new TrainingFacade(teamScheduleFacade, trainingDAO);
        final UserFacade userFacade = new UserFacade(userDAO);
        
        /** Logs of incoming requests **/
        //TODO: remove in production
        environment.jersey().register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), 
        		Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE));
        
        /** Set up authentication **/
        UserAuthenticator authenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
        		.create(UserAuthenticator.class, UserDAO.class, userDAO);
        
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(authenticator)
                    .setAuthorizer(new UserAuthorizer())
                    .setRealm("SUPER SECRET STUFF")
                    .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        
        /** Set up image upload **/
        environment.jersey().register(MultiPartFeature.class);
        
        /** Register resources **/
        environment.jersey().register(new BugReportResource(bugReportFacade));
        environment.jersey().register(new ClubResource(clubFacade, clubNewsContentFacade, clubNewsFacade,
        		clubVoteFacade, departmentNewsFacade, departmentVoteFacade, teamNewsFacade, teamVoteFacade));
        environment.jersey().register(new CoachResource(coachFacade));
        environment.jersey().register(new DepartmentResource(departmentFacade, departmentNewsContentFacade, 
        		departmentNewsFacade, departmentVoteFacade));
        environment.jersey().register(new EventResource(eventFacade));
        environment.jersey().register(new ExerciseResource(exerciseFacade));
        environment.jersey().register(new GameResource(gameFacade));
        environment.jersey().register(new ImageFileResource(imageFileFacade));
        environment.jersey().register(new OrganizerResource(organizerFacade));
        environment.jersey().register(new NewsResource(newsDAO));
        environment.jersey().register(new NewsContentResource(newsContentDAO));
        environment.jersey().register(new PlayerResource(playerFacade));
        environment.jersey().register(new ScheduleResource(scheduleFacade));
        environment.jersey().register(new SportTypeResource(sportTypeFacade));
        environment.jersey().register(new TeamResource(teamFacade, teamNewsContentFacade, teamNewsFacade,
        		teamVoteFacade));
        environment.jersey().register(new TeamScheduleResource(teamScheduleFacade));
        environment.jersey().register(new TournamentResource(tournamentFacade));
        environment.jersey().register(new TrainingResource(trainingFacade));
        environment.jersey().register(new UserResource(imageFileFacade, userFacade));
        environment.jersey().register(new UserScheduleResource(userScheduleFacade));
        environment.jersey().register(new VoteResource(voteDAO));
        
        /** Add tasks **/
        VoteExpirationCheckTask voteExpirationCheckTask = 
        		new VoteExpirationCheckTask(hibernateBundle.getSessionFactory(), voteDAO);
        voteExpirationCheckTask.executeNow();
    }

}
