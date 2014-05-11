package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.GlobalData;
import gov.polisen.ainappen.MainActivity;

import java.util.TimerTask;

public class PuppetMaster extends TimerTask{
	private int              lastFragment   = 0;

  	@Override
  	public void run() {
  		MainActivity.main.runOnUiThread( new Runnable (){
  
  			@Override
        public void run() {
				if (lastFragment == 0) {
    			MainActivity.main.selectItem(2);
					lastFragment = 2;

					GlobalData.puppeteerTimer.schedule(new CaseUpdater(), 1000);

				} else if (lastFragment == 2) {
					MainActivity.main.selectItem(3);
					lastFragment = 3;
				} else {
					MainActivity.main.selectItem(0);
					lastFragment = 0;
				}
			}
    		
		});
	}
}
