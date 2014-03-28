package gov.polisen.ainappen;

import java.util.ArrayList;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.*;

import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;

public class MapFragment extends Activity implements LocationListener{
	
	String PROVIDER = LocationManager.GPS_PROVIDER;

	MapView mapView;
	MapController mapController;
	LocationManager locationManager;
	ArrayList<OverlayItem> overlayItemArray;
	double myLatitude, myLongitude;

	 MyLocationOverlay myLocationOverlay = null;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_map);
	
		
		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setMultiTouchControls(true);
		mapController = (MapController)mapView.getController();
		mapController.setZoom(6);
		
		Drawable marker = getResources().getDrawable(android.R.drawable.star_big_on);
		int markerWidth = marker.getIntrinsicWidth();
		int markerHeight = marker.getIntrinsicHeight();
		marker.setBounds(0, markerHeight, markerWidth,0);
		
		ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
		


		/*
		// The map starting location is Linköping, this will be my location
		GeoPoint startPoint = new GeoPoint(58.4109, 15.6216);
		mapController.setCenter(startPoint);
		*/
		
	
	}

	// Adds an actionbar to the fragment
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_fragment_map, menu);
		return true;

	}

	// This method handles onClick at our actionbar
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		// If we press the addObstacle in the actionbar, call the addObstacle
		// function in MainActivity
		case R.id.addobstacle_item:
			// Call IT
			return true;
		case R.id.visualsettings_item:
			// Call IT
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
