package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Class used to represent obstacles placed in Open StreetMap
 * @author Joakim
 *
 */
@DatabaseTable (tableName = "mappoints")
public class MapPoint {
	@DatabaseField
	int localMapPointID;
	@DatabaseField
	int deviceID;
	@DatabaseField(useGetSet = true, id = true)
	String mapPointID;
	@DatabaseField
	String mapPointTitle;
	@DatabaseField
	String mapPointSnippet;
	@DatabaseField
	double latitude;
	@DatabaseField
	double longitude;
	@DatabaseField
	boolean isObstacle;

	public MapPoint(){
		//Empty constructor needed by ORMLite
	}

	public MapPoint(
			int localObstacleID, 
			int deviceID, 
			String obstacleTitle,
			String obstacleSnippet,
			double latitude,
			double longitude,
			boolean isObstacle
			){
		this.localMapPointID = localObstacleID;
		this.deviceID = deviceID;
		this.mapPointTitle = obstacleTitle;
		this.mapPointSnippet = obstacleSnippet;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isObstacle = isObstacle;
	}

	public String toString(){
		return "Obstacle [ObstacleID=" + this.mapPointID + ", Device ID=" + this.deviceID + ", localObstacleID=" +
				this.localMapPointID + ", ObstacleTitle = " + this.mapPointTitle + ", ObstacleSnippet = " 
				+ this.mapPointSnippet + ", Latitude" + this.latitude + ", Longitude" + this.longitude +
				", isObstacle=" + (Boolean.valueOf(this.isObstacle)) + "]";		
	}

	public String getObstacleID(){
		return (this.deviceID + "-" + this.localMapPointID);
	}
	public void setObstacleID(String obstacleID){
		this.mapPointID = obstacleID;
	}
	public int getLocalObstacleID(){
		return this.localMapPointID;
	}
	public void setLocalObstacleID(int localObstacleID){
		this.localMapPointID = localObstacleID;
	}
	public int getDeviceID(){
		return this.deviceID;
	}
	public void setDeviceID(int deviceID){
		this.deviceID = deviceID;
	}
	public String getObstacleTitle(){
		return this.mapPointTitle;
	}
	public void setObstacleTitle(String obstacleTitle){
		this.mapPointTitle = obstacleTitle;
	}
	public String getObstacleSnippet(){
		return this.mapPointSnippet;
	}
	public void setObstacleSnippet(String obstacleSnippet){
		this.mapPointSnippet = obstacleSnippet;
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
