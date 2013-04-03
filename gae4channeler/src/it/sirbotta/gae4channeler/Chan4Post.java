package it.sirbotta.gae4channeler;

public class Chan4Post {
	private String imgurl,thumburl,imagename,message;

	public Chan4Post(String imgurl, String thumburl,String imagename, String message) {
		this.imgurl = imgurl;
		this.thumburl = thumburl;
		this.imagename=imagename;
		this.message = message;
	}

	public Chan4Post(String message) {
		this.message = message;
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
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	
}
