package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "userdata")
public class User {
	@DatabaseField(id = true)
	private int		userId;
	@DatabaseField
	private String	username;

	public User() {
		// Needed by ORMLite
	}

	public User(int userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public int getUserId() {
		return this.userId;
	}
}
