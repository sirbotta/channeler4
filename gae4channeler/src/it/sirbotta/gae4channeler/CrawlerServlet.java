package it.sirbotta.gae4channeler;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class CrawlerServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(PageServlet.class
			.getName());
	private int nimages = 0, nthread = 0;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		long startTime = System.currentTimeMillis();
		String page = req.getParameter("page");
		String snpage = req.getParameter("npage");
		int npage = (snpage == null) ? 1 : Integer.parseInt(snpage);

		long startPageScrapeTime = System.currentTimeMillis();
		// List<Chan4Thread> test =
		if (page.equals("adult")) {
			jsonSave4ChanThreads("s", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("hc", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("h", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("hr", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("d", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("gif", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
		} else if (page.equals("SFW")) {
			jsonSave4ChanThreads("cgl", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("a", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("co", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("vg", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("v", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
			jsonSave4ChanThreads("wsg", npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
		} else {
			// retrive4ChanThreads(page, npage);
			jsonSave4ChanThreads(page, npage);
			log.info("Thread:" + nthread + " Image:" + nimages);
		}
		long totalPageScrapeTime = System.currentTimeMillis()
				- startPageScrapeTime;

		String htmlindex = "<html>" + "<head>" + "<title> Crawler of " + page
				+ "</title>"
				// + "<script src='js/jquery-1.7.2.min.js'></script>"
				// + "<script src='js/lightbox.js'></script>"
				// + "<script src='js/jquery.lazyload.min.js'></script>"
				// +
				// + "<script src='js/jquery.inview.js'></script>"
				// "<script src='js/my.js'></script>"
				// + "<link href='css/lightbox.css' rel='stylesheet' />"
				// + "<link href='css/style.css' rel='stylesheet' />" +
				// "</head>"
				+ "<body>";

		htmlindex += "<div>Page Scraping processing time: "
				+ totalPageScrapeTime + "ms</div>";
		htmlindex += "<a href='page?page=" + page
				+ "&from=0&to=10'> Vai alla pagina</a>";
		htmlindex += "</body></html>";

		resp.setContentType("text/html");
		resp.getWriter().println(htmlindex);
		long totalProcessingTime = System.currentTimeMillis() - startTime;
		log.info("Page Scraping processing time: " + totalPageScrapeTime + "ms");
		log.info("Total processing time: " + totalProcessingTime + "ms");

	}

	public Boolean jsonSave4ChanThreads(String board, int npage) {
		List<String> st = json4ChanThreadsId(board);

		nthread = 0;
		nimages = 0;
		try {
			for (int i = 0; i < npage * 15; i++) {

				String id = st.get(i);
				ofyChan4Thread t = new ofyChan4Thread(id, board, new Date());
				List<ofyChan4Post> ps = json4ChanPosts(board, id);
				t.setPosts(ps);
				String lastEpoch = ps.get(ps.size() - 1).getDate();
				t.setDate(new Date(Long.parseLong(lastEpoch) * 1000L));

				// controllo se ce ne è uno uguale
				ofyChan4Thread told = ofy().load().type(ofyChan4Thread.class)
						.id(id).get();

				if (told != null) {
					if (told.getDate().equals(t.getDate())) {
						log.info("thread "+id+" not new, skip");
					} else {
						ofy().save().entity(t);
					}
				} else {
					ofy().save().entity(t);
				}
				nthread++;
			}

			ofyChan4Section section = new ofyChan4Section(board, nthread,
					nimages, new Date());

			ofy().save().entity(section);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getMessage());
			return false;
		}
	}

	public List<String> json4ChanThreadsId(String board) {
		List<String> st = new ArrayList<String>();

		try {

			URL u = new URL("http://api.4chan.org/" + board + "/catalog.json");

			ObjectMapper m = new ObjectMapper();

			JsonNode rootNode = m.readTree(downloadUrl(u));

			Iterator<JsonNode> pageNode = rootNode.elements();

			while (pageNode.hasNext()) {
				JsonNode threadNode = pageNode.next().path("threads");
				// System.out.println(threadNode.toString());

				Iterator<JsonNode> thread = threadNode.elements();

				while (thread.hasNext()) {

					JsonNode singleThread = thread.next();
					// System.out.println(singleThread.path("no"));
					st.add(singleThread.path("no").asText());
				}

			}
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		return st;
	}

	public List<ofyChan4Post> json4ChanPosts(String board, String threadId) {

		List<ofyChan4Post> ps = new ArrayList<ofyChan4Post>();

		try {

			URL u = new URL("http://api.4chan.org/" + board + "/res/"
					+ threadId + ".json");

			ObjectMapper m = new ObjectMapper();

			JsonNode rootNode = m.readTree(downloadUrl(u));

			Iterator<JsonNode> postNode = rootNode.path("posts").elements();
			Boolean flag = true;
			while (postNode.hasNext()) {

				JsonNode p = postNode.next();
				String no = p.path("no").asText();
				String tim = p.path("tim").asText();
				String ext = p.path("ext").asText();
				String com = p.path("com").asText();
				String time = p.path("time").asText();
				String imageurl, thumburl;
				String imagename = tim + ext;
				if (!imagename.equals("")) {
					imageurl = "http://images.4chan.org/" + board + "/src/"
							+ imagename;
					if (flag) {
						thumburl = "http://0.thumbs.4chan.org/" + board
								+ "/thumb/" + tim + "s.jpg";
						flag = false;
					} else {
						thumburl = "http://1.thumbs.4chan.org/" + board
								+ "/thumb/" + tim + "s.jpg";
						flag = true;
					}

				} else {
					imageurl = "";
					thumburl = "";
				}

				ps.add(new ofyChan4Post(imageurl, thumburl, imagename, com,
						time));
				// System.out.println(imagename);
				// System.out.println(imageurl);
				// System.out.println(thumburl);
				// System.out.println(com);
				if (!imageurl.equals("")) {
					nimages++;
				}

			}
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		return ps;

	}

	private byte[] downloadUrl(URL u) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			byte[] chunk = new byte[4096];
			int bytesRead;
			HttpURLConnection uConn = (HttpURLConnection) u.openConnection();
			uConn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/536.26.17 (KHTML, like Gecko) Version/6.0.2 Safari/536.26.17");
			// uConn.setRequestProperty("If-Modified-Since","7 Sep 2005 19:43:31 GMT");
			InputStream stream = uConn.getInputStream();

			while ((bytesRead = stream.read(chunk)) > 0) {
				outputStream.write(chunk, 0, bytesRead);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return outputStream.toByteArray();
	}

	public Element retrive4ChanBoardElements(String uri) throws IOException {
		ofyChan4Metric incomingBandwith = ofy().load()
				.type(ofyChan4Metric.class).id("incomingBandwith").get();

		if (incomingBandwith == null) {
			incomingBandwith = new ofyChan4Metric("incomingBandwith", 0);
		}

		try {
			long startTime = System.currentTimeMillis();
			Document doc = Jsoup
					.connect(uri)
					// .data("query", "Java")
					.userAgent(
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/536.26.17 (KHTML, like Gecko) Version/6.0.2 Safari/536.26.17")
					.cookie("auth", "token")
					// .timeout(6000)
					.get();

			incomingBandwith.setValue(doc.toString().getBytes().length);
			incomingBandwith.setDate(new Date());
			ofy().save().entity(incomingBandwith);

			Elements boards = doc.getElementsByClass("board");
			long totalTime = System.currentTimeMillis() - startTime;
			log.info("page " + uri + " parsed in " + totalTime + "ms");
			return boards.first();

		} catch (Exception e) {
			log.severe(e.toString());
			return null;// new Element(Tag.valueOf("p"), "");
		}

	}

	// TODO da sistemare per ora ha ancora pezzi del vecchio
	public List<Chan4Thread> retrive4ChanThreads(String page, int npage)
			throws IOException {
		List<Chan4Thread> threads = new ArrayList<Chan4Thread>();

		int localnimages = 0;
		String lastEpoch = "";

		for (int i = 0; i <= npage; i++) {

			Element board;

			if (i == 0) {
				// board =
				// retrive4ChanBoardElements("http://channeler4.appspot.com/gae4channeler");
				board = retrive4ChanBoardElements("http://boards.4chan.org/"
						+ page + "/");
			} else {
				board = retrive4ChanBoardElements("http://boards.4chan.org/"
						+ page + "/" + i);
			}
			if (board != null) {
				Elements PostsLinks = board.select("a[class=replylink]");
				for (Element pl : PostsLinks) {
					if (pl.html().equals("here")) {
						Element singleThreaBoard = retrive4ChanBoardElements(pl
								.absUrl("href"));
						Element t = singleThreaBoard.select("div.thread")
								.first();

						Chan4Thread c4t = new Chan4Thread(t.attr("id"));
						ofyChan4Thread ofyc4t = new ofyChan4Thread(
								t.attr("id"), page, new Date());

						Elements posts = t.select("div.postContainer");
						for (Element p : posts) {
							String imgurl = p.select("div.file")
									.select("a[href]").attr("abs:href");
							String thumburl = p.select("img[src$=.jpg]").attr(
									"abs:src");
							String message = StringEscapeUtils.escapeXml(p
									.select("blockquote.postMessage").text());
							String imagename = p.select("div.fileInfo")
									.select("a[href]").html();

							lastEpoch = p.select(".postInfo")
									.select(".dateTime").attr("data-utc");
							Chan4Post c4p = new Chan4Post(imgurl, thumburl,
									imagename, message);

							ofyChan4Post ofyc4p = new ofyChan4Post(imgurl,
									thumburl, imagename, message);

							c4t.addPost(c4p);
							ofyc4t.addPost(ofyc4p);

							if (!imgurl.equals("")) {
								nimages++;
								localnimages++;
							}
						}

						// se non lo contiene già
						if (!threads.contains(c4t)) {
							threads.add(c4t);
						}

						ofyc4t.setDate(new Date(
								Long.parseLong(lastEpoch) * 1000L));

						ofy().save().entity(ofyc4t);
						nthread++;
					}
				}
			}
		}

		ofyChan4Section section = new ofyChan4Section(page, threads.size(),
				localnimages, new Date());

		ofy().save().entity(section);

		return threads;
	}

}
