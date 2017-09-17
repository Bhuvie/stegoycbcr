/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stego;

/**
 *
 * @author Enigmatic Hunk
 */
import java.math.BigInteger;
import java.util.Random;
class rsakeygen {
    public  BigInteger n;
    public  BigInteger e;
    public  BigInteger d;
   public void gen(int s) {
      
      int size = s;
      Random rnd = new Random();
      BigInteger p = BigInteger.probablePrime(size/2,rnd);
      BigInteger q = p.nextProbablePrime();
      n = p.multiply(q);
      BigInteger fi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
      e = getCoprime(fi);
      d = e.modInverse(fi);

     // System.out.println("p: "+p);
     // System.out.println("q: "+q);
     // System.out.println("phi: "+phi);
     // System.out.println("Modulus: "+n);
     // System.out.println("Key size: "+n.bitLength());
      
      //System.out.println("Public key: "+e.bitLength());
      
      //System.out.println("Private key: "+d.bitLength());
            
   }
   public static BigInteger getCoprime(BigInteger m) {
      Random rnd = new Random();
      int length = m.bitLength()-1;
      BigInteger e = BigInteger.probablePrime(length,rnd);
      while (! (m.gcd(e)).equals(BigInteger.ONE) ) {
      	 e = BigInteger.probablePrime(length,rnd);
      }
      return e;
   }
}
