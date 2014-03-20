package gov.polisen.ainappen;

public class Contact {
	String name;
	String title;
	String phone;
	
	Contact (String name, String title, String phone){
		this.name = name;
		this.title=title;
		this.phone=phone;
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
