package gov.polisen.ainappen;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CaseListAdapter extends BaseAdapter {
	private final Context context;
	private final List<Case> caseList;

	public CaseListAdapter(Context context, List<Case> caseList) {
		this.context = context;
		this.caseList = caseList;
	}

	public int getCount() {
		return caseList.size();
	}

	public Context getContext(){
		return context;
	}

	public Object getItem(int pos) {
		return caseList.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		// get selected entry
		Case entry = caseList.get(pos);

		// inflating list view layout if null
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.caselist_item, null);
		}

		// set crime classification
		TextView name = (TextView) convertView.findViewById(R.id.crime_classification);
		name.setText(entry.getCrimeClassification());

		// set case id
		TextView caseId = (TextView) convertView.findViewById(R.id.date);
		caseId.setText(entry.getCaseID().toString());		

		return convertView;
	}


}
