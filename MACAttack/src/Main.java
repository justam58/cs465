import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Main {

	public static void main(String args[]) throws UnsupportedEncodingException {
		String originalMsgHex = toHexString("No one has completed lab 2 so give them all a 0");
		// every two char is a byte (8 bits) which means every char is 4 bits
		int originalMsgSizeInBits = originalMsgHex.length()*4; // original msg size = 376 bits which is 47 bytes
		int keySizeInBits = 128; // 128 bits which is 16 bytes
		int originalSizeInBits = originalMsgSizeInBits + keySizeInBits; // key size + original msg size = 504 bits
		// a block is 512 bits which is 64 bytes and we need 2 blocks
		int paddingPlusSizeSizeInBits = 1024 - originalSizeInBits; // 520 bits left for padding and and size
		
		int sizeSizeInBits = 64; // which means 8 bytes
		String originalSizeHex = padding(Integer.toHexString(originalSizeInBits)); // 1f8 which means 00000000000001f8
		
		int paddingBits = paddingPlusSizeSizeInBits - sizeSizeInBits; // 456 bits left for padding which is 10 byte
		int paddingBytes = paddingBits/8; // 57 bytes of 80
		
		String padding = "8";
		for(int i = 0; i < paddingBytes*2-1; i++){
			padding += "0";
		}
		
		String originalMsg = originalMsgHex + padding + originalSizeHex; // should be 128 byte 
		
		System.out.println(originalMsg + toHexString("P. S. Except for Paula, go ahead and give her the full 100 points."));
		System.out.println(SHA1.encode("P. S. Except for Paula, go ahead and give her the full 100 points."));
	}
	
	public static String toHexString(String arg) throws UnsupportedEncodingException {
	    return new BigInteger(arg.getBytes("iso-8859-1")).toString(16);
	}
	
	public static String padding(String s){
		while(s.length() < 16){
			s = "0" + s;
		}
		return s;
	}
}
