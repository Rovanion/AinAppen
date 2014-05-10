package gov.polisen.ainappen;

import gov.polisen.ainappen.kandidat.EnergySavingPolicy;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class CaseListFragment extends Fragment {

	private ListView	caseListView;
	private View		rootView;
	private EnergySavingPolicy policy;

	public CaseListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_case_list, container,
				false);
		policy = new EnergySavingPolicy(rootView);

		setHasOptionsMenu(true);
		getActivity().setTitle("Ärenden");
		setUpHighLevelFragment();

		setupCaseList();
		addCaseListListener();

		return rootView;
	}

	private void addCaseListListener() {
		caseListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int myItemInt, long mylng) {
				Case selectedFromList = (Case) (caseListView
						.getItemAtPosition(myItemInt));

				((MainActivity) getActivity()).gotoCase(selectedFromList);
			}
		});
	}

	private void setupCaseList() {
		caseListView = (ListView) rootView.findViewById(R.id.case_list);
		LocalDBHandler ldbh = new LocalDBHandler(getActivity());

		// Fills list with cases from local DB
		List<Case> caseList = ldbh.getCasesFromDB();

		// Create list with local cases
		CaseListAdapter adapter = new CaseListAdapter(getActivity(), caseList);
		caseListView.setAdapter(adapter);

		// Releases local db helper. Important when finished.
		ldbh.release();

		// 1. Updates local db with external cases
		// 2. Updats listview		
				
		policy.getAlgorithm().syncDatabases(caseListView, false);		
		
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

	// Adds an actionbar to the fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_fragment_case_list, menu);

	}

	// This method handles onClick at our actionbar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		// If we press the addCasebutton in the actionbar, call the addCase
		// function in MainActivity
		case R.id.addcase_item:
			View rootView = item.getActionView();
			((MainActivity) getActivity()).gotoAddCase(rootView);
			return true;
		case R.id.update_case_list:
			policy.getAlgorithm().syncDatabases(caseListView,true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
