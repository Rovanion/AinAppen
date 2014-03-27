package gov.polisen.ainappen;


import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cases")

public class Case {

	@DatabaseField
	private int deviceID;
	@DatabaseField
	private int localCaseID;
	@DatabaseField
	private String crimeClassification; 
	@DatabaseField
	private String location; 
	@DatabaseField
	private int commander; 
	@DatabaseField
	private Date date; 
	@DatabaseField
	private String status;
	@DatabaseField
	private String description;
	@DatabaseField(useGetSet=true, id = true)
	private String caseID;

	public Case(){
		//Empty constructor needed by ORMLite
	}

	public Case (
			int deviceID,
			int localCaseID,
			String classification, 
			String location, 
			int commander, 
			Date date, 
			String status, 
			String description){
		this.deviceID = deviceID;
		this.localCaseID = localCaseID;
		this.setCrimeClassification(classification);
		this.setLocation(location);
		this.setCommander(commander);
		this.setDate(date);
		this.setStatus(status);
		this.setDescription(description);
	}

	public String toString(){
		return "Case [CaseID=" + this.caseID + ", Device ID=" + this.deviceID + ", LocalCaseID=" +
				this.localCaseID + ", location=" + this.location + ", Commander=" + this.commander + ", Date=" + 
				this.date + ", Status=" + this.status + ", Description=" + this.description + "]";		
	}

	/**
	 * Needs to look this way because ORMLite uses it to create
	 * the artificial Primary Key (in this 'case', caseID)
	 * @return
	 */
	public String getCaseID(){
		return (this.deviceID + "-" + this.localCaseID);
	}

	public void setCaseID(String caseID){
		this.caseID = caseID;
	}

	public int getlocalCaseID(){
		return this.localCaseID;
	}

	public void setlocalCaseID(int localCaseID){
		this.localCaseID = localCaseID;
	}

	public int getdeviceID(){
		return this.deviceID;
	}

	public void setdeviceID(int deviceID){
		this.deviceID = deviceID;
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

	public int getCommander() {
		return commander;
	}

	public void setCommander(int commander) {
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
