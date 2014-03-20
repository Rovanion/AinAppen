package gov.polisen.ainappen;
import java.io.File;
import java.sql.*;
/**
 * This class is used by AinAppen for handling the local SQLite database.
 * @author Joakim
 */


public class MobileDBHandler{
	String username;
	String dbName;
	CaseHandler ch;
	
	public MobileDBHandler(String uname){
		this.username = uname;
		dbName = this.username+"SQLitedb";
		CaseHandler ch = new CaseHandler();
	}
	/**
	 * Called by AinAppen at startup to chose the right database as well as
	 * check for updates to the database structure.
	 * @throws Exception
	 */
	public void dBStartup()throws Exception{
		File file = new File(dbName);

		if(file.exists()){
			//TODO - implement database version update/check
		}
		else{
			tableCreator();
		}
	}
	/**
	 * Creates the database with an appropriate name, and creates the appropriate tables.
	 */
	private void tableCreator(){
		Connection c = null;
		Statement stmt = null;
		try{
			// This block creates the database tables
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
			c.setAutoCommit(false);
			System.out.println("Opened Database Successfully");
			stmt = c.createStatement();

			String sql = "CREATE TABLE Cases " + "(deviceID INT	NOT NULL, " + 
					" localCaseID	TEXT	NOT NULL, " + 
					" crimeClassification	TEXT	NOT NULL, " + 
					" location	TEXT, " +
					" commander TEXT, " +
					" date DATE, " +
					" status TEXT, " +
					"description TEXT);";

			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch(SQLException s){
			//DONOTHING
		} catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	public void upDateCase(){
		//TODO - fix method for updating existing case
	}
	public void insertCase(){
		ch.insert();
	}
}