package gov.polisen.ainappen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ImageHandeler {

	int						imageHeight;
	int						imageWidth;
	int						reqWidth;
	int						reqHeight;
	String					imageType;
	BitmapFactory.Options	options;
	View					rootView;

	public ImageHandeler(View rootView) {
		this.reqHeight = 400;
		this.reqWidth = 400;
		this.rootView = rootView;
	}

	public void resetOptions() {
		this.options = new BitmapFactory.Options();
	}

	public Bitmap decodeSampledBitmapFromResource(String path) {

		resetOptions();

		readBitmapDimensionsAndType(path);

		// First decode with inJustDecodeBounds=true to check dimensions
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize();

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public void readBitmapDimensionsAndType(String path) {
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		imageHeight = options.outHeight;
		imageWidth = options.outWidth;
		imageType = options.outMimeType;
	}

	public void createImageViewForImage(String path) {
		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.linear_layout_case);
		ImageView imageView = new ImageView(rootView.getContext());
		LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(vp);

		imageView.setImageBitmap(decodeSampledBitmapFromResource(path));
		layout.addView(imageView);
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

	public void createImages(String foldername) {
		List<String> images = getListOfImgageUrls(foldername);
		for (String url : images) {
			createImageViewForImage(url);
		}
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
}
