import java.math.BigInteger;
import java.security.SecureRandom;


public class Main {

	public static SecureRandom random = new SecureRandom();
	public static int minBits = 512;
	public static int certainty = Integer.MAX_VALUE;
	public static BigInteger g = BigInteger.valueOf(5);
	
	public static void main(String[] args) {
//		PrimeGenerator generator = new PrimeGenerator(minBits,certainty,random);
//		BigInteger p = generator.getSafePrime();
//		BigInteger e = new BigInteger(minBits, random);

		String pString = "29071464959229161194509717855295824812332943546472089949051043502517873284360094952063620095063794692001862208720246800303222945075453380060602941351924987";
		String eString = "12805111598789561645685186157876531771880567515809538793020233442777951264589034616315479449216034793730838694373998588111230697377226530885901401109647985";
		String gtpString = "8305525221737005021136983182735841736473217241491652305553125530972015803933154217636269528220092164239574243335057333271171115202585415813549296200231937";
		BigInteger p = new BigInteger(pString);
		BigInteger e = new BigInteger(eString);
		BigInteger gtp = new BigInteger(gtpString);
		
		System.out.println("p: "+p);
		System.out.println("e: "+e);
		System.out.println("gep: "+modExp(g, e, p));
		System.out.println("gtpep: "+modExp(gtp, e, p));
	}
	
	public static BigInteger modExp(BigInteger g, BigInteger e, BigInteger p){
		BigInteger result = BigInteger.ONE;
		String eString = e.toString(2);
		for(int i = eString.length()-1 ; i >= 0; i--){
			if(eString.charAt(i) == '1'){
				result = result.multiply(g).mod(p);
			}
			g = g.multiply(g).mod(p);
		}
		return result;
	}

}
