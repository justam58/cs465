import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;


public class Main {
	
	private static SecureRandom random = new SecureRandom();
	private static int times = 100;
	private static int bitLength = 22;

	public static void main(String[] args) throws Exception {
		preImage2();
		collision();
	}
	
	public static void preImage2() throws Exception {
		System.out.println("Second Pre-image Attack");
		int totalAttempts = 0;
		for(int i = 0; i < times; i++){
			String targetString = getRandomString();
			String targetHash = hash(targetString);
			int attempts = 0;
			while(true){
				attempts++;
				String newString = getRandomString();
				String newHash = hash(newString);
				if(targetHash.equals(newHash) && !targetString.equals(newString)){
					break;
				}
			}
//			System.out.println((i+1) + ": " +attempts);
			totalAttempts += attempts;
		}
		System.out.println("Average:" + (double)totalAttempts/times);
		System.out.println("Theory:" + Math.pow(2, bitLength));
	}
	
	public static void collision() throws Exception {
		System.out.println("Collision Attack");
		int totalAttempts = 0;
		for(int i = 0; i < times; i++){
			HashMap<String, String> hashStore = new HashMap<String, String>();
			int attempts = -1;
			while(true){
				attempts++;
				String newString = getRandomString();
				String newHash = hash(newString);
				if(hashStore.containsKey(newHash) && !hashStore.get(newHash).equals(newString)){
					break;
				}
				hashStore.put(newHash, newString);
			}
//			System.out.println((i+1) + ": " +attempts);
			totalAttempts += attempts;
		}
		System.out.println("Average:" + (double)totalAttempts/times);
		System.out.println("Theory:" + Math.pow(2, bitLength/2.0));
	}
	
	public static String hash(String x) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return byteArrayToBitString(d.digest());
	}
	
	public static String byteArrayToBitString(byte[] bytes) {
		String result = "";
		int i = 0;
		int bitLengthTemp = bitLength;
		while(bitLengthTemp > 8){
			result += byteToBinaryString(bytes[i]);
			bitLengthTemp -= 8;
			i++;
		}
		result += byteToBinaryString(bytes[i]).substring(0, bitLengthTemp);;
		return result;
	}
	
	public static String byteToBinaryString(byte b){
		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
	}
	
	public static String getRandomString() {
		return new BigInteger(130, random).toString(32);
	}

}
