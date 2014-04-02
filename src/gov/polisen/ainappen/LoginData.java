package gov.polisen.ainappen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "logindata") 
public class LoginData {
	@DatabaseField(id = true)
	private String userName;
	@DatabaseField
	private String hashedPassword;
	@DatabaseField
	private String salt;

	public LoginData(String userName){
		this.userName = userName;
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
	public String getSalt(){
		return this.salt;
	}
	public void setSalt(String salt){
		this.salt = salt;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(this.userName.equals(((LoginData) obj).getUserName()) &&
				(this.hashedPassword.equals(((LoginData) obj).getHashedPassword()))){
			return true;
		}
		return false;
	}

}
