package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment{

	public MapFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_map, container, false);
		getActivity().setTitle("Karta");
		setUpHighLevelFragment();

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
}
