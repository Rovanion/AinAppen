package gov.polisen.ainappen;

import gov.polisen.ainappen.ipTelephony.Call;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private DrawerLayout		mDrawerLayout;
	private ListView		mDrawerList;
	private ActionBarDrawerToggle	mDrawerToggle;
	private Case		selectedCase;
	private Call		sipCall;
	private String[]	mMenuOptions;

	public Call getSipCall() {
		return sipCall;
	}

	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		final GlobalData globalData = ((GlobalData) getApplicationContext());
		if (!globalData.user.getUserName().equals("fuskLog"))
			sipCall.initializeManager(globalData.user.getUserName(),
					globalData.password);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMenuOptions = getResources().getStringArray(R.array.top_level_options);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout
		.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mMenuOptions));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /* "open drawer" description for accessibility */
				R.string.drawer_close /* "close drawer" description for accessibility */
				) {
			@Override
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}

		showLoggedInUser();

		sipCall = new Call(this);
		final GlobalData globalData = ((GlobalData) getApplicationContext());
		if (!globalData.user.getUserName().equals("fuskLog"))
			sipCall.initializeManager(globalData.user.getUserName(),
					globalData.password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle action buttons
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// turn on the Navigation Drawer image; this is called in the
		// LowerLevelFragments
	}

	// Decides what happens when drawer button is pressed.
	private void selectItem(int position) {

		switch (position) {
		case 0:
			gotoFragment(new CaseListFragment());
			break; // Case
		case 1:
			if (hasExternalStorage() == true) {
				Intent a = new Intent(getBaseContext(), MapActivity.class);
				startActivity(a);
				break; // Map
			}

		case 2:
			gotoFragment(new ContactListFragment());
			break; // Contacts
		case 3:
			gotoFragment(new InformationFragment());
			break; // Information
		default:
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	public void gotoAddCase(View view) {
		gotoLowLevelFragment(new AddCaseFragment());
	}

	private boolean hasExternalStorage() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			return true;
		else
			return false;
	}

	public void gotoPlayVideo(View view, String videoPath) {
		Bundle bundle = new Bundle();
		bundle.putString("videoPath", videoPath);
		VideoFragment fragment = new VideoFragment();
		fragment.setArguments(bundle);

		gotoLowLevelFragment(fragment);
	}

	public void gotoAddContact(View view) {
		gotoLowLevelFragment(new AddContactFragment());
	}

	public void gotoMap(View view) {
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		startActivity(intent);
	}

	public void gotoCase(View view, Case selectedCase) {
		this.selectedCase = selectedCase;
		gotoLowLevelFragment(new CaseFragment());
	}

	public void gotoCall() {
		gotoLowLevelFragment(new CallFragment());
	}

	public void gotoFragment(Fragment fragment) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
		.commit();
	}

	public void gotoEditCase(View view) {
		gotoLowLevelFragment(new EditCaseFragment());
	}

	public void gotoLowLevelFragment(Fragment fragment) {
		// Drawer wont be able to open with gestures at lower level fragments.
		FragmentManager fragmentManager = getFragmentManager();
		disableDrawerIndicator();
		// addToBackStack because addCase is a lower level fragment
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
		.addToBackStack(null).commit();
	}

	@Override
	public void setTitle(CharSequence title) {
		getActionBar().setTitle(title);
	}

	// Drawer will not be able to open with gestures.
	public void lockDrawer() {
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	}

	// Drawer will be able to open with gestures.
	public void unlockDrawer() {
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
	}

	public void disableDrawerIndicator() {
		mDrawerToggle.setDrawerIndicatorEnabled(false);
	}

	public void enableDrawerIndicator() {
		mDrawerToggle.setDrawerIndicatorEnabled(true);
	}

	/*
	 * Shows logged in user.
	 */
	public void showLoggedInUser() {
		final GlobalData appData = (GlobalData) getApplicationContext();
		if (appData.user != null) {
			Toast.makeText(this, "Inloggad som användare: "
					+ appData.user.getUserName(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onStop();
		sipCall.closeLocalProfile();
	}

	public Case getSelectedCase() {
		return this.selectedCase;
	}

}
