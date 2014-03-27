package gov.polisen.ainappen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_case, container, false);
		setUpLowLevelFragment();
		this.selectedCase = ((MainActivity) getActivity()).getSelectedCase();
		getActivity().setTitle(selectedCase.getCrimeClassification());
		setHasOptionsMenu(true);

		setupComponents();
		fillTextfields();

		createImages();

		return rootView;
	}

	private void createImages() {
		List<String> images = getImgagesUrl();
		for (String url : images) {
			createImageViewForImage(url);
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
		commander.setText(selectedCase.getCommander());
		location.setText(selectedCase.getLocation());
		date.setText(selectedCase.getDate().toString());
		status.setText(selectedCase.getCrimeClassification());
		description.setText(selectedCase.getDescription());
	}

	// Adds an actionbar to the fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_case, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:
			// called when the up affordance/carat in actionbar is pressed
			getActivity().onBackPressed();
			return true;
		case R.id.camera_actionbar_button:
			Intent intent = new Intent(getActivity(), CameraActivity.class);
			// TODO: Change crime classification to case id.
			intent.putExtra("SELECTED_CASE_ID",
					selectedCase.getCrimeClassification());
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

	private void createImageViewForImage(String path) {
		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.linear_layout_case);
		ImageView imageView = new ImageView(this.getActivity());
		LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(vp);
		imageView.setImageBitmap(getBitmapFromPath(path));
		layout.addView(imageView);
	}

	private Bitmap getBitmapFromPath(String path) {
		File imgFile = new File(path);

		ImageHandeler ih = new ImageHandeler(imgFile.getAbsolutePath(), 400,
				400);
		return ih.decodeSampledBitmapFromResource();
	}

	private List<String> getImgagesUrl() {
		List<String> imageList = new ArrayList<String>();// list of file paths
		File[] listFile;
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"AinAppen" + File.separator
						+ selectedCase.getCrimeClassification());

		if (mediaStorageDir.isDirectory()) {
			listFile = mediaStorageDir.listFiles();
			// For loop backwards because latest image should be listed highest.
			for (int i = listFile.length; i > 0; i--) {
				imageList.add(listFile[i - 1].getAbsolutePath());
			}
		}
		return imageList;
	}
}
