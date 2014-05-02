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
import android.widget.Toast;

public class DeviceStatusUpdater extends TimerTask {

	BroadcastReceiver mBatInfoReceiver;
	Intent batteryStatus;
	Context context;
	LocationManager locationManager;
	Location lastLocation;
	String server = "http://89.160.65.182:1337/";
	Integer deviceID;
	String action;

	public DeviceStatusUpdater(Context context) {
		this.context = context;
		// Set up reciever to get battery level
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryStatus = context.registerReceiver(null, ifilter);
		// Set up listener to get gps-position
		SetupGps();
	}

	@Override
	public void run() {
		// retrieves the devices battery level
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		Log.d("TAG", "Batteri " + level);

		final GlobalData appData = (GlobalData) context.getApplicationContext();

		Integer deviceID = appData.getDeviceID();
		String action;

		// doesnt do anything if location is unknown
		if (lastLocation == null) {
			return;
		}

		if (deviceID == null) {
			// TODO Skapa nytt device - returnerar ID
			action = "newDevice/";
			// new SendDeviceInfo().execute(server + action + "/" + level + "/"
			// + "long" + "/" + "lat");
		} else {
			// TODO ta bort
			deviceID = 2;
			// PUT
			double longitude = lastLocation.getLongitude();
			double latitude = lastLocation.getLatitude();

			action = "updateDevice/";
			new SendDeviceInfo().execute(server + action + deviceID + "/"
					+ level + "/" + longitude + "/" + latitude);
			Log.d("TAG", "Lat: " + lastLocation.getLatitude());
			Log.d("TAG", "Long: " + lastLocation.getLongitude());
		}

	}

	private void SetupGps() {
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				lastLocation = location;
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	private class SendDeviceInfo extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

			// StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpPut httpPut = new HttpPut(urls[0]);
			Log.d("TAG", urls[0]);
			String ret = "TST";

			try {
				Log.d("TAG", "1");
				HttpResponse response = client.execute(httpPut);
				Log.d("TAG", "2");
				StatusLine statusLine = response.getStatusLine();
				Log.d("TAG", "3");
				int statusCode = statusLine.getStatusCode();
				ret = String.valueOf(statusCode);
				Log.d("TAG", "Statuscode: " + statusCode);
				
				
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
				Log.d("TAG", "e1");
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("TAG", "e2");
			}

			return ret;
		}

		@Override
		protected void onPostExecute(String result) {
			
				Log.d("TAG", "Result: " + result);
			
		}

	}

}
