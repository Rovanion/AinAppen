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
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ExternalDBHandeler {

	String		webserver		= "http://christian.cyd.liu.se";
	String		casesForUser	= "http://christian.cyd.liu.se:1337/casesForUser/2";
	ListView	caseListView;
	Context		rootview;
	List<Case>	externalCaseList;
	int			responseCode;

	public ExternalDBHandeler(Activity activity) {
		this.rootview = activity;
	}

	/*
	 * 1. Synchronizing external and local databases. 2. Updates Case list view
	 * if caseListView argument is not null.
	 */
	public void syncDatabases(List<Case> localCaseList, int userID,
			ListView caseListView) {
		if (caseListView != null)
			this.caseListView = caseListView;
		final SyncDB syncer = new SyncDB();
		Handler handler = new Handler();
		syncer.execute(casesForUser);
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				if(syncer.getStatus() == AsyncTask.Status.RUNNING){
					syncer.cancel(true);
				}
			}
		}, 10000);
	}

	private class SyncDB extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

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
				List<Case> externalCaseList = new Gson().fromJson(
						result, new TypeToken<List<Case>>() {
						}.getType());

				// Example case on server doesn't contain every field
				externalCaseList = addMissedFields(externalCaseList);

				// Do the actual syncing
				List<Case> mergedCaseList = syncWithLocalDB(externalCaseList);

				// Updates listview
				if (caseListView != null) {
					updateListView(mergedCaseList);
				}
				showToast("Synced with external DB.");
			}	
			else{
				showToast("Could not sync database, no internet connection!");
			}
		}

		@Override
		protected void onCancelled(){
			showToast("För dålig connection för att synka!");
		}

		private List<Case> syncWithLocalDB(List<Case> externalCaseList) {

			// Gets all local cases from local database
			LocalDBHandler ldbh = new LocalDBHandler(rootview);
			List<Case> localCaseList = ldbh.getCasesFromDB();

			// Merged Caselist started with all local cases.
			List<Case> mergedCaseList = localCaseList;

			boolean exists;
			for (Case eCase : externalCaseList) {
				exists = false;

				for (Case lCase : localCaseList) {
					// If a case with same id is found in local DB
					if (eCase.getCaseId() == lCase.getCaseId()
							&& eCase.getDeviceId() == lCase.getDeviceId()) {
						exists = true;

						// Update case in local db
						// if new case found in external db is newer version of
						// local case.
						if (eCase.getModificationTime().after(
								lCase.getModificationTime())) {
							ldbh.removeCaseFromDB(lCase);
							ldbh.addExistingCase(eCase);
						}

						// Update external db if new case found in external db
						// is older version of local case.
						else if (eCase.getModificationTime().before(
								lCase.getModificationTime())) {
							// TODO: when post case method exist write this.
						}
					}
				}
				// If the external case doesnt exist in local DB
				if (!exists) {
					ldbh.addExistingCase(eCase);
					mergedCaseList.add(eCase);
				}
			}
			ldbh.release();
			return mergedCaseList;
		}

		public List<Case> addMissedFields(List<Case> caseList) {
			for (Case c : caseList) {
				// TimeOfCrime is missing in external database why this
				// temporary solution.
				if (c.getTimeOfCrime() == null)
					c.setTimeOfCrime(new Date());
				if (c.getPriority() == null)
					c.setPriority((short) 1);
			}
			return caseList;
		}

		private void updateListView(List<Case> mergedCaseList) {
			CaseListAdapter adapter = new CaseListAdapter(rootview,
					mergedCaseList);
			caseListView.setAdapter(adapter);
		}

	}

	public void showToast(String text) {
		Toast.makeText(rootview, text, Toast.LENGTH_SHORT).show();
	}
}
