import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the plain text:");
		String plainText = sc.nextLine();
		System.out.println("Please enter the key:");
		String key = sc.nextLine();
		

//		String plainText = "00112233445566778899aabbccddeeff";
//		// C1
//		String key = "000102030405060708090a0b0c0d0e0f"; // 69c4e0d86a7b0430d8cdb78070b4c55a
		
//		// C2
//		String key = "000102030405060708090a0b0c0d0e0f1011121314151617"; // dda97ca4864cdfe06eaf70a0ec0d7191
		
//		// C3
//		String key = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f"; // 8ea2b7ca516745bfeafc49904b496089
		
		
//		String plainText = "3243f6a8885a308d313198a2e0370734";
//		// A1 B
//		String key = "2b7e151628aed2a6abf7158809cf4f3c"; // 3902dc1925dc116a8409850b1dfb9732
		
//		// A2
//		String key = "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b";
		
//		// A3
//		String key = "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4";
		
		System.out.format("plain text: %s\n", plainText);
		System.out.format("key: %s\n", key);
		System.out.println("Start Computing...\n");
		
		AES aes = new AES(key);
		String cipher = aes.cipher(plainText);
		System.out.println("Cipher: " + cipher);
		String invCipher = aes.invCipher(cipher);
		System.out.println("Inverse Cipher: " + invCipher);
		
		sc.close();
	}

}
