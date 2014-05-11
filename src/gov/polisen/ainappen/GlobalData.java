package gov.polisen.ainappen;

import gov.polisen.ainappen.kandidat.PuppetMaster;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;

public class GlobalData extends Application {
	public User         user;
	public String       password;
	public int          deviceID;
	// webUrl must start with http:// , otherwise the app will crash.
	public final String webUrl = "http://christian.cyd.liu.se:1337/";
	public final String SipUrl = "christian.cyd.liu.se";

	public final Timer  caseUpdateTimer = new Timer();

	public GlobalData() {
		TimerTask caseUpdate = new PuppetMaster(this);

		caseUpdateTimer.scheduleAtFixedRate(caseUpdate, 6000, 6000);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
}