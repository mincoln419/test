package Utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Main.PlayScreen;
import Network.Client;

public class OneIcon {//�׸� �ߺ����� �ʰ� �־���
	
   public ImageIcon[][] icon2=new ImageIcon[5][13]; 
   public static ImageIcon[] icon=new ImageIcon[54];//�������� ���� 
   public static ImageIcon[] icon3=new ImageIcon[54];//������� ����(���¸���Ʈ Ư����)

   public Image tmp;
   public Image tmp2;
   static public ImageIcon[] shape=new ImageIcon[4];
   public ImageIcon spade;
   public ImageIcon heart;
   public ImageIcon dia;
   public ImageIcon club;   
  
   OneIcon() {
   	// TODO Auto-generated method stub     

//////////////////////////////////////////���� 7ī�� �� �� ���ٲٱ�� icon
      for(int i=0;i<shape.length;i++){
         shape[i]=new ImageIcon("Image/shape_"+i+".jpg");      
         tmp   = shape[i].getImage();  //ImageIcon�� Image�� ��ȯ.
         tmp = tmp.getScaledInstance(30,50, java.awt.Image.SCALE_SMOOTH);
         shape[i]=new ImageIcon(tmp); //Image�� ImageIcon ����
      }
   
////////////////////////////////////���߹迭�� ī�忡 �׸��ֱ�      
      int cnt=0;
      for(int i=0;i<4;i++){
         for(int j=0;j<13; j++){            
         icon2[i][j]=new ImageIcon("Image/"+(i+1)+"_"+(j+1)+".png");      
         tmp = icon2[i][j].getImage();  //ImageIcon�� Image�� ��ȯ.
         tmp = tmp.getScaledInstance(80,110, java.awt.Image.SCALE_SMOOTH);
         icon2[i][j]=new ImageIcon(tmp); //Image�� ImageIcon ���� 
         icon2[i][j].setDescription(cnt+"");
         icon3[cnt]=new ImageIcon(tmp);
         icon3[cnt].setDescription(cnt+"");
         cnt++;         
         }
      }        
          
      icon2[4][0]=new ImageIcon("Image/"+5+"_"+1+".png");            
      tmp   = icon2[4][0].getImage();  //ImageIcon�� Image�� ��ȯ.
      tmp = tmp.getScaledInstance(80,110, java.awt.Image.SCALE_SMOOTH);
      icon2[4][0]=new ImageIcon(tmp); //Image�� ImageIcon ����
      icon2[4][0].setDescription(52+"");
      icon3[52]=new ImageIcon(tmp);
      icon3[52].setDescription(52+"");
      
      icon2[4][1]=new ImageIcon("Image/"+5+"_"+2+".png");
      tmp   = icon2[4][1].getImage();  //ImageIcon�� Image�� ��ȯ.
      tmp = tmp.getScaledInstance(80,110, java.awt.Image.SCALE_SMOOTH);
      icon2[4][1]=new ImageIcon(tmp); //Image�� ImageIcon ����
      icon2[4][1].setDescription(53+"");
      icon3[53]=new ImageIcon(tmp);
      icon3[53].setDescription(53+"");
   //////////////////////////////////////���߹迭�� �ִ� ī�� 1��¥�� �迭�� �����
      
      cnt=0;
         for(int i=0;i<4;i++){
             for(int j=0;j<13;j++){              
                icon[Client.num[cnt]]=icon2[i][j];
                icon[Client.num[cnt]].setDescription(icon2[i][j].getDescription());
                cnt++;
                }
             } 
         	icon[Client.num[52]]=icon2[4][0]; 
         	icon[Client.num[52]].setDescription(icon2[4][0].getDescription());
         	icon[Client.num[53]]=icon2[4][1];
         	icon[Client.num[52]].setDescription(icon2[4][1].getDescription());
         	////////////////////////////////////////////////////         
         	
   }  

}