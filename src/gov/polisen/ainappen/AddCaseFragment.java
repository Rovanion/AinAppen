package gov.polisen.ainappen;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import android.app.AlertDialog;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCaseFragment extends Fragment {

	View			rootView;

	EditText		classificationField;
	EditText		descriptionField;
	EditText		priorityField;
	Spinner			spinnerField;
	CalendarView	timeOfCrimeField;

	public AddCaseFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_edit_case, container,
				false);
		getActivity().setTitle("Skapa nytt ärende");
		setUpLowLevelFragment();

		setupStatusSpinner(rootView);

		return rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:

			//called when the up affordance/carat in actionbar is pressed
			showSaveOptionsPopUp();

			return true;
		case R.id.saveeditcase_item:
			saveEditedCase();
			getActivity().onBackPressed();
			return true;
		}
		return false;
	}

	private void saveEditedCase() {
		// read from all the textfields in the GUI
		textFieldSetter();
		// Create a caseObject from the set fields
		Case caseToBeAdded = createCaseFromForm();
		// add the case to the server

		
		Gson gson = new Gson();
		String json = gson.toJson(caseToBeAdded);
		Log.d("LOOOOOOOOOG", json);
		// add the case to database and returns a case with a correct caseId
		caseToBeAdded = addCaseToDB(caseToBeAdded);
		// notify the user about successfull commitment
		makeToast(caseToBeAdded);
		
		// Sends case to server async
		AddCaseToServer add = new AddCaseToServer();
		add.execute(caseToBeAdded);
		
		/*
		 * Nästa line ser till att vi kommer tillbaka till case-listan (från
		 * "ärendevyn man per automatik skickas till vid creation) när vi
		 * trycker bakåt. Detta istället för att komma tillbaka till
		 * "skapa nytt ärende"-vyn.
		 */
		getActivity().getFragmentManager().popBackStack();
		((MainActivity) getActivity()).gotoCase(rootView, caseToBeAdded);
	}

	/*
	 * Needs to be included in low level fragments Low level fragments =
	 * fragments that is not in main drawer menu.
	 */
	private void setUpLowLevelFragment() {
		// needed to indicate that the fragment would like to add items to the
		// Options Menu
		setHasOptionsMenu(true);
		// update the actionbar to show the up carat/affordance
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// locks navigation drawer from open in lower lever fragment.
		((MainActivity) getActivity()).lockDrawer();
	}

	// Setups the status dropdown list.
	private void setupStatusSpinner(View view) {
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner_status);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.case_status,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}

	/**
	 * Extracts useful information from a case, and prints them to the user.
	 * 
	 * @param caseToBeAdded
	 */
	public void makeToast(Case caseToBeAdded) {
		String toastMessage = "Nytt ärende med ID: "
				+ caseToBeAdded.getCaseId() + " angående "
				+ caseToBeAdded.getClassification();
		Toast.makeText(getActivity(), toastMessage,
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Helpmethod used by setupButtonListener
	 */
	private void textFieldSetter() {
		/*
		 * This part fetches what's input into the GUI
		 */
		classificationField = (EditText) rootView
				.findViewById(R.id.classification_add_case);
		timeOfCrimeField = (CalendarView) rootView
				.findViewById(R.id.crimedate_add_case);
		spinnerField = (Spinner) rootView.findViewById(R.id.spinner_status);
		descriptionField = (EditText) rootView
				.findViewById(R.id.description_add_case);
		priorityField = (EditText) rootView
				.findViewById(R.id.priority_add_case);
	}

	/**
	 * Uses the global fields defined in the AddCaseFragment to create a case,
	 * the created case is returned to the calling parent.
	 * 
	 * @return
	 */
	private Case createCaseFromForm() {
		final GlobalData appData = ((GlobalData) getActivity()
				.getApplicationContext());
		// Unique ID for this device
		// Setting all values except caseID and firstRevisionCaseID which is
		// generated from ORMLite Autoincrement later.

		// Will be set to 0 then the dadtabase will autoincrement this value
		// which is what we want.
		int caseID = 0;
		int firstRevisionCaseID = 0;
		int deviceID = appData.getDeviceID();
		int author = appData.getUser().getUserId();
		Date modificationDate = new Date();
		int firstRevisionDeviceID = deviceID;
		Date deletionTime = null;
		Short classification = Short.valueOf(classificationField.getText().toString());
		Short status = (short) spinnerField.getSelectedItemPosition();
		Short priority = Short.valueOf(priorityField.getText().toString());
		Float longitude = null;
		Float latitude = null;
		Date timeOfCrime = new Date(timeOfCrimeField.getDate());
		String description = descriptionField.getText().toString();

		Case newCase = new Case(deviceID,caseID,author,modificationDate,firstRevisionCaseID,firstRevisionDeviceID,deletionTime, classification, status,priority,longitude,latitude,timeOfCrime,description);
		return newCase;
	}

	/**
	 * TODO: Integrity check input-fields before committing to database (i.e
	 * crime_class must not be null)
	 */
	private Case addCaseToDB(Case newCase) {
		Case returnCase;
		LocalDBHandler lh = new LocalDBHandler(getActivity());
		returnCase = lh.addNewCaseToDB(newCase);
		lh.release();
		return returnCase;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// adds save button in action barcamelCase
		inflater.inflate(R.menu.actionbar_fragment_save_editcase, menu);
	}

	/**
	 * Shows a popup where the user can choose if wanting to save changes or
	 * not,
	 */
	private void showSaveOptionsPopUp() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Vill du spara dina ändringar?");
		builder.setCancelable(true);
		builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				saveEditedCase();
				getActivity().onBackPressed();
			}
		});
		builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				getActivity().onBackPressed();
			}
		});
		AlertDialog alert11 = builder.create();
		alert11.show();
	}
	
	private class AddCaseToServer extends AsyncTask<Case, Void, String> {

		@Override
		protected String doInBackground(Case... cases) {
		Case newCase = cases[0];
			
			// Get the adress to the server
		final GlobalData appData = ((GlobalData) getActivity()
				.getApplicationContext());
			String url = appData.getServerAdress();
			
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url + "/case");

			try {
				// convert newCase into JSON object
				Gson gson = new Gson();
				String json = gson.toJson(newCase);

				// Sets the post request as the resulting string entity
				httppost.setEntity(new StringEntity(json));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				Log.d("HTTPRESPONSE", response.toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.d("TAG", "Client");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("TAG","IO");
				// httppost.abort();
			}
			
			return url;
			
		}

		@Override
		protected void onPostExecute(String result) {
		
		}

		




	}


}
