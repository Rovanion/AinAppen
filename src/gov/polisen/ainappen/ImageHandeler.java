package gov.polisen.ainappen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHandeler {

	int						imageHeight;
	int						imageWidth;
	int						reqWidth;
	int						reqHeight;
	String					imageType;
	BitmapFactory.Options	options;

	public ImageHandeler(String path) {
		this.options = new BitmapFactory.Options();
		this.reqHeight = 400;
		this.reqWidth = 400;
	}

	public Bitmap decodeSampledBitmapFromResource(String path) {
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
}
