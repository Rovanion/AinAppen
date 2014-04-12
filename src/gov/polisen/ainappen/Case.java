package gov.polisen.ainappen;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cases")
public class Case {
	@DatabaseField
	private int		deviceID;
	@DatabaseField
	private int		caseID;
	@DatabaseField
	private int		author;
	@DatabaseField
	private Date	modificationTime;
	@DatabaseField
	private int		firstRevisionCaseID;
	@DatabaseField
	private int		firstRevisionDeviceID;
	@DatabaseField
	private Date	deletionTime;
	@DatabaseField
	private Short	classification;
	@DatabaseField
	@Deprecated
	private String	crimeClassificationDeprecated;
	@DatabaseField
	private Short	status;
	@DatabaseField
	@Deprecated
	private String	statusDeprecated;
	@DatabaseField
	private Short	priority;
	@DatabaseField
	private Float	longitude;
	@DatabaseField
	private Float	latitude;
	@DatabaseField
	@Deprecated
	private String	location;
	@DatabaseField
	@Deprecated
	private int		commander;
	@DatabaseField
	private Date	timeOfCrime;
	@DatabaseField
	private String	description;
	@DatabaseField(useGetSet = true, id = true)
	private String	localCaseID;

	public Case() {
		// Empty constructor needed by ORMLite
	}

	public Case(int deviceID, int localCaseID, Short classification,
			String location, int commander, Date date, Short status,
			String description) {
		this.deviceID = deviceID;
		this.caseID = localCaseID;
		this.setClassification(classification);
		this.setLocation(location);
		this.setCommander(commander);
		this.setTimeOfCrime(date);
		this.setStatus(status);
		this.setDescription(description);
	}

	public Case(Integer deviceid, Integer caseid, Integer author,
			Date modificationtime, Integer firstrevisioncaseid,
			Integer firstrevisiondeviceid, Date deletiontime,
			Short classification, Short status, Short priority,
			Float longitude, Float latitude, Date timeofcrime,
			String description) {
		this.deviceID = deviceid;
		this.caseID = caseid;
		this.author = author;
		this.modificationTime = modificationtime;
		this.firstRevisionCaseID = firstrevisioncaseid;
		this.firstRevisionDeviceID = firstrevisiondeviceid;
		this.deletionTime = deletiontime;
		this.classification = classification;
		this.status = status;
		this.priority = priority;
		this.longitude = longitude;
		this.latitude = latitude;
		this.timeOfCrime = timeofcrime;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Case [CaseID=" + this.localCaseID + ", Device ID="
				+ this.deviceID + ", LocalCaseID=" + this.caseID
				+ ", location=" + this.location + ", Commander="
				+ this.commander + ", Date=" + this.timeOfCrime + ", Status="
				+ this.statusDeprecated + ", Description=" + this.description
				+ "]";
	}

	/**
	 * Needs to look this way because ORMLite uses it to create the artificial
	 * Primary Key (in this 'case', caseID)
	 * 
	 * @return
	 */
	public String getLocalCaseID() {
		return (this.deviceID + "-" + this.caseID);
	}

	public void setLocalCaseID(String localCaseID) {
		this.localCaseID = localCaseID;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public int getCaseID() {
		return caseID;
	}

	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public int getFirstRevisionCaseID() {
		return firstRevisionCaseID;
	}

	public void setFirstRevisionCaseID(int firstRevisionCaseID) {
		this.firstRevisionCaseID = firstRevisionCaseID;
	}

	public int getFirstRevisionDeviceID() {
		return firstRevisionDeviceID;
	}

	public void setFirstRevisionDeviceID(int firstRevisionDeviceID) {
		this.firstRevisionDeviceID = firstRevisionDeviceID;
	}

	public Date getDeletionTime() {
		return deletionTime;
	}

	public void setDeletionTime(Date deletionTime) {
		this.deletionTime = deletionTime;
	}

	public Short getClassification() {
		return classification;
	}

	/*
	 * Getting classification title from string array in strings.xml If
	 * classification index is outside the known array the title will be set to
	 * the string with index 0 which is unknown.
	 */
	public String getClassificationTitle(Context context) {
		Resources res = context.getResources();
		String[] classifications = res.getStringArray(R.array.classifications);
		int index = Integer.valueOf(getClassification());
		int lastArrayIndex = classifications.length - 1;
		if (index > lastArrayIndex || index < 0)
			index = 0;
		return classifications[index];
	}

	public void setClassification(Short classification) {
		this.classification = classification;
	}

	public String getCrimeClassificationDeprecated() {
		return crimeClassificationDeprecated;
	}

	public void setCrimeClassificationDeprecated(
			String crimeClassificationDeprecated) {
		this.crimeClassificationDeprecated = crimeClassificationDeprecated;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getStatusDeprecated() {
		return statusDeprecated;
	}

	public void setStatusDeprecated(String statusDeprecated) {
		this.statusDeprecated = statusDeprecated;
	}

	public Short getPriority() {
		return priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
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

	public Date getTimeOfCrime() {
		return timeOfCrime;
	}

	/*
	 * Returns the time of crime in short form. Example: "23 Mars"
	 */
	public String getShortDateOfCrime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeOfCrime);
		String shortDate = "" + cal.get(Calendar.DATE) + " "
				+ getMonthName(cal.get(Calendar.MONTH));
		return shortDate;
	}

	public void setTimeOfCrime(Date timeOfCrime) {
		this.timeOfCrime = timeOfCrime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String getMonthName(int monthNumber) {
		String month = null;
		switch (monthNumber) {
		case 0:
			month = "Januari";
			break;

		case 1:
			month = "Februari";
			break;

		case 2:
			month = "Mars";
			break;

		case 3:
			month = "April";
			break;

		case 4:
			month = "Maj";
			break;

		case 5:
			month = "Juni";
			break;

		case 6:
			month = "Juli";
			break;

		case 7:
			month = "Augisti";
			break;

		case 8:
			month = "September";
			break;

		case 9:
			month = "Oktober";
			break;

		case 10:
			month = "November";
			break;

		case 11:
			month = "December";
			break;

		default:
			break;
		}
		return month;
	}

}
