package gov.polisen.ainappen;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * This class is needed by ORMLite. It helps handle alot of useful things, one
 * of them is the creation of the database and creation of the tables.
 * 
 * @author Joakim
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "ainappensdatabas.db";
	private static final int DATABASE_VERSION = 1;

	/*
	 * Create a "Dao"-object for each type of object you are about to store in
	 * your database. Also, create a RuntimeExceptionDao for each type of object
	 * you want to store in the database. The first argument for the Dao-object
	 * is the object Type (here, Case/Contact/etc). The second argument for the
	 * Dao is the "type" of the Primary Key used in the table for said
	 * Object-type. Getters for these also need implementation.
	 */
	private Dao<Case, String> caseDao = null;
	private Dao<Contact, Integer> contactDao = null;
	private Dao<LocalID, Integer> localCaseIdDao = null;
	private Dao<MapPoint, String> mapPointDao = null;
	private Dao<User, Integer> userDao = null;
	private Dao<LocalMapPointID, Integer> localMapPointIdDao = null;
	private RuntimeExceptionDao<Case, String> caseRuntimeDao = null;
	private RuntimeExceptionDao<Contact, Integer> contactRuntimeDao = null;
	private RuntimeExceptionDao<LocalID, Integer> localCaseIdRuntimeDao = null;
	private RuntimeExceptionDao<User, Integer> userRuntimeDao = null;
	private RuntimeExceptionDao<MapPoint, String> mapPointRuntimeDao = null;
	private RuntimeExceptionDao<LocalMapPointID, Integer> localMapPointIdRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	/**
	 * This is called when the database is first created, if you've added a new
	 * type of object you want to store in database, don't forget to create the
	 * new table for it in this method.
	 */
	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Case.class);
			TableUtils.createTable(connectionSource, Contact.class);
			TableUtils.createTable(connectionSource, LocalID.class);
			TableUtils.createTable(connectionSource, MapPoint.class);
			TableUtils.createTable(connectionSource, LocalMapPointID.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public Dao<Case, String> getCaseDao() throws SQLException {
		if (caseDao == null) {
			caseDao = getDao(Case.class);
		}
		return caseDao;
	}

	public RuntimeExceptionDao<Case, String> getCaseRuntimeExceptionDao() {
		if (caseRuntimeDao == null) {
			caseRuntimeDao = getRuntimeExceptionDao(Case.class);
		}
		return caseRuntimeDao;
	}

	public Dao<Contact, Integer> getContactDao() throws SQLException {
		if (contactDao == null) {
			contactDao = getDao(Contact.class);
		}
		return contactDao;
	}

	public RuntimeExceptionDao<Contact, Integer> getContactRuntimeExceptionDao() {
		if (contactRuntimeDao == null) {
			contactRuntimeDao = getRuntimeExceptionDao(Contact.class);
		}
		return contactRuntimeDao;
	}

	public Dao<LocalID, Integer> getLocalIdDao() throws SQLException {
		if (localCaseIdDao == null) {
			localCaseIdDao = getDao(LocalID.class);
		}
		return localCaseIdDao;
	}

	public RuntimeExceptionDao<LocalID, Integer> getLocalIdRuntimeExceptionDao() {
		if (localCaseIdRuntimeDao == null) {
			localCaseIdRuntimeDao = getRuntimeExceptionDao(LocalID.class);
		}
		return localCaseIdRuntimeDao;
	}

	public Dao<MapPoint, String> getMapPointDao() throws SQLException {
		if (mapPointDao == null) {
			mapPointDao = getDao(MapPoint.class);
		}
		return mapPointDao;
	}

	public RuntimeExceptionDao<MapPoint, String> getMapPointRuntimeExceptionDao() {
		if (mapPointRuntimeDao == null) {
			mapPointRuntimeDao = getRuntimeExceptionDao(MapPoint.class);
		}
		return mapPointRuntimeDao;
	}

	public Dao<LocalMapPointID, Integer> getLocalMapPointIdDao()
			throws SQLException {
		if (localMapPointIdDao == null) {
			localMapPointIdDao = getDao(LocalMapPointID.class);
		}
		return localMapPointIdDao;
	}

	public Dao<User, Integer> getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(User.class);
		}
		return userDao;
	}

	public RuntimeExceptionDao<User, Integer> getUserRuntimeExceptionDao() {
		if (userRuntimeDao == null) {
			userRuntimeDao = getRuntimeExceptionDao(User.class);
		}
		return userRuntimeDao;
	}

	public RuntimeExceptionDao<LocalMapPointID, Integer> getLocalMapPointIdRuntimeDao() {
		if (localMapPointIdRuntimeDao == null) {
			localMapPointIdRuntimeDao = getRuntimeExceptionDao(LocalMapPointID.class);
		}
		return localMapPointIdRuntimeDao;
	}
}
