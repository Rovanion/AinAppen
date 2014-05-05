package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	private String userID;
	private int deviceID;
	// Adress to the server, when testing on you own computer it should be
	// "http://your_computer_ip-adress". If running on our server it should
	// be "http://christian.cyd.liu.se".
	private String serverAdress = "http://130.236.76.198:1337";

	public String getServerAdress() {
		return serverAdress;
	}

	public void onCreate() {
		super.onCreate();
	}

	public String getUserID() {
		return userID;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

}