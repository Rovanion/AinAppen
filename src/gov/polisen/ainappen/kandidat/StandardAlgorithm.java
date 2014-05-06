package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import android.view.View;
import android.widget.ListView;

/*
 * No scheduling algorithm is activated. All commands are running directly. 
 */
public class StandardAlgorithm extends Algorithm{

	public StandardAlgorithm(View root) {
		super(root);
	}

	@Override
	public void syncDatabases(ListView listView, boolean userInitiated) {
		runSyncDatabases(listView);		
	}

	@Override
	public void uploadNewCase(Case aCase) {
		runUploadNewCase(aCase);
	}

	@Override
	public void uploadPosition(Object position) {
		runUploadPosition(position);
	}

}
