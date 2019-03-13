package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			String sql = "select uid from account where username=? and password=?";
			String sql1 ="INSERT INTO time (uid,logintime)VALUES(?,?) ";
			PreparedStatement ps = connection.prepareStatement(sql);
			PreparedStatement ps1 = connection.prepareStatement(sql1);

			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(new Date().getTime());

			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				int u=getUid(username);
				ps1.setInt(1, u);
				ps1.setString(2, date);
				ps1.executeUpdate();
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
	
	public synchronized void signUp(String username,String password,String nickname) {
		try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password)){
			String sql1 = "INSERT INTO account (username,password) VALUES(?,? )";
			String sql2 = "INSERT INTO nickname (uid,nickname) VALUES(?,? )";
			String sql3="INSERT INTO record (uid,win,lose,score) VALUES(?,0,0,0)";
            String sql4="SELECT uid FROM account WHERE username=?";
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			PreparedStatement ps3 = connection.prepareStatement(sql3);
			PreparedStatement ps4 = connection.prepareStatement(sql4);
			ps1.setString(1, username);
			ps1.setString(2, password);
			ps2.setString(1,nickname);
			ps4.setString(1,username);

			ps1.executeUpdate();
			ResultSet rs = ps4.executeQuery();
			int u=rs.getInt(1);
			ps2.setInt(1,u);
			ps2.setString(2,nickname);
			ps3.setInt(1,u);
			ps2.executeUpdate();
			ps3.executeUpdate();
		}

		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public boolean checkUsernameExist(String username) throws Exception{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "select uid from account where username=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

	public boolean checkNicknameExist(String nickname) {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "select uid from nickname where nickname=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, nickname);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getUid(String username) throws Exception{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql="SELECT uid FROM account WHERE username=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();
			int a = rs.getInt(1);
			return a;
		}
	}
	
		public String getNickName(int uid) throws Exception {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "select nickname from nickname where uid=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			String s = String.valueOf(uid);
			ps.setString(1, s);
			ResultSet rs = ps.executeQuery();
			return rs.getString(1);
		}
	}


	//temp
	public void UpdateGameRecord(String nickName) {
		
	}
}
