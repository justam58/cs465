import java.math.BigInteger;
import java.security.SecureRandom;


public class Main {
	
	public static SecureRandom random = new SecureRandom();
	public static int minBits = 512;
	public static int certainty = Integer.MAX_VALUE;
	public static BigInteger e = BigInteger.valueOf(65537);
	public static BigInteger p = null;
	public static BigInteger q = null;
	public static BigInteger n = null;
	public static BigInteger phiN = null;
	public static PrimeGenerator generator = new PrimeGenerator(minBits,certainty,random);

	public static void main(String[] args) {
		generatePQ();
		while(GCD(phiN,e).compareTo(BigInteger.ONE) != 0){
			generatePQ();
		}
		
		System.out.println("p: " + p);
		System.out.println("q: " + q);
		System.out.println("n: " + n);
		System.out.println("e: " + e);
		
		BigInteger d = extendedGCD(e,phiN).getFirst();
		
		System.out.println("d: " + d);

	}
	
	public static void generatePQ(){
		p = generator.getStrongPrime();
		q = generator.getStrongPrime();
		n = p.multiply(q);
		phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
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
	
	public static BigInteger GCD(BigInteger a, BigInteger b){
		if(b.compareTo(BigInteger.ZERO) == 0){
			return a;
		}
		return GCD(b,a.mod(b));
	}
	
	public static Pair<BigInteger,BigInteger> extendedGCD(BigInteger a, BigInteger b){
		System.out.println("("+a+","+b+")");
		if(b.compareTo(BigInteger.ZERO) == 0){
			return new Pair<BigInteger,BigInteger>(BigInteger.ONE,BigInteger.ZERO);
		}
		BigInteger q = a.divide(b);
		BigInteger r = a.mod(b);
		Pair<BigInteger,BigInteger> pairST = extendedGCD(b,r);
		BigInteger s = pairST.getFirst();
		BigInteger t = pairST.getSecond();
		return new Pair<BigInteger,BigInteger>(t,s.subtract(q).multiply(t));
	}
	
}
