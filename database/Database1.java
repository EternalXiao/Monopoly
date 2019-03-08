package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class Database1 {
	private static Database1 database=new Database1();
	String driver,Url,Username,Password;
	private Database1() {
		try(FileInputStream in = new FileInputStream(new File("src/database/db.properties"))){
		Properties pro = new Properties();
		pro.load(in);
		driver = pro.getProperty("driver");
		Url = pro.getProperty("url");
		Username = pro.getProperty("user");
		Password = pro.getProperty("password");
		Class.forName(driver);
		//Connection connection = DriverManager.getConnection(Url, Username, Password);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Database1 getInstance() {
		return database;
	}
	public synchronized boolean login(String username,String password) throws Exception{
		String sql = "select username,password from player where username=? and password=?";
		Connection connection = DriverManager.getConnection(Url, Username, Password);
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return true;
		}
		else{
			return false;
		}
	}
	public synchronized boolean signUp(String username,String password) throws Exception{
		if(username.length()>20||password.length()>20){
			return false;
		}
		Connection connection = DriverManager.getConnection(Url, Username, Password);
		String sql1 = "select username,password from player where username=? and password=?";
		PreparedStatement ps1 = connection.prepareStatement(sql1);
		ps1.setString(1, username);
		ps1.setString(2, password);
		ResultSet rs = ps1.executeQuery();
		if(rs.next()){
			return false;
		}
		else{
			String sql = "INSERT INTO Player (username,password) VALUES(username,password )";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			return true;
		}
	}

	public String getNickName(String username)throws Exception {
		if(username.length()>20){
			return "Username is not vaild";
		}
		String sql = "select nickname from nickname where username=?";
		Connection connection = DriverManager.getConnection(Url, Username, Password);
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		return rs.getString(1);
	}

	public boolean setNickName(String username,String nickName) throws Exception{
		if(nickName.length()>20||username.length()>20){
			return false;
		}
		String sql = "UPDATE nickname SET nickname=? WHERE username=?";
		Connection connection = DriverManager.getConnection(Url, Username, Password);
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, nickName);
		ps.setString(2, username);
		ps.executeUpdate();
		String sql1 = "SELECT nickname from nickname where username=?";
		PreparedStatement ps1 = connection.prepareStatement(sql1);
		ps1.setString(1,username);
		ResultSet rs =ps1.executeQuery();
		if(rs.getString(1)==nickName){
			return true;
		}
		else{
			return false;
		}
	}
	//temp
	public void UpdateGameRecord(String nickName) {
		
	}
}
