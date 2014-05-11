package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.GlobalData;
import gov.polisen.ainappen.MainActivity;

import java.util.TimerTask;

public class PuppetMaster extends TimerTask{
	GlobalData settings;
	int        lastFragment = 0;

	public PuppetMaster(GlobalData settings) {
		this.settings = settings;
	}
  	@Override
  	public void run() {
  		MainActivity.main.runOnUiThread( new Runnable (){
  
  			@Override
        public void run() {
				if (lastFragment == 0) {
    			MainActivity.main.selectItem(2);
					lastFragment = 2;
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
