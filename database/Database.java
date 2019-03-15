package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
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
			//String sql = "select uid from account where username=? and password=?";
			String sql1 ="INSERT INTO time (uid,logintime)VALUES(?,?) ";//record user's login time
			String sql2="SELECT salt FROM account WHERE username=?";
			String sql3="SELECT password FROM account WHERE username=?";
			//PreparedStatement ps = connection.prepareStatement(sql);
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			PreparedStatement ps3 = connection.prepareStatement(sql3);

			ps3.setString(1,username);
			ps2.setString(1,username);
			ResultSet rs3=ps3.executeQuery();

			rs3.next();
				String hashdatabases = rs3.getString(1);//get the hash value from databases


				ResultSet rs2 = ps2.executeQuery();
				rs2.next();
				String string = rs2.getString(1);
				byte[] salt = Base64.getDecoder().decode(string);

				hash h = new hash();
				String hashclient = h.get_SHA_256_SecurePassword(password, salt);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = df.format(new Date().getTime());

				//ps.setString(1, username);
				//ps.setString(2, password);
				//ResultSet rs = ps.executeQuery();
				if (hashclient.equals(hashdatabases)) {

					int u = getUid(username);
					ps1.setInt(1, u);
					ps1.setString(2, date);
					ps1.executeUpdate();
					return true;
				} else {
					return false;
				}

		}
		catch(Exception e) {
			
		}
		return false;
		
	}
	
	public synchronized void signUp(String username,String password,String nickname) throws Exception {
		try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password)){
			String sql1 = "INSERT INTO account (username,password,salt) VALUES(?,?,?)";
			String sql2 = "INSERT INTO nickname (uid,nickname) VALUES(?,? )";
			String sql3="INSERT INTO record (uid,win,lose,score) VALUES(?,0,0,0)";
            String sql4="SELECT uid FROM account WHERE username=?";
            String sql5="INSERT INTO account (username,password,salt) VALUES('abc','c4e5262888a9c79e85cba3558dc47549d84a7b2a317579d6a7f6f5267a55a3ad','g47bSgvJHeTFTtZgEpEHnQ==')";

			hash h=new hash();
			byte[] saltb = h.getSalt();
			String securePassword = h.get_SHA_256_SecurePassword(password, saltb);
			String salt=javax.xml.bind.DatatypeConverter.printBase64Binary(saltb);
			String salt2 = Base64.getEncoder().encodeToString(saltb);
			byte[] decoded = Base64.getDecoder().decode(salt);
			String securePassword1 = h.get_SHA_256_SecurePassword(password, decoded);
			//hash+salt strengthen the safety

			PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			PreparedStatement ps3 = connection.prepareStatement(sql3);
			PreparedStatement ps4 = connection.prepareStatement(sql4);
			PreparedStatement ps5 = connection.prepareStatement(sql5);
			//ps5.executeUpdate();
			ps1.setString(1, username);
			ps1.setString(2, securePassword); //add hash value to databases
			ps1.setString(3,salt);// add salt to databases
			ps2.setString(1,nickname);
			ps4.setString(1,username);
			//System.out.println(ps1);
			//System.out.println(ps4);
			ps1.executeUpdate();
			ResultSet rs = ps4.executeQuery();
			while(rs.next()) {
				int u = rs.getInt(1);
				System.out.println(u);
				//
				ps2.setInt(1, u);
				ps2.setString(2, nickname);
				ps3.setInt(1, u);
				ps2.executeUpdate();
				ps3.executeUpdate();
			}
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
			rs.next();
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
			rs.next();
			return rs.getString(1);
		}
	}


	//temp
	public void UpdateGameRecord(String nickName) {
		
	}
}
