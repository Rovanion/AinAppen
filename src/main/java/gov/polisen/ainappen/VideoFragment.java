package gov.polisen.ainappen;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoFragment extends Fragment {

	View		rootView;
	VideoView	videoView;

	public VideoFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_video, container, false);
		getActivity().setTitle("Video");
		setUpLowLevelFragment();

		String videoPath = getArguments().getString("videoPath");
		createVideoViewForVideo(videoPath);

		return rootView;
	}

	private void createVideoViewForVideo(String path) {
		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.linear_fragment_video);
		VideoView videoView = new VideoView(rootView.getContext());

		DisplayMetrics displaymetrics = new DisplayMetrics();
		((MainActivity) rootView.getContext()).getWindowManager()
				.getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		videoView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
		MediaController mc = new MediaController(videoView.getContext());
		videoView.setMediaController(mc);
		videoView.setVideoPath(path);
		videoView.setVisibility(View.VISIBLE);
		videoView.start();
		layout.addView(videoView);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:

			// called when the up affordance/carat in actionbar is pressed
			getActivity().onBackPressed();

			return true;
		}
		return false;
	}

	/*
	 * Needs to be included in low level fragments Low level fragments =
	 * fragments that is not in main drawer menu.
	 */
	private void setUpLowLevelFragment() {
		// needed to indicate that the fragment would like to add items to the
		// Options Menu
		setHasOptionsMenu(true);

		// update the actionbar to show the up carat/affordance
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// locks navigation drawer from open in lower lever fragment.
		((MainActivity) getActivity()).lockDrawer();
	}

}
