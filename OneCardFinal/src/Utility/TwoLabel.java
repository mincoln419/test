package Utility;


import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.JButton;

import Network.Client;

public class TwoLabel {
		
	public static Clip clip;
	static OneIcon oi=new OneIcon();	
	static int su;
	public ArrayList<JButton> tmplist=new ArrayList<JButton>();
	public ArrayList<JButton> sumlist=new ArrayList<JButton>();
	/////////////////////////////////////////////////////////////////////
	
	
	public TwoLabel() {
		// TODO Auto-generated method stub	
		
		 for(int i=0;i<54;i++){
	        tmplist.add(new JButton(OneIcon.icon3[i].getDescription(),OneIcon.icon[i]));
	      }
		
	      for(int i=0;i<54;i++){
	        sumlist.add(new JButton(OneIcon.icon[i].getDescription(),OneIcon.icon[i]));
	      }	
	   }	
	
	public static void sound(String file, boolean Loop) {//À½¾ÇÆÄÀÏ.
		AudioInputStream ais;
//		try {
//			ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
//		clip = AudioSystem.getClip();
//		clip.open(ais);
//		clip.start();
//		if(Loop) clip.loop(-1);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//		public static void StopSound(){//À½¾Ç¸ØÃã
//			clip.stop();
//			clip.close();
		}
		
		public static void StartSound(String file) throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException{
//			if(clip.isActive()){
//				
//			}else{
//			AudioInputStream ais; //À½¾Ç½ÇÇà
//			ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
//			clip = AudioSystem.getClip();
//			clip.open(ais);
//			clip.start();
//			}
		}


}


