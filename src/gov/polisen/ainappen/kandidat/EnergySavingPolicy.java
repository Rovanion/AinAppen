package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import gov.polisen.ainappen.GlobalData;

import java.util.Date;

import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

public class EnergySavingPolicy{

	private final View root;
	protected Double   batteryLevel;
	SiminAlgorithm     simin;
	NewAlgorithm       newAlg;
	Algorithm          currentAlgorithm;
	static EnergySavingPolicy singleton = null;


	private EnergySavingPolicy(View root) {
		this.root = root;
		batteryLevel = getBatteryLevel();
		simin = new SiminAlgorithm(root);
		newAlg = new NewAlgorithm(root);

		// Schould be updated every X minutes.
		choseAlgorithm();
	}

	public static EnergySavingPolicy getPolicy(View root) {
		if (singleton == null)
			singleton = new EnergySavingPolicy(root);
		return singleton;
	}

	public static EnergySavingPolicy getPolicy() {
		return singleton;
	}

	private void choseAlgorithm() {
		batteryLevel = getBatteryLevel();

		if (batteryLevel > 0.5) {
			// Standard algorithm = no algorithm.
		}
		else if (batteryLevel > 0.3){
			simin.startRepeatingTask();
			currentAlgorithm = simin;
		}
		else if (batteryLevel > 0.2) {
			simin.stopRepeatingTask();
			currentAlgorithm = newAlg;
		}
		// Below battery level 0.5
		else {
			// Standard algorithm = no algorithm.
		}
	}


	public Algorithm getAlgorithm() {
		//Remove when all algorithms works
		if (!simin.isRunning()){
			simin.startRepeatingTask();
		}
		return simin;

		//		if (currentAlgorithm instanceof SiminAlgorithm){
		//			if (!((SiminAlgorithm) currentAlgorithm).isRunning()){
		//				((SiminAlgorithm) currentAlgorithm).startRepeatingTask();
		//			}
		//			((SiminAlgorithm) currentAlgorithm).startRepeatingTask();
		//		}
		//		return currentAlgorithm;
	}

	public double getBatteryLevel() {
		Intent batteryIntent = root.getContext().registerReceiver(null,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int rawlevel = batteryIntent.getIntExtra("level", -1);
		double scale = batteryIntent.getIntExtra("scale", -1);
		double level = -1;
		if (rawlevel >= 0 && scale > 0) {
			level = rawlevel / scale;
		}
		return level;
	}
	
	public Case getDummyCase() {
		int deviceId = GlobalData.deviceID;
		Case dummyCase = new Case(deviceId, 0, 1, new Date(), 0, deviceId, new Date(), Short.valueOf((short)1), Short.valueOf((short)1), Short.valueOf((short)1), null, null, new Date(), "Hehe");
		return dummyCase;
	}
}
