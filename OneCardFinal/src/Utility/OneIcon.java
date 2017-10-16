package Utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Main.PlayScreen;
import Network.Client;

public class OneIcon {//그림 중복되지 않게 넣어줌
	
   public ImageIcon[][] icon2=new ImageIcon[5][13]; 
   public static ImageIcon[] icon=new ImageIcon[54];//랜덤으로 나열 
   public static ImageIcon[] icon3=new ImageIcon[54];//순서대로 나열(오픈리스트 특정용)

   public Image tmp;
   public Image tmp2;
   static public ImageIcon[] shape=new ImageIcon[4];
   public ImageIcon spade;
   public ImageIcon heart;
   public ImageIcon dia;
   public ImageIcon club;   
  
   OneIcon() {
   	// TODO Auto-generated method stub     

//////////////////////////////////////////숫자 7카드 낼 때 모양바꾸기용 icon
      for(int i=0;i<shape.length;i++){
         shape[i]=new ImageIcon("Image/shape_"+i+".jpg");      
         tmp   = shape[i].getImage();  //ImageIcon을 Image로 변환.
         tmp = tmp.getScaledInstance(30,50, java.awt.Image.SCALE_SMOOTH);
         shape[i]=new ImageIcon(tmp); //Image로 ImageIcon 생성
      }
   
////////////////////////////////////이중배열로 카드에 그림넣기      
      int cnt=0;
      for(int i=0;i<4;i++){
         for(int j=0;j<13; j++){            
         icon2[i][j]=new ImageIcon("Image/"+(i+1)+"_"+(j+1)+".png");      
         tmp = icon2[i][j].getImage();  //ImageIcon을 Image로 변환.
         tmp = tmp.getScaledInstance(80,110, java.awt.Image.SCALE_SMOOTH);
         icon2[i][j]=new ImageIcon(tmp); //Image로 ImageIcon 생성 
         icon2[i][j].setDescription(cnt+"");
         icon3[cnt]=new ImageIcon(tmp);
         icon3[cnt].setDescription(cnt+"");
         cnt++;         
         }
      }        
          
      icon2[4][0]=new ImageIcon("Image/"+5+"_"+1+".png");            
      tmp   = icon2[4][0].getImage();  //ImageIcon을 Image로 변환.
      tmp = tmp.getScaledInstance(80,110, java.awt.Image.SCALE_SMOOTH);
      icon2[4][0]=new ImageIcon(tmp); //Image로 ImageIcon 생성
      icon2[4][0].setDescription(52+"");
      icon3[52]=new ImageIcon(tmp);
      icon3[52].setDescription(52+"");
      
      icon2[4][1]=new ImageIcon("Image/"+5+"_"+2+".png");
      tmp   = icon2[4][1].getImage();  //ImageIcon을 Image로 변환.
      tmp = tmp.getScaledInstance(80,110, java.awt.Image.SCALE_SMOOTH);
      icon2[4][1]=new ImageIcon(tmp); //Image로 ImageIcon 생성
      icon2[4][1].setDescription(53+"");
      icon3[53]=new ImageIcon(tmp);
      icon3[53].setDescription(53+"");
   //////////////////////////////////////이중배열로 있는 카드 1개짜리 배열로 만들기
      
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