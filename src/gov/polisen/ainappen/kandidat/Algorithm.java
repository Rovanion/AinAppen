package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import gov.polisen.ainappen.DeviceStatusUpdater;
import gov.polisen.ainappen.ExternalDBHandeler;
import gov.polisen.ainappen.GlobalData;
import gov.polisen.ainappen.LocalDBHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;
import android.view.View;

public abstract class Algorithm {

	protected View                                 root;
	protected Queue<Triple<Integer, Object, Long>> queue;
	private final ExternalDBHandeler               eh;
	private final DeviceStatusUpdater              dsu;
	private Long                                   initiated;
	private long                                   excecuted;
	private long                                   latency;
	private long                                   starttime;
	private Boolean                                userInitiatedSync;


	public Algorithm(View root){
		this.root = root;
		queue = new LinkedList<Triple<Integer, Object, Long>>();
		eh = new ExternalDBHandeler(root);
		dsu = new DeviceStatusUpdater(root.getContext());
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
		Log.d("henning", "running que with the size: " + queue.size());
		int queueSize = 0;
		while (!queue.isEmpty()){
			userInitiatedSync = false;
			Triple<Integer, Object, Long> p = queue.poll();
			if (p.a == 1){
				runSyncDatabases();
				userInitiatedSync = ((Boolean) p.b);
			}
			else if (p.a == 2) runUploadPosition((String) p.b);
			else if (p.a == 3) runUploadNewCase((Case) p.b);

			starttime = GlobalData.startTime;
			initiated = (p.c - starttime);
			excecuted = (System.currentTimeMillis() - starttime);
			latency = excecuted - initiated;

			// 1 if user initiated, else 0.
			int init = userInitiatedSync ? 1 : 0;

			String statsString = excecuted + ";" + p.a + ";" + init + ";"
			    + (++queueSize) + ";" + initiated + ";" + latency;

			try {
				GlobalData.getLogWriter().append(statsString + '\n');
				GlobalData.getLogWriter().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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