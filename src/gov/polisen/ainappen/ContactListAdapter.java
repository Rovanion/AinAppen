package gov.polisen.ainappen;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {
	private Context context;
	private List<Contact> contactList;

	public ContactListAdapter(Context context, List<Contact> contactList) {
		this.context = context;
		this.contactList = contactList;
	}

	@Override
	public int getCount() {
		return contactList.size();
	}
	
	public Context getContext(){
		return context;
	}

	@Override
	public Object getItem(int pos) {
		return contactList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		// get selected entry
		Contact entry = contactList.get(pos);

		 // inflating list view layout if null
		 if(convertView == null) {
		 LayoutInflater inflater = LayoutInflater.from(context);
		 convertView = inflater.inflate(R.layout.contactlist_item, null);
		 }

		// set name
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(entry.getFirstName()+" "+entry.getLastName());

		// set phone
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		phone.setText(entry.getPhone());

		// set title
		TextView title = (TextView) convertView.findViewById(R.id.jobTitle);
		title.setText(entry.getTitle());
		
		return convertView;
	}

}
