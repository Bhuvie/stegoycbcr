/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stego;

/**
 *
 * @author Shepard
 */
import java.math.BigInteger ;
import java.io.* ;
import java.util.*;

public class rsa
{
	static BigInteger N ;

	static BigInteger E, D ;
	static BigInteger[] ciphertext;
	static int m[]  = new int[1000];
	static String st[]  = new String[1000];
	static String str = "";
        static	String sarray1[] = new String[100000];

	static StringBuffer sb1 = new StringBuffer();
	
        public static String RSAencrypt(String pk,String mod,FileReader fr) throws Exception{
                String info;
                    try(BufferedReader br = new BufferedReader(fr)) 
      	{
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
        	
            sb.append(line);
            //sb.append(System.lineSeparator());
            line = br.readLine();
        }
        info = sb.toString();
        }
                   
                    
		E = new BigInteger(pk);
		N = new BigInteger(mod);
		try {
		ciphertext = encrypt( info ) ;
		for( int i = 0 ; i < ciphertext.length ; i++ )
		{
			st[i] = (ciphertext[i].toString());
                        //System.out.println("Strenc:"+st[i]);
			sb1.append(st[i]);
			sb1.append(" ");
			str = sb1.toString();

		}
		}
		catch (Exception e) {
			System.out.println(e);
		}

		return str;
	}
	public static BigInteger[] encrypt( String message )
	{
			int i ;
			byte[] temp = new byte[1] ;
			byte[] digits = new byte[8];
			try {
			digits = message.getBytes() ;
			String ds = new String(digits);

			//System.out.println("ds="+ds);

			}
			catch (Exception e) {
				System.out.println(e);
			}
			BigInteger[] bigdigits = new BigInteger[digits.length] ;
			for( i = 0 ; i < bigdigits.length ; i++ )
			{
				temp[0] = digits[i] ;
				bigdigits[i] = new BigInteger( temp ) ;
			}
			BigInteger[] encrypted = new BigInteger[bigdigits.length] ;
			for( i = 0 ; i < bigdigits.length ; i++ )
                        {encrypted[i] = bigdigits[i].modPow( E, N ) ;
                //System.out.println("dat:"+encrypted[i]);
                        }

			return( encrypted ) ;
	}


	

	public static String RSAdecrypt(String pk,String mod,String ciph) {
		D = new BigInteger(pk);
		N = new BigInteger(mod);
		int k1= 0;
		StringTokenizer st = new StringTokenizer(ciph);
		while (st.hasMoreTokens()) {
			sarray1[k1] = st.nextToken(" ");
			k1++;
		}

		BigInteger[] ciphertext1 = new BigInteger[100000];

		for( int i = 0 ; i <k1 ; i++ ) {
                    //System.out.println("decr:"+sarray1[i]);
			ciphertext1[i] = new BigInteger(sarray1[i]);
		}
		String recoveredPlaintext = decrypt( ciphertext1,D,N,k1) ;
		//System.out.println(recoveredPlaintext);
		return recoveredPlaintext;
	}
	public static String decrypt( BigInteger[] encrypted,BigInteger D,BigInteger N,int size )
	{
		int i ;
		String rs="";
		BigInteger[] decrypted = new BigInteger[size] ;
		for( i = 0 ; i < decrypted.length ; i++ ) {
			decrypted[i] = encrypted[i].modPow( D, N ) ;
		}
		char[] charArray = new char[decrypted.length] ;
		byte[] byteArray = new byte[decrypted.length] ;
		for( i = 0 ; i < charArray.length ; i++ ) {
			charArray[i] = (char) ( decrypted[i].intValue() ) ;
			byteArray[i] = decrypted[i].byteValue();
		}
		try {
			rs=new String( byteArray );
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return(rs) ;
	}


}

