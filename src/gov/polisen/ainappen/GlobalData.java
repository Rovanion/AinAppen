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
	public static ListView caseListView;
	public static long starttime;
	// webUrl must start with http:// , otherwise the app will crash.
	public static final String webUrl         = "http://christian.cyd.liu.se:1337/";
	public static final String SipUrl         = "christian.cyd.liu.se";
	public static DeviceStatusUpdater dsu = null;


	public static final Timer  puppeteerTimer = new Timer();

	public GlobalData() {

		TimerTask puppeteer = new PuppetMaster(0, 0);

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