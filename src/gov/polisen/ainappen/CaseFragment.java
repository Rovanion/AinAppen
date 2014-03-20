package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		setHasOptionsMenu(true);
		
		View rootView = inflater.inflate(R.layout.fragment_case, container, false);
		return rootView;
	}
	// Adds an actionbar to the fragment
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_fragment_case, menu);

	}
	
	//This method handles onClick at our actionbar
	public boolean onOptionsItemSelected(MenuItem item) {
	   // handle item selection
	   switch (item.getItemId()) {
//If we press the addCasebutton in the actionbar, call the addCase function in MainActivity	   
	      case R.id.addcase_item:
	    	  //Call IT
	         return true;
	      default:
	         return super.onOptionsItemSelected(item);
	   }
	}



	
}