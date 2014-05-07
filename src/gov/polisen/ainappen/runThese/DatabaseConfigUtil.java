package gov.polisen.ainappen.runThese;

import gov.polisen.ainappen.Case;
import gov.polisen.ainappen.Contact;
import gov.polisen.ainappen.LocalID;
import gov.polisen.ainappen.LocalMapPointID;
import gov.polisen.ainappen.MapPoint;
import gov.polisen.ainappen.User;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	private static final Class<?>[]	classes	= new Class[] {
		Case.class,
		Contact.class,
		LocalID.class,
		User.class,
		MapPoint.class,
		LocalMapPointID.class
	};

	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt", classes);
	}

}