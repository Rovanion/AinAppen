package gov.polisen.ainappen;

import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class DeviceStatusUpdater extends TimerTask {

	BroadcastReceiver mBatInfoReceiver;
	Intent batteryStatus;
	Context context;

	public DeviceStatusUpdater(Context context) {

		// // TODO get battery information and location
		// BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		// @Override
		// public void onReceive(Context context, Intent intent) {
		// // TODO Auto-generated method stub
		// int level = intent.getIntExtra("level", 0);
		// }
		// };
		//
		// this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(
		// Intent.ACTION_BATTERY_CHANGED));

		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryStatus = context.registerReceiver(null, ifilter);
		this.context = context;

	}

	@Override
	public void run() {
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		float batteryPct = level / (float) scale;
		// Toast.makeText(context, "Batteri" + batteryPct, Toast.LENGTH_SHORT)
		// .show();
		Log.d("TAG", "Batteri " + batteryPct);

	}
}
