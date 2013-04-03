package it.sirbotta.gae4channeler;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

public class ofyStartupListener implements ServletContextListener {
	private static final Logger log = Logger.getLogger(ofyStartupListener.class
			.getName());
	
	//registrare qui tutte le classi di objectify
	@Override
    public void contextInitialized(ServletContextEvent sce) {
		
		ObjectifyService.register(ofyChan4Thread.class);
		log.info("Datastore class ofyChan4Thread registered");
		ObjectifyService.register(ofyChan4Section.class);
		log.info("Datastore class ofyChan4Section registered");
		ObjectifyService.register(ofyChan4Metric.class);
		log.info("Datastore class ofyChan4Metric registered");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
