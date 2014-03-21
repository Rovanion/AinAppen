package gov.polisen.ainappen;

import java.sql.Date;

public class Case {

	@SuppressWarnings("unused")
	private int deviceID;
	@SuppressWarnings("unused")
	private int localCaseID;
	private String crimeClassification; 
	private String location; 
	private String commander; 
	private Date date; 
	private String status; 
	private String description;

	public Case (
			String classification, 
			String location, 
			String commander, 
			Date date, 
			String status, 
			String description){
		this.deviceID = getDeviceID();
		this.localCaseID = getLocalCaseID();
		this.setCrimeClassification(classification);
		this.setLocation(location);
		this.setCommander(commander);
		this.setDate(date);
		this.setStatus(status);
		this.setDescription(description);
	}

	private int getLocalCaseID() {
		// Get LocalCaseID from local DB
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDeviceID() {
		// Get DeviceID from local DB
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCrimeClassification() {
		return crimeClassification;
	}

	public void setCrimeClassification(String crimeClassification) {
		this.crimeClassification = crimeClassification;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCommander() {
		return commander;
	}

	public void setCommander(String commander) {
		this.commander = commander;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
