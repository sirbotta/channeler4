package it.sirbotta.gae4channeler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;

@Embed
class ofyChan4Post {
	private String imgurl,thumburl,imagename,date;
	private Text message;
	
	
	@SuppressWarnings("unused")
	private ofyChan4Post() {}
	
	public ofyChan4Post(String imgurl, String thumburl,String imagename, String message) {
		this.imgurl = imgurl;
		this.thumburl = thumburl;
		this.imagename=imagename;
		this.message = new Text(message);
	}
	
	public ofyChan4Post(String imgurl, String thumburl,String imagename, String message, String date) {
		this.imgurl = imgurl;
		this.thumburl = thumburl;
		this.imagename=imagename;
		this.message = new Text(message);
		this.date = date;
	}

	public ofyChan4Post(String message) {
		this.message = new Text(message);
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getThumburl() {
		return thumburl;
	}

	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getMessage() {
		return message.getValue();
	}

	public void setMessage(String message) {
		this.message = new Text(message);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}

@Entity
@Cache
public class ofyChan4Thread {
	
	@Id private String id;
	private List<ofyChan4Post> posts =new ArrayList<ofyChan4Post>();
	@Index(IfNotNull.class) private String page;
	@Index(IfNotNull.class) private Date date;
	
	@SuppressWarnings("unused")
	private ofyChan4Thread() {}
	
	public ofyChan4Thread(String id) {
		this.id = id;
	}
	
	public ofyChan4Thread(String id, String page, Date date) {
		this.id = id;
		this.page = page;
		this.date = date;
	}
	
	
	public void addPost(ofyChan4Post post){
		posts.add(post);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ofyChan4Post> getPosts() {
		return posts;
	}

	public void setPosts(List<ofyChan4Post> posts) {
		this.posts = posts;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
