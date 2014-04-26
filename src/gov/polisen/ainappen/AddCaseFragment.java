package gov.polisen.ainappen;

import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
		rootView = inflater.inflate(R.layout.fragment_add_case, container,
				false);
		getActivity().setTitle("Skapa nytt ärende");
		setUpLowLevelFragment();
		setupAddCaseButtonListener();

		setupStatusSpinner(rootView);

		return rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:

			// called when the up affordance/carat in actionbar is pressed
			getActivity().onBackPressed();

			return true;
		}
		return false;
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

	public void setupAddCaseButtonListener() {
		Button addCaseButton = (Button) rootView
				.findViewById(R.id.addCaseButton);
		addCaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// read from all the textfields in the GUI
				textFieldSetter();
				// Create a caseObject from the set fields
				Case caseToBeAdded = createCaseFromForm();
				// add the case to database
				caseToBeAdded = addCaseToDB(caseToBeAdded);
				// notify the user about successfull commitment
				makeToast(v, caseToBeAdded);
				/*
				 * Nästa line ser till att vi kommer tillbaka till case-listan
				 * (från "ärendevyn man per automatik skickas till vid creation)
				 * när vi trycker bakåt. Detta istället för att komma tillbaka
				 * till "skapa nytt ärende"-vyn.
				 */
				getActivity().getFragmentManager().popBackStack();
				((MainActivity) getActivity()).gotoCase(v, caseToBeAdded);
			}
		});
	}

	/**
	 * Extracts useful information from a case, and prints them to the user.
	 * 
	 * @param v
	 * @param caseToBeAdded
	 */
	public void makeToast(View v, Case caseToBeAdded) {
		String toastMessage = "Nytt ärende med ID: "
				+ caseToBeAdded.getLocalCaseID() + " angående "
				+ caseToBeAdded.getClassification() + " vid: ";
		Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
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
	 * Helpmethod used by setupButtonListener. Uses the global fields defined in
	 * the AddCaseFragment to create a case, the created case is returned to the
	 * calling parent.
	 * 
	 * @return
	 */
	private Case createCaseFromForm() {
		final GlobalData appData = ((GlobalData) getActivity()
				.getApplicationContext());

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
}
