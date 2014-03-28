package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contacts")
public class Contact {

	@DatabaseField(id = true)
	int contactID;
	@DatabaseField
	String name;
	@DatabaseField
	String title;
	@DatabaseField
	String phone;

	public Contact (String name, String title, String phone, int contactID){
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
