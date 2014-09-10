import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
//		System.out.println("Please enter Nk:");
//		int Nk = Integer.parseInt(sc.nextLine());
//		System.out.println("Please enter the plain text:");
//		String plainText = sc.nextLine();
//		System.out.println("Please enter the key:");
//		String key = sc.nextLine();
		
//		// C1
//		String plainText = "00112233445566778899aabbccddeeff";
//		int Nk = 4;
//		String key = "000102030405060708090a0b0c0d0e0f";
		
//		// C2
//		String plainText = "00112233445566778899aabbccddeeff";
//		int Nk = 6;
//		String key = "000102030405060708090a0b0c0d0e0f1011121314151617";
		
		// C3
		String plainText = "00112233445566778899aabbccddeeff";
		int Nk = 8;
		String key = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f";
		
//		String plainText = "3243f6a8885a308d313198a2e0370734";
//		int Nk = 4;
//		String key = "2b7e151628aed2a6abf7158809cf4f3c";
		
//		int Nk = 6;
//		String key = "8e73b0f7da0e6452c810f32b809079e562f8ead2522c6b7b";
		
//		int Nk = 8;
//		String key = "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4";
		
		System.out.format("Nk is %d\n", Nk);
		System.out.format("plain text is %s\n", plainText);
		System.out.format("key is %s\n", key);
		System.out.println("Start Computing...");
		
		AES aes = new AES(Nk, key);
		String cipher = aes.cipher(plainText);
		System.out.println("Cipher is: " + cipher);
		System.out.println("Inverse Cipher is: " + aes.InvCipher(cipher));
	}

}
