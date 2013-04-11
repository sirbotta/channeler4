package it.sirbotta.gae4channeler;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class testerjson {

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws JsonProcessingException
	 */
	public static void main(String[] args) throws JsonProcessingException,
			MalformedURLException, IOException {
		// TODO Auto-generated method stub

		long startPageScrapeTime = System.currentTimeMillis();
		jsonSave4ChanThreads("s", 3);
		long totalPageScrapeTime = System.currentTimeMillis()
				- startPageScrapeTime;

		System.out.println("Page Scraping processing time: "
				+ totalPageScrapeTime + "ms");

	}

	public static Boolean jsonSave4ChanThreads(String board, int npage)
			throws JsonProcessingException, MalformedURLException, IOException {
		List<String> st = json4ChanThreadsId(board);

		for (int i = 0; i <= npage * 15; i++) {

			String id = st.get(i);
			ofyChan4Thread t = new ofyChan4Thread(id);
			List<ofyChan4Post> ps = json4ChanPosts(board, id);
			t.setPosts(ps);
			String lastEpoch = ps.get(ps.size() - 1).getDate();
			t.setDate(new Date(Long.parseLong(lastEpoch) * 1000L));

		}

		return true;
	}

	public static List<String> json4ChanThreadsId(String board)
			throws JsonProcessingException, MalformedURLException, IOException {
		List<String> st = new ArrayList<String>();

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

		return st;
	}

	public static List<ofyChan4Post> json4ChanPosts(String board,
			String threadId) throws JsonProcessingException,
			MalformedURLException, IOException {

		List<ofyChan4Post> ps = new ArrayList<ofyChan4Post>();

		URL u = new URL("http://api.4chan.org/" + board + "/res/" + threadId
				+ ".json");

		ObjectMapper m = new ObjectMapper();

		JsonNode rootNode = m.readTree(downloadUrl(u));

		Iterator<JsonNode> postNode = rootNode.path("posts").elements();

		while (postNode.hasNext()) {

			JsonNode p = postNode.next();
			@SuppressWarnings("unused")
			String no = p.path("no").asText();
			String tim = p.path("tim").asText();
			String ext = p.path("ext").asText();
			String com = p.path("com").asText();
			String time = p.path("time").asText();

			String imagename = tim + ext;
			String imageurl = "http://images.4chan.org/" + board + "/src/"
					+ imagename;
			String thumburl = "http://thumbs.4chan.org/" + board + "/thumb/"
					+ tim + "s.jpg";

			ps.add(new ofyChan4Post(imageurl, thumburl, imagename, com, time));
			// System.out.println(imagename);
			// System.out.println(imageurl);
			// System.out.println(thumburl);
			// System.out.println(com);

		}

		return ps;

	}

	private static byte[] downloadUrl(URL u) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			byte[] chunk = new byte[4096];
			int bytesRead;
			HttpURLConnection uConn = (HttpURLConnection) u.openConnection();
			uConn.setRequestProperty("User-Agent", "MyAgent");
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

}
