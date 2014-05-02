package gov.polisen.ainappen;

import java.io.IOException;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

public class DeviceStatusUpdater extends TimerTask implements LocationListener {

	BroadcastReceiver mBatInfoReceiver;
	Intent batteryStatus;
	Context context;
	LocationManager locationManager;
	private String provider;

	public DeviceStatusUpdater(Context context) {
		this.context = context;
		// Set up reciever to get battery level
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryStatus = context.registerReceiver(null, ifilter);
		// Set up listener to get gps-position
		setupLocationListener();
	}

	@Override
	public void run() {
		// retrieves the devices battery level
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		Log.d("TAG", "Batteri " + level);

		final GlobalData appData = (GlobalData) context.getApplicationContext();
		// TODO hårdkodad för testning
		String server = "http://christian.cyd.liu.se:1337/";
		Integer deviceID = appData.getDeviceID();
		String action;
		if (deviceID == null) {
			// TODO Skapa nytt device - returnerar ID
			action = "newDevice/";
			new SendDeviceInfo().execute(server + action + "/" + level + "/"
					+ "long" + "/" + "lat");
		} else {
			// PUT
			Location loc = getLocation();

			double longitude, latitude;
			if (loc != null) {
				Log.d("TAG", "Location: " + loc.getLatitude());
				longitude = loc.getLongitude();
				latitude = loc.getLatitude();
			} else {
				// TODO
				longitude = 1;
				latitude = 1;
			}
			action = "updateDevice/";
			new SendDeviceInfo().execute(server + action + deviceID + "/"
					+ level + "/" + longitude + "/" + latitude);
			Log.d("TAG", "Long: " + longitude);
		}

	}

	private void setupLocationListener() {
		// Get the location manager
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the location provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
	}

	public Location getLocation() {
		Location location = locationManager.getLastKnownLocation(provider);
		return location;

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	private class SendDeviceInfo extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

			// StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpPut httpPut = new HttpPut(urls[0]);

			try {
				HttpResponse response = client.execute(httpPut);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {

					// HttpEntity entity = response.getEntity();
					// InputStream content = entity.getContent();
					// BufferedReader reader = new BufferedReader(
					// new InputStreamReader(content));
					// String line;
					// while ((line = reader.readLine()) != null) {
					// builder.append(line);
					// }
					//
					// return builder.toString();
				} else {
					// Ev error message
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

				// Converting json to list of case objects
				// String camelCasedJson = camelCase(result);
				// List<Case> externalCaseList = new Gson().fromJson(
				// camelCasedJson, new TypeToken<List<Case>>() {
				// }.getType());
				//
				// // Example case on server doesn't contain every field
				// externalCaseList = addMissedFields(externalCaseList);
				//
				// // Do the actual syncing
				// List<Case> mergedCaseList =
				// syncWithLocalDB(externalCaseList);
				//
				// // Updates listview
				// if (caseListView != null) {
				// updateListView(mergedCaseList);
				// }
				//
				// showToast("Synced with external DB.");
			}
		}

		private String camelCase(String casesJson) {
			String[][] replacements = {
					{ "modificationtime", "modificationTime" },
					{ "firstrevisioncaseid", "firstRevisionCaseId" },
					{ "firstrevisioncaseid", "firstRevisionCaseId" },
					{ "deviceid", "deviceID" }, { "caseid", "caseID" },
					{ "firstrevisioncaseid", "firstRevisionCaseID" },
					{ "deviceid", "firstrevisiondeviceid" },
					{ "deviceid", "deletiontime" },
					{ "deviceid", "timeofcrime" }, };

			// loop over the array and replace
			String strOutput = casesJson;
			for (String[] replacement : replacements) {
				strOutput = strOutput.replace(replacement[0], replacement[1]);
			}
			return strOutput;
		}

	}

}
