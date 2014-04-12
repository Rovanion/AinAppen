package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	private User	user;
	private int		deviceID;

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

}