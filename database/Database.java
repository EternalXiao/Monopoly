package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class Database {
	private static Database database=new Database();
	private String driver;
	private String url;
	private String username;
	private String password;
	private Database() {
		try(FileInputStream in = new FileInputStream(new File("src/database/db.properties"))){
		Properties pro = new Properties();
		pro.load(in);
		driver = pro.getProperty("driver");
		url = pro.getProperty("url");
		username = pro.getProperty("user");
		password = pro.getProperty("password");
		Class.forName(driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Database getInstance() {
		return database;
	}
	public synchronized boolean login(String username,String password) {
		try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password)){
			String sql = "select username,password from account where username=? and password=?";
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
		catch(Exception e) {
			
		}
		return false;
		
	}
	public synchronized boolean signUp(String username,String password) {
		try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password)){
			String sql = "select username,password from player where username=? and password=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return false;
			}
			
			else{
				String sql1 = "INSERT INTO Player (username,password) VALUES(username,password )";
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				ps1.executeUpdate();
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return true;
		
	}
	public void signUp(String username,String password,String nickname) {
		
	}
	public boolean checkUsernameExist(String username) {
		return true;
	}
	public boolean checkNicknameExist(String nickname) {
		return true;
	}
	public int getUid(String username) {
		return 0;
	}
	public String getNickname(int uid) {
		return null;
	}
	//temp
	public void UpdateGameRecord(String nickName) {
		
	}
}
