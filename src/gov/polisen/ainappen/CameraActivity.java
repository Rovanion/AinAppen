package gov.polisen.ainappen;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CameraActivity extends Activity {

	private static final int	CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE	= 100;
	private static final int	CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE	= 200;
	private Uri					fileUri;
	public static final int		MEDIA_TYPE_IMAGE					= 1;
	public static final int		MEDIA_TYPE_VIDEO					= 2;
	private String				selectedCaseId;
	private int					selectedMode;
	public static Activity		thisActivity						= null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		thisActivity = this;

		this.selectedCaseId = getSelectedCaseID();
		this.selectedMode = getSelectedMode();

		if (selectedMode == 1)
			captureImage();
		else if (selectedMode == 2)
			recordVideo();
		else
			Toast.makeText(this, "Error mode.", Toast.LENGTH_LONG).show();

	}

	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, selectedCaseId);

		if (fileUri != null)
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	private void recordVideo() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO, selectedCaseId);

		// set video quality
		// 1- for high quality video
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the video capture Intent
		startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
	}

	// Get the selected caseID included when the Camera activity was started.
	// If no case is specified (a photo just needs to be taken), null is
	// returned.
	private String getSelectedCaseID() {
		Bundle extras = getIntent().getExtras();
		String caseId;
		if (extras == null) {
			caseId = null;
		} else {
			caseId = extras.getString("SELECTED_CASE_ID");
		}
		return caseId;
	}

	private int getSelectedMode() {
		Bundle extras = getIntent().getExtras();
		int mode;
		if (extras == null) {
			mode = 0;
		} else {
			mode = extras.getInt("SELECTED_MODE");
		}
		return mode;
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Foto sparat.", Toast.LENGTH_LONG).show();
				finish();

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				finish();

			} else {
				// Image capture failed, advise user
				Toast.makeText(this, "Foto kunde inte sparas. Något gick fel.",
						Toast.LENGTH_LONG).show();
				finish();
			}
		}

		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Video captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Video saved", Toast.LENGTH_LONG).show();
				finish();

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
				finish();
			} else {
				// Video capture failed, advise user
				Toast.makeText(this,
						"Videoh kunde inte sparas. Något gick fel.",
						Toast.LENGTH_LONG).show();
				finish();

			}
		}
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type, String caseID) {
		return Uri.fromFile(getOutputMediaFile(type, caseID));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type, String caseID) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"AinAppen" + File.separator + caseID);

		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("AinAppen", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyddMM_hhmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
