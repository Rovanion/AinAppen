package gov.polisen.ainappen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class Contacts extends Activity {

    private ListView testList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		testList = (ListView)findViewById(R.id.listPhone);
		
		List<Contact> contactList = new ArrayList<Contact>();
		contactList.add(new Contact("Peter","polis","112"));
		contactList.add(new Contact("Adolf","befäl","114114"));
		contactList.add(new Contact("Jocke","sopa","000000"));
		ContactListAdapter adapter = new ContactListAdapter(this, contactList);
		testList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kontakter, menu);
		return true;
	}
}
