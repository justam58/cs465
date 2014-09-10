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
		
		int Nk = 4;
		String plainText = "3243f6a8885a308d313198a2e0370734";
		String key = "2b7e151628aed2a6abf7158809cf4f3c";
		
		System.out.format("Nk is %d\n", Nk);
		System.out.format("plain text is %s\n", plainText);
		System.out.format("key is %s\n", key);
		System.out.println("Start Computing...");
		
		AES aes = new AES(Nk, key);
		aes.cipher(plainText);
	}

}
