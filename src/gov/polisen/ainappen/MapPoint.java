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
	int			localMapPointID;
	@DatabaseField
	int			deviceID;
	@DatabaseField(useGetSet = true, id = true)
	String	mapPointID;
	@DatabaseField
	String	mapPointTitle;
	@DatabaseField
	String	mapPointSnippet;
	@DatabaseField
	double	latitude;
	@DatabaseField
	double	longitude;
	@DatabaseField
	boolean	isObstacle;

	public MapPoint(){
		//Empty constructor needed by ORMLite
	}

	public MapPoint(
			int localMapPointID, 
			int deviceID, 
			String mapPointTitle,
			String mapPointSnippet,
			double latitude,
			double longitude,
			boolean isObstacle
			){
		this.localMapPointID = localMapPointID;
		this.deviceID = deviceID;
		this.mapPointTitle = mapPointTitle;
		this.mapPointSnippet = mapPointSnippet;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isObstacle = isObstacle;
	}

	@Override
	public String toString(){
		return "Obstacle [ObstacleID=" + this.mapPointID + ", Device ID=" + this.deviceID + ", localObstacleID=" +
				this.localMapPointID + ", ObstacleTitle = " + this.mapPointTitle + ", ObstacleSnippet = " 
				+ this.mapPointSnippet + ", Latitude" + this.latitude + ", Longitude" + this.longitude +
				", isObstacle=" + (Boolean.valueOf(this.isObstacle)) + "]";		
	}

	public String getMapPointID(){
		return (this.deviceID + "-" + this.localMapPointID);
	}
	public void setMapPointID(String mapPointID){
		this.mapPointID = mapPointID;
	}
	public int getLocalMapPointID(){
		return this.localMapPointID;
	}
	public void setLocalMapPointID(int localMapPointID){
		this.localMapPointID = localMapPointID;
	}
	public int getDeviceID(){
		return this.deviceID;
	}
	public void setDeviceID(int deviceID){
		this.deviceID = deviceID;
	}
	public String getMapPointTitle(){
		return this.mapPointTitle;
	}
	public void setMapPointTitle(String mapPointTitle){
		this.mapPointTitle = mapPointTitle;
	}
	public String getMapPointSnippet(){
		return this.mapPointSnippet;
	}
	public void setMapPointSnippet(String mapPointSnippet){
		this.mapPointSnippet = mapPointSnippet;
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
