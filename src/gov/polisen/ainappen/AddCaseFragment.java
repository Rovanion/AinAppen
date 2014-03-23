package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddCaseFragment extends Fragment {

	public AddCaseFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_case, container, false);
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

	
}
