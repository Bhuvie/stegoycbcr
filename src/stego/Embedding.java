/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stego;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



/**
 *
 * @author Enigmatic Hunk
 */
public class Embedding {
    
    
    
    public static void embed(File imgfile,String ciphertext,String hashcode) throws IOException {
       
      BufferedImage img = ImageIO.read(imgfile);
      int width = img.getWidth();
      int height = img.getHeight();
      if((width<100)||(height<100))
      {
          JOptionPane.showMessageDialog(null, "Cover Image Resolution is too low..");
          return;
      }
       BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
      String s=conv.strtobin(ciphertext);
      //System.out.print("2222222222222222");
      //System.out.print(s);
      int ciphlen=s.length();
      String cipherlength=conv.tobinary(ciphlen);
      System.out.println("\nLen:"+cipherlength+"L:"+ciphlen);
      if(ciphlen>65536)
      {
          JOptionPane.showMessageDialog(null, "The data to be embedded exceeded the size limit...\n Either reduce the size of your message.. or \nReduce the RSA key size...");
          return;
      }
      String h=conv.strtobin(hashcode);
      //System.out.println("hia:"+h);
      System.out.println();
      
      char[] size=new char[16];int sia=0;
      {for(int i=size.length-1;i>=0;i--)
          size[i]='0';
      for(int i=16-cipherlength.length(),a=0;(i<=15);i++)
          size[i]=cipherlength.charAt(a++);}//catch(Exception e){System.out.println("heere"+e);}
      
      //String s;
      //for(int i=0;i<16;i++)
      //System.out.println(size[i]);
      char[] ch=new char[16+ciphlen];
      for(int i=0,a=0;i<ch.length;i++)
      {
          if(i<16)
          {ch[i]=size[i];}
          else
          {ch[i]=s.charAt(a++);}
      }
      
      int cha=0;int rc=0;
       char[] hh=h.toCharArray();int hha=0;
      //int R[][]=new int[height][width];
      //int G[][]=new int[height][width];
      //int B[][]=new int[height][width];
      //System.out.println("ch:"+ch.length);
      //int Y1[][]=new int[height][width];
            //int Cb1[][]=new int[height][width];int Cr1[][]=new int[height][width];
            double Y,Cb,Cr;
      
      int[][] arrpix=new int[height][width];
      int[] arrpix1=new int[height*width];
               arrpix=conv.convertTo2D(img);
               //System.out.println();
               for (int row = 0; row < height; row++) 
               {
                    for (int col = 0; col < width; col++)
                        { int r, g, b;
                Color c = new Color(arrpix[row][col]);
                // System.out.println(arrpix[row][col]);
                     //System.out.println();
                r=c.getRed();
                g=c.getGreen();
                b=c.getBlue();
                //System.out.println("\nRed: "+c.getGreen());  
               
               
              
               Y= (0.299 * r + 0.587 * g + 0.114 * b);              
                Cb= 128 - (0.168736 * r) - (0.331264 * g) + 0.5 * b;
                Cr= 128 + (0.5 * r - 0.418688 * g - 0.081312 * b);
                double reee,geee,beee;
                //System.out.println("Y:"+Math.round(Cb));
                //System.out.println("R:"+Math.round(reee));
               
               //System.out.println("O Y1:"+red);
               //int flag=0;
                
                //if(rc==1742||rc==12320)
               //{System.out.println(Y+" "+r+" ");}
              
              char[] re=new char[conv.tobinary((int)Math.round(Y)).length()];
              
               if((cha<ch.length))
               {
               int Y1=(int)Math.round(Y);
               String red=conv.tobinary(Y1);  
               re=red.toCharArray();
               if(Y1!=0)
               { re[red.length()-1]=ch[cha]; 
               
               
               StringBuilder fred=new StringBuilder();
               for(int i=0;i<re.length;i++)
               {    fred.append(re[i]);  }
               Y1=conv.todecimal(fred.toString());
               }
               else
               {
                   if(ch[cha]=='0')
                   {Y1=0;}
                   else
                   {Y1=1;}
                   
               }
               //if(rc==1742||rc==12320)
               //{System.out.println(Y1+" "+r+" "+ch[cha]);}
               
//try{              System.out.print();}catch(Exception e){System.out.print("z: "+rc);}
               reee =  (Y1 + 1.402 * (Cr - 128));
               geee =  (Y1 - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
               beee =  (Y1 + 1.772 * (Cb - 128));
               cha++;
               }
               else
               {
               reee =  (Y + 1.402 * (Cr - 128));
               geee =  (Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
               beee =  (Y + 1.772 * (Cb - 128));
               }
               
               char[] re1=new char[conv.tobinary((int)Math.round(Y)).length()];
               if(((height-row)*(width-col))<300)
               {
               if(hha<h.length())
               {
               int Y1=(int)Math.round(Y);
               String red=conv.tobinary(Y1);  
               re1=red.toCharArray();
               if(Y1!=0)
               {
               re1[red.length()-1]=hh[hha];
               
               StringBuilder fred=new StringBuilder();
               for(int i=0;i<re1.length;i++)
               {    fred.append(re1[i]);  }
               Y1=conv.todecimal(fred.toString());
               }
               else
               {
                       if(hh[hha]=='0')
                   {Y1=0;}
                   else
                   {Y1=1;}
               }
               hha++;
               reee =  (Y1 + 1.402 * (Cr - 128));
               geee =  (Y1 - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
               beee =  (Y1 + 1.772 * (Cb - 128));
               }
               else
               {
               reee =  (Y + 1.402 * (Cr - 128));
               geee =  (Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
               beee =  (Y + 1.772 * (Cb - 128));
               }
               }
               
               
               //System.out.println("E:"+fred.toString());
              
              //System.out.println(Cb1);
                
                r=(int)Math.round(reee);
                g=(int)Math.round(geee);
                b=(int)Math.round(beee);
                //System.out.println(g);
                //System.out.println("\nRed: "+c.getRed());     
                //System.out.println("Green: "+c.getGreen());  
                //System.out.println("Blue: "+c.getBlue());        
                //R [row][col]=r;
                //G [row][col]=g;
                //B [row][col]=b;
                /*Y= (0.299 * r + 0.587 * g + 0.114 * b);
                Cb= 128 - (0.168736 * r) - (0.331264 * g) + 0.5 * b;
                Cr= 128 + (0.5 * r - 0.418688 * g - 0.081312 * b);
                double reee =  (Y + 1.402 * (Cr - 128));
                double geee =  (Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
                double beee =  (Y + 1.772 * (Cb - 128));
                */
                //R [row][col]=r;
                //G [row][col]=g;
                //B [row][col]=b;
                //Y [row][col]=Y1; 
                //Cb1[row][col]=Cb; 
                //Cr1[row][col]=Cr;
                
//        image = new BufferedImage(250,250,BufferedImage.TYPE_INT_RGB);
          int coll=(r<<16)|(g<<8)|(b);
            //image.setRGB(249-col,row, coll);
         arrpix1[rc++]=coll; r=0; g=0; b=0;
                        }
               }
                
               image.setRGB(0, 0, width, height, arrpix1, 0, width);
    try {
        File oloc=new File(imgfile.getParent()+"\\stego.png");
        if(oloc.exists())
              {
                  oloc.delete();
              }
        ImageIO.write(image, "PNG", oloc);
        JOptionPane.showMessageDialog(null, "Data Embedding Successful..");
    } catch (IOException e) {
        System.out.println("Insid");
    }
                
                        
               
      
//.setRGB(row, col, Y1);
                //System.out.println("Y: "+Y1);
                //System.out.println("Cb: "+Cb);
                //System.out.println("Cr: "+Cr);
                
                 //System.in.read();
    
     /* for (int row = 0; row < height; row++) {
         for (int col = 0; col < width; col++) {
            System.out.println(arrpix[row][col]);
         }}*/
               
            
               
               
    }
    
}
