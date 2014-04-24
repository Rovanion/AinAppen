package gov.polisen.ainappen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ExternalDBHandeler {

	String	webserver	= "http://christian.cyd.liu.se";
	String casesForUser = "http://christian.cyd.liu.se:1337/casesForUser/1";
	ListView caseListView;
	Context rootview;

	public ExternalDBHandeler(Activity activity) {
		this.rootview = activity;
	}

	public void getCasesFromDB(List<Case> localCaseList, int userID, ListView caseListView) {
		this.caseListView = caseListView;
		GrabURL url = new GrabURL();
		url.execute(casesForUser);
	}

	public void updateCaseList(String casesJson) {
		String camelCasedcases = camelCase(casesJson);

		Log.d("henning", camelCasedcases);
		List<Case> caseList = new Gson().fromJson(casesJson, new TypeToken<List<Case>>() {}.getType());

		for (Case c : caseList) {
			// TimeOfCrime is missing in external database why this temporary solution.
			if (c.getTimeOfCrime() == null) c.setTimeOfCrime(new Date());
			if (c.getPriority() == null) c.setPriority((short)1);
		}

		CaseListAdapter adapter = new CaseListAdapter(rootview, caseList);
		caseListView.setAdapter(adapter);
	}



	private String camelCase(String casesJson) {
		//array to hold replacements
		String[][] replacements = {
				{"modificationtime", "modificationTime"},
				{"firstrevisioncaseid", "firstRevisionCaseId"},
				{"firstrevisioncaseid", "firstRevisionCaseId"},
				{"deviceid", "deviceID"},
				{"caseid", "caseID"},
				{"firstrevisioncaseid", "firstRevisionCaseID"},
				{"deviceid", "firstrevisiondeviceid"},
				{"deviceid", "deletiontime"},
				{"deviceid", "timeofcrime"},
		};

		//loop over the array and replace
		String strOutput = casesJson;
		for(String[] replacement: replacements) {
			strOutput = strOutput.replace(replacement[0], replacement[1]);
		}
		return strOutput;
	}



	private class GrabURL extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urls[0]);

			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					return builder.toString();
				} else {
					Log.e("Getter", "Failed to download file");
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
			updateCaseList(result);
		}
	}



}