package it.sirbotta.gae4channeler;

import java.util.ArrayList;
import java.util.List;

public class Chan4Thread {
	private String id;
	private List<Chan4Post> posts;
	
	public Chan4Thread(String id) {
		this.id = id;
		this.posts = new ArrayList<Chan4Post>();
	}
	
	public Chan4Thread(String id, List<Chan4Post> posts) {
		this.id = id;
		this.posts = posts;
	}
	
	public void addPost(Chan4Post post){
		posts.add(post);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Chan4Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Chan4Post> posts) {
		this.posts = posts;
	}
	
	
	
	
}
