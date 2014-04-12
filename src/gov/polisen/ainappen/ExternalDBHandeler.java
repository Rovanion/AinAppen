package gov.polisen.ainappen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ExternalDBHandeler {

	String	webserver	= "http://christian.cyd.liu.se";

	public List<Case> getCasesFromDB(List<Case> localCaseList, int userID) {

		// List<Case> mergedCaseList;

		// Get external caselist from server
		Date dummyDate = new Date();
		String json = "[{\"author\":1,\"modificationTime\":\"Apr 10, 2014 3:04:36 PM\",\"firstrevisioncaseid\":1,\"firstrevisiondeviceid\":1,\"classification\":1,\"status\":1,\"description\":\"Snatteri på skånskgatan, skåning misstänkt.\",\"deviceID\":1337,\"priority\":1,\"caseID\":1}]";
		// try {
		// json = readUrl("http://localhost:1337/casesForUser/1");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		List<Case> data = new Gson().fromJson(json,
				new TypeToken<List<Case>>() {
		}.getType());

		for (Case currentCase : data) {
			currentCase.setTimeOfCrime(currentCase.getModificationTime());
			localCaseList.add(currentCase);
		}

		// List<Case> data = new Gson().fromJson(json,
		// new TypeToken<List<Case>>() {
		// }.getType());
		//
		// for (Case case1 : data) {
		// localCaseList.add(case1);
		// }

		// Dummy external case
		// List<Case> externalCaseList = localCaseList;
		// externalCaseList.add(new Case(456, 243, "Under utredning",
		// "Linköping",
		// 567, new Date(20140514), "Under utredning", "Inget speciellt"));

		// Compare local caselist with external caslist and merge
		// for (Case oneCase : externalCaseList) {
		//
		// if (localCaseList.contains(oneCase.g))
		//
		//
		// }

		return localCaseList;
	}

	private static String readUrl(String urlString) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(new HttpGet(urlString));
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		return responseString;
	}
	public Date extractDate(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a zzz"); //zzz
		try {
			Date date = sdf.parse(data);
			return date;
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
