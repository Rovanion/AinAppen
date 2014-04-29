package gov.polisen.ainappen.devUtilities;

import gov.polisen.ainappen.orm.Case;
import gov.polisen.ainappen.orm.Contact;
import gov.polisen.ainappen.orm.LocalID;
import gov.polisen.ainappen.orm.LocalMapPointID;
import gov.polisen.ainappen.orm.MapPoint;
import gov.polisen.ainappen.orm.User;

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