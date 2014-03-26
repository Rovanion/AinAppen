package gov.polisen.ainappen;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ContactListFragment extends Fragment{

	private ListView testList;
	private View rootView;

	public ContactListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
		getActivity().setTitle("Kontakter");
		setUpHighLevelFragment();
		setHasOptionsMenu(true);

		setupContactList();
		//setupAddContactButton();

		return rootView;
	}
	
	// This method handles onClick at our actionbar
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		// If we press the addcontactbutton in the actionbar, call the addContact
		// function in MainActivity
		case R.id.addcontact_item:
			View rootView = item.getActionView();
			((MainActivity) getActivity()).gotoAddContact(rootView);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setupContactList(){
		testList = (ListView)rootView.findViewById(R.id.listPhone);
		List<Contact> contactList = new ArrayList<Contact>();
		contactList.add(new Contact("Peter","Johansson","polis","112"));
		contactList.add(new Contact("Alfons","Åberg","befäl","114114"));
		contactList.add(new Contact("Jocke","Pajas","sopa","000000"));
		ContactListAdapter adapter = new ContactListAdapter(getActivity(), contactList);
		testList.setAdapter(adapter);
	}
	
	/* 
	* Needs to be included in high level fragments
	* high level fragments = fragments that is main drawer menu.
	*/
	private void setUpHighLevelFragment(){
		//unlocks navigation drawer to open after visited a low level fragment
		((MainActivity) getActivity()).unlockDrawer();
	}
	
	// Adds an actionbar to the fragment
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_fragment_contactlist, menu);

	}

}
