package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import android.view.View;
import android.widget.ListView;

public class NewAlgorithm extends Algorithm {

	public NewAlgorithm(View root) {
		super(root);
	}

	@Override
	public void syncDatabases(ListView listView, boolean userInitiated) {
		putOnQueue(1, listView);
		if (userInitiated) runQueue();
	}

	@Override
	public void uploadPosition(Object position) {
		putOnQueue(2, position);
	}

	@Override
	public void uploadNewCase(Case aCase) {
		putOnQueue(3, aCase);
	}

}
