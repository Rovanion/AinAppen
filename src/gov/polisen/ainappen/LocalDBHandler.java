package gov.polisen.ainappen;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class LocalDBHandler {

	public Case addNewCaseToDB(Case newCase, Context context){
		DatabaseHelper dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		RuntimeExceptionDao<Case, String> caseDao = dbHelper.getCaseRuntimeExceptionDao();
		RuntimeExceptionDao<LocalID, Integer> localIdDao = dbHelper.getLocalIdRuntimeExceptionDao();

		/*
		 * This small part is rather unsexy, but needed to
		 * bypass constraints in ORMLite.
		 */
		LocalID newId = new LocalID(0);
		localIdDao.create(newId);
		int lid = newId.getLocalCaseID();
		newCase.setlocalCaseID(lid);
		//Put the data into database
		caseDao.create(newCase);
		String lastEntryID = newCase.getCaseID();
		newCase = caseDao.queryForId(lastEntryID);
		OpenHelperManager.releaseHelper();
		return newCase;
	}

	public List<Case> getCasesFromDB(Context context){
		DatabaseHelper dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		RuntimeExceptionDao<Case, String> caseDao = dbHelper.getCaseRuntimeExceptionDao();
		List<Case> caseList = caseDao.queryForAll();
		OpenHelperManager.releaseHelper();
		return caseList;
	}
}
