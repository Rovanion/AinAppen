package gov.polisen.ainappen;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class GetNewDevice {

	private View rootView;
	String newDevice = "/newDevice/1/1/1";

	public GetNewDevice(View rootView) {
		this.rootView=rootView;
		
		newDevice = "http://christian.cyd.liu.se:1337"+newDevice;
		
//		GlobalData appdata = ((GlobalData) rootView.getContext().getApplicationContext());
//		appdata.getServer()
		
		final SyncDB syncer = new SyncDB();
		syncer.execute(newDevice);
		
	}

	private class SyncDB extends AsyncTask<String, Void, Integer> {

		private int responseCode;

		@Override
		protected Integer doInBackground(String... urls) {

			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urls[0]);

			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				responseCode = statusLine.getStatusCode();
				if (responseCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					return Integer.parseInt(builder.toString());
				} else {
					// error
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			GlobalData appdata = ((GlobalData) rootView.getContext()
					.getApplicationContext());
			appdata.setDeviceID(result);
			Log.d("global", Integer.toString(result));

		}

	}

}
