package kube.demo.spring.data.mongodb.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "startup_log")
public class Log {
	@Id
	private String id;
	
	private String hostname;
	private Date startTime;
	private String startTimeLocal;
	private Integer pid;

	public Log() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeLocal() {
		return startTimeLocal;
	}

	public void setStartTimeLocal(String startTimeLocal) {
		this.startTimeLocal = startTimeLocal;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", hostname=" + hostname + ", startTime=" + startTime + ", startTimeLocal="
				+ startTimeLocal + ", pid=" + pid + "]";
	}

}
