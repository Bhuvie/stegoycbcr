/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stego;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 *
 * @author Shepherd
 */
public class conv {
    
    public static int[][] convertTo2D(BufferedImage image) {
     /* int width = image.getWidth();
      int height = image.getHeight();
      int[][] result = new int[height][width];

      for (int row = 0; row < height; row++) {
         for (int col = 0; col < width; col++) {
            result[row][col] = image.getRGB(row, col);
         }
      }

      return result;
   }*/
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        
      final int width = image.getWidth();
      final int height = image.getHeight();
      int[][] result = new int[height][width];
         final int pixelLength = 3;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int rgb = 0;
            
            rgb += -16777216; 
            rgb += ((int) pixels[pixel] & 0xff); 
            rgb += (((int) pixels[pixel+1] & 0xff) << 8); 
            rgb += (((int) pixels[pixel+2] & 0xff) << 16);
            result[row][col] = rgb;
            
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         
      }

      return result;
   }
    
    public static String tobinary(int num)
    {
                    
                    //int bin[]=new int[7];
                    String bin;
                    StringBuilder s=new StringBuilder();
                    int rem,i=0;
                    rem=0;
                   while(num>0)
                   {
                       rem=(num%2);
                      // bin[i]=rem;
                       s.append(rem);
                       num=num/2;
                       i++;
                   }
                   s.reverse();
                   bin=s.toString();
                   return bin;
    }
    
    public static int todecimal(String r)
    {
        int num=0;
        try{
        num=Integer.parseInt(r,2);}catch(Exception e){System.out.println("e"+e);}
        //System.out.println(num);
        return num;
    }
    
    public static String strtobin(String authcode)
            {
                //String authcode="efgh";
               //int bin2[]=null;
               String str="";String bin2;
               for(int i=0;i<authcode.length();i++)
               {
                   char temp=authcode.charAt(i);
                   int t=(int)temp;
                   //System.out.println(t);
                   //binauth[i]=Integer.parseInt(
                           bin2=tobinary(t);
                           //System.out.println(bin2.length());
                           if(bin2.length()<7)
                           {
                               StringBuilder sb=new StringBuilder();
                               sb.append("0");
                               sb.append(bin2);
                               bin2=sb.toString();
                           }
                           str=str+bin2;
                           //System.out.println(bin2);
                           
               }
               //System.out.println("String Len:"+str.length());
        return str;
            }
    
}
