package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	private User	user;
	private int		deviceID;
	private String	password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}