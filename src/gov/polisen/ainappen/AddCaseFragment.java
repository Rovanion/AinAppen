package gov.polisen.ainappen;

import java.util.Date;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
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

	EditText		crime_classText;
	EditText		location_Text;
	EditText		commanderText;
	CalendarView	dateDate;
	Spinner			statusText;
	EditText		descriptionText;
	View			rootView;

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
		// add the case to database
		caseToBeAdded = addCaseToDB(caseToBeAdded);
		// notify the user about successfull commitment
		makeToast(caseToBeAdded);
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
	 * @param v
	 * @param caseToBeAdded
	 */
	public void makeToast(Case caseToBeAdded) {
		String toastMessage = "Nytt ärende med ID: "
				+ caseToBeAdded.getCaseID() + " angående "
				+ caseToBeAdded.getCrimeClassification() + " vid: "
				+ caseToBeAdded.getLocation() + " har lagts till i databasen.";
		Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
	}

	/**
	 * Helpmethod used by setupButtonListener
	 */
	private void textFieldSetter() {
		/*
		 * This part fetches what's input into the GUI
		 */
		crime_classText = (EditText) rootView
				.findViewById(R.id.crime_classification_text_edit);
		location_Text = (EditText) rootView
				.findViewById(R.id.location_text_edit);
		commanderText = (EditText) rootView
				.findViewById(R.id.commander_text_edit);
		dateDate = (CalendarView) rootView.findViewById(R.id.calendarView1);
		statusText = (Spinner) rootView.findViewById(R.id.spinner_status);
		descriptionText = (EditText) rootView
				.findViewById(R.id.description_text_edit);
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
		// Unique ID for this device
		int dId = appData.getDeviceID();
		Case newCase = new Case(dId, 0, crime_classText.getText().toString(),
				location_Text.getText().toString(),
				Integer.parseInt(commanderText.getText().toString()), new Date(
						dateDate.getDate()), statusText.getSelectedItem()
						.toString(), descriptionText.getText().toString());
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
		return returnCase;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// adds save button in action bar
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
}
