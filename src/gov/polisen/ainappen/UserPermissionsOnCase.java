package gov.polisen.ainappen;

import java.security.AccessControlException;

import com.j256.ormlite.field.DatabaseField;

public class UserPermissionsOnCase extends Object{
	@DatabaseField
	private int deviceID;
	@DatabaseField
	private String caseID;
	@DatabaseField
	private String userID;
	@DatabaseField
	private boolean read;
	@DatabaseField
	private boolean addData;
	@DatabaseField
	private boolean modify;
	@DatabaseField
	private boolean changePermissions;
	
	public UserPermissionsOnCase (int deviceID, String caseID, String userID, boolean read, boolean addData, boolean modify, boolean changePermissions){
		this.deviceID = deviceID;
		this.caseID = caseID;
		this.userID = userID;
		this.read = read;
		this.addData = addData;
		this.modify = modify;
		this.changePermissions = changePermissions;
	}
	
	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		if(!changePermissions){
			throw new AccessControlException("User does not have permission to change permissions.");
		} else{
			//TODO: Write code to change the read permission.
		}
	}

	public boolean isAddData() {
		return addData;
	}

	public void setAddData(boolean addData) {
		if(!changePermissions){
			throw new AccessControlException("User does not have permission to change permissions.");
		} else{
			//TODO: Write code to change the add data permission.
		}
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		if(!changePermissions){
			throw new AccessControlException("User does not have permission to change permissions.");
		} else{
			//TODO: Write code to change the modify permission.
		}
	}

	public boolean isChangePermissions() {
		return changePermissions;
	}

	public void setChangePermissions(boolean changePermissions) {
			if(!changePermissions){
				throw new AccessControlException("User does not have permission to change permissions.");
			} else{
				//TODO: Write code to change the change permission.
			}
		}
	
}
