package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contacts")
public class Contact {
	String firstName;
	String lastName;

	@DatabaseField(id = true)
	int contactID;
	@DatabaseField
	@DatabaseField
	String title;
	@DatabaseField
	String phone;

	Contact (String firstName, String lastName, String title, String phone){
		this.firstName = firstName;
		this.lastName = lastName;
		this.title=title;
		this.phone=phone;
		this.contactID = contactID;
	}

	public Contact(){
		//Needed by ORMLite
	}

	public String toString(){
		return "Contact [ContactID=" + this.contactID + ", Name=" + this.name + ", Title=" +
				this.title + ", Phone=" + this.phone + "]";
	}

	public int getContactID(){
		return this.contactID;
	}
	public void setContactID(int cid){
		this.contactID = cid;
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
