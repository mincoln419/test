package Data;

import javax.swing.JButton;

import Main.PlayScreen;
import Network.Client;
import Utility.OneIcon;
import Utility.TwoLabel;

public class OpenCardSetting implements Runnable{
	int card;	
	
	
	public OpenCardSetting(int card){
	this.card=card;	
		
	}
	
	
	@Override
	public void run() {
		System.out.println("더하기전 오픈리스트 인덱스 0은?"+PlayScreen.openlist.get(0).getText());			
			JButton open=new JButton(card+"",OneIcon.icon3[card]);
			PlayScreen.openlist.add(0,open);
	        PlayScreen.openlist.get(0).setEnabled(false);	
	        PlayScreen.openlist.get(0).setSize(70,100);	        	        
			PlayScreen.openPanel.add(PlayScreen.openlist.get(0));
	        System.out.println("더한 후에 오픈리스트 값은?"+PlayScreen.openlist.get(0).getText());	      
					
	}	
}
