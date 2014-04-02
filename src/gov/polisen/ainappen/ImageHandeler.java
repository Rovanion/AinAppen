package gov.polisen.ainappen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class ImageHandeler extends AsyncTask<String, Void, Object> {

	int						imageHeight;
	int						imageWidth;
	int						reqWidth;
	int						reqHeight;
	String					imageType;
	BitmapFactory.Options	options;
	View					rootView;
	protected String		videoPath;

	public ImageHandeler(View rootView) {
		this.rootView = rootView;
		setupImageSize();
	}

	private void setupImageSize() {
		reqWidth = (int) convertPixelsToDp(rootView.getContext().getResources()
				.getDisplayMetrics().widthPixels, rootView.getContext());
		reqHeight = (int) convertPixelsToDp(rootView.getContext()
				.getResources().getDisplayMetrics().heightPixels,
				rootView.getContext());
	}

	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}

	public void resetOptions() {
		this.options = new BitmapFactory.Options();
	}

	public Bitmap decodeSampledBitmapFromResource(String path) {

		resetOptions();

		readBitmapDimensionsAndType(path);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize();

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		// Decoding file and creating bitmap
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		bitmap = rotateIfNeeded(bitmap, path);

		return bitmap;
	}

	public void readBitmapDimensionsAndType(String path) {
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		imageHeight = options.outHeight;
		imageWidth = options.outWidth;
		imageType = options.outMimeType;
	}

	public void createImageView(Bitmap bitmap, boolean isVideo) {
		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.linear_layout_case);
		ImageView imageView = new ImageView(rootView.getContext());
		LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(vp);
		imageView.setAdjustViewBounds(true);
		imageView.setPadding(0, 20, 0, 20);
		imageView.setImageBitmap(bitmap);
		if (isVideo) {
			imageView.setOnClickListener(getVideoThumbNailListener());
		}
		layout.addView(imageView);
	}

	private OnClickListener getVideoThumbNailListener() {
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity mainActivity = (MainActivity) rootView
						.getContext();
				mainActivity.gotoPlayVideo(rootView, videoPath);
			}
		};
		return l;
	}

	public int calculateInSampleSize() {

		// Raw height and width of image
		int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	public List<String> getListOfImgageUrls(String foldername) {
		List<String> imageList = new ArrayList<String>();// list of file paths
		File[] listFile;
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"AinAppen" + File.separator + foldername);

		if (mediaStorageDir.isDirectory()) {
			listFile = mediaStorageDir.listFiles();
			// For loop backwards because latest image should be listed highest.
			for (int i = listFile.length; i > 0; i--) {
				imageList.add(listFile[i - 1].getAbsolutePath());
			}
		}
		return imageList;
	}

	public Bitmap rotateIfNeeded(Bitmap bitmap, String uri) {

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
				ExifInterface.ORIENTATION_UNDEFINED);
		Bitmap bmRotated = rotateBitmap(bitmap, orientation);
		return bmRotated;
	}

	public Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

		Matrix matrix = new Matrix();
		switch (orientation) {
		case ExifInterface.ORIENTATION_NORMAL:
			return bitmap;
		case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
			matrix.setScale(-1, 1);
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			matrix.setRotate(180);
			break;
		case ExifInterface.ORIENTATION_FLIP_VERTICAL:
			matrix.setRotate(180);
			matrix.postScale(-1, 1);
			break;
		case ExifInterface.ORIENTATION_TRANSPOSE:
			matrix.setRotate(90);
			matrix.postScale(-1, 1);
			break;
		case ExifInterface.ORIENTATION_ROTATE_90:
			matrix.setRotate(90);
			break;
		case ExifInterface.ORIENTATION_TRANSVERSE:
			matrix.setRotate(-90);
			matrix.postScale(-1, 1);
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			matrix.setRotate(-90);
			break;
		default:
			return bitmap;
		}
		try {
			Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			bitmap.recycle();
			return bmRotated;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected Object doInBackground(String... params) {
		String mediaPath = params[0];
		if (isImage(mediaPath))
			return decodeSampledBitmapFromResource(mediaPath);
		else if (isVideo(mediaPath))
			return mediaPath;
		else
			return null;

	}

	private boolean isVideo(String mediaPath) {
		if (mediaPath.endsWith(".mp4")) {
			return true;

		} else
			return false;
	}

	private boolean isImage(String mediaPath) {
		if (mediaPath.endsWith(".jpg"))
			return true;
		else
			return false;
	}

	private Bitmap createVideoThumbnail(String videoFilePath) {
		Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoFilePath,
				MediaStore.Images.Thumbnails.MINI_KIND);
		return thumb;
	}

	// Object is a Bitmap or videopath
	@Override
	protected void onPostExecute(Object object) {
		if (object instanceof Bitmap)
			createImageView((Bitmap) object, false);
		else if (object instanceof String) {
			Bitmap thumbnail = createVideoThumbnail((String) object);
			this.videoPath = (String) object;
			createImageView(thumbnail, true);
		} else
			Toast.makeText(rootView.getContext(),
					"Error: Inget stöd för filtypen.", Toast.LENGTH_LONG)
					.show();
	}

}
