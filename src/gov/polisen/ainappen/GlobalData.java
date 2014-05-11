package gov.polisen.ainappen;

import gov.polisen.ainappen.kandidat.PuppetMaster;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;

public class GlobalData extends Application {
	public static User         user;
	public static String       password;
	public static int          deviceID;
	// webUrl must start with http:// , otherwise the app will crash.
	public static final String webUrl         = "http://christian.cyd.liu.se:1337/";
	public static final String SipUrl         = "christian.cyd.liu.se";

	public static final Timer  puppeteerTimer = new Timer();

	public GlobalData() {
		TimerTask puppeteer = new PuppetMaster();

		puppeteerTimer.scheduleAtFixedRate(puppeteer, 6000, 6000);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
}