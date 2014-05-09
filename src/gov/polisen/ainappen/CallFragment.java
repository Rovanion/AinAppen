package gov.polisen.ainappen;

import gov.polisen.ainappen.ipTelephony.Call;
import android.app.Fragment;
import android.net.sip.SipException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment that handles an ongoing call.
 */
public class CallFragment extends Fragment {

	private Call sipCall;
	private View rootView;

	public CallFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sipCall = ((MainActivity) getActivity()).getSipCall();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater
				.inflate(R.layout.fragment_calling, container, false);
		setUpLowLevelFragment();
		TextView textView = (TextView) rootView
				.findViewById(R.id.call_status_text);
		textView.setText("Ringer "
				+ sipCall.getCall().getPeerProfile().getUserName());
		return rootView;
	}

	private void setUpLowLevelFragment() {
		// needed to indicate that the fragment would like to add items to the
		// Options Menu
		setHasOptionsMenu(true);
		// update the actionbar to show the up carat/affordance
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// locks navigation drawer from open in lower lever fragment.
		((MainActivity) getActivity()).lockDrawer();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:
			// called when the up affordance/carat in actionbar is pressed
			getActivity().onBackPressed();

			return true;
		}
		return false;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		try {
			if (sipCall.getCall() != null && sipCall.getCall().isInCall())
				sipCall.getCall().endCall();
		} catch (SipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
