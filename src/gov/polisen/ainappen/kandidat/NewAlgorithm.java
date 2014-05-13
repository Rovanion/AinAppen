package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import android.view.View;
import android.widget.ListView;

public class NewAlgorithm extends Algorithm {

	public NewAlgorithm(View root) {
		super(root);
	}

	@Override
	public void syncDatabases(boolean userInitiated) {
		putOnQueue(1, userInitiated);	
		if (userInitiated) runQueue();
	}

	@Override
	public void uploadPosition(String positionInfo) {
		putOnQueue(2, positionInfo);
	}

	@Override
	public void uploadNewCase(Case aCase) {
		putOnQueue(3, aCase);
	}

}
