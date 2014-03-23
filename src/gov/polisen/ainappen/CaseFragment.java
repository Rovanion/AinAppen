package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class CaseFragment extends Fragment {

	public CaseFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_case, container, false);
		getActivity().setTitle("Ärenden");
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

