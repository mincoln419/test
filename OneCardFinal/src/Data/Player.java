package Data;

import java.util.ArrayList;

import Main.PlayScreen;
import Network.Client;

public class Player implements Runnable {	
	
	int turn;
	int su;
	public ArrayList<Integer> arr;
	public Player(int turn){
		this.turn=turn;
	}
	
	@Override
	public void run() {
	
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
			PlayScreen.timer.setText("����");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		}
/////////////////////////////////////////////////////////////////////	
		
		
//		for(int i=0;i< PlayScreen.mylist.size();i++){//���� ī��
//	    	  PlayScreen.mylist.get(i).setEnabled(true);//my card �����̰�
//	      }		
//		
			
			PlayScreen.ol.deck.setEnabled(true);
			su=0;
			  arr=new ArrayList<Integer>();
		      int opennum=Integer.parseInt(PlayScreen.openlist.get(0).getText());   
		      System.out.println("opennum��?"+opennum);
		      for(int i=0;i<PlayScreen.mylist.size();i++){
		    	  int mynum=Integer.parseInt(PlayScreen.mylist.get(i).getText());
		    	  arr.add(mynum);		    	  
		      System.out.println("mynum��?"+mynum);
		      Client.ps.validate();
		      su=(opennum)/13;
		      
		      switch(su) {
		      
		      case 0://Ŭ�ι��϶�
		    	  
		    	  if(mynum<13&&mynum==52||mynum==53) {
		    		  PlayScreen.mylist.get(i).setEnabled(true);
		    	  }else{
		    		  PlayScreen.mylist.get(i).setEnabled(false);
		    	  }
		    	  Client.ps.validate();
		    	  
		    	  break;
		    	  
		      case 1://��Ʈ�϶�
		    	  
		    	  if(mynum>12&&mynum<26||mynum==53||mynum==54) {
		    		  PlayScreen.mylist.get(i).setEnabled(true);
		    	  }else{
		    		  PlayScreen.mylist.get(i).setEnabled(false);
		    	  }		
		    	  Client.ps.validate();
		    	  break;
		    	  
		      case 2://���̾��϶�
		    	  if(mynum>25&&mynum<39||mynum==53||mynum==54) {
		    		  PlayScreen.mylist.get(i).setEnabled(true);
		    	  }else{
		    		  PlayScreen.mylist.get(i).setEnabled(false);
		    	  }	
		    	  Client.ps.validate();
		    	  break;
		    	  
		      case 3://�����̵��϶�
		    	  if(mynum>38&&mynum<52||mynum==53||mynum==54) {
		    		  PlayScreen.mylist.get(i).setEnabled(true);
		    	  }else{
		    		  PlayScreen.mylist.get(i).setEnabled(false);
		    	  }
		    	  Client.ps.validate();
		    	  break;
		      }//switch ��
		      //////////////////////////////////////////////////////////		 
		      }//����˻� ��
		      
		      for(int i=0;i<arr.size();i++) {//���ڰ˻�		    	  
		    	  if(su==(arr.get(i))%13) {
		    		  PlayScreen.mylist.get(i).setEnabled(true);
		    	  }
		    	  int mynum=Integer.parseInt(PlayScreen.mylist.get(i).getText());
		    	  if(mynum==53||mynum==54) {
		    		  PlayScreen.mylist.get(i).setEnabled(true);
		    	  }	  		  
		   
		      }//���ڰ˻� ��
		      
		      
		      if(opennum==53||opennum==54) {//���µ� ī�尡 ��Ŀ�� ���� �ƹ��ų� ���� ����
		    	  for(int i=0;i<arr.size();i++) {
		    		  PlayScreen.mylist.get(i).setEnabled(true);
		    	  }
		      }     
		      
		      
		      }
	}

