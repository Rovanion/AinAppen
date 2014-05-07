package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	private User	user;
	private int		deviceID;
	private String	password;
	// Adress to the server, when testing on you own computer it should be
	// "http://your_computer_ip-adress". If running on our server it should
	// be "http://christian.cyd.liu.se".
	private String serverAdress = "http://christian.cyd.liu.se:1337";

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
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}