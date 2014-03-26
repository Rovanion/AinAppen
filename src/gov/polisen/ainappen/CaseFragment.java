package gov.polisen.ainappen;

import java.io.File;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CaseFragment extends Fragment {

	public CaseFragment() {
		// Empty constructor required for fragment subclasses
	}

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
		setHasOptionsMenu(true);

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

	// Adds an actionbar to the fragment
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_case, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:
			//called when the up affordance/carat in actionbar is pressed
			getActivity().onBackPressed();
			return true;
		case R.id.camera_actionbar_button:
			Intent intent = new Intent(getActivity(), CameraActivity.class);
			// TODO: Change crime classification to case id.
			intent.putExtra("SELECTED_CASE_ID", selectedCase.getCrimeClassification());
			startActivity(intent);
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

	private List<String> getImgages(){
		List<String> imageList = new ArrayList<String>();// list of file paths
		File[] listFile;

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"AinAppen" + File.separator + crimeClassification);	
		
		if (mediaStorageDir.isDirectory())
		{
			listFile = mediaStorageDir.listFiles();
			
			for (int i = 0; i < listFile.length; i++)
			{
				imageList.add(listFile[i].getAbsolutePath());
			}
		}
		return imageList;
	}
}
