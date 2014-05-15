package gov.polisen.ainappen;

import gov.polisen.ainappen.kandidat.PuppetMaster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.widget.ListView;

public class GlobalData extends Application {
	public static User                user;
	public static String              password;
	public static int                 deviceID;
	public static ListView            caseListView;
	public static final long          startTime      = System.currentTimeMillis();
	// webUrl must start with http:// , otherwise the app will crash.
	public static final String        webUrl         = "http://christian.cyd.liu.se:1337/";
	public static final String        SipUrl         = "christian.cyd.liu.se";

	public static final Timer         puppeteerTimer = new Timer();
	private static OutputStreamWriter logWriter;

	public GlobalData() throws IOException {
		TimerTask puppeteer = new PuppetMaster(0, 0);

		puppeteerTimer.schedule(puppeteer, 1000);

		if (GlobalData.deviceID == 0) {
			new GetNewDevice();
		}

		File file = new File("/sdcard/" + GlobalData.startTime + ".log");
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file, true);
		logWriter = new OutputStreamWriter(fos);
	}

	public static OutputStreamWriter getLogWriter() {
		return logWriter;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
}