package gov.polisen.ainappen;

import java.security.AccessControlException;

public class UserPermissionsOnCase extends Object{
	
	private int deviceID;
	private int caseID;
	private int userID;
	private boolean read;
	private boolean addData;
	private boolean modify;
	private boolean changePermissions;
	
	public UserPermissionsOnCase (int deviceID, int caseID, int userID, boolean read, boolean addData, boolean modify, boolean changePermissions){
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
			//TODO: Write code to change the read permission.
		}
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		if(!changePermissions){
			throw new AccessControlException("User does not have permission to change permissions.");
		} else{
			//TODO: Write code to change the read permission.
		}
	}

	public boolean isChangePermissions() {
		return changePermissions;
	}

	public void setChangePermissions(boolean changePermissions) {
			if(!changePermissions){
				throw new AccessControlException("User does not have permission to change permissions.");
			} else{
				//TODO: Write code to change the read permission.
			}
		}
	
}
