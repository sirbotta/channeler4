package it.sirbotta.gae4channeler;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

@SuppressWarnings("serial")
public class StoreImageServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(StoreImageServlet.class
			.getName());

	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String imageurl = req.getParameter("image");
		String[] s = imageurl.replace("http://", "").split("/");
		@SuppressWarnings("unused")
		String page = s[1];
		String name = s[3];
		s = name.split("\\.");
		String type = s[1];
		type = (type.equals("jpg")) ? "jpeg" : type;

		byte[] yourBinaryData = jsoupGetImage(imageurl);

		Query query = new Query("__BlobInfo__");
		query.addFilter("filename", FilterOperator.EQUAL, name);
		query.addSort("creation", SortDirection.DESCENDING);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		PreparedQuery pq = datastore.prepare(query);
		Entity queriedFile = pq.asSingleEntity();

		ImagesService imagesService = ImagesServiceFactory.getImagesService();

		// se il blob non esiste giˆ e ho binary data
		if (queriedFile == null) {
			if (yourBinaryData != null) {
				FileService fileService = FileServiceFactory.getFileService();

				// Create a new Blob file with mime-type "text/plain"
				AppEngineFile file = fileService.createNewBlobFile("image/"
						+ type, name);

				boolean lock = true;
				FileWriteChannel writeChannel = fileService.openWriteChannel(
						file, lock);
				writeChannel.write(ByteBuffer.wrap(yourBinaryData));
				writeChannel.closeFinally();
				// not used
				// BlobKey blobKey = fileService.getBlobKey(file);
				// Image oldImage =
				// ImagesServiceFactory.makeImageFromBlob(blobKey);
				// Transform resize = ImagesServiceFactory.makeResize(200, 300);
				// Image newImage = imagesService.applyTransform(resize,
				// oldImage);
			} else {
				log.warning("can't retrive image from " + imageurl);
			}
		}

		// se non trovo i binarydata ma ho il blob
		if (yourBinaryData == null) {
			if (queriedFile != null) {
				BlobKey bKey = new BlobKey(queriedFile.getKey().getName());
				// get url
				imageurl = imagesService
						.getServingUrl(ServingUrlOptions.Builder
								.withBlobKey(bKey));
			}
		}

		resp.setContentType("text/plain");
		resp.getWriter().println(imageurl);
		resp.sendRedirect(imageurl);

	}

	public static byte[] jsoupGetImage(String uri) {
		ofyChan4Metric incomingBandwith = ofy().load()
				.type(ofyChan4Metric.class).id("incomingBandwith").get();

		if (incomingBandwith == null ) {
			incomingBandwith = new ofyChan4Metric("incomingBandwith", 0);
		}

		// se supera gli 800mega di bandwith, allora pippe
		if (incomingBandwith.getValue() < 838860800L) {
			try {
				byte[] data = Jsoup
						.connect(uri)
						.ignoreContentType(true)
						.userAgent(
								"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/536.26.17 (KHTML, like Gecko) Version/6.0.2 Safari/536.26.17")
						.cookie("auth", "token").timeout(3000).execute()
						.bodyAsBytes();
				incomingBandwith.setValue(data.length);
				incomingBandwith.setDate(new Date());
				ofy().save().entity(incomingBandwith);
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

	}

	public static byte[] netUrlGetImage(String uri) throws IOException {
		URL url = new URL(uri);

		// Read the image ...
		InputStream inputStream = url.openStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		int n = 0;
		while (-1 != (n = inputStream.read(buffer))) {
			output.write(buffer, 0, n);
		}
		inputStream.close();

		// Here's the content of the image...
		byte[] data = output.toByteArray();
		return data;
	}
}
