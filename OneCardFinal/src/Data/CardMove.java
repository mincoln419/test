package Data;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;

import Main.PlayScreen;
import Main.WaitingRoom;
import Network.Client;
import Utility.Random2;
import Utility.TwoLabel;

public class CardMove extends PlayScreen{
      
   public CardMove(String idname) {
      super(idname);      
      // TODO Auto-generated constructor stub
   }

	private void setChangeShape(String card) {		
		
		dl7.setVisible(true);
		dl7.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	            // TODO Auto-generated method stub      
	        	Client.sendCard(card);
	        	dl7.dispose();
	         }
	      });
			
	
}
   
   @Override
      public void actionPerformed(ActionEvent e) {
            
      chatta.setText(chatta.getText()+chattf.getText()+"\r\n");
      chattf.setText("");
      if(e.getActionCommand()=="전송"){         
         chatta.setText(chatta.getText()+chattf.getText()+"\r\n");
         chattf.setText("");
         
      }else if(e.getSource()==spade){
    	  Client.sendCard("45");//스페이드 7에 해당하는 번호를 전송
    	  dl7.setVisible(false);
      }else if(e.getSource()==heart){  
    	  Client.sendCard("19");//하트 7에 해당하는 번호를 전송
    	  dl7.setVisible(false);
      }else if(e.getSource()==dia){ 
    	  Client.sendCard("32");//다이아 7에 해당하는 번호를 전송
    	  dl7.setVisible(false);
      }else if(e.getSource()==club){ 
    	  Client.sendCard("6");//클로버 7에 해당하는 번호를 전송
    	  dl7.setVisible(false);
         
      }else if(e.getActionCommand().equals("y")){//선잡을때 
      
    	  if(e.getSource()==cho.get(0).dec2){   
    		  
              num=0+"";
              Client.sendOpen(num);           
             
                 
           }else if(e.getSource()==cho.get(1).dec2){ 
      
          	 num=1+"";
               Client.sendOpen(num);
              
           }else if(e.getSource()==cho.get(2).dec2){
          	 
                           
          	 num=2+"";
               Client.sendOpen(num);
              
           }else if(e.getSource()==cho.get(3).dec2){
          	 
          	num=3+"";
              Client.sendOpen(num);
              
           }
         
      }else if(e.getActionCommand().equals("x")){//가운데 카드 눌렀을 때
    	  if(closelist.size()!=0) {
    	  TwoLabel.sound("cardopen.wav", false); //소오리
    	  leftcard1++;// 내 남은 카드수가 증가한다
    	  Client.sendLeftCard(leftcard1+""); 
    	  Client.sendCard(70+"");///////////////////////////카드를 먹을 때는 string 70을 준다
            mylist.add(closelist.get(0));            
               for(int i=0;i<mylist.size();i++){//나의 카드
                  me.add(mylist.get(i));            
                   mylist.get(i).setBounds(x, 10, 70,100);    
                   mylist.get(i).addActionListener(this);                  
                   x+=20;                   
               }
               me.removeAll();
               for(int c=0;c<mylist.size();c++){  //재배치     
                   me.add(mylist.get(c));                   
                 mylist.get(c).setBounds(location1, 10, 70,100);     
                 location1+=20;              
              }   
                 location1=40;
                 
                 String su=0+"";//closelist의 첫번째 카드 삭제할거다                 
                 Client.sendReMove(su);
              
     	        validate();
                 
                 
               System.out.println("close카드"+closelist.size());
    	  }else  if(closelist.size()==0){
            	   Client.sendCardReset();
               }
      }else{//내 카드 눌렀을 때
    	  leftcard1--;//내 남은 카드 수가 줄어든다    
    	  TwoLabel.sound("cardbomb.wav", false); //소오리
    	  Client.sendLeftCard(leftcard1+"");    
         for(int i=0;i<mylist.size();i++){ //눌렀을때 나의카드 삭제 및 openlist로 add                     
               if(e.getSource().equals(mylist.get(i))){  
            	  String card= mylist.get(i).getText();
            	   int su=Integer.parseInt(card);
            	if(su%13==6) {
            		setChangeShape(card);//7을 눌렀을 때는 여기로
            	}else{ //그밖에는 여기로  
                  Client.sendCard(card);//클라이언트로 내 카드 값 보내기
                 System.out.println(card);//카드값 확인출력
            	}
            	/////////////////////////어떤 카드를 누르던 여기는 고정
                me.removeAll();            
               }
               }
             for(int c=0;c<mylist.size();c++){ //재배치     
                me.add(mylist.get(c));
              mylist.get(c).setBounds(location1, 10, 70,100);     
              location1+=20; 
           }
           
  	        validate();
              location1=40;
      }
   }
}