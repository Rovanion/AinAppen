package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import android.util.Log;
import android.view.View;

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
		Log.d("KANDI", "Upload position");
		putOnQueue(2, positionInfo);
	}

	@Override
	public void uploadNewCase(Case aCase) {
		putOnQueue(3, aCase);
	}

}
