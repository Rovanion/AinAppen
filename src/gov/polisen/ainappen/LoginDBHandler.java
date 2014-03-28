package gov.polisen.ainappen;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class LoginDBHandler {

	public boolean loginAuthenticity(LoginData loginData, Context context){
		LoginDatabaseHelper ldbh = OpenHelperManager.getHelper(context, LoginDatabaseHelper.class);
		RuntimeExceptionDao<LoginData, String> localDataDao = ldbh.getLoginDataRuntimeExceptionDao();
		String queryId = loginData.getUserName();
		/*
		 * 
		 * TO FUCKING DO
		 * 
		 * 
		 * 
		 */
		//		LoginData storedData = localDataDao.queryForId(queryId);
		//		OpenHelperManager.releaseHelper();
		//		return loginData.equals(storedData);
		return false;
	}
}
