package gov.polisen.ainappen.kandidat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class TextFileHandeler extends Activity {

	private Context c;
	public static String MESSAGE_FILE = "trace1.txt";
	FileWriter f;


	public TextFileHandeler(Context c){
		this.c = c; 

	}

	/* 
	 * Write all chat messages to file. 
	 * TODO Improve by only adding last line to file. 
	 */
	public void saveText(String output) {
		try
		{
			File root = new File(Environment.getExternalStorageDirectory(), "Trace");
			if (!root.exists()) {
				root.mkdirs();
			}
			f = new FileWriter(Environment.getExternalStorageDirectory()+
					"/Trace/"+"trace.txt", true);
			f.write(output);
			f.flush();
			f.close();
			showToast("saved");		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void showToast(String string) {
		Toast.makeText(c, string, 
				Toast.LENGTH_SHORT).show();
	}

	public void saveLogcatToFile(Context context) {    
		String fileName = "trace.txt";
		File outputFile = new File(context.getExternalCacheDir(),fileName);
		try {
			Process process = Runtime.getRuntime().exec("logcat -f "+outputFile.getAbsolutePath() + " | grep KAND");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
