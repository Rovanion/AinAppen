package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "logindata")
public class User {
	@DatabaseField(id = true)
	private int		userId;
	@DatabaseField
	private String	username;

	public User() {
		// Needed by ORMLite
	}

	public String getUsername() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public int getId() {
		return this.userId;
	}
}
