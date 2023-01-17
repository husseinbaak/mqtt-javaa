package com.amu.custommqtt.dto;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class MessageMqtt {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String topic;

	private String message;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MessageMqtt [message=" + message + "]";
	}

}
