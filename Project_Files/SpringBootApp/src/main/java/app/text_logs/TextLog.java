package app.text_logs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "textLog")
public class TextLog {
	
	@Id
	private String id;
	private String operatorId;
	private String personId;
	private String timestamp;
	private String duration; //in seconds
	private String messages;
	private String images;
	
	public TextLog() {
		super();
	}

	public TextLog(String id, String operatorId, String personId, String timestamp, String duration, String messages,
			String images) {
		super();
		this.id = id;
		this.operatorId = operatorId;
		this.personId = personId;
		this.timestamp = timestamp;
		this.duration = duration;
		this.messages = messages;
		this.images = images;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	public String toString() {
		return id + ": time: " + timestamp;
	}
	
	
	
}
