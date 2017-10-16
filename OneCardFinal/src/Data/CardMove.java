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
      if(e.getActionCommand()=="����"){         
         chatta.setText(chatta.getText()+chattf.getText()+"\r\n");
         chattf.setText("");
         
      }else if(e.getSource()==spade){
    	  Client.sendCard("45");//�����̵� 7�� �ش��ϴ� ��ȣ�� ����
    	  dl7.setVisible(false);
      }else if(e.getSource()==heart){  
    	  Client.sendCard("19");//��Ʈ 7�� �ش��ϴ� ��ȣ�� ����
    	  dl7.setVisible(false);
      }else if(e.getSource()==dia){ 
    	  Client.sendCard("32");//���̾� 7�� �ش��ϴ� ��ȣ�� ����
    	  dl7.setVisible(false);
      }else if(e.getSource()==club){ 
    	  Client.sendCard("6");//Ŭ�ι� 7�� �ش��ϴ� ��ȣ�� ����
    	  dl7.setVisible(false);
         
      }else if(e.getActionCommand().equals("y")){//�������� 
      
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
         
      }else if(e.getActionCommand().equals("x")){//��� ī�� ������ ��
    	  if(closelist.size()!=0) {
    	  TwoLabel.sound("cardopen.wav", false); //�ҿ���
    	  leftcard1++;// �� ���� ī����� �����Ѵ�
    	  Client.sendLeftCard(leftcard1+""); 
    	  Client.sendCard(70+"");///////////////////////////ī�带 ���� ���� string 70�� �ش�
            mylist.add(closelist.get(0));            
               for(int i=0;i<mylist.size();i++){//���� ī��
                  me.add(mylist.get(i));            
                   mylist.get(i).setBounds(x, 10, 70,100);    
                   mylist.get(i).addActionListener(this);                  
                   x+=20;                   
               }
               me.removeAll();
               for(int c=0;c<mylist.size();c++){  //���ġ     
                   me.add(mylist.get(c));                   
                 mylist.get(c).setBounds(location1, 10, 70,100);     
                 location1+=20;              
              }   
                 location1=40;
                 
                 String su=0+"";//closelist�� ù��° ī�� �����ҰŴ�                 
                 Client.sendReMove(su);
              
     	        validate();
                 
                 
               System.out.println("closeī��"+closelist.size());
    	  }else  if(closelist.size()==0){
            	   Client.sendCardReset();
               }
      }else{//�� ī�� ������ ��
    	  leftcard1--;//�� ���� ī�� ���� �پ���    
    	  TwoLabel.sound("cardbomb.wav", false); //�ҿ���
    	  Client.sendLeftCard(leftcard1+"");    
         for(int i=0;i<mylist.size();i++){ //�������� ����ī�� ���� �� openlist�� add                     
               if(e.getSource().equals(mylist.get(i))){  
            	  String card= mylist.get(i).getText();
            	   int su=Integer.parseInt(card);
            	if(su%13==6) {
            		setChangeShape(card);//7�� ������ ���� �����
            	}else{ //�׹ۿ��� �����  
                  Client.sendCard(card);//Ŭ���̾�Ʈ�� �� ī�� �� ������
                 System.out.println(card);//ī�尪 Ȯ�����
            	}
            	/////////////////////////� ī�带 ������ ����� ����
                me.removeAll();            
               }
               }
             for(int c=0;c<mylist.size();c++){ //���ġ     
                me.add(mylist.get(c));
              mylist.get(c).setBounds(location1, 10, 70,100);     
              location1+=20; 
           }
           
  	        validate();
              location1=40;
      }
   }
}