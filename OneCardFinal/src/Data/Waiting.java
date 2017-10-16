package Data;

import Main.PlayScreen;

public class Waiting implements Runnable{
	int turn;
	public Waiting(int turn){
		this.turn=turn;
	}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(turn==0){
			 
	        PlayScreen.openlist.get(0).setVisible(true);
	        PlayScreen.openlist.get(0).setEnabled(true);
	        PlayScreen.openlist.get(0).setBounds(10, 10, 70,100); 
	        PlayScreen.openPanel.add(PlayScreen.openlist.get(0)); 
	        PlayScreen.closelist.remove(0); 
			
			try {
				Thread.sleep(1000);
				PlayScreen.timer.setText("5");
				Thread.sleep(1000);
				PlayScreen.timer.setText("4");
				Thread.sleep(1000);
				PlayScreen.timer.setText("3");
				Thread.sleep(1000);
				PlayScreen.timer.setText("2");
				Thread.sleep(1000);
				PlayScreen.choice.dispose();
				PlayScreen.timer.setText("시작");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
	
	}
			
		
		for(int i=0;i< PlayScreen.mylist.size();i++){//나의 카드
	    	  PlayScreen.mylist.get(i).setEnabled(false);//my card 움직이게
	      }				
			PlayScreen.ol.deck.setEnabled(false);
		
	}
}
