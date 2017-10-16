package Utility;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class OneLabel2 {
	public  JButton deck;
	public OneLabel2(){
		ImageIcon icon=new ImageIcon("./Image/back_up.png");
		Image icon2 = icon.getImage();  //ImageIcon을 Image로 변환.
		icon2 = icon2.getScaledInstance(70,100, java.awt.Image.SCALE_SMOOTH);
		icon=new ImageIcon(icon2); //Image로 ImageIcon 생성	
		deck=new JButton("x",icon);
		
	}
}
