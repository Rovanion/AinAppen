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
 * This class is used to handle the database we connect to when authenticating 
 * users locally.
 * @author Joakim
 *
 */
public class LoginDatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "ainappenslogindb.db";
	private static final int DATABASE_VERSION = 1;

	private Dao<LoginData, String> loginDataDao = null;
	private RuntimeExceptionDao<LoginData, String> runtimeExceptionLoginDataDao = null;

	public LoginDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_login_config);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, LoginData.class);
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

	public Dao<LoginData, String> getLoginDataDao() throws SQLException{
		if(loginDataDao == null){
			loginDataDao = getDao(LoginData.class);
		}
		return loginDataDao;
	}

	public RuntimeExceptionDao<LoginData, String> getLoginDataRuntimeExceptionDao(){
		if (runtimeExceptionLoginDataDao == null){
			runtimeExceptionLoginDataDao = getRuntimeExceptionDao(LoginData.class);
		}
		return runtimeExceptionLoginDataDao;
	}
}
