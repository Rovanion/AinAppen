package gov.polisen.ainappen;

import java.util.List;

public class ExternalDBHandeler {

	String	webserver	= "http://christian.cyd.liu.se";

	public List<Case> getCasesFromDB(List<Case> localCaseList, int userID) {

		// Get external caselist from server
		List<Case> externalCaseList = localCaseList;

		// Compare local caselist with external caslist and merge
		for (Case oneCase : externalCaseList) {

			//

		}

		return localCaseList;
	}

}
