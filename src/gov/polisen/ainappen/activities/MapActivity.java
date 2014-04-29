package gov.polisen.ainappen.activities;

import gov.polisen.ainappen.R;
import gov.polisen.ainappen.R.drawable;
import gov.polisen.ainappen.R.id;
import gov.polisen.ainappen.R.layout;
import gov.polisen.ainappen.R.menu;

import java.util.ArrayList;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MapActivity extends Activity implements LocationListener {

	String									PROVIDER						= LocationManager.GPS_PROVIDER;

	MapView									mapView;
	MapController						mapController;
	LocationManager					locationManager;
	ArrayList<OverlayItem>	overlayItemArray;
	double									myLatitude, myLongitude;
	MenuItem								addObstacleMenuItem;
	static Boolean					addObstacleEnabled	= false;

	MyLocationNewOverlay		myLocationOverlay		= null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_map);

		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setMultiTouchControls(true);
		mapView.setClickable(true);
		mapController = (MapController) mapView.getController();
		mapController.setZoom(14);

		myLocationOverlay = new MyLocationNewOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableMyLocation();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Location location = locationManager.getLastKnownLocation(PROVIDER);
		if (location != null) {
			GeoPoint currentLocation = new GeoPoint(location.getLatitude(),
					location.getLongitude());

			mapController.setCenter(currentLocation);
		} else {
			GeoPoint startPoint = new GeoPoint(58.4109, 15.6216);
			mapController.setCenter(startPoint);
		}

		// Create an ArrayList with overlays to display objects on map
		overlayItemArray = new ArrayList<OverlayItem>();

		// Create som init objects
		OverlayItem linkopingItem = new OverlayItem("Linkoping", "Hej",
				new GeoPoint(58.4109, 15.6216));
		OverlayItem stockholmItem = new OverlayItem("Stockholm", "Bajs",
				new GeoPoint(59.3073348, 18.0747967));

		// Add the init objects to the ArrayList overlayItemArray
		overlayItemArray.add(linkopingItem);
		overlayItemArray.add(stockholmItem);

		// Add the Array to the IconOverlay
		ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(
				this, overlayItemArray, null);
		// Add the overlay to the MapView
		mapView.getOverlays().add(itemizedIconOverlay);
	}

	// Adds an actionbar to the fragment
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_fragment_map, menu);
		addObstacleMenuItem = menu.findItem(R.id.addobstacle_item);
		return true;

	}

	// This method handles onClick at our actionbar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		// If we press the addObstacle in the actionbar, call the addObstacle
		// function in MainActivity
		case R.id.addobstacle_item:
			addObstacle();
			return true;
		case R.id.visualsettings_item:
			// Call IT
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void addObstacle() {

		if (addObstacleEnabled = true) {
			addObstacleMenuItem.setIcon(R.drawable.icon_addobstacle_enabled);
		}

	}

	@Override
	public void onLocationChanged(Location location) {

		this.mapController.setCenter(new GeoPoint(location));

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
