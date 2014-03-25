package gov.polisen.ainappen;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCaseFragment extends Fragment {


	EditText crime_classText;
	EditText location_Text;
	EditText commanderText;
	EditText dateText;
	Spinner statusText;
	EditText descriptionText;
	View rootView;

	public AddCaseFragment(){
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_add_case, container, false);
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

			//called when the up affordance/carat in actionbar is pressed
			getActivity().onBackPressed();

			return true;
		}
		return false;
	}

	/* 
	 * Needs to be included in low level fragments
	 * Low level fragments = fragments that is not in main drawer menu.
	 */
	private void setUpLowLevelFragment(){
		//needed to indicate that the fragment would like to add items to the Options Menu      
		setHasOptionsMenu(true);
		//update the actionbar to show the up carat/affordance 
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		//locks navigation drawer from open in lower lever fragment. 
		((MainActivity) getActivity()).lockDrawer();
	}

	// Setups the status dropdown list. 
	private void setupStatusSpinner(View view) {
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner_status);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.case_status, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);		
	}


	public void setupAddCaseButtonListener(){
		Button addCaseButton = (Button) rootView.findViewById(R.id.addCaseButton);
		addCaseButton.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				textFieldSetter();
				addCaseToDB();
			}

		});
	}

	/**
	 * Helpmethod used by setupButtonListener
	 */
	public void textFieldSetter(){
		/*
		 * This part fetches what's input into the GUI
		 */
		crime_classText = (EditText) rootView.findViewById(R.id.crime_classification_text_edit);
		location_Text = (EditText) rootView.findViewById(R.id.location_text_edit);
		commanderText = (EditText) rootView.findViewById(R.id.commander_text_edit);
		dateText = (EditText) rootView.findViewById(R.id.date_text_edit);
		statusText = (Spinner) rootView.findViewById(R.id.spinner_status);
		descriptionText = (EditText) rootView.findViewById(R.id.description_text_edit);
	}

	/**
	 * TODO: Integrity check input-fields before committing to database
	 * (i.e crime_class must not be null)
	 */
	public void addCaseToDB(){
		DatabaseHelper dbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
		RuntimeExceptionDao<Case, String> caseDao = dbHelper.getCaseRuntimeExceptionDao();
		RuntimeExceptionDao<LocalID, Integer> localIdDao = dbHelper.getLocalIdRuntimeExceptionDao();

		/*
		 * This small part is rather unsexy, but needed to
		 * bypass constraints in ORMLite.
		 */
		LocalID newId = new LocalID(0);
		localIdDao.create(newId);
		int lid = newId.getLocalCaseID();

		Case newCase = new Case(1337, lid, crime_classText.getText().toString(),
				location_Text.getText().toString(),
				commanderText.getText().toString(),
				dateText.getText().toString(),
				statusText.getSelectedItem().toString(),
				descriptionText.getText().toString()
				);

		//Put the data into database
		caseDao.create(newCase);
		String lastEntryID = newCase.getCaseID();
		Toast.makeText(getActivity(), (CharSequence) caseDao.queryForId(lastEntryID).toString() , Toast.LENGTH_LONG).show();
		OpenHelperManager.releaseHelper();
	}

}
