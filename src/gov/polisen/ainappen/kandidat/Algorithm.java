package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import gov.polisen.ainappen.ExternalDBHandeler;
import gov.polisen.ainappen.LocalDBHandler;

import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;

public abstract class Algorithm {

	protected View root;
	protected Queue<Pair<Integer, Object>> queue;
	private final ExternalDBHandeler eh;

	public Algorithm(View root){
		this.root = root;
		queue = new LinkedList<Pair<Integer, Object>>();
		eh = new ExternalDBHandeler(root);
	}

	/*
	 *  1 = Sync between external and local database.
	 *  2 = Upload of client location.
	 *  3 = Upload of new case to database.
	 * 
	 *  Object for 1 = ListView since it should be updated while sync is done.
	 *  Object for 2 = PositionObject (not sure yet because function doesn't exist)
	 *  Object for 3 = the new Case object that is supposed to be uploaded.
	 * 
	 */
	protected void putOnQueue(int i, Object o) {
		queue.add(new Pair<Integer, Object>(i, o));
	}
	
	public void runQueue(){
		Log.d("henning", "running que" + queue.size());
		while (!queue.isEmpty()){
			Pair<Integer, Object> p = queue.poll();
			if (p.first == 1) runSyncDatabases((ListView) p.second);
			else if (p.first == 2) runUploadPosition(p.second);
			else if (p.first == 3) runUploadNewCase((Case) p.second);
		}
	}

	protected void runSyncDatabases(ListView listView){
		Log.d("henning", "Run sync database");
		eh.syncDatabases(listView);
	}

	protected void runUploadPosition(Object position) {
		// TODO:	 Funktionen inte skriven än.
	}

	protected void runUploadNewCase(Case aCase) {
		Log.d("kand", "RUN Upload Case");
		LocalDBHandler lh = new LocalDBHandler(root.getContext());
		Case returnCase = lh.addNewCaseToDB(aCase);
		returnCase.setFirstRevisionCaseId(returnCase.getCaseId());
		lh.release();
		eh.uploadCase(returnCase);
	}
	
	public abstract void syncDatabases(ListView listView, boolean userInitiated);

	public abstract void uploadPosition(Object position);
	
	public abstract void uploadNewCase(Case aCase);

}
