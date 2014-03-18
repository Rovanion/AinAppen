package gov.polisen.ainappen;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Arende extends ListActivity{

static final String[] arendeItem = new String[] {
    "Misshandel", "Försvunnen", "Rån"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.arende);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.arende,arendeItem));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
    }
}
