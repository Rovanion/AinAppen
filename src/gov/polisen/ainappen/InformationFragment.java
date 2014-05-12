package gov.polisen.ainappen;

import gov.polisen.ainappen.kandidat.EnergySavingPolicy;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InformationFragment extends Fragment {

	public InformationFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_information,
				container, false);
		getActivity().setTitle("Information");
		setUpHighLevelFragment();
		
		Case aCase = EnergySavingPolicy.getPolicy().getDummyCase();
		EnergySavingPolicy.getPolicy().getAlgorithm().uploadNewCase(aCase);

		return rootView;
	}

	/*
	 * Needs to be included in high level fragments high level fragments =
	 * fragments that is main drawer menu.
	 */
	private void setUpHighLevelFragment() {
		// unlocks navigation drawer to open after visited a low level fragment
		((MainActivity) getActivity()).unlockDrawer();
		((MainActivity) getActivity()).enableDrawerIndicator();
	}
}
