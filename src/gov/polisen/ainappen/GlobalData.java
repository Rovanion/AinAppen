package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	private User	user;
	private int		deviceID;
	// Adress to the server, when testing on you own computer it should be
	// "http://your_computer_ip-adress". If running on our server it should
	// be "http://christian.cyd.liu.se".
	private String serverAdress = "http://130.236.76.198:1337";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public User getUser() {
		return user;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}	

	public String getServerAdress() {
		return serverAdress;
	}

}