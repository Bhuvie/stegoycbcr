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
import java.io.*;
import java.util.*;

class emdi
{

  String hex_chr = "0123456789abcdef";
  private String rhex(int num)
  {
    String str = "";
    for(int j = 0; j <= 3; j++)
      str = str + hex_chr.charAt((num >> (j * 8 + 4)) & 0x0F) + hex_chr.charAt((num >> (j * 8)) & 0x0F);
    return str;
  }
  

  private int[] md5append512blks(String str)
  {
    int nblk = ((str.length() + 8) >> 6) + 1;
    int[] blks = new int[nblk * 16];
    int i = 0;
    for(i = 0; i < nblk * 16; i++) {
      blks[i] = 0;
    }
    for(i = 0; i < str.length(); i++) {
      blks[i >> 2] |= str.charAt(i) << ((i % 4) * 8);
    }
    blks[i >> 2] |= 0x80 << ((i % 4) * 8);
    blks[nblk * 16 - 2] = str.length()*8;
      //System.out.println(Arrays.toString(blks));
    return blks;
  }
  

  private int addmod(int x, int y)
  {
    return ((x&0x7FFFFFFF) + (y&0x7FFFFFFF)) ^ (x&0x80000000) ^ (y&0x80000000);  
  }
  

  private int rotate(int num, int cnt)
  {
    return (num << cnt) | (num >>> (32 - cnt));
  }
  

  private int common(int q, int a, int b, int x, int s, int t)
  {
    return addmod(rotate(addmod(addmod(a, q), addmod(x, t)), s), b);
  }
  private int ffunc(int a, int b, int c, int d, int x, int s, int t)
  {
    return common((b & c) | ((~b) & d), a, b, x, s, t);
  }
  private int gfunc(int a, int b, int c, int d, int x, int s, int t)
  {
    return common((b & d) | (c & (~d)), a, b, x, s, t);
  }
  private int hfunc(int a, int b, int c, int d, int x, int s, int t)
  {
    return common(b ^ c ^ d, a, b, x, s, t);
  }
  private int ifunc(int a, int b, int c, int d, int x, int s, int t)
  {
    return common(c ^ (b | (~d)), a, b, x, s, t);
  }
  

  public String calcMD5(String str)
  {
    int[] x = md5append512blks(str);
    int a = 0x67452301;
    int b = 0xEFCDAB89;
    int c = 0x98BADCFE;
    int d = 0x10325476;
  
    for(int i = 0; i < x.length; i += 16)
    {
      int olda = a;
      int oldb = b;
      int oldc = c;
      int oldd = d;
  
      a = ffunc(a, b, c, d, x[i+ 0], 7 , 0xD76AA478);          
      d = ffunc(d, a, b, c, x[i+ 1], 12, 0xE8C7B756);
      c = ffunc(c, d, a, b, x[i+ 2], 17, 0x242070DB);
      b = ffunc(b, c, d, a, x[i+ 3], 22, 0xC1BDCEEE);
      a = ffunc(a, b, c, d, x[i+ 4], 7 , 0xF57C0FAF);
      d = ffunc(d, a, b, c, x[i+ 5], 12, 0x4787C62A);
      c = ffunc(c, d, a, b, x[i+ 6], 17, 0xA8304613);
      b = ffunc(b, c, d, a, x[i+ 7], 22, 0xFD469501);
      a = ffunc(a, b, c, d, x[i+ 8], 7 , 0x698098D8);
      d = ffunc(d, a, b, c, x[i+ 9], 12, 0x8B44F7AF);
      c = ffunc(c, d, a, b, x[i+10], 17, 0xFFFF5BB1);
      b = ffunc(b, c, d, a, x[i+11], 22, 0x895CD7BE);
      a = ffunc(a, b, c, d, x[i+12], 7 , 0x6B901122);
      d = ffunc(d, a, b, c, x[i+13], 12, 0xFD987193);
      c = ffunc(c, d, a, b, x[i+14], 17, 0xA679438E);
      b = ffunc(b, c, d, a, x[i+15], 22, 0x49B40821);
  
      a = gfunc(a, b, c, d, x[i+ 1], 5 , 0xF61E2562);
      d = gfunc(d, a, b, c, x[i+ 6], 9 , 0xC040B340);
      c = gfunc(c, d, a, b, x[i+11], 14, 0x265E5A51);
      b = gfunc(b, c, d, a, x[i+ 0], 20, 0xE9B6C7AA);
      a = gfunc(a, b, c, d, x[i+ 5], 5 , 0xD62F105D);
      d = gfunc(d, a, b, c, x[i+10], 9 , 0x02441453);
      c = gfunc(c, d, a, b, x[i+15], 14, 0xD8A1E681);
      b = gfunc(b, c, d, a, x[i+ 4], 20, 0xE7D3FBC8);
      a = gfunc(a, b, c, d, x[i+ 9], 5 , 0x21E1CDE6);
      d = gfunc(d, a, b, c, x[i+14], 9 , 0xC33707D6);
      c = gfunc(c, d, a, b, x[i+ 3], 14, 0xF4D50D87);
      b = gfunc(b, c, d, a, x[i+ 8], 20, 0x455A14ED);
      a = gfunc(a, b, c, d, x[i+13], 5 , 0xA9E3E905);
      d = gfunc(d, a, b, c, x[i+ 2], 9 , 0xFCEFA3F8);
      c = gfunc(c, d, a, b, x[i+ 7], 14, 0x676F02D9);
      b = gfunc(b, c, d, a, x[i+12], 20, 0x8D2A4C8A);
  
      a = hfunc(a, b, c, d, x[i+ 5], 4 , 0xFFFA3942);
      d = hfunc(d, a, b, c, x[i+ 8], 11, 0x8771F681);
      c = hfunc(c, d, a, b, x[i+11], 16, 0x6D9D6122);
      b = hfunc(b, c, d, a, x[i+14], 23, 0xFDE5380C);
      a = hfunc(a, b, c, d, x[i+ 1], 4 , 0xA4BEEA44);
      d = hfunc(d, a, b, c, x[i+ 4], 11, 0x4BDECFA9);
      c = hfunc(c, d, a, b, x[i+ 7], 16, 0xF6BB4B60);
      b = hfunc(b, c, d, a, x[i+10], 23, 0xBEBFBC70);
      a = hfunc(a, b, c, d, x[i+13], 4 , 0x289B7EC6);
      d = hfunc(d, a, b, c, x[i+ 0], 11, 0xEAA127FA);
      c = hfunc(c, d, a, b, x[i+ 3], 16, 0xD4EF3085);
      b = hfunc(b, c, d, a, x[i+ 6], 23, 0x04881D05);
      a = hfunc(a, b, c, d, x[i+ 9], 4 , 0xD9D4D039);
      d = hfunc(d, a, b, c, x[i+12], 11, 0xE6DB99E5);
      c = hfunc(c, d, a, b, x[i+15], 16, 0x1FA27CF8);
      b = hfunc(b, c, d, a, x[i+ 2], 23, 0xC4AC5665);
  
      a = ifunc(a, b, c, d, x[i+ 0], 6 , 0xF4292244);
      d = ifunc(d, a, b, c, x[i+ 7], 10, 0x432AFF97);
      c = ifunc(c, d, a, b, x[i+14], 15, 0xAB9423A7);
      b = ifunc(b, c, d, a, x[i+ 5], 21, 0xFC93A039);
      a = ifunc(a, b, c, d, x[i+12], 6 , 0x655B59C3);
      d = ifunc(d, a, b, c, x[i+ 3], 10, 0x8F0CCC92);
      c = ifunc(c, d, a, b, x[i+10], 15, 0xFFEFF47D);
      b = ifunc(b, c, d, a, x[i+ 1], 21, 0x85845DD1);
      a = ifunc(a, b, c, d, x[i+ 8], 6 , 0x6FA87E4F);
      d = ifunc(d, a, b, c, x[i+15], 10, 0xFE2CE6E0);
      c = ifunc(c, d, a, b, x[i+ 6], 15, 0xA3014314);
      b = ifunc(b, c, d, a, x[i+13], 21, 0x4E0811A1);
      a = ifunc(a, b, c, d, x[i+ 4], 6 , 0xF7537E82);
      d = ifunc(d, a, b, c, x[i+11], 10, 0xBD3AF235);
      c = ifunc(c, d, a, b, x[i+ 2], 15, 0x2AD7D2BB);
      b = ifunc(b, c, d, a, x[i+ 9], 21, 0xEB86D391);
  
      a = addmod(a, olda);
      b = addmod(b, oldb);
      c = addmod(c, oldc);
      d = addmod(d, oldd);
    }
    return rhex(a) + rhex(b) + rhex(c) + rhex(d);
  }
}

public class mdfive

{

public static String calculateauth(FileReader fr)throws Exception
    {
        emdi md = new emdi();
        
 
        String mdinput,hash;
      try(BufferedReader br = new BufferedReader(fr)) 
      	{
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
        	
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        mdinput = sb.toString();
        
        }
      //System.out.println(mdinput);
        hash=md.calcMD5(mdinput);
 
 
        //System.out.println("MD5 Code: " + hash);
        return hash;
    }
}