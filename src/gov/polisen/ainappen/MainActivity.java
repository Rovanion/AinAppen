package gov.polisen.ainappen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   
       
        /*
        Button buttonArende = (Button) findViewById(R.id.buttonArende);
        Button buttonKarta = (Button) findViewById(R.id.buttonKarta);
        Button buttonInformation = (Button) findViewById(R.id.buttonInformation);
        Button buttonKontakter = (Button) findViewById(R.id.buttonKontakter);
        
    	Typeface font = Typeface.createFromAsset(getAssets(), "sf-eccentric-opus.bold.ttf");
    	
    	buttonArende.setTypeface(font);
    	buttonKarta.setTypeface(font);
    	buttonInformation.setTypeface(font);
    	buttonKontakter.setTypeface(font);
    	*/
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
