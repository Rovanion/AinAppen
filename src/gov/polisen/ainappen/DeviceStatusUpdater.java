package gov.polisen.ainappen;

import gov.polisen.ainappen.kandidat.EnergySavingPolicy;

import java.io.IOException;
import java.util.Timer;
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
	final GlobalData appData;
	Intent batteryStatus;
	Context context;
	LocationManager locationManager;
	Location lastLocation;
	// Ändra server här för att testa
	String server = "http://christian.cyd.liu.se:1337/";
	int deviceID;
	String action;
	boolean isNetworkEnabled;
	boolean isGPSEnabled;

	public DeviceStatusUpdater(Context context) {
		this.context = context;
		// Set up reciever to get battery level
		// IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		// batteryStatus = context.registerReceiver(null, ifilter);
		appData = (GlobalData) context.getApplicationContext();
		// Set up listener to get gps-position
		// SetupGps();

		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(this, 1000, 60000); // (timertask,delay,period)
	}

	@Override
	public void run() {
		// retrieves the devices battery level
		// int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		// Log.d("TAG", "Batteri " + level);

		deviceID = 2; // TODO för att kunna testa med något som finns i
		// databasen

		// doesnt do anything if location is unknown
		// if (lastLocation == null) {
		// lastLocation = new Location("")
		// }

		// double longitude = lastLocation.getLongitude();
		// double latitude = lastLocation.getLatitude();
		action = "updateDevice/";

		// String positionInfo = server + action + deviceID + "/" + level
		// + "/" + longitude + "/" + latitude;

		// Fake data
		String positionInfo = server + action + deviceID + "/" + 100 + "/" + 1
				+ "/" + 1;

		// Log.d("TAG", "Lat: " + lastLocation.getLatitude());
		// Log.d("TAG", "Long: " + lastLocation.getLongitude());

		EnergySavingPolicy.getPolicy().getAlgorithm().uploadPosition(positionInfo);
	}

	public void uploadPosition(String positionInfo){
		new SendDeviceInfo().execute(positionInfo);
	}

	private void SetupGps() {
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		//Checks if device has atleast one GPS- and Network provider enabled
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		Log.d("jocke","network: "+Boolean.toString(isNetworkEnabled));
		Log.d("jocke","GPS: "+Boolean.toString(isGPSEnabled));

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				// lastLocation = location;
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}
		};

		/*
		 *  Register the listener with the Location Manager to receive location updates
		 *  if statement catches program crash due to no location provider
		 */

		if(isNetworkEnabled){
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		}
		else if(isGPSEnabled){
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}
		else{
			//Donothing
		}
	}

	private class SendDeviceInfo extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

			// StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpPut httpPut = new HttpPut(urls[0]);
			Log.d("TAG", urls[0]);

			String ret = "";

			try {

				HttpResponse response = client.execute(httpPut);

				StatusLine statusLine = response.getStatusLine();

				int statusCode = statusLine.getStatusCode();
				ret = String.valueOf(statusCode);

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
			showToast("Uploaded position.");
			Log.d("TAG", "Result: " + result);
		}

	}
	public void showToast(String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
