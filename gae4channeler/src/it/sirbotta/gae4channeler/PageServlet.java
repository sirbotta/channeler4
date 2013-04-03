package it.sirbotta.gae4channeler;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@SuppressWarnings("serial")
public class PageServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(PageServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int nimages = 0, nthread = 0;
		long startTime = System.currentTimeMillis();
		String page = req.getParameter("page");
		// String snpage = req.getParameter("npage");

		String sfrom = req.getParameter("from");
		String sto = req.getParameter("to");

		//minutes in delay
		int delay = 3;
		if (sfrom == null & sto == null) {

			ofyChan4Section section = ofy().load().type(ofyChan4Section.class)
					.id(page).get();
			if (section != null) {
				if (new Date().after(new Date(section.getLastCrawl().getTime()
						+ delay * 60 * 1000))) {
					Queue queue = QueueFactory.getDefaultQueue();
					queue.add(TaskOptions.Builder.withUrl("/crawl")
							.param("page", page).method(Method.GET));
				}
			} else {
				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/crawl")
						.param("page", page).method(Method.GET));
			}
		}

		int from = (sfrom == null) ? 0 : Integer.parseInt(sfrom);
		int to = (sto == null) ? 10 : Integer.parseInt(sto);

		if (from > to) {
			from = to - 1;
		}

		long startHtmlTime = System.currentTimeMillis();
		resp.setContentType("text/html");
		resp.getWriter().println( "<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<title> Welcome to "
				+ page
				+ "</title>"
				+ "<script src='http://code.jquery.com/jquery-latest.min.js'></script>"
				//+ "<script src='js/lightbox.js'></script>"
			    +"<link rel='stylesheet' href='js/jquery.fancybox.css?v=2.1.4' type='text/css' media='screen' />"
				+"<script type='text/javascript' src='js/jquery.fancybox.pack.js?v=2.1.4'></script>"
				+ "<script src='js/jquery.lazyload.min.js'></script>"
				+ "<link rel='stylesheet' href='http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css' />"
				+ "<script src='http://code.jquery.com/ui/1.10.1/jquery-ui.js'></script>"
				// + "<script src='js/jquery.inview.js'></script>"
				+ "<script src='js/my.js'></script>"
				//+ "<link href='css/lightbox.css' rel='stylesheet' />"
				+ "<link href='css/style.css' rel='stylesheet' />"
				+ "</head>"
				+ "<body>"
				+ "<a HREF='javascript:history.back()'><h1>Go Back </a>"
				+ "/"
				+ page
				+ "/<a HREF='javascript:document.location.reload();'> Reload</a></h1>");

		List<ofyChan4Thread> c4t = ofy().load().type(ofyChan4Thread.class)
				.filter("page", page).order("-date").limit(to - from)
				.offset(from).list();

		for (ofyChan4Thread t : c4t) {
			nthread++;
			// System.out.println("Thread id:" + t.getId());
			resp.getWriter().println( "<div class='post'><div>thread id:" + t.getId() + " "
					+ t.getDate() + "<p>" + t.getPosts().get(0).getMessage()
					+ "</p></div>");
			for (ofyChan4Post p : t.getPosts()) {
				if (!p.getThumburl().isEmpty()) {
					nimages++;
					// System.out.println("|imgurl:" + p.getImgurl());
					// System.out.println("|thumburl:" + p.getThumburl());
					// System.out.println("|imagename:" + p.getImagename());

					resp.getWriter().println( "<a class='fancybox' href='store?image=" + p.getImgurl()
							+ "' rel='lightbox["+t.getId()+"]' title='"
							+ p.getMessage().replace("\"", "") + "'>"
							+ "<img src='images/loading.gif' "
							+ "data-original='" + p.getThumburl()
							+ "' class='postImg' title='"
							+ p.getMessage().replace("\"", "") + "'></a>");
				} else {
					resp.getWriter().println( "<a class='fancybox' href='images/bigmessage.png"
							+ "' rel='lightbox["+t.getId()+"]' title='"
							+ p.getMessage().replace("\"", "") + "'>"
							+ "<img src='images/loading.gif' "
							+ "data-original='images/message2.png"
							+ "' class='postMsg' title='"
							+ p.getMessage().replace("\"", "") + "'></a>");
				}
			}
			resp.getWriter().println( "</div>");
		}

		if (!c4t.isEmpty()) {
			resp.getWriter().println( "<a href='page?page=" + page + "&from=" + to + "&to="
					+ (to + (to - from)) + "'> <h1>next page</h1></a>");
		}
		// htmlindex +=
		// "<button id='continue' type='button'>Click Me!</button>";
		resp.getWriter().println("</body></html>");

		long totalHtmlTime = System.currentTimeMillis() - startHtmlTime;
		
		//resp.getWriter().println(htmlindex);
		long totalProcessingTime = System.currentTimeMillis() - startTime;
		log.info("Html Generation processing time: " + totalHtmlTime + "ms");
		log.info("Total processing time: " + totalProcessingTime + "ms");
		log.info("Thread:" + nthread + " Image:" + nimages);

	}

}
