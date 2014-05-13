package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import android.view.View;

/*
 * No scheduling algorithm is activated. All commands are running directly. 
 */
public class StandardAlgorithm extends Algorithm{

	public StandardAlgorithm(View root) {
		super(root);
	}

	@Override
	public void syncDatabases(boolean userInitiated) {
		runSyncDatabases();		
	}

	@Override
	public void uploadNewCase(Case aCase) {
		runUploadNewCase(aCase);
	}

	@Override
	public void uploadPosition(String position) {
		runUploadPosition(position);
	}

}
