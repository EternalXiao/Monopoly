package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class Database {
	private static Database database = new Database();
	private String driver;
	private String url;
	private String username;
	private String password;

	private Database() {
		try (FileInputStream in = new FileInputStream(new File("src/database/db.properties"))) { //./db.properties
			Properties pro = new Properties();
			pro.load(in);
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			username = pro.getProperty("username");
			password = pro.getProperty("password");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
     /**
	 *
	 * @return The private instance called database is returned
	 */
	public static Database getInstance() {
		return database;
	}
	
	/**
	 * This method is used to vertify the username and password that user enter
	 * It firstly will obtain hash code and salt value from table account and hashsalt.
	 * After that,the salt value and password will be used to generate a hash code  called
	 * hashpassword. System will then compare if the hashpaaword is the same with the one in
	 * database, if so, the user can login to the game, else login failed.
	 * @param username user's username
	 * @param password user's password
	 *
	 * @return
	 */
	public synchronized boolean login(String username, String password) {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "select account.password,hashsalt.salt from hashsalt INNER JOIN account on account.uid = hashsalt.uid where account.uid=?";

			PreparedStatement ps = connection.prepareStatement(sql);

			int uid = getUid(username);
			if (uid == -1) {
				return false;
			}
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String encodedPassword = rs.getString(1);
			String passwordSalt = rs.getString(2);
			byte[] salt = Base64.getDecoder().decode(passwordSalt);
			String hashPassword = hash.get_SHA_256_SecurePassword(password, salt);

			if (hashPassword.equals(encodedPassword)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {

		}
		return false;

	}

	/**
	 * This method is used to create a new account for user. It will firstly generate a
	 * random salt value which stored in a byte array. Based on the password that user
	 * would like to use and salt value just generated, this method will then create a
	 * specific hash code as password and store this hash code to table account and salt.
	 * @param username User's username.
	 * @param password The password that user want to use.
	 * @param nickname The nickname that user want to use.
	 * @throws Exception
	 */
	public synchronized void signUp(String username, String password, String nickname) throws Exception {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql1 = "INSERT INTO account (username,password) VALUES(?,?)";
			String sql2 = "INSERT INTO nickname (uid,nickname) VALUES(?,? )";
			String sql3 = "INSERT INTO record (uid,win,lose,score) VALUES(?,0,0,0)";
			String sql4 = "INSERT INTO hashsalt (uid,salt) VALUES(?,?)";

			byte[] saltb = hash.getSalt();
			String securePassword = hash.get_SHA_256_SecurePassword(password, saltb);
			String salt = javax.xml.bind.DatatypeConverter.printBase64Binary(saltb);
			// String salt2 = Base64.getEncoder().encodeToString(saltb);
			// String securePassword1 = h.get_SHA_256_SecurePassword(password, decoded);
			// hash+salt strengthen the safety

			PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			PreparedStatement ps3 = connection.prepareStatement(sql3);
			PreparedStatement ps4 = connection.prepareStatement(sql4);

			ps1.setString(1, username);
			ps1.setString(2, securePassword); // add hash value to databases
			ps4.setString(2, salt);
			ps1.executeUpdate();
			int uid = this.getUid(username);
				ps4.setInt(1, uid);
				ps2.setInt(1, uid);
				ps2.setString(2, nickname);
				ps3.setInt(1, uid);
				ps2.executeUpdate();
				ps3.executeUpdate();
				ps4.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to update the login time of user whose uid is the
	 * same as the parameter of this method.
	 * @param uid
	 * @throws Exception
	 */
	public synchronized void updatelogintime(int uid) throws Exception {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "INSERT INTO time (uid,logintime)VALUES(?,?) ";// record user's login time
			PreparedStatement ps = connection.prepareStatement(sql);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(new Date().getTime());
			ps.setInt(1, uid);
			ps.setString(2, date);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to obtain the last login time of a specific user.
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public String getlastlogintime(int uid) throws Exception {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "SELECT logintime from time WHERE uid=?";
			PreparedStatement stmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, uid);
			ResultSet rs1 = stmt.executeQuery();
			if(rs1.last())
				return rs1.getString(1);
			else
				return "0";
		}
	}

	/**
	 * This method is used to checked if a username has been used before.
	 * @param username The username that user would like to use.
	 * @return The availability of username is returned.
	 */
	public boolean checkUsernameAvailable(String username) throws Exception {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "select uid from account where username=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
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

	/**
	 * This method is used to checked if a nickname has been used before.
	 * @param nickname The nickname that user would like to use.
	 * @return The availability of nickname is returned.
	 */
	public boolean checkNicknameAvailable(String nickname) {
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

	/**
	 * Getter for uid
	 * @param username User's username
	 * @return The uid number is returned.
	 * @throws Exception
	 */
	public int getUid(String username) throws Exception {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "SELECT uid FROM account WHERE username=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return -1;
		}
	}

	/**
	 * Getter for nickname
	 * @param uid user's uid
	 * @return The nickname is returned.
	 * @throws Exception
	 */
	public String getNickName(int uid) throws Exception {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
			String sql = "select nickname from nickname where uid=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getString(1);
		}
	}

	// temp
	public void UpdateGameRecord(String nickName) {

	}
}
