package it.sirbotta.gae4channeler;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class WelcomeServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(WelcomeServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/html");

		resp.getWriter().println(
				"<!DOCTYPE html>"
						+ "<html>"
						+ "<head>"
						+ "<title> Welcome to 4channeler"
						+ "</title>"
						// + "<script src='js/jquery-1.7.2.min.js'></script>"
						// + "<script src='js/lightbox.js'></script>"
						// + "<script src='js/jquery.lazyload.min.js'></script>"
						// +
						// "<link rel='stylesheet' href='http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css' />"
						// +
						// "<script src='http://code.jquery.com/ui/1.10.1/jquery-ui.js'></script>"
						// + "<script src='js/jquery.inview.js'></script>"
						// + "<script src='js/my.js'></script>"
						// + "<link href='css/lightbox.css' rel='stylesheet' />"
						+ "<link href='css/style.css' rel='stylesheet' />"
						+ "</head>" + "<body>");
		resp.getWriter().println(
				"<h1>Welcome to 4channeler a new 4chan.org way of view</h1>");

		resp.getWriter().println("<div class='content'>");

		resp.getWriter().println(
				"<div class='box-outer top-box' id='announce'>");
		resp.getWriter()
				.println(
						"<div class='box-inner'>"
								+ "<div class='boxbar'><h2>What is 4channeler</h2></div>");
		resp.getWriter().println("<div class='boxcontent'>");
		resp.getWriter()
				.println(
						"<p>You can visit a snapshot of 4chan's most viewed topic.<br>");
		resp.getWriter()
				.println(
						"Every time you visit a main page you trig a refresh for other people.<br>");
		resp.getWriter()
				.println(
						"Every hour page 1 for all nsfw section will be refreshed.<br>");
		resp.getWriter()
				.println(
						"Every 3 hours all page for all nsfw section will be refreshed.<br>");
		resp.getWriter().println(
				"Other section are on demand with 5 minutes delay<br>");
		resp.getWriter()
				.println(
						"if you open an image, the system save it off 4chan network for 14 days</p>");
		resp.getWriter().println("<p>Enjoy ...  :><p>");
		resp.getWriter().println("</div></div></div>");

		resp.getWriter()
				.println(
						"<div class='box-outer top-box' id='boards'>"
								+ "<div class='box-inner'>"
								+ "<div class='boxbar'>"
								+ "<h2>Boards</h2></div>"
								+ "<div class='boxcontent'>"
								+ "<div class='column'>"
								+ "<h3 style='text-decoration: underline; display: inline;'>Japanese Culture</h3>"
								+ "<ul>"
								+ "<li><a href='page?page=a' class='boardlink' title='Anime &amp; Manga'>Anime &amp; Manga</a></li>"
								+ "<li><a href='page?page=c' class='boardlink' title='Anime/Cute'>Anime/Cute</a></li>"
								+ "<li><a href='page?page=w' class='boardlink' title='Anime/Wallpapers'>Anime/Wallpapers</a></li>"
								+ "<li><a href='page?page=m' class='boardlink' title='Mecha'>Mecha</a></li>"
								+ "<li><a href='page?page=cgl' class='boardlink' title='Cosplay &amp; EGL'>Cosplay &amp; EGL</a></li>"
								+ "<li><a href='page?page=cm' class='boardlink' title='Cute/Male'>Cute/Male</a></li>"
								+ "<li><a href='page?page=f' class='boardlink' title='Flash'>Flash</a></li>"
								+ "<li><a href='page?page=n' class='boardlink' title='Transportation'>Transportation</a></li>"
								+ "<li><a href='page?page=jp' class='boardlink' title='Otaku Culture'>Otaku Culture</a></li>"
								+ "<li><a href='page?page=vp' class='boardlink' title='Pokémon'>Pokémon</a></li>"
								+ "</ul>"
								+ "</div>"
								+ "<div class='column'>"
								+ "<h3 style='text-decoration: underline; display: inline;'>Interests</h3>"
								+ "<ul>"
								+ "<li><a href='page?page=v' class='boardlink' title='Video Games'>Video Games</a></li>"
								+ "<li><a href='page?page=vg' class='boardlink' title='Video Game Generals'>Video Game Generals</a></li>"
								+ "<li><a href='page?page=co' class='boardlink' title='Comics &amp; Cartoons'>Comics &amp; Cartoons</a></li>"
								+ "<li><a href='page?page=g' class='boardlink' title='Technology'>Technology</a></li>"
								+ "<li><a href='page?page=tv' class='boardlink' title='Television &amp; Film'>Television &amp; Film</a></li>"
								+ "<li><a href='page?page=k' class='boardlink' title='Weapons'>Weapons</a></li>"
								+ "<li><a href='page?page=o' class='boardlink' title='Auto'>Auto</a></li>"
								+ "<li><a href='page?page=an' class='boardlink' title='Animals &amp; Nature'>Animals &amp; Nature</a></li>"
								+ "<li><a href='page?page=tg' class='boardlink' title='Traditional Games'>Traditional Games</a></li>"
								+ "<li><a href='page?page=sp' class='boardlink' title='Sports'>Sports</a></li>"
								+ "<li><a href='page?page=sci' class='boardlink' title='Science &amp; Math'>Science &amp; Math</a></li>"
								+ "<li><a href='page?page=int' class='boardlink' title='International'>International</a></li>"
								+ "</ul>"
								+ "</div>"
								+ "<div class='column'>"
								+ "<h3 style='text-decoration: underline; display: inline;'>Creative</h3>"
								+ "<ul>"
								+ "<li><a href='page?page=i' class='boardlink' title='Oekaki'>Oekaki</a></li>"
								+ "<li><a href='page?page=po' class='boardlink' title='Papercraft &amp; Origami'>Papercraft &amp; Origami</a></li>"
								+ "<li><a href='page?page=p' class='boardlink' title='Photography'>Photography</a></li>"
								+ "<li><a href='page?page=ck' class='boardlink' title='Food &amp; Cooking'>Food &amp; Cooking</a></li>"
								+ "<li><a href='page?page=ic' class='boardlink' title='Artwork/Critique'>Artwork/Critique</a></li>"
								+ "<li><a href='page?page=wg' class='boardlink' title='Wallpapers/General'>Wallpapers/General</a></li>"
								+ "<li><a href='page?page=mu' class='boardlink' title='Music'>Music</a></li>"
								+ "<li><a href='page?page=fa' class='boardlink' title='Fashion'>Fashion</a></li>"
								+ "<li><a href='page?page=toy' class='boardlink' title='Toys'>Toys</a></li>"
								+ "<li><a href='page?page=3' class='boardlink' title='3DCG'>3DCG</a></li>"
								+ "<li><a href='page?page=diy' class='boardlink' title='Do-It-Yourself'>Do-It-Yourself</a></li>"
								+ "<li><a href='page?page=wsg' class='boardlink' title='Worksafe GIF'>Worksafe GIF</a></li>"
								+ "</ul>"
								+ "</div>"
								+ "<div class='column'>"
								+ "<h3 style='text-decoration: underline; display: inline;'>Adult</h3> <h3 style='display: inline;'><span class='warning' title='Not Safe For Work'><sup style='vertical-align: text-bottom;'>(NSFW)</sup></span></h3>"
								+ "<ul>"
								+ "<li><a href='page?page=s' class='boardlink' title='Sexy Beautiful Women'>Sexy Beautiful Women</a></li>"
								+ "<li><a href='page?page=hc' class='boardlink' title='Hardcore'>Hardcore</a></li>"
								+ "<li><a href='page?page=hm' class='boardlink' title='Handsome Men'>Handsome Men</a></li>"
								+ "<li><a href='page?page=h' class='boardlink' title='Hentai'>Hentai</a></li>"
								+ "<li><a href='page?page=e' class='boardlink' title='Ecchi'>Ecchi</a></li>"
								+ "<li><a href='page?page=u' class='boardlink' title='Yuri'>Yuri</a></li>"
								+ "<li><a href='page?page=d' class='boardlink' title='Hentai/Alternative'>Hentai/Alternative</a></li>"
								+ "<li><a href='page?page=y' class='boardlink' title='Yaoi'>Yaoi</a></li>"
								+ "<li><a href='page?page=t' class='boardlink' title='Torrent'>Torrents</a></li>"
								+ "<li><a href='http://rs.4chan.org/' class='boardlink' title='Rapidshares'>Rapidshares</a></li>"
								+ "<li><a href='page?page=hr' class='boardlink' title='High Resolution'>High Resolution</a></li>"
								+ "<li><a href='page?page=gif' class='boardlink' title='Adult GIF'>Adult GIF</a></li>"
								+ "</ul>"
								+ "</div>"
								+ "<div class='column'>"
								+ "<h3 style='text-decoration: underline; display: inline;'>Other</h3>"
								+ "<ul>"
								+ "<li><a href='page?page=q' class='boardlink' title='4chan Discussion'>4chan Discussion</a></li>"
								+ "<li><a href='page?page=trv' class='boardlink' title='Travel'>Travel</a></li>"
								+ "<li><a href='page?page=fit' class='boardlink' title='Health &amp; Fitness'>Health &amp; Fitness</a></li>"
								+ "<li><a href='page?page=x' class='boardlink' title='Paranormal'>Paranormal</a></li>"
								+ "<li><a href='page?page=lit' class='boardlink' title='Literature'>Literature</a></li>"
								+ "<li><a href='page?page=adv' class='boardlink' title='Advice'>Advice</a></li>"
								+ "<li><a href='page?page=mlp' class='boardlink' title='Pony'>Pony</a></li>"
								+ "</ul>"
								+ "<h3 style='text-decoration: underline; display: inline;'>Misc.</h3> <h3 style='display: inline;'><span class='warning' title='Not Safe For Work'><sup style='vertical-align: text-bottom;'>(NSFW)</sup></span></h3>"
								+ "<ul>"
								+ "<li><a href='page?page=b' class='boardlink' title='Random'>Random</a></li>"
								+ "<li><a href='page?page=r' class='boardlink' title='Request'>Request</a></li>"
								+ "<li><a href='page?page=r9k' class='boardlink' title='ROBOT9001'>ROBOT9001</a></li>"
								+ "<li><a href='page?page=pol' class='boardlink' title='Politically Incorrect'>Politically Incorrect</a></li>"
								+ "<li><a href='page?page=soc' class='boardlink' title='Social'>Social</a></li>"
								+ "</ul>" + "</div>" + "<br class='clear-bug'>"
								+ "</div>" + "</div>" + "</div>");

		// sections recent-threads
		resp.getWriter().println(
				"<div class='box-outer top-box' id='recent-threads'>");
		resp.getWriter().println(
				"<div class='box-inner'>"
						+ "<div class='boxbar'><h2>Last Update</h2></div>");
		resp.getWriter().println("<div class='boxcontent'>");

		List<ofyChan4Section> c4ss = ofy().load().type(ofyChan4Section.class)
				.order("-lastCrawl").list();

		for (ofyChan4Section c4s : c4ss) {
			c4s.getThreads();
			c4s.getImages();
			c4s.getLastCrawl();

			c4s.getName();

			resp.getWriter().println(
					"<div class='section'>" + "Section /" + c4s.getName()
							+ "/ with " + c4s.getImages() + " images and "
							+ c4s.getThreads() + " Threads "
							+ "<a href='page?page=" + c4s.getName()
							+ "'>Visit</a> " +
							// "<a href='crawl?page="+c4s.getName()+"'>Update</a> "
							// +
							"last update " + c4s.getLastCrawl() + "</div>");

		}
		resp.getWriter().println("</div></div></div>");

		// pay section
		resp.getWriter().println(
				"<h2> If you like it you can pay me a beer </h2>");
		resp.getWriter().println("<div class='donation'>");

		resp.getWriter()
				.println(
						"<form action='https://www.paypal.com/cgi-bin/webscr' method='post'>"
								+ "<input type='hidden' name='cmd' value='_s-xclick'>"
								+ "<input type='hidden' name='encrypted' value='-----BEGIN PKCS7-----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-----END PKCS7-----'>"
								+ "<input type='image' src='https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif' border='0' name='submit' alt='PayPal - The safer, easier way to pay online!'>"
								+ "<img alt='' border='0' src='https://www.paypalobjects.com/it_IT/i/scr/pixel.gif' width='1' height='1'>"
								+ "</form>");

		resp.getWriter().println("</div>");

		// close content
		resp.getWriter().println("</div>");
		resp.getWriter().println("</body></html>");

	}

}
