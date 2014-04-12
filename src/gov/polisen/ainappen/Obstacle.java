package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Class used to represent obstacles placed in Open StreetMap
 * @author Joakim
 *
 */
@DatabaseTable (tableName = "obstacles")
public class Obstacle {
	@DatabaseField
	int localObstacleID;
	@DatabaseField
	int deviceID;
	@DatabaseField(useGetSet = true, id = true)
	String obstacleID;
	@DatabaseField
	String obstacleTitle;
	@DatabaseField
	String obstacleSnippet;
	@DatabaseField
	double latitude;
	@DatabaseField
	double longitude;
	@DatabaseField
	boolean isObstacle;

	public Obstacle(){
		//Empty constructor needed by ORMLite
	}

	public Obstacle(
			int localObstacleID, 
			int deviceID, 
			String obstacleTitle,
			String obstacleSnippet,
			double latitude,
			double longitude,
			boolean isObstacle
			){
		this.localObstacleID = localObstacleID;
		this.deviceID = deviceID;
		this.obstacleTitle = obstacleTitle;
		this.obstacleSnippet = obstacleSnippet;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isObstacle = isObstacle;
	}

	public String toString(){
		return "Obstacle [ObstacleID=" + this.obstacleID + ", Device ID=" + this.deviceID + ", localObstacleID=" +
				this.localObstacleID + ", ObstacleTitle = " + this.obstacleTitle + ", ObstacleSnippet = " 
				+ this.obstacleSnippet + ", Latitude" + this.latitude + ", Longitude" + this.longitude +
				", isObstacle=" + (Boolean.valueOf(this.isObstacle)) + "]";		
	}

	public String getObstacleID(){
		return (this.deviceID + "-" + this.localObstacleID);
	}
	public void setObstacleID(String obstacleID){
		this.obstacleID = obstacleID;
	}
	public int getLocalObstacleID(){
		return this.localObstacleID;
	}
	public void setLocalObstacleID(int localObstacleID){
		this.localObstacleID = localObstacleID;
	}
	public int getDeviceID(){
		return this.deviceID;
	}
	public void setDeviceID(int deviceID){
		this.deviceID = deviceID;
	}
	public String getObstacleTitle(){
		return this.obstacleTitle;
	}
	public void setObstacleTitle(String obstacleTitle){
		this.obstacleTitle = obstacleTitle;
	}
	public String getObstacleSnippet(){
		return this.obstacleSnippet;
	}
	public void setObstacleSnippet(String obstacleSnippet){
		this.obstacleSnippet = obstacleSnippet;
	}
	public double getLatitude(){
		return this.latitude;
	}
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	public double getLongitude(){
		return this.longitude;
	}
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	public boolean getIsObstacle(){
		return this.isObstacle;
	}
	public void setIsObstacle(boolean isObstacle){
		this.isObstacle = isObstacle;
	}



}
