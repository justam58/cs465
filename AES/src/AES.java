
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
	
	public AES(int Nk, String key) {
		super();
		this.Nk = Nk;
		
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
	
	public String InvCipher(String input){
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
		// TODO Auto-generated method stub
		return null;
	}

	private String[][] invSubBytes(String[][] state) {
		// TODO Auto-generated method stub
		return null;
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
//				System.out.println("yes");
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
