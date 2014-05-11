package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

/*
 * SiminAlgorithm is waiting 60 seconds before elastic data is send.
 * Timer starts when first request is done.
 */
public class SiminAlgorithm extends Algorithm{

	private int mInterval = 10000; // milliseconds
	private Handler mHandler;
	private boolean isRunning;
	private boolean firstRound;

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
	public void syncDatabases(ListView listView, boolean userInitiated) {
		putOnQueue(1, listView);

		// Run directly if user initiated the sync and run queue. 
		if (userInitiated){
			runQueue();
			stopRepeatingTask();
		}
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




