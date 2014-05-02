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

	EditText classification;
	EditText priority;
	Spinner status ;

	EditText description;
	CalendarView calendar;



	public EditCaseFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		selectedCase = ((MainActivity) getActivity()).getSelectedCase();
		rootView = inflater.inflate(R.layout.fragment_add_case, container,
				false);
		getActivity().setTitle("Redigera ärende");
		setUpLowLevelFragment();

		setupStatusSpinner(rootView);

		setupFields();
		fillTextFieldsWithCurrentCase();

		return rootView;
	}

	private void setupFields() {
		classification = (EditText) rootView
				.findViewById(R.id.classification_add_case);

		priority = (EditText) rootView
				.findViewById(R.id.priority_add_case);

		status = (Spinner) rootView.findViewById(R.id.spinner_status);

		description = (EditText) rootView
				.findViewById(R.id.description_add_case);

		calendar = (CalendarView) rootView
				.findViewById(R.id.crimedate_add_case);

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
		selectedCase.setClassification(Short.valueOf(classification.getText()
				.toString()));
		selectedCase.setPriority(Short.valueOf(priority.getText().toString()));
		selectedCase.setStatus((short) status.getSelectedItemPosition());
		selectedCase.setDescription(description.getText().toString());
		selectedCase.setTimeOfCrime(new Date(calendar.getDate()));

		LocalDBHandler localDBHandler = new LocalDBHandler(getActivity());
		localDBHandler.editCase(selectedCase, getActivity());
		localDBHandler.release();
	}

	/**
	 * Fills the textfields with the current case information.
	 */
	private void fillTextFieldsWithCurrentCase() {

		classification.setText(String.valueOf(selectedCase
				.getClassification()));

		priority.setText(Integer.toString(selectedCase.getPriority()));

		status.setSelection(Integer.valueOf(selectedCase.getStatus()));

		description.setText(selectedCase.getDescription());

		calendar.setDate(selectedCase.getTimeOfCrime().getTime());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_fragment_save_editcase, menu);
	}

}
