import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;


public class Main {
	
	private static SecureRandom random = new SecureRandom();
	private static int times = 1000;
	private static int length = 2;

	public static void main(String[] args) throws Exception {
//		preImage2();
		collision();
	}
	
	public static void preImage2() throws Exception {
		System.out.println("Second Pre-image Attack");
		int totalAttempts = 0;
		for(int i = 0; i < times; i++){
			String targetString = getRandomString();
			long targetHash = hash(targetString, length);
			int attempts = 0;
			while(true){
				attempts++;
				String newString = getRandomString();
				long newHash = hash(newString, length);
				if(targetHash == newHash && !targetString.equals(newString)){
					break;
				}
			}
//			System.out.println((i+1) + ": " +attempts);
			totalAttempts += attempts;
		}
		System.out.println("Average:" + (double)totalAttempts/times);
		System.out.println("Theory:" + Math.pow(2, length*8));
	}
	
	public static void collision() throws Exception {
		System.out.println("Collision Attack");
		int totalAttempts = 0;
		for(int i = 0; i < times; i++){
			HashMap<Long, String> hashStore = new HashMap<Long, String>();
			int attempts = 0;
			while(true){
				attempts++;
				String newString = getRandomString();
				long newHash = hash(newString, length);
				String storedHash = hashStore.get(newHash);
				if(storedHash != null && !storedHash.equals(newString)){
					break;
				}
				hashStore.put(newHash, newString);
			}
//			System.out.println((i+1) + ": " +attempts);
			totalAttempts += attempts;
		}
		System.out.println("Average:" + totalAttempts/times);
		System.out.println("Theory:" + Math.pow(2, length*4));
	}
	
	public static long hash(String x, int length) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return byteArrayToLong(Arrays.copyOf(d.digest(), length));
	}
	
	public static long byteArrayToLong(byte[] bytes) {
		long value = 0;
		for (int i = 0; i < bytes.length; i++) {
		   value = (value << 8) + (bytes[i] & 0xff);
		}
		return value;
	}
	
	public static String getRandomString() {
		return new BigInteger(130, random).toString(32);
	}

}
