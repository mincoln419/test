package Data;

import java.util.ArrayList;

import javax.swing.JButton;

import Main.PlayScreen;

public class CardReset {
	static public ArrayList<JButton> resetlist=new ArrayList<JButton>();
	public CardReset(){
		reset();
	}
	
	void reset(){
		int size=PlayScreen.openlist.size();
		int[] num=new int[size];
		
		 for(int i=0;i<num.length;i++){
	         num[i]=(int)(Math.random()*size);
	         for(int j=0;j<i;j++){
	            if(num[j]==num[i]){
	               i--;
	            }
	         }         
	      }		
		
    for(int i=0;i<PlayScreen.openlist.size();i++){
   	 resetlist.add(PlayScreen.openlist.get(num[i]));	    	 
    }
	}
	
}
