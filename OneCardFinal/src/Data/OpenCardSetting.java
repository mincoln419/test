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
		System.out.println("���ϱ��� ���¸���Ʈ �ε��� 0��?"+PlayScreen.openlist.get(0).getText());			
			JButton open=new JButton(card+"",OneIcon.icon3[card]);
			PlayScreen.openlist.add(0,open);
	        PlayScreen.openlist.get(0).setEnabled(false);	
	        PlayScreen.openlist.get(0).setSize(70,100);	        	        
			PlayScreen.openPanel.add(PlayScreen.openlist.get(0));
	        System.out.println("���� �Ŀ� ���¸���Ʈ ����?"+PlayScreen.openlist.get(0).getText());	      
					
	}	
}
