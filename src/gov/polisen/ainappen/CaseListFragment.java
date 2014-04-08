package gov.polisen.ainappen;

import java.sql.Date;
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

	private ListView caseListView;
	private View rootView;

	public CaseListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_case_list, container,
				false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Ärenden");
		setUpHighLevelFragment();
		setupCaseList();
		addCaseListListener();

		return rootView;
	}

	private void addCaseListListener() {
		caseListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
				Case selectedFromList =(Case) (caseListView.getItemAtPosition(myItemInt));

				((MainActivity) getActivity()).gotoCase(rootView, selectedFromList);
			}                 
		});		
	}

	private void setupCaseList() {
		caseListView = (ListView)rootView.findViewById(R.id.case_list);
		LocalDBHandler lh = new LocalDBHandler(getActivity());
		List<Case> caseList = lh.getCasesFromDB();
		caseList.add(new Case(1337,1454,"Snatteri","Hemköp Ryd",80085,Date.valueOf("2007-12-03"),"asdsa","Odrägliga ynglingar som snattat choklad på Hemköp."));
		caseList.add(new Case(1337,1455,"Helikopterrån","Pengadepå",80085,Date.valueOf("2013-12-05"),"Snaasdsadtteri","Mycket pengar men det kan bli svårt att få vittnen. Familjer hotade."));
		caseList.add(new Case(1337,1456,"Tvångsgifte","Kyrka",80085,Date.valueOf("2014-01-10"),"Snatteraasdi","Inte okej."));
		caseList.add(new Case(1337,1457,"Palmemordet","Stan",800085,Date.valueOf("1983-10-29"),"asdsadsad","Det är dags att reda upp det här mordet grabbar. Ta er i kragen och fixa bevis. Deadline imorn."));
		CaseListAdapter adapter = new CaseListAdapter(getActivity(), caseList);
		caseListView.setAdapter(adapter);		
	}


	/*
	 * Needs to be included in high level fragments high level fragments =
	 * fragments that is main drawer menu.
	 */
	private void setUpHighLevelFragment() {
		// unlocks navigation drawer to open after visited a low level fragment
		((MainActivity) getActivity()).unlockDrawer();
	}

	// Adds an actionbar to the fragment
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_fragment_case, menu);

	}

	// This method handles onClick at our actionbar
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		// If we press the addCasebutton in the actionbar, call the addCase
		// function in MainActivity
		case R.id.addcase_item:
			View rootView = item.getActionView();
			((MainActivity) getActivity()).gotoAddCase(rootView);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
