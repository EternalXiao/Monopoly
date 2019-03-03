package database;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Database {
	private static Database database=new Database();
	private Database() {
		String driver,url,username,password;
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
		return true;
	}
	public synchronized boolean signUp(String username,String password) {
		return true;
	}
	public String getNickName(String username) {
		return null;
	}
	public boolean setNickName(String username,String nickName) {
		return true;
	}
	//temp
	public void UpdateGameRecord(String nickName) {
		
	}
}
