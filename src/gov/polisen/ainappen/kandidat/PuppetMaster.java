package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.GlobalData;
import gov.polisen.ainappen.MainActivity;

import java.util.TimerTask;

public class PuppetMaster extends TimerTask {
	private int lastFragment;
	private int iteration;
	private long startTime;

	public PuppetMaster(int iteration, int lastFragment, long startTime){
		this.iteration = iteration;
		this.lastFragment = lastFragment;
		this.startTime = startTime;
	}

	@Override
	public void run() {
		MainActivity.main.runOnUiThread( new Runnable (){
			@Override
			public void run() {
									
				if (lastFragment == 0) {
					MainActivity.main.selectItem(2);
					lastFragment = 2;
					if (iteration % 12 == 0) {
						EnergySavingPolicy.getPolicy().getAlgorithm()
						.syncDatabases(true);
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
				new PuppetMaster(iteration, lastFragment, startTime), iteration * 1000);

	}
}
