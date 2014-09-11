
public class AES {
	private int Nb = 4;
	private int Nk;
	private int Nr;
	private String[] words;
	
	private String[][] sBox = {{ "63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76" } ,
								{ "ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0" } ,
								{ "b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15" } ,
								{ "04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75" } ,
								{ "09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84" } ,
								{ "53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf" } ,
								{ "d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8" } ,
								{ "51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2" } ,
								{ "cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73" } ,
								{ "60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db" } ,
								{ "e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79" } ,
								{ "e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08" } ,
								{ "ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a" } ,
								{ "70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e" } ,
								{ "e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df" } ,
								{ "8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16" }}; 
	


	private int[][] invSBox = {{ 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb } ,
								{ 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb } ,
								{ 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e } ,
								{ 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25 } ,
								{ 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92 } ,
								{ 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84 } ,
								{ 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06 } ,
								{ 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b } ,
								{ 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73 } ,
								{ 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e } ,
								{ 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b } ,
								{ 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4 } ,
								{ 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f } ,
								{ 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef } ,
								{ 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61 } ,
								{ 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d }};
	
	private String[] rCon = { "00000000", // Rcon[] is 1-based, so the first entry is just a place holder 
						      "01000000", "02000000", "04000000", "08000000", 
						      "10000000", "20000000", "40000000", "80000000", 
						      "1B000000", "36000000", "6C000000", "D8000000", 
						      "AB000000", "4D000000", "9A000000", "2F000000", 
						      "5E000000", "BC000000", "63000000", "C6000000", 
						      "97000000", "35000000", "6A000000", "D4000000", 
						      "B3000000", "7D000000", "FA000000", "EF000000", 
						      "C5000000", "91000000", "39000000", "72000000", 
						      "E4000000", "D3000000", "BD000000", "61000000", 
						      "C2000000", "9F000000", "25000000", "4A000000", 
						      "94000000", "33000000", "66000000", "CC000000", 
						      "83000000", "1D000000", "3A000000", "74000000", 
						      "E8000000", "CB000000", "8D000000"};
	
	public AES(String key) {
		super();
		this.Nk = key.length()/8;
		
		switch(Nk){
			case 4:	Nr = 10;
					break;
			case 6:	Nr = 12;
					break;
			case 8:	Nr = 14;
		}
		
		keyExpansion(key);
	}

	public String cipher(String input){
		String[][] state = stringToState(input);
//		printState(state);
		state = addRoundKey(state, getRoundKey(0, Nb-1));
		for(int round = 1; round <= Nr-1; round++){
//			System.out.println("Round #: " + round);
			state = subBytes(state);
			state = shiftRows(state);
			state = mixColumns(state);
			state = addRoundKey(state, getRoundKey(round*Nb, (round+1)*Nb-1));
		}
		
		state = subBytes(state);
		state = shiftRows(state);
		state = addRoundKey(state, getRoundKey(Nr*Nb, (Nr+1)*Nb-1));
		return stateToString(state);
	}
	
	public String invCipher(String input){
		String[][] state = stringToState(input);
		state = addRoundKey(state, getRoundKey(Nr*Nb, (Nr+1)*Nb-1));
		
		for(int round = Nr-1; round >= 1; round--){
			state = invShiftRows(state);
			state = invSubBytes(state);
			state = addRoundKey(state, getRoundKey(round*Nb, (round+1)*Nb-1));
			state = invMixColumns(state);
		}
		
		state = invShiftRows(state);
		state = invSubBytes(state);
		state = addRoundKey(state, getRoundKey(0, Nb-1));
		return stateToString(state);
	}
	
	
	private String[][] invMixColumns(String[][] state) {
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			String s0 = state[0][i];
			String s1 = state[1][i];
			String s2 = state[2][i];
			String s3 = state[3][i];
			
			result[0][i] = xor(xor(multiply("0e",s0),multiply("0b",s1)),xor(multiply("0d",s2),multiply("09",s3)));
			result[1][i] = xor(xor(multiply("09",s0),multiply("0e",s1)),xor(multiply("0b",s2),multiply("0d",s3)));
			result[2][i] = xor(xor(multiply("0d",s0),multiply("09",s1)),xor(multiply("0e",s2),multiply("0b",s3)));
			result[3][i] = xor(xor(multiply("0b",s0),multiply("0d",s1)),xor(multiply("09",s2),multiply("0e",s3)));
		}
//		System.out.println("after invMixColumns:");
//		printState(result);
		return result;
	}

	private String[][] invSubBytes(String[][] state) {
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			for(int j = 0; j < Nb; j++){
				String temp = state[i][j];
				int x = stringToIndex(Character.toString(temp.charAt(0)));
				int y = stringToIndex(Character.toString(temp.charAt(1)));
				result[i][j] = padding(Integer.toHexString(invSBox[x][y]));
			}
		}
//		System.out.println("after invSubBytes:");
//		printState(result);
		return result;
	}

	private String[][] invShiftRows(String[][] state) {
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			String[] row = state[i];
			int j = i;
			while(j > 0){
				row = invShiftRow(row);
				j--;
			}
			result[i] = row;
		}
//		System.out.println("after invShiftRows:");
//		printState(result);
		return result;
	}
	
	private String[] invShiftRow(String[] row) {
		String[] result = new String[Nb];
		for(int i = 0; i < Nb; i++){
			if(i == 0){
				result[i] = row[Nb-1];
			}
			else{
				result[i] = row[i-1];
			}
		}
		return result;
	}

	private String[][] mixColumns(String[][] state) {
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			String s0 = state[0][i];
			String s1 = state[1][i];
			String s2 = state[2][i];
			String s3 = state[3][i];
			
			result[0][i] = xor(xor(xor(multiply("02",s0),multiply("03",s1)),s2),s3);
			result[1][i] = xor(xor(xor(multiply("02",s1),multiply("03",s2)),s0),s3);
			result[2][i] = xor(xor(xor(multiply("02",s2),multiply("03",s3)),s0),s1);
			result[3][i] = xor(xor(xor(multiply("02",s3),multiply("03",s0)),s2),s1);
		}
//		System.out.println("after mixColumns:");
//		printState(result);
		return result;
	}

	private String[][] shiftRows(String[][] state) {
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			String[] row = state[i];
			int j = i;
			while(j > 0){
				row = shiftRow(row);
				j--;
			}
			result[i] = row;
		}
//		System.out.println("after shiftRows:");
//		printState(result);
		return result;
	}
	
	private String[] shiftRow(String[] row) {
		String[] result = new String[Nb];
		for(int i = 0; i < Nb; i++){
			if(i == Nb-1){
				result[i] = row[0];
			}
			else{
				result[i] = row[i+1];
			}
		}
		return result;
	}

	private String[][] subBytes(String[][] state) {
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			for(int j = 0; j < Nb; j++){
				String temp = state[i][j];
				int x = stringToIndex(Character.toString(temp.charAt(0)));
				int y = stringToIndex(Character.toString(temp.charAt(1)));
				result[i][j] = sBox[x][y];
			}
		}
//		System.out.println("after subBytes:");
//		printState(result);
		return result;
	}

	private String[][] getRoundKey(int start, int end){
		String[][] result = new String[Nb][Nb];
		int j = 0;
		for(int i = start; i <= end; i++){
			result[0][j] = words[i].substring(0, 2);
			result[1][j] = words[i].substring(2, 4);
			result[2][j] = words[i].substring(4, 6);
			result[3][j] = words[i].substring(6, 8);
			j++;
		}
//		System.out.println("roundkey:");
//		printState(result);
		return result;
	}
	
	private String[][] addRoundKey(String[][] state, String[][] roundKey){
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			for(int j = 0; j < Nb; j++){
				result[i][j] = xor(state[i][j], roundKey[i][j]);
			}
		}
//		System.out.println("after adding roundkey:");
//		printState(result);
		return result;
	}
	
	private void keyExpansion(String key) {
		words = stringToWords(key);
		String temp;
		for(int i = Nk; i < Nb*(Nr+1); i++){
//			System.out.println("i=" + i);
			temp = words[i-1];
			if (i % Nk == 0){
//				System.out.println("after rotWord: " + rotWord(temp)); 
//				System.out.println("after subWord: " + subWord(rotWord(temp)));
//				System.out.println("rCon[i/Nk]: " + rCon[i/Nk]);
				temp = xorWord(subWord(rotWord(temp)), rCon[i/Nk]);
//				System.out.println("after xor with rCon: " + temp);
			}
			else if(Nk > 6 && i%Nk == 4){
				temp = subWord(temp);
			}
//			System.out.println("words[i-Nk]: " + words[i-Nk]);
			words[i] = xorWord(words[i-Nk], temp);
//			System.out.println("words[i]: " + words[i]);
		}
//		printWords(words);
	}
	
	private String xorWord(String a, String b){
		String result = "";
		while(!a.equals("")){
			String aSub = a.substring(0, 2);
			String bSub = b.substring(0, 2);
			result += xor(aSub, bSub);
			a = a.substring(2);
			b = b.substring(2);
		}
		return result;
	}
	
	private String xor(String x, String y){
		int a = Integer.decode("0x"+x);
		int b = Integer.decode("0x"+y);
		return padding(Integer.toHexString(a ^ b));
	}
	
	private String multiply(String x, String y) {
		int a = Integer.decode("0x"+x);
		int b = Integer.decode("0x"+y);
		int i = 1;
		int result = b % 2 == 0? 0 : a;
		int xtimeResult = a;
		b = b >> 1;
//		System.out.println("multiplying " + x + " and " + y);
//		System.out.println("result is " +  padding(Integer.toHexString(result)));
//		System.out.println("xtimeResult is " +  padding(Integer.toHexString(xtimeResult)));
		while(b > 0){
//			System.out.println("checking 2^" + i);
			int d = b % 2; // get the rightest digit of b
//			System.out.println("rightest digit of b is " + d);
			xtimeResult = xtime(xtimeResult);
//			System.out.println("xtimeResult become " +  padding(Integer.toHexString(xtimeResult)));
			if(d == 1){
				result = result ^ xtimeResult;
			}
			b = b >> 1;
			i++;
		}
		return padding(Integer.toHexString(result));
	}
	
	private int xtime(int n){
		n = n << 1;
		if(Integer.toHexString(n).length() > 2){
			n = n ^ 0x11b;
		}
		return n;
	}
	
	private String padding(String s){
		while(s.length() < 2){
			s = "0" + s;
		}
		return s;
	}
	
	private String subWord(String word){
		String result = "";
		for(int i = 0; i < 4; i++){
			String temp = word.substring(0, 2);
			int x = stringToIndex(Character.toString(temp.charAt(0)));
			int y = stringToIndex(Character.toString(temp.charAt(1)));
			result += sBox[x][y];
			word = word.substring(2);
		}
		return result;
	}
	
	private String rotWord(String word){
		return word.substring(2) + word.substring(0,2);
	}
	
	private int stringToIndex(String s){
		try{
			return Integer.parseInt(s);
		}
		catch(NumberFormatException e){
			switch(s){
				case "a":	return 10;
				case "b":	return 11;
				case "c":	return 12;
				case "d":	return 13;
				case "e":	return 14;
				case "f":	return 15;
			}
		}
		return -1; // should never happen
	}
	
	private String[] stringToWords(String input) {
		String[] result = new String[Nb*(Nr+1)];;
		for(int i = 0; i < Nk; i++){
			result[i] = input.substring(0, 8);
			input = input.substring(8);
		}	
		return result;
	}

	private String[][] stringToState(String input) {
		String[][] result = new String[Nb][Nb];
		for(int i = 0; i < Nb; i++){
			for(int j = 0; j < Nb; j++){
				result[j][i] = input.substring(0, 2);
				input = input.substring(2);
			}	
		}	
		return result;
	}
	
	private String stateToString(String[][] output){
		String result = "";
		for(int i = 0; i < Nb; i++){
			for(int j = 0; j < Nb; j++){
				result += output[j][i];
			}	
		}	
		return result;
	}

	private void printState(String[][] matrix) {
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
				System.out.print(matrix[i][j]);
				System.out.print(" ");
			}	
			System.out.println();
		}
	}
	
	private void printWords(String[] words) {
		for(int i = 0; i < words.length; i++){
			System.out.println("i=" + i + " " + words[i]);
		}
	}
}
