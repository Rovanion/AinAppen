package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "logindata") 
public class LoginData {
	@DatabaseField(id = true)
	private String userName;
	@DatabaseField
	private String hashedPassword;

	public LoginData(String userName, String hashedPassword){
		this.userName = userName;
		this.hashedPassword = hashedPassword;
	}
	public LoginData(){
		//Needed by ORMLite
	}

	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getHashedPassword(){
		return this.hashedPassword;
	}
	public void setHashedPassword(String hashedPassword){
		this.hashedPassword = hashedPassword;
	}

	@Override
	public boolean equals(Object obj){
		if(this.userName.equals(((LoginData) obj).getUserName()) &&
				(this.hashedPassword.equals(((LoginData) obj).getHashedPassword()))){
			return true;
		}
		return false;
	}

}
