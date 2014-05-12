package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.CaseListFragment;
import gov.polisen.ainappen.GlobalData;
import gov.polisen.ainappen.MainActivity;

import java.util.TimerTask;

import android.util.Log;

public class PuppetMaster extends TimerTask {
	private int lastFragment;
	private int iteration;

	public PuppetMaster(int iteration, int lastFragment){
		this.iteration = iteration;
		this.lastFragment = lastFragment;
	}

	@Override
	public void run() {
		MainActivity.main.runOnUiThread( new Runnable (){
			@Override
			public void run() {
				if (lastFragment == 0) {
					MainActivity.main.selectItem(2);
					lastFragment = 2;
					if (iteration % 9 == 0 || iteration % 10 == 0 || iteration % 11 == 0
							|| iteration % 12 == 0) {
						CaseListFragment clf = MainActivity.main.getCaseListFragment();
						EnergySavingPolicy.getPolicy().getAlgorithm()
						.syncDatabases(clf.getCaseListView(), true);
						Log.d("kandidat", "Doing a user initiated sync on iteration: "
								+ iteration);
					}

				} else if (lastFragment == 2) {
					MainActivity.main.selectItem(3);
					lastFragment = 3;
				} else if (lastFragment == 3) {
					MainActivity.main.gotoAddCase();
					lastFragment = 10;
				} else {
					MainActivity.main.selectItem(0);
					lastFragment = 0;
				}

			}
		});
		// When iteration is 60 it takes 180s to circle through the activities.
		iteration = (iteration + 1) % 60;
		try {
			// Don't ask me why this is here. It simply doesn't work without this
			// Thread.sleep(); Wierd as hell.
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GlobalData.puppeteerTimer.schedule(
		    new PuppetMaster(iteration, lastFragment), (iteration % 16) * 2000);

	}
}
