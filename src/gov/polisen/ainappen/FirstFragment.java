package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class FirstFragment extends Fragment {

	public FirstFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_first, container, false);
		getActivity().setTitle("AinAppen");
		return rootView;
	}
}