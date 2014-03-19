package gov.polisen.ainappen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
    
    public void goToCase(View view){
    	Intent intent = new Intent(this,Case.class);
    	startActivity(intent);	
    }
    
    public void goToMap(View view){
    	Intent intent = new Intent(this,Map.class);
    	startActivity(intent);
    }
    
    public void goToInformation(View view){
    	Intent intent = new Intent(this, Information.class);
    	startActivity(intent);
    }
    
    public void goToContacts(View view){
    	Intent intent = new Intent(this, Contacts.class);
    	startActivity(intent);
    }
    
}
