package de.hummelflug.clubapp.server;

import de.hummelflug.clubapp.server.core.Club;
import de.hummelflug.clubapp.server.core.Coach;
import de.hummelflug.clubapp.server.core.Event;
import de.hummelflug.clubapp.server.core.Game;
import de.hummelflug.clubapp.server.core.Organizer;
import de.hummelflug.clubapp.server.core.Player;
import de.hummelflug.clubapp.server.core.Schedule;
import de.hummelflug.clubapp.server.core.SportType;
import de.hummelflug.clubapp.server.core.Team;
import de.hummelflug.clubapp.server.core.TeamSchedule;
import de.hummelflug.clubapp.server.core.Tournament;
import de.hummelflug.clubapp.server.core.User;
import de.hummelflug.clubapp.server.core.UserSchedule;
import de.hummelflug.clubapp.server.db.ClubDAO;
import de.hummelflug.clubapp.server.db.CoachDAO;
import de.hummelflug.clubapp.server.db.EventDAO;
import de.hummelflug.clubapp.server.db.GameDAO;
import de.hummelflug.clubapp.server.db.OrganizerDAO;
import de.hummelflug.clubapp.server.db.PlayerDAO;
import de.hummelflug.clubapp.server.db.ScheduleDAO;
import de.hummelflug.clubapp.server.db.SportTypeDAO;
import de.hummelflug.clubapp.server.db.TeamDAO;
import de.hummelflug.clubapp.server.db.TeamScheduleDAO;
import de.hummelflug.clubapp.server.db.TournamentDAO;
import de.hummelflug.clubapp.server.db.UserDAO;
import de.hummelflug.clubapp.server.db.UserScheduleDAO;
import de.hummelflug.clubapp.server.resources.ClubRessource;
import de.hummelflug.clubapp.server.resources.CoachRessource;
import de.hummelflug.clubapp.server.resources.EventRessource;
import de.hummelflug.clubapp.server.resources.GameRessource;
import de.hummelflug.clubapp.server.resources.OrganizerRessource;
import de.hummelflug.clubapp.server.resources.PlayerRessource;
import de.hummelflug.clubapp.server.resources.ScheduleRessource;
import de.hummelflug.clubapp.server.resources.SportTypeRessource;
import de.hummelflug.clubapp.server.resources.TeamRessource;
import de.hummelflug.clubapp.server.resources.TeamScheduleRessource;
import de.hummelflug.clubapp.server.resources.TournamentRessource;
import de.hummelflug.clubapp.server.resources.UserRessource;
import de.hummelflug.clubapp.server.resources.UserScheduleRessource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
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
            		Game.class,
            		Organizer.class,
            		Player.class,
            		Schedule.class,
                    SportType.class,
                    Team.class,
                    TeamSchedule.class,
                    Tournament.class,
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
    	
    	// Enable variable substitution with environment variables
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
    	final GameDAO gameDAO = new GameDAO(hibernateBundle.getSessionFactory());
    	final OrganizerDAO organizerDAO = new OrganizerDAO(hibernateBundle.getSessionFactory());
    	final PlayerDAO playerDAO = new PlayerDAO(hibernateBundle.getSessionFactory());
    	final ScheduleDAO scheduleDAO = new ScheduleDAO(hibernateBundle.getSessionFactory());
    	final SportTypeDAO sportTypeDAO = new SportTypeDAO(hibernateBundle.getSessionFactory());
    	final TeamDAO teamDAO = new TeamDAO(hibernateBundle.getSessionFactory());
    	final TeamScheduleDAO teamScheduleDAO = new TeamScheduleDAO(hibernateBundle.getSessionFactory());
    	final TournamentDAO tournamentDAO = new TournamentDAO(hibernateBundle.getSessionFactory());
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final UserScheduleDAO userScheduleDAO = new UserScheduleDAO(hibernateBundle.getSessionFactory());
        
        environment.jersey().register(new ClubRessource(clubDAO));
        environment.jersey().register(new CoachRessource(coachDAO));
        environment.jersey().register(new EventRessource(eventDAO));
        environment.jersey().register(new GameRessource(gameDAO));
        environment.jersey().register(new OrganizerRessource(organizerDAO));
        environment.jersey().register(new PlayerRessource(playerDAO));
        environment.jersey().register(new ScheduleRessource(scheduleDAO));
        environment.jersey().register(new SportTypeRessource(sportTypeDAO));
        environment.jersey().register(new TeamRessource(teamDAO));
        environment.jersey().register(new TeamScheduleRessource(teamScheduleDAO));
        environment.jersey().register(new TournamentRessource(tournamentDAO));
        environment.jersey().register(new UserRessource(userDAO));
        environment.jersey().register(new UserScheduleRessource(userScheduleDAO));
    }

}