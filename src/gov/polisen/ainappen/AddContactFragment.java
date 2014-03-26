package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class AddContactFragment extends Fragment {

	public AddContactFragment() {
		// Empty constructor required for fragment subclasses
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);
		getActivity().setTitle("Lägg till ny kontakt");
		setUpLowLevelFragment();
		
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
	
	private void setUpLowLevelFragment(){
		//needed to indicate that the fragment would like to add items to the Options Menu      
		setHasOptionsMenu(true);
		//update the actionbar to show the up carat/affordance 
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		//locks navigation drawer from open in lower lever fragment. 
		((MainActivity) getActivity()).lockDrawer();
	}
	
}
