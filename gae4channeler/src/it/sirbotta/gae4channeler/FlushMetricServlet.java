package it.sirbotta.gae4channeler;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FlushMetricServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(FlushMetricServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String m = req.getParameter("metric");
		ofyChan4Metric metric = ofy().load().type(ofyChan4Metric.class).id(m)
				.get();
		if (metric != null) {
			metric.setValue(0);
			metric.setDate(new Date());
			ofy().save().entity(metric);
		}
		log.info(m + " is flushed at " + new Date());

	}

}
