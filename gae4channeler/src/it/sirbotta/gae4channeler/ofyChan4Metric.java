package it.sirbotta.gae4channeler;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ofyChan4Metric {
	
	@Id private String name;
	private long value;
	private Date date;
	
	@SuppressWarnings("unused")
	private ofyChan4Metric(){}

	public ofyChan4Metric(String name, long value) {
		this.name = name;
		this.value = value;
		this.date = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}
