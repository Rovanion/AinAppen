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

public class GetNewDevice {
	private final GlobalData settings;

	public GetNewDevice(GlobalData settings) {
		this.settings = settings;
		String deviceUrl = settings.webUrl + "/newDevice/1/1/1";
		final SyncDB syncer = new SyncDB();
		syncer.execute(deviceUrl);
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
			settings.deviceID = result;
			Log.d("global", Integer.toString(result));
		}
	}
}
