package gov.polisen.ainappen.runThese;

import gov.polisen.ainappen.LoginData;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DbLoginConfigUtil extends OrmLiteConfigUtil{

	private static final Class<?>[] classes = new Class[]{LoginData.class};

	public static void main(String[] args) throws SQLException, IOException{
		writeConfigFile("ormlite_login_config.txt", classes);
	}
}