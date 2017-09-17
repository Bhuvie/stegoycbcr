/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stego;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Shepard
 */
public class extract {
    
    
    
    public static void extraction(File loc,String inputmd,String privkey,String mod,File oloc) throws IOException {
       
      BufferedImage img = ImageIO.read(loc);
       int width = img.getWidth();
      int height = img.getHeight();
      int r, g, b;int cha=0;char[] ch=new char[16]; double Y,Cb,Cr;
      int[][] arrpix=new int[height][width];
               arrpix=conv.convertTo2D(img);
               /*int[][] arrpix1=new int[height][width];
               for(int row=0;row<height;row++)
               {
                   for(int col=0;col<width;col++)
                   {
                       arrpix1[249-row][col]=arrpix[row][col];
                   }
               }*/
               
               
               for (int row = 0; row < height; row++) 
               {
                    for (int col = 0; col < width; col++)
                        {
                Color c = new Color(arrpix[row][col]);
                
                r=c.getRed();
                g=c.getGreen();
                b=c.getBlue();
                //System.out.println("R:"+r);
                Y= (0.299 * r + 0.587 * g + 0.114 * b);              
                Cb= 128 - (0.168736 * r) - (0.331264 * g) + 0.5 * b;
                Cr= 128 + (0.5 * r - 0.418688 * g - 0.081312 * b);
                 
                 int Y1=(int)Math.round(Y);
                 //System.out.println(Y1);
                 String red=conv.tobinary(Y1);
                //System.out.println(red);
                if(cha<16)
                {
                    char[] ab=red.toCharArray();
                    if(Y1!=0)
                    ch[cha]=ab[red.length()-1];
                    else
                        ch[cha]='0';
                    cha++;
                }
                        }
                    
               }
               StringBuilder sb=new StringBuilder();
               for(int i=0;i<ch.length;i++)
               sb.append(ch[i]);
               int cipherlength=conv.todecimal(sb.toString());
               System.out.println(cipherlength);
               char[] ci=new char[cipherlength];
               int cia=0,rc=0;
               int hha=0; char[] ha=new char[224];
               for (int row = 0; row < height; row++) 
               {
                    for (int col = 0; col < width; col++)
                        {
                Color c = new Color(arrpix[row][col]);
                
                r=c.getRed();
                g=c.getGreen();
                b=c.getBlue();
                Y= (0.299 * r + 0.587 * g + 0.114 * b);              
                Cb= 128 - (0.168736 * r) - (0.331264 * g) + 0.5 * b;
                Cr= 128 + (0.5 * r - 0.418688 * g - 0.081312 * b);
                 
                /*if(rc==1742||rc==12320)
                {
                    if(Y1==0)
                    ci[cia]='0';
                    else if(Y1%2==0)
                        ci[cia]='0';
                    else
                        ci[cia]='1';
                }*/
                 if((cia<cipherlength)&&(rc>15))
                {
                    int Y1=(int)Math.round(Y);
                 String red=conv.tobinary(Y1);
                    
                    char[] ab=red.toCharArray();
                    if(Y1==0)
                    ci[cia]='0';
                    else
                        ci[cia]=ab[red.length()-1];
                    
                    cia++;
                }
               
               //{System.out.println(Y1+" "+r+" "+ci[cia-1]);}
                 
                 if(((height-row)*(width-col))<300)
               {
               if(hha<ha.length)
               {
                   int Y1=(int)Math.round(Y);
                 String red=conv.tobinary(Y1);
                   char[] ab=red.toCharArray();
                   if(Y1==0)
                   ha[hha]='0';
                   else
                   ha[hha]=ab[red.length()-1];
                   
                   hha++;
               }
               }
                 rc++;
                        }
               }
               //System.out.print("text:12345123456");
               /*for(int i=0;i<ci.length;i++)
               {
                   System.out.print(ci[i]);
               }
               /*System.out.println("\n"+ci.length);
               for(int i=0;i<ha.length;i++)
               {
                   System.out.print(ha[i]);
               }*/
                StringBuilder shi=new StringBuilder();
               for(int i=0;i<ha.length;i+=7)
               {
                   StringBuilder in=new StringBuilder();
                   for(int j=i;j<i+7;j++)
                   {
                       in.append(ha[j]);
                   }
                   shi.append((char)conv.todecimal(in.toString()));
               }
               String hash=shi.toString();
               if(hash.equalsIgnoreCase(inputmd))
               {
                   
               }
               else
               {
                   System.out.println(hash);
                   JOptionPane.showMessageDialog(null,"Authentication failed!!! Enter correct hash code..");
                   return;
               }
               for(int c=0;c<ci.length;c++)
               {
                 if(c%7==0)
                       ci[c]='0';
               }
               //System.out.println(" cilength:"+ci.length);
               //for(int i=0;i<ci.length;i++)
               //{
                 //  System.out.print(ci[i]);
               //}
               StringBuilder sci=new StringBuilder();
               for(int i=0;i<ci.length;i+=7)
               {
                   StringBuilder in=new StringBuilder();
                   for(int j=i;j<i+7;j++)
                   {
                       in.append(ci[j]);                       
                   }
                   sci.append((char)conv.todecimal(in.toString()));
               }
              String cipher=sci.toString();
              //System.out.println("\ndciph: "+cipher);
              String text=rsa.RSAdecrypt(privkey,mod,cipher);
              //System.out.println("\nreco: "+text);
              //System.out.println("\nloc: "+oloc.toString());
              if(oloc.exists())
              {
                  oloc.delete();
              }
              oloc.createNewFile();
              FileWriter writer=new FileWriter(oloc);
              writer.write(text);
              writer.flush();
              writer.close();
              JOptionPane.showMessageDialog(null, "Data Extraction Successful..");
               
    }
    
}
