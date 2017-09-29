package de.hummelflug.clubapp.server;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import de.hummelflug.clubapp.server.auth.UserAuthenticator;
import de.hummelflug.clubapp.server.auth.UserAuthorizer;
import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.core.Exercise;
import de.hummelflug.clubapp.server.core.Game;
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
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.EventDAO;
import de.hummelflug.clubapp.server.db.ExerciseDAO;
import de.hummelflug.clubapp.server.db.GameDAO;
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
import de.hummelflug.clubapp.server.facade.ClubFacade;
import de.hummelflug.clubapp.server.facade.CoachFacade;
import de.hummelflug.clubapp.server.facade.ExerciseFacade;
import de.hummelflug.clubapp.server.facade.GameFacade;
import de.hummelflug.clubapp.server.facade.OrganizerFacade;
import de.hummelflug.clubapp.server.facade.PlayerFacade;
import de.hummelflug.clubapp.server.facade.ScheduleFacade;
import de.hummelflug.clubapp.server.facade.SportTypeFacade;
import de.hummelflug.clubapp.server.facade.TeamFacade;
import de.hummelflug.clubapp.server.facade.TeamScheduleFacade;
import de.hummelflug.clubapp.server.facade.TournamentFacade;
import de.hummelflug.clubapp.server.facade.TrainingFacade;
import de.hummelflug.clubapp.server.facade.UserFacade;
import de.hummelflug.clubapp.server.facade.UserScheduleFacade;
import de.hummelflug.clubapp.server.resources.ClubResource;
import de.hummelflug.clubapp.server.resources.CoachResource;
import de.hummelflug.clubapp.server.resources.EventResource;
import de.hummelflug.clubapp.server.resources.ExerciseResource;
import de.hummelflug.clubapp.server.resources.GameResource;
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
            		Club.class,
            		Coach.class,
            		Event.class,
            		Exercise.class,
            		Game.class,
            		Organizer.class,
            		Player.class,
            		Schedule.class,
                    SportType.class,
                    Team.class,
                    TeamSchedule.class,
                    Tournament.class,
                    Training.class,
            		User.class,
            		UserSchedule.class
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
                    final Environment environment) {
    	final ClubDAO clubDAO = new ClubDAO(hibernateBundle.getSessionFactory());
    	final CoachDAO coachDAO = new CoachDAO(hibernateBundle.getSessionFactory());
    	final EventDAO eventDAO = new EventDAO(hibernateBundle.getSessionFactory());
    	final ExerciseDAO exerciseDAO = new ExerciseDAO(hibernateBundle.getSessionFactory());
    	final GameDAO gameDAO = new GameDAO(hibernateBundle.getSessionFactory());
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
        
        final UserScheduleFacade userScheduleFacade = new UserScheduleFacade(userScheduleDAO);
        final TeamScheduleFacade teamScheduleFacade = new TeamScheduleFacade(coachDAO, playerDAO, teamDAO,
        		teamScheduleDAO, userScheduleFacade);
        
        final ClubFacade clubFacade = new ClubFacade(clubDAO, coachDAO, playerDAO, userDAO);
        final CoachFacade coachFacade = new CoachFacade(clubDAO, coachDAO, teamDAO, userScheduleDAO);
        final ExerciseFacade exerciseFacade = new ExerciseFacade(exerciseDAO);
        final GameFacade gameFacade = new GameFacade(gameDAO, teamScheduleFacade);
        final OrganizerFacade organizerFacade = new OrganizerFacade(organizerDAO);
        final PlayerFacade playerFacade = new PlayerFacade(clubDAO, playerDAO, teamDAO, userScheduleDAO);
        final ScheduleFacade scheduleFacade = new ScheduleFacade(eventDAO, scheduleDAO);
        final SportTypeFacade sportTypeFacade = new SportTypeFacade(sportTypeDAO);
        final TeamFacade teamFacade = new TeamFacade(coachDAO, playerDAO, teamDAO, teamScheduleDAO, userDAO);    
        final TournamentFacade tournamentFacade = new TournamentFacade(tournamentDAO);
        final TrainingFacade trainingFacade = new TrainingFacade(teamScheduleFacade, trainingDAO);
        final UserFacade userFacade = new UserFacade(userDAO);
        
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
        
        environment.jersey().register(new ClubResource(clubFacade));
        environment.jersey().register(new CoachResource(coachFacade));
        environment.jersey().register(new EventResource(eventDAO));
        environment.jersey().register(new ExerciseResource(exerciseFacade));
        environment.jersey().register(new GameResource(gameFacade));
        environment.jersey().register(new OrganizerResource(organizerFacade));
        environment.jersey().register(new PlayerResource(playerFacade));
        environment.jersey().register(new ScheduleResource(scheduleFacade));
        environment.jersey().register(new SportTypeResource(sportTypeFacade));
        environment.jersey().register(new TeamResource(teamFacade));
        environment.jersey().register(new TeamScheduleResource(teamScheduleFacade));
        environment.jersey().register(new TournamentResource(tournamentFacade));
        environment.jersey().register(new TrainingResource(trainingFacade));
        environment.jersey().register(new UserResource(userFacade));
        environment.jersey().register(new UserScheduleResource(userScheduleFacade));

    }

}
