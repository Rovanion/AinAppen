package gov.polisen.ainappen;

import gov.polisen.ainappen.ipTelephony.Call;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {
	private Context			context;
	private List<Contact>	contactList;
	private Call			sipCall;

	public ContactListAdapter(Context context, List<Contact> contactList) {
		this.context = context;
		this.contactList = contactList;
	}

	@Override
	public int getCount() {
		return contactList.size();
	}

	public Context getContext() {
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
	public View getView(final int pos, View convertView, ViewGroup parent) {
		// get selected entry
		Contact entry = contactList.get(pos);

		// inflating list view layout if null
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.contactlist_item, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(entry.getFirstName() + " " + entry.getLastName());

		TextView phoneNumber = (TextView) convertView
				.findViewById(R.id.phoneNumber);
		phoneNumber.setText(entry.getPhone());

		TextView title = (TextView) convertView.findViewById(R.id.jobTitle);
		title.setText(entry.getTitle());

		sipCall = ((MainActivity) context).getSipCall();

		ImageButton call = (ImageButton) convertView
				.findViewById(R.id.callPhoneButton);

		call.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sipCall.initiateCall(contactList.get(pos).getLastName());
				((MainActivity) context).gotoCall();
			}
		});

		return convertView;
	}

}
