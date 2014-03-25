package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {

	public MapFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_map, container, false);
		getActivity().setTitle("Karta");
		setUpHighLevelFragment();

		setHasOptionsMenu(true);
		return rootView;
	}
	
	
	/* 
	* Needs to be included in high level fragments
	* high level fragments = fragments that is main drawer menu.
	*/
	private void setUpHighLevelFragment(){
		//unlocks navigation drawer to open after visited a low level fragment
		((MainActivity) getActivity()).unlockDrawer();
	}

	// Adds an actionbar to the fragment
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_fragment_map, menu);

	}

	// This method handles onClick at our actionbar
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		// If we press the addObstacle in the actionbar, call the addObstacle
		// function in MainActivity
		case R.id.addobstacle_item:
			// Call IT
			return true;
		case R.id.visualsettings_item:
			// Call IT
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
