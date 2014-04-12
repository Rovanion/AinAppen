package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	private String userID;
	private int deviceID;

	public void onCreate(){
		super.onCreate();
	}
	public String getUserID(){
		return userID;
	}
	public int getDeviceID(){
		return deviceID;
	}
	public void setUserID(String userID){
		this.userID = userID;
	}
	public void setDeviceID(int deviceID){
		this.deviceID = deviceID;
	}

}