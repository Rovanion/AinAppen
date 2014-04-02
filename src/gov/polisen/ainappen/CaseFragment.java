package gov.polisen.ainappen;

import java.util.List;
import java.util.concurrent.Executor;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CaseFragment extends Fragment {

	public CaseFragment() {
		// Empty constructor required for fragment subclasses
	}

	private Case		selectedCase;
	private TextView	crimeClassification;
	private TextView	location;
	private TextView	commander;
	private TextView	date;
	private TextView	status;
	private TextView	description;
	private View		rootView;

	String				foldername;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_case, container, false);
		setUpLowLevelFragment();
		this.selectedCase = ((MainActivity) getActivity()).getSelectedCase();
		getActivity().setTitle(selectedCase.getCrimeClassification());
		setHasOptionsMenu(true);
		foldername = selectedCase.getCrimeClassification();

		setupComponents();
		fillTextfields();
		displayImages();

		return rootView;
	}

	private void displayImages() {

		ImageHandeler ih = new ImageHandeler(rootView);
		List<String> imagesUrls = ih.getListOfImgageUrls(foldername);

		for (int i = 0; i < imagesUrls.size(); i++) {
			Executor executor;
			if (i == 0)
				executor = AsyncTask.THREAD_POOL_EXECUTOR;
			else
				executor = AsyncTask.SERIAL_EXECUTOR;
			new ImageHandeler(rootView).executeOnExecutor(executor,
					imagesUrls.get(i));
		}
	}

	private void setupComponents() {
		crimeClassification = (TextView) rootView
				.findViewById(R.id.crime_classification);
		commander = (TextView) rootView.findViewById(R.id.commander);
		location = (TextView) rootView.findViewById(R.id.location);
		date = (TextView) rootView.findViewById(R.id.date);
		status = (TextView) rootView.findViewById(R.id.status);
		description = (TextView) rootView.findViewById(R.id.description);
	}

	private void fillTextfields() {
		crimeClassification.setText(selectedCase.getCrimeClassification());
		commander.setText(Integer.toString(selectedCase.getCommander()));
		location.setText(selectedCase.getLocation());
		date.setText(selectedCase.getDate().toString());
		status.setText(selectedCase.getStatus());
		description.setText(selectedCase.getDescription());
	}

	// Adds an actionbar to the fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_case, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:
			// called when the up affordance/carat in actionbar is pressed
			getActivity().onBackPressed();
			return true;
		case R.id.camera_actionbar_button:
			intent = new Intent(getActivity(), CameraActivity.class);
			intent.putExtra("SELECTED_CASE_ID",
					selectedCase.getCrimeClassification());
			intent.putExtra("SELECTED_MODE", 1);
			startActivity(intent);
			return true;
		case R.id.video_camera_actionbar_button:
			intent = new Intent(getActivity(), CameraActivity.class);
			// TODO: Change crime classification to case id.
			intent.putExtra("SELECTED_CASE_ID",
					selectedCase.getCrimeClassification());
			intent.putExtra("SELECTED_MODE", 2);
			startActivity(intent);
			return true;

		}
		return false;
	}

	/*
	 * Needs to be included in low level fragments Low level fragments =
	 * fragments that is not in main drawer menu.
	 */
	private void setUpLowLevelFragment() {
		// needed to indicate that the fragment would like to add items to the
		// Options Menu
		setHasOptionsMenu(true);
		// update the actionbar to show the up carat/affordance
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// locks navigation drawer from open in lower lever fragment.
		((MainActivity) getActivity()).lockDrawer();
	}

}
