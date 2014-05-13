package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import android.os.Handler;
import android.util.Log;
import android.view.View;

/*
 * SiminAlgorithm is waiting 60 seconds before elastic data is send.
 * Timer starts when first request is done.
 */
public class SiminAlgorithm extends Algorithm{

	private final int     mInterval = 60000; // 10 seconds
	private final Handler mHandler;
	private boolean       isRunning;
	private boolean       firstRound;

	public SiminAlgorithm(View root) {
		super(root);
		isRunning = false;
		firstRound = true;
		mHandler = new Handler();
	}

	Runnable mStatusChecker = new Runnable() {
		@Override
		public void run() {

			mHandler.postDelayed(mStatusChecker, mInterval);
			if (!firstRound){
				runQueue();
				stopRepeatingTask();
				Log.d("kandidat", "Queue is run");

			}
			else{
				firstRound = false;
			}
		}
	};

	public void startRepeatingTask() {
		mStatusChecker.run();
		isRunning = true;
	}

	public void stopRepeatingTask() {
		mHandler.removeCallbacks(mStatusChecker);
		isRunning = false;
		firstRound = true;
	}

	public boolean isRunning(){
		return isRunning;
	}

	@Override
	public void syncDatabases(boolean userInitiated) {
		putOnQueue(1, userInitiated);

		// Run directly if user initiated the sync and run queue.
		if (userInitiated){
			runQueue();
			stopRepeatingTask();
		}
	}

	@Override
	public void uploadPosition(String positionInfo) {
		Log.d("kandidat", "Upload position put on queue");
		putOnQueue(2, positionInfo);
	}

	@Override
	public void uploadNewCase(Case aCase) {
		putOnQueue(3, aCase);
	}
}




