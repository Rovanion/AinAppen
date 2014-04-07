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

public class EditCaseFragment extends Fragment {

	private Case	selectedCase;
	private View	rootView;

	public EditCaseFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		selectedCase = ((MainActivity) getActivity()).getSelectedCase();
		rootView = inflater.inflate(R.layout.fragment_edit_case, container,
				false);
		getActivity().setTitle("Redigera ärende");
		setUpLowLevelFragment();

		setupStatusSpinner(rootView);
		fillTextFieldsWithCurrentCase();

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
	 * Saves the status of the textfields to the selectcase object and to the
	 * database.
	 */
	private void saveEditedCase() {
		EditText crimeClassification = (EditText) rootView
				.findViewById(R.id.crime_classification_text_edit);
		selectedCase.setCrimeClassification(crimeClassification.getText()
				.toString());
		EditText location = (EditText) rootView
				.findViewById(R.id.location_text_edit);
		selectedCase.setLocation(location.getText().toString());
		EditText commander = (EditText) rootView
				.findViewById(R.id.commander_text_edit);
		selectedCase.setCommander(Integer.parseInt(commander.getText()
				.toString()));
		Spinner status = (Spinner) rootView.findViewById(R.id.spinner_status);
		selectedCase.setStatus(status.getSelectedItem().toString());
		EditText description = (EditText) rootView
				.findViewById(R.id.description_text_edit);
		selectedCase.setDescription(description.getText().toString());
		CalendarView calendar = (CalendarView) rootView
				.findViewById(R.id.calendarView1);
		selectedCase.setDate(new Date(calendar.getDate()));

		LocalDBHandler localDBHandler = new LocalDBHandler();
		localDBHandler.editCase(selectedCase, getActivity());
	}

	/**
	 * Fills the textfields with the current case information.
	 */
	private void fillTextFieldsWithCurrentCase() {
		EditText crimeClassification = (EditText) rootView
				.findViewById(R.id.crime_classification_text_edit);
		crimeClassification.setText(selectedCase.getCrimeClassification());
		EditText location = (EditText) rootView
				.findViewById(R.id.location_text_edit);
		location.setText(selectedCase.getLocation());
		EditText commander = (EditText) rootView
				.findViewById(R.id.commander_text_edit);
		commander.setText(Integer.toString(selectedCase.getCommander()));
		Spinner status = (Spinner) rootView.findViewById(R.id.spinner_status);
		status.setSelection(getSelectedIndex(selectedCase.getStatus()));
		EditText description = (EditText) rootView
				.findViewById(R.id.description_text_edit);
		description.setText(selectedCase.getDescription());
		CalendarView calendar = (CalendarView) rootView
				.findViewById(R.id.calendarView1);
		calendar.setDate(selectedCase.getDate().getTime());
	}

	private int getSelectedIndex(String status) {
		String[] statuses = getResources().getStringArray(R.array.case_status);
		for (int i = 0; i < statuses.length; i++) {
			if (statuses[i].equals(status))
				return i;
		}
		return 0;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_fragment_save_editcase, menu);
	}

}
