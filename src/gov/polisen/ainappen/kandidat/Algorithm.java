package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import gov.polisen.ainappen.DeviceStatusUpdater;
import gov.polisen.ainappen.ExternalDBHandeler;
import gov.polisen.ainappen.GlobalData;
import gov.polisen.ainappen.LocalDBHandler;

import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;
import android.view.View;

public abstract class Algorithm {

	protected View root;
	protected Queue<Triple<Integer, Object, Long>> queue;
	private final ExternalDBHandeler eh;
	DeviceStatusUpdater dsu;
	private Long initiated;
	private long excecuted;
	private long latency;
	private long starttime;
	private Boolean userInitiatedSync;
	private String statsString;
	private TextFileHandeler w;


	public Algorithm(View root){
		this.root = root;
		queue = new LinkedList<Triple<Integer, Object, Long>>();
		eh = new ExternalDBHandeler(root);
		dsu = new DeviceStatusUpdater(root.getContext());
		w = new TextFileHandeler(root.getContext());

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
		long l = System.currentTimeMillis();
		queue.add(new Triple<Integer, Object, Long>(i, o, l));
	}

	public void runQueue(){
		Log.d("henning", "running que" + queue.size());
		while (!queue.isEmpty()){
			userInitiatedSync = false;
			Triple<Integer, Object, Long> p = queue.poll();
			if (p.a == 1){
				runSyncDatabases();
				userInitiatedSync = ((Boolean) p.b);
			}
			else if (p.a == 2) runUploadPosition((String) p.b);
			else if (p.a == 3) runUploadNewCase((Case) p.b);

			starttime = GlobalData.starttime;
			initiated = (p.c - starttime) / 1000;
			excecuted = (System.currentTimeMillis() - starttime) / 1000;
			latency = excecuted - initiated;
			
			String init;
			if (userInitiatedSync) init = "1";
			else init = "";
			
			statsString = 
					Integer.toString(p.a) + init + ";" + 
					Integer.toString(queue.size()) + ";" +
					Long.valueOf(initiated) + ";" + 
					Long.valueOf(excecuted)+ ";" + 
					Long.valueOf(latency);
			
			w.saveText(statsString);		
		}
	}

	protected void runSyncDatabases(){
		Log.d("kandidat", "Run sync database");
		eh.syncDatabases();
	}

	protected void runUploadPosition(String positionInfo) {
		Log.d("kandidat", "Run upload position");
		dsu.uploadPosition(positionInfo);
	}

	protected void runUploadNewCase(Case aCase) {
		Log.d("kand", "RUN Upload Case");
		LocalDBHandler lh = new LocalDBHandler(root.getContext());
		Case returnCase = lh.addNewCaseToDB(aCase);
		returnCase.setFirstRevisionCaseId(returnCase.getCaseId());
		lh.release();
		eh.uploadCase(returnCase);
	}

	public abstract void syncDatabases(boolean userInitiated);

	public abstract void uploadPosition(String positionInfo);

	public abstract void uploadNewCase(Case aCase);
	
	

}
