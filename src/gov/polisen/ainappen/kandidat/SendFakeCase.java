package gov.polisen.ainappen.kandidat;

import gov.polisen.ainappen.Case;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class SendFakeCase extends AsyncTask<Case, Void, Boolean> {

	Context rootView;

	public SendFakeCase(Context root){
		rootView = root;
	}

	public boolean postData(Case aCase) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://devnull-as-a-service.com/dev/null");

		try {
			// Add your data
			httppost.setEntity(new ByteArrayEntity(aCase.toString().getBytes()));
			// Execute HTTP Post Request
			@SuppressWarnings("unused")
			HttpResponse response = httpclient.execute(httppost);
			return true;
		} catch (ClientProtocolException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	protected Boolean doInBackground(Case... aCase) {
		return postData(aCase[0]);
	}

	@Override
	protected void onPostExecute(Boolean sent) {
		if (sent) showToast("Case posted!");
	}

	public void showToast(String text){
		Toast.makeText(rootView, text,
				Toast.LENGTH_SHORT).show();
	}
}