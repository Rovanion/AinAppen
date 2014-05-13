package gov.polisen.ainappen;

import gov.polisen.ainappen.kandidat.PuppetMaster;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.widget.ListView;

public class GlobalData extends Application {
	public static User         user;
	public static String       password;
	public static int          deviceID;
	// webUrl must start with http:// , otherwise the app will crash.
	public static final String webUrl         = "http://christian.cyd.liu.se:1337/";
	public static final String SipUrl         = "christian.cyd.liu.se";

	public static final Timer  puppeteerTimer = new Timer();
	public static long starttime;
	public static ListView caseListView;

	public GlobalData() {
		TimerTask puppeteer = new PuppetMaster(0, 0, System.currentTimeMillis());

		starttime = System.currentTimeMillis();
		puppeteerTimer.schedule(puppeteer, 1000);
		
		if (GlobalData.deviceID == 0) {
			new GetNewDevice();
		}	

}

	@Override
	public void onCreate() {
		super.onCreate();
	}
}