package gov.polisen.ainappen;

public class Contact {
	String firstName;
	String lastName;
	String title;
	String phone;
	
	Contact (String firstName, String lastName, String title, String phone){
		this.firstName = firstName;
		this.lastName = lastName;
		this.title=title;
		this.phone=phone;
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
