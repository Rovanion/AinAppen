package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contacts")
public class Contact {

	@DatabaseField
	String firstName;
	@DatabaseField
	String lastName;
	@DatabaseField(id = true)
	int    userId;
	@DatabaseField
	String title;
	@DatabaseField
	String phone;

	public Contact(String firstName, String lastName, String title,
			String phone, int userId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.phone = phone;
		this.userId = userId;
	}

	public Contact() {
		// Needed by ORMLite
	}

	@Override
	public String toString() {
		return "Contact [UserID=" + this.userId + ", firstName="
				+ this.firstName + ", lastName=" + this.lastName + ", Title="
				+ this.title + ", Phone=" + this.phone + "]";
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int cid) {
		this.userId = cid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
