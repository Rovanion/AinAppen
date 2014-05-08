package gov.polisen.ainappen;

import android.app.Application;

public class GlobalData extends Application {
	public User	user;
        public String   password;
	public int	deviceID;
	// This URL must start with http:// , otherwise the app will crash.
	public final String webUrl = "http://christian.cyd.liu.se:1337/";
	public final String SipUrl = "itkand-1.ida.liu.se";
}
