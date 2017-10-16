package Utility;

import java.awt.Image;
import java.util.TreeSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class OneLabel {

   public  JLabel deck;
   public JButton dec2;
   public OneLabel(){
      ImageIcon icon=new ImageIcon("./Image/back_up.png");
      Image icon2 = icon.getImage();  //ImageIcon을 Image로 변환.
      icon2 = icon2.getScaledInstance(70,100, java.awt.Image.SCALE_SMOOTH);
      icon=new ImageIcon(icon2); //Image로 ImageIcon 생성   
      deck=new JLabel(icon);
      
      ImageIcon icon3=new ImageIcon("./Image/back_up.png");
      Image icon4 = icon3.getImage();  //ImageIcon을 Image로 변환.
      icon4 = icon4.getScaledInstance(60,75, java.awt.Image.SCALE_SMOOTH);
      icon3=new ImageIcon(icon4); //Image로 ImageIcon 생성   
      dec2=new JButton("y",icon3);
      
      
   }
   
}