package gov.polisen.ainappen;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

/**
 * This class is made to simplify common actions taken upon the AinAppen
 * database. It mostly contains methods for saving objects to database, fetching
 * objects from database etc.
 * 
 * @author Joakim
 * 
 */
public class LocalDBHandler {

	DatabaseHelper dbHelper;
	public LocalDBHandler(Context context){
		/*
		 * Open a DatabaseHelper, this object helps us handle the database connection and keeps
		 * track of all the current connections to the database.
		 */
		dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}

	/*
	 * IMPORTANTE! : release the OpenHelperManager!
	 */
	public void release(){
		OpenHelperManager.releaseHelper();
		dbHelper.close();
		dbHelper = null;
	}

	/**
	 * This method is used to store user-generated Cases in database. Returns
	 * the Case it stored in database (now containing it's very unique parameter
	 * "caseID".
	 * 
	 * @param newCase
	 * @param context
	 * @return
	 */
	public Case addNewCaseToDB(Case newCase, Context context) {
		/*
		 * Open a DatabaseHelper, this object helps us handle the database
		 * connection and keeps track of all the current connections to the
		 * database.
		 */
		DatabaseHelper dbHelper = OpenHelperManager.getHelper(context,
				DatabaseHelper.class);
		// Create the necessary RuntimeExceptionDao's, one for each type of
		// object to be stored
		RuntimeExceptionDao<Case, String> caseDao = dbHelper
				.getCaseRuntimeExceptionDao();
		RuntimeExceptionDao<LocalID, Integer> localIdDao = dbHelper
				.getLocalIdRuntimeExceptionDao();

		/*
		 * This small part is rather unsexy, but needed to bypass constraints in
		 * ORMLite.
		 */
		LocalID newId = new LocalID(0);

		/*
		 * This is where the ORMLite magic happens, the RTExceptioDao created
		 * earlier contains smooth and easy methods for committing objects to
		 * database.
		 */
		localIdDao.create(newId);

		/*
		 * Once committed to the database, ORMLite makes sure that the
		 * referenced object is given the appropriate value for it's primary key
		 * field. PÅ SVENSKA SÅ BETYDER DETTA ATT ORMLITE GENOM MAGI TROLLAR IN
		 * ETT VÄRDE PÅ 'localCaseID' I 'LocalID' EFTER ATT DET BLEV INTRYCKT I
		 * DATABASEN, ÄVEN FAST 'LocalID' FICK VÄRDET 0 NÄR DET SKAPADES HAR DET
		 * NU FÅTT SAMMA VÄRDE SOM DET 'autoincrement'-värde DATABASEN GAV DEN.
		 */
		int lid = newId.getLocalCaseID();
		newCase.setlocalCaseID(lid);
		// Put the data into database
		caseDao.create(newCase);
		String lastEntryID = newCase.getCaseID();
		newCase = caseDao.queryForId(lastEntryID);

		/*
		 * IMPORTANTE! : release the OpenHelperManager before returning.
		 */
		OpenHelperManager.releaseHelper();
		return newCase;
	}

	/**
	 * Updates values of a case in the database.
	 * 
	 * @param editedCase The case as it will be after the update
	 * @param context The current context
	 */
	public void editCase(Case editedCase, Context context) {
		DatabaseHelper dbHelper = OpenHelperManager.getHelper(context,
				DatabaseHelper.class);
		RuntimeExceptionDao<Case, String> caseDao = dbHelper
				.getCaseRuntimeExceptionDao();
		UpdateBuilder<Case, String> updateBuilder = caseDao.updateBuilder();
		Log.d("in edit case", "in edit case");
		try {
			updateBuilder.where().eq("caseID", editedCase.getCaseID());
			updateBuilder.updateColumnValue("location",
					editedCase.getLocation());
			updateBuilder.updateColumnValue("crimeClassification",
					editedCase.getCrimeClassification());
			updateBuilder.updateColumnValue("commander",
					editedCase.getCommander());
			updateBuilder.updateColumnValue("date", editedCase.getDate());
			updateBuilder.updateColumnValue("status", editedCase.getStatus());
			updateBuilder.updateColumnValue("description",
					editedCase.getDescription());
			updateBuilder.update();
		} catch (SQLException e) {
			Log.d("failed to add to database", "failed to add to database");
			e.printStackTrace();
		}
		OpenHelperManager.releaseHelper();
	}

	public List<Case> getCasesFromDB(Context context) {
		DatabaseHelper dbHelper = OpenHelperManager.getHelper(context,
				DatabaseHelper.class);
		RuntimeExceptionDao<Case, String> caseDao = dbHelper
				.getCaseRuntimeExceptionDao();
		List<Case> caseList = caseDao.queryForAll();
		OpenHelperManager.releaseHelper();
		return caseList;
	}

}
