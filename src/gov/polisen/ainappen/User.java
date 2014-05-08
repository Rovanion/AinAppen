package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "userdata")
public class User {
	@DatabaseField(id = true)
	private int		userId;
	@DatabaseField
	private String	userName;

	public User() {
		// Needed by ORMLite
	}

	public User(int userID, String username) {
		this.userId = userID;
		this.userName = username;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return this.userId;
	}
}
