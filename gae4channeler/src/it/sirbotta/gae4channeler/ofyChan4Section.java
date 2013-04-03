package it.sirbotta.gae4channeler;

import java.util.Date;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;


@Entity
@Cache
public class ofyChan4Section {
		
		@Id private String name;
		private int threads,images;
		@Index(IfNotNull.class) private Date lastCrawl;
		
		@SuppressWarnings("unused")
		private ofyChan4Section() {}

		public ofyChan4Section(String name, int threads, int images,
				Date lastCrawl) {
			this.name = name;
			this.threads = threads;
			this.images = images;
			this.lastCrawl = lastCrawl;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getThreads() {
			return threads;
		}

		public void setThreads(int threads) {
			this.threads = threads;
		}

		public int getImages() {
			return images;
		}

		public void setImages(int images) {
			this.images = images;
		}

		public Date getLastCrawl() {
			return lastCrawl;
		}

		public void setLastCrawl(Date lastCrawl) {
			this.lastCrawl = lastCrawl;
		}
		
		
		
		
}
