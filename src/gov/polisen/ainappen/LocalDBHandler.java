package gov.polisen.ainappen;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

/**
 * This class is made to simplify common actions taken upon the AinAppen
 * database. It mostly contains methods for saving objects to database, fetching
 * objects from database etc.
 * 
 * @author Joakim
 * 
 */
public class LocalDBHandler {

	DatabaseHelper	dbHelper;

	public LocalDBHandler(Context context) {
		/*
		 * Open a DatabaseHelper, this object helps us handle the database
		 * connection and keeps track of all the current connections to the
		 * database.
		 */
		dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}

	/*
	 * IMPORTANTE! : release the OpenHelperManager!
	 */
	public void release() {
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
	public Case addNewCaseToDB(Case newCase) {
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
		newCase.setCaseID(lid);
		newCase.setFirstRevisionCaseID(lid);
		// Put the data into database
		caseDao.create(newCase);
		String lastEntryID = newCase.getLocalCaseID();
		newCase = caseDao.queryForId(lastEntryID);
		// Runtime exception Daos are important to set to null.
		localIdDao = null;
		caseDao = null;
		return newCase;
	}


	/*
	 * Put Case from external DB to local DB
	 */
	public Case addExistingCase(Case existingCase){
		// Create the necessary RuntimeExceptionDao's, one for each type of
		// object to be stored
		RuntimeExceptionDao<Case, String> caseDao = dbHelper
				.getCaseRuntimeExceptionDao();

		// Put the data into database
		caseDao.create(existingCase);
		String lastEntryID = existingCase.getLocalCaseID();
		existingCase = caseDao.queryForId(lastEntryID);
		// Runtime exception Daos are important to set to null.
		caseDao = null;
		return existingCase;
	}

	/**
	 * Updates values of a case in the database.
	 * 
	 * @param editedCase The case as it will be after the update
	 * @param context The current context
	 */
	public void editCase(Case editedCase, Context context) {
		RuntimeExceptionDao<Case, String> caseDao = dbHelper
				.getCaseRuntimeExceptionDao();
		UpdateBuilder<Case, String> updateBuilder = caseDao.updateBuilder();
		Log.d("in edit case", "in edit case");
		try {
			updateBuilder.where()
			.eq("localCaseID", editedCase.getLocalCaseID());
			updateBuilder.updateColumnValue("priority",
					editedCase.getPriority());
			updateBuilder.updateColumnValue("classification",
					editedCase.getClassification());
			updateBuilder.updateColumnValue("author",
					editedCase.getAuthor());
			updateBuilder.updateColumnValue("timeOfCrime",
					editedCase.getTimeOfCrime());
			updateBuilder.updateColumnValue("status", editedCase.getStatus());
			updateBuilder.updateColumnValue("description",
					editedCase.getDescription());
			updateBuilder.updateColumnValue("modificationTime", new Date());
			updateBuilder.update();
		} catch (SQLException e) {
			Log.d("failed to add to database", "failed to add to database");
			e.printStackTrace();
		}
		caseDao = null;
	}

	public List<Case> getCasesFromDB() {
		RuntimeExceptionDao<Case, String> caseDao = dbHelper
				.getCaseRuntimeExceptionDao();
		List<Case> caseList = caseDao.queryForAll();
		caseDao = null;
		return caseList;
	}

	public void removeCaseFromDB(Case caseToRemove){
		RuntimeExceptionDao<Case, String> caseDao = dbHelper
				.getCaseRuntimeExceptionDao();
		caseDao.delete(caseToRemove);
		caseDao = null;
	}

	public int getUserId(String username) {
		RuntimeExceptionDao<User, Integer> userDao = dbHelper
				.getUserRuntimeExceptionDao();
		int userId = 0;
		Where<User, Integer> users;
		try {
			users = userDao.queryBuilder().where().eq("username", username);
			userId = users.queryForFirst().getUserId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}

	//	public List<Case> addDummyCases(List<Case> caseList) {
	//		caseList.add(new Case(1337, 1454, (short) 1, "Hemköp Ryd", 80085, Date
	//				.valueOf("2007-12-03"), (short) 1,
	//				"Odrägliga ynglingar som snattat choklad på Hemköp."));
	//		caseList.add(new Case(1337, 1455, (short) 2, "Pengadepå", 80085, Date
	//				.valueOf("2013-12-05"), (short) 2,
	//				"Mycket pengar men det kan bli svårt att få vittnen. Familjer hotade."));
	//		caseList.add(new Case(1337, 1456, (short) 3, "Kyrka", 80085, Date
	//				.valueOf("2014-01-10"), (short) 2, "Inte okej."));
	//		caseList.add(new Case(
	//				1337,
	//				1457,
	//				(short) 4,
	//				"Stan",
	//				800085,
	//				Date.valueOf("1983-10-29"),
	//				(short) 1,
	//				"Det är dags att reda upp det här mordet grabbar. Ta er i kragen och fixa bevis. Deadline imorn."));
	//		return caseList;
	//	}
}
