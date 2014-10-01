// http://mycila.googlecode.com/svn/mycila-math/trunk/todo/java/Introduction%20to%20Cryptography%20with%20Java%20Applets/PrimeGenerator.java

import java.security.*;
import java.math.*;
import java.util.*;
public class PrimeGenerator {

   //To find primes, we first specify the minimum bit length
   //The methods will produce primes 1 to 3 bits larger than requested, but never smaller
   int minBitLength;

   //certainty is the number of primality tests to pass
   int certainty;

   SecureRandom sr;

   public PrimeGenerator(int minBitLength, int certainty, SecureRandom sr) {
      //The bit length of the prime will exceed minBitLength
      if (minBitLength<512) throw new IllegalArgumentException("Strong/Safe primes must be at least 64 bytes long.");
      //Set the values
      this.minBitLength=minBitLength;
      this.certainty=certainty;
      this.sr=sr;
   }

   //This method finds and returns a strong prime
   public BigInteger getStrongPrime() {
      //The strong prime p will be such that p+1 has a large prime factor s
      BigInteger s=new BigInteger(minBitLength/2-8,certainty,sr);
      //t will be a large prime factor of r, which follows
      BigInteger t=new BigInteger(minBitLength/2-8,certainty,sr);
      BigInteger i=BigInteger.valueOf(1);
      //p-1 will have a large prime factor r
      //r is the first prime in the sequence 2t+1, 2*2t+1, 2*3t+1,...
      BigInteger r;
      do {
         r=BigIntegerMath.TWO.multiply(i).multiply(t).add(BigIntegerMath.ONE);
         i=i.add(BigIntegerMath.ONE);
      } while (!r.isProbablePrime(certainty));
      BigInteger z=s.modPow(r.subtract(BigIntegerMath.TWO),r);
      BigInteger pstar=BigIntegerMath.TWO.multiply(z).multiply(s).subtract(BigIntegerMath.ONE);
      BigInteger k=BigInteger.valueOf(1);
      //The strong prime p is the first prime in the sequence 2rs+p*, 2*2rs+p*, 2*3rs+p*,...
      BigInteger p=BigIntegerMath.TWO.multiply(r).multiply(s).add(pstar);
      while (p.bitLength()<=minBitLength) {
         k=k.multiply(BigIntegerMath.TWO);
         p=BigIntegerMath.TWO.multiply(k).multiply(r).multiply(s).add(pstar);
      }
      while (!p.isProbablePrime(certainty)) {
         k=k.add(BigIntegerMath.ONE);
         p=BigIntegerMath.TWO.multiply(k).multiply(r).multiply(s).add(pstar);
      }
      return p;
   }

   //This method returns a safe prime of form 2rt+1 where t is a large prime
   public BigInteger getSafePrime() {
      BigInteger p;
      BigInteger r=BigInteger.valueOf(0x7fffffff);
      BigInteger t=new BigInteger(minBitLength-30,certainty,sr);
      //p is the first prime in the sequence 2rt+1, 2*2rt+1, 2*3rt+1,...
      do {
         r=r.add(BigIntegerMath.ONE);
         p=BigIntegerMath.TWO.multiply(r).multiply(t).add(BigIntegerMath.ONE);
      } while (!p.isProbablePrime(certainty));
      return p;
   }

   //This method returns a safe prime of form 2rt+1 where t is a large prime,
   //and the factorization of r is known
   //It also returns a generator for the safe prime
   //The zero-th slot in the resulting array is the safe prime
   //Slot 1 of the result is the generator
   public BigInteger[] getSafePrimeAndGenerator() {
      BigInteger[] p=new BigInteger[2];
      BigInteger r=BigInteger.valueOf(0x7fffffff);
      BigInteger t=new BigInteger(minBitLength-30,certainty,sr);
      //p is the first prime in the sequence 2rt+1, 2*2rt+1, 2*3rt+1,...
      do {
         r=r.add(BigIntegerMath.ONE);
         p[0]=BigIntegerMath.TWO.multiply(r).multiply(t).add(BigIntegerMath.ONE);
      } while (!p[0].isProbablePrime(certainty));

      //We must get the prime factors of p-1=2rt
      //Put the prime factors in a Vector-list each prime factor only once
      Vector factors=new Vector();

      //Add t to the vector, since t is a prime factor of p-1=2rt
      factors.addElement(t);

      //We know 2 is a factor of p-1=2rt, so add 2 to the Vector
      factors.addElement(BigInteger.valueOf(2));

      //r may be prime-don't factor it if you don't have to
      if (r.isProbablePrime(10)) factors.addElement(r);
      //otherwise, find the factors of r and add them to the Vector
      else {
         //Divide all the 2's out of r, since 2 is already in the Vector
         while (r.mod(BigIntegerMath.TWO).equals(BigIntegerMath.ZERO)) {
            r=r.divide(BigIntegerMath.TWO);
         }

         //We now get the prime factors of r, which should be small enough to factor
         //Start with 3 - 2 is already in the Vector
         BigInteger divisor=BigInteger.valueOf(3);
         //Do not search for factors past the square root of r
         //Square the divisor for comparison to r
         BigInteger square=divisor.multiply(divisor);
         while (square.compareTo(r)<=0) {
            //If this divisor divides r, add it to the Vector
            if (r.mod(divisor).equals(BigIntegerMath.ZERO)) {
               factors.addElement(divisor);
               //Divide r by this divisor until it no longer divides
               while (r.mod(divisor).equals(BigIntegerMath.ZERO)) r=r.divide(divisor);
            }
            divisor=divisor.add(BigIntegerMath.ONE);
            //Do not search for factors past the square root of r
            //Square the divisor for comparison to r
            square=divisor.multiply(divisor);
         }

      }

      //Now, start looking for a generator
      boolean isGenerator;
      BigInteger pMinusOne=p[0].subtract(BigIntegerMath.ONE);
      BigInteger x,z,lnr;
      do {
         //Start by assuming the test # is a generator
         isGenerator=true;
         //Pick a random integer x smaller than the safe prime p
         x=new BigInteger(p[0].bitLength()-1,sr);
         for (int i=0;i<factors.size();i++) {
            //Compute z as p-1 divided by the i-th prime factor in the Vector
            z=pMinusOne.divide((BigInteger)factors.elementAt(i));
            //Raise x to the z power modulo p
            lnr=x.modPow(z,p[0]);
            //If this equals 1, x is not a generator
            if (lnr.equals(BigIntegerMath.ONE)) {
               isGenerator=false;
               //break-no reason to try the other prime factors for this failed x
               break;
            }
         }
         //While x is not a generator, go back and pick another random x
      } while (!isGenerator);
      //If we get here, we found a generator-set it and return it
      p[1]=x;
      return p;
   }

}