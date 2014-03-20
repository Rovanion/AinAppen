package gov.polisen.ainappen;

import android.app.Activity;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void goToArende(View view){
    	Intent intent = new Intent(this,Arende.class);
    	startActivity(intent);	
    }
    
    public void goToKarta(View view){
    	Intent intent = new Intent(this,Karta.class);
    	startActivity(intent);
    }
    
    public void goToInformation(View view){
    	Intent intent = new Intent(this, Information.class);
    	startActivity(intent);
    }
    
    public void goToKontakter(View view){
    	Intent intent = new Intent(this, Kontakter.class);
    	startActivity(intent);
    }
    
}
