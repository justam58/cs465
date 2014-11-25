import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import org.apache.commons.codec.digest.Md5Crypt;

public class Main {
	
	public enum PasswordStrength {
		WEAK(5), MEDIUM(8), STRONG(14), BEST(15);
		
		private final int value;
		PasswordStrength(int value) { this.value = value; }
	    public int getValue() { return value; }
	}
	
	public static int passwordLenth = 20; //13-24
	public static final int accountNum = 1;
	public static final int saltLenth = 8;
	public static final String availableChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static Random random = new Random();

	public static void main(String[] args) throws FileNotFoundException{
		generateUnshadowFile();
	}
	
	public static void generateFiles() throws FileNotFoundException{
		generatePasswordFile();
		generateShadowFile();
	}
	
	public static void generatePasswordFile() throws FileNotFoundException{
		PrintWriter writer = new PrintWriter("run/passwd");
		for(int i = 1; i <= accountNum; i++){
			String userName = "user" + i;
			int userID = 1000 + i;
			int groupID = 1000;
			String userInfo = "user" + i + "info";
			String home = "/home/" + userName;
			String command = "/usr/bin/sh";
			String line = String.format("%s:x:%d:%d:%s:%s:%s", userName, userID, groupID, userInfo, home, command);
			writer.println(line);
		}
		writer.close();
	}
	
	public static void generateShadowFile() throws FileNotFoundException{
		PrintWriter writer = new PrintWriter("run/shadow");
		for(int i = 1; i <= accountNum; i++){
			String userName = "user" + i;
			String passwordHash = getRandomPasswordHash();
			int lastChange = 16060;
			int minDays = 0;
			int maxDays = 99999;
			int warnDays = 30;
			String line = String.format("%s:%s:%d:%d:%d:%d:::", userName, passwordHash, lastChange, minDays, maxDays, warnDays);
			writer.println(line);
		}
		writer.close();
	}
	
	public static void generateUnshadowFile() throws FileNotFoundException{
		PrintWriter writer = new PrintWriter("run/notshadow");
		for(int i = 1; i <= accountNum; i++){
			String userName = "user" + i;
			String passwordHash = getRandomPasswordHash();
			int userID = 1000 + i;
			int groupID = 1000;
			String userInfo = "user" + i + "info";
			String home = "/home/" + userName;
			String command = "/usr/bin/sh";
			String line = String.format("%s:%s:%d:%d:%s:%s:%s", userName, passwordHash, userID, groupID, userInfo, home, command);
			writer.println(line);
		}
		writer.close();
	}
	
	public static String getRandomPasswordHash(){
		String password = generateRandomString();
		String salt = "$1$" + generateRandomSalt() + "$";
		System.out.println(password);
		return Md5Crypt.md5Crypt(password.getBytes(),salt);
	}
	
	public static String generateRandomString() {
	   StringBuilder builder = new StringBuilder(passwordLenth);
	   for( int i = 0; i < passwordLenth; i++ ){
		   builder.append(availableChars.charAt(random.nextInt(availableChars.length())));
	   }
	   return builder.toString();
	}
	
	public static String generateRandomSalt(){
	   StringBuilder builder = new StringBuilder(saltLenth);
	   for( int i = 0; i < saltLenth; i++ ) {
		   Character c = availableChars.charAt(random.nextInt(availableChars.length()));
		   while(c.equals('\\')){
			   c = availableChars.charAt(random.nextInt(availableChars.length()));
		   }
		   builder.append(c);
	   }
	   return builder.toString();
	}

}
