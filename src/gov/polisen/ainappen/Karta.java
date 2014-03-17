package gov.polisen.ainappen;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Karta extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_karta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.karta, menu);
		return true;
	}

}
