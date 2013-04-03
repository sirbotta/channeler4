package it.sirbotta.gae4channeler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import javax.servlet.http.*;

import org.jsoup.Jsoup;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.Transform;

@SuppressWarnings("serial")
public class Gae4channelerServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(Gae4channelerServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Get a file service
		FileService fileService = FileServiceFactory.getFileService();

		// Create a new Blob file with mime-type "text/plain"
		AppEngineFile file = fileService.createNewBlobFile("image/jpeg");

		boolean lock = true;
		FileWriteChannel writeChannel = fileService
				.openWriteChannel(file, lock);
		byte[] yourBinaryData = jsoupGetImage("http://www.smnb.it/images/welcome_california.jpg"); // = // get your data from request;
		writeChannel.write(ByteBuffer.wrap(yourBinaryData));
		writeChannel.closeFinally();

		BlobKey blobKey = fileService.getBlobKey(file);
		ImagesService imagesService = ImagesServiceFactory.getImagesService();

		// not used
		// Image oldImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
		// Transform resize = ImagesServiceFactory.makeResize(200, 300);
		// Image newImage = imagesService.applyTransform(resize, oldImage);

		// get url
		String bloburl = imagesService.getServingUrl(ServingUrlOptions.Builder
				.withBlobKey(blobKey));

		resp.setContentType("text/html");
		resp.getWriter().println(bloburl);
		resp.getWriter().println("<img src='"+bloburl+"'>");
		resp.getWriter().println("<img src='"+bloburl+"=s32'>");
		log.severe("wei");
	}

	public static byte[] jsoupGetImage(String uri) {
		try {
			return Jsoup
					.connect(uri)
					.ignoreContentType(true)
					.userAgent(
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/536.26.17 (KHTML, like Gecko) Version/6.0.2 Safari/536.26.17")
					.cookie("auth", "token").timeout(6000).execute()
					.bodyAsBytes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
