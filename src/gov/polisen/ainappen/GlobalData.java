package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	public User         user;
	public String       password;
	public int          deviceID;
	// webUrl must start with http:// , otherwise the app will crash.
	public final String webUrl = "http://christian.cyd.liu.se:1337/";
	public final String SipUrl = "itkand-1.ida.liu.se";
}

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