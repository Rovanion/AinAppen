package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CaseFragment extends Fragment {

	private Case selectedCase;
	private TextView crimeClassification;
	private TextView location; 
	private TextView commander; 
	private TextView date; 
	private TextView status; 
	private TextView description; 
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_case, container, false);
		setUpLowLevelFragment();
		this.selectedCase = ((MainActivity) getActivity()).getSelectedCase();
		getActivity().setTitle(selectedCase.getCrimeClassification());

		
		setupComponents();
		fillTextfields();
		
		return rootView;
	}

	private void setupComponents() {
		crimeClassification = (TextView) rootView.findViewById(R.id.crime_classification);
		commander = (TextView) rootView.findViewById(R.id.commander);
		location = (TextView)rootView.findViewById(R.id.location);
		date = (TextView)rootView.findViewById(R.id.date);
		status = (TextView)rootView.findViewById(R.id.status);
		description = (TextView)rootView.findViewById(R.id.description);
	}

	private void fillTextfields() {
		crimeClassification.setText(selectedCase.getCrimeClassification());
		commander.setText(selectedCase.getCommander());
		location.setText(selectedCase.getLocation());
		date.setText(selectedCase.getDate().toString());
		status.setText(selectedCase.getCrimeClassification());
		description.setText(selectedCase.getDescription());
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

}
