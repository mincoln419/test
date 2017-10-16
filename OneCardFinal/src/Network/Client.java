package Network;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JPanel;

import Data.BattleRecord;
import Data.CardMove;
import Data.CardReset;
import Data.LeftCard;
import Data.OpenCardSetting;
import Data.Player;
import Data.Waiting;
import Main.PlayMode;
import Main.PlayScreen;
import Main.ReadyRoom;
import Main.WaitingRoom;
import Utility.Random;
import Utility.Random2;
import Utility.TwoLabel;

public class Client implements Runnable{
	public static Socket socket;
	public static DataInputStream din;
	public static DataOutputStream dout;	
	public String msg;
	public String msg2;
	public static String getNum; 
	private static String id;
	public Client client;	
	public static ArrayList <String> member=new ArrayList<String>();//id�� �޾Ƽ� ����
	public static ArrayList <String> readyMember=new ArrayList<String>();//������ ���̵�
	ArrayList <JPanel> readylight=new ArrayList<JPanel>();//������ ���̵�
	public static Map<Integer, Integer> tmpMap=new HashMap<Integer, Integer>();
	
	/////////////////////////////////////////////////////////////////////
	
	
	
	public static int[] num=new int[54];
	public static int[] num2=new int[4];
	static int cnt;//���ο� ���̵� �����ϸ� cnt�� �ϳ��� �þ��. 
	static int suncnt=0;
	static ReadyRoom rr;
	public static PlayScreen ps;
	public static Thread timer;
	public static Thread plays;
	public static Thread waits;
	static Thread thr3;
	public static int idNum;
	static int turn=0;	
	int rcnt;
	static int ccnt;
	
	
	public Client(String idname){		
		id=idname;			
	}	
	
	@Override
	public void run() {	
		String ip="127.0.0.1";
		int port=7777;
		try {
			socket= new Socket(ip, port);
			
			System.out.println("��������");			
			dout=new DataOutputStream(socket.getOutputStream());
			din =new DataInputStream(socket.getInputStream());
			  dout.writeUTF(id);
			  member.add(id);

			while(true){				
				  msg=din.readUTF();
				  
		            char[] arr=msg.toCharArray();
		            if(arr[0]=='2'){//�޽��� ���� ��
		               msg=msg.substring(1, arr.length);
		               WaitingRoom.chatt.append(msg);		               					
		            }else if(arr[0]=='1'){//���̵� ����Ʈ ���� �� - ����(WatingRoom)�� member�� ����Ѵ�
		               msg=msg.substring(2, arr.length-1);
		               setList(msg);
//				}else if(arr[0]=='-') {/// �ߺ����̵� �˻�   	
//		            	PlayMode.chk=false;
//		            	sendEnd();		            	
//		            
			
		            }else if(arr[0]=='3'){// �ٸ� ���̵𿡼� �غ�ƴٴ� ��ȣ
		            	msg=msg.substring(1, arr.length);
			            getReady(msg);	
			            
		            }else if(arr[0]=='4'){// �ٸ� ���̵𿡼� �غ� �����ߴٴ� ��ȣ
		            	msg=msg.substring(1, arr.length);
			            getCancle(msg);		
			            
		            }else if(arr[0]=='5'){    //�غ�ǿ� �����ϸ� ���� id �ѹ��� �������� �������ش�
			            msg=msg.substring(1, arr.length);
			            idNum=Integer.parseInt(msg);//idnum int��
			           
			    		
		            }else if(arr[0]=='6'){    //�� Ȥ�� �ٸ� ������ ī�带 �� �� (������ 6+idNum+nextidNum+ī�������ȣ)         	   
		            	msg=msg.substring(1, arr.length);
			            getCard(msg);
			          
		            }else if(arr[0]=='f'){    //���� ī��� ������  	   
		            	msg=msg.substring(1, arr.length);
			            getLeftCard(msg);  
		            }else if(arr[0]=='g') {
			        	msg=msg.substring(1, arr.length);
			            getFirstNum(msg); 
			            

		            }else if(arr[0]=='p'){    //2ī��� ����Ա�
		            	msg=msg.substring(1, arr.length);
		            	if(msg.equals(idNum)){
		            	getCard2();
		            	}
		            }else if(arr[0]=='q'){    //Aī��� ����Ա�
		            	msg=msg.substring(1, arr.length);
		            	if(msg.equals(idNum)){
		            	getCard3();
		            	}
		            }else if(arr[0]=='r'){    //��Ŀ ī��� �ټ���Ա�
		            	msg=msg.substring(1, arr.length);
		            	if(msg.equals(idNum)){
		            	getCard5();
		            	}
			            
		            }else if(arr[0]=='7'){    //�غ�ǿ� �������� �����ų� �����鼭 ���Ӹ���� ������ ����� �غ�ǿ� �ִ� ����Ʈ�� �ݿ��ȴ�.		            	
		            	msg=msg.substring(1, arr.length);		            	
		            	settingPlayer(msg);		            	
		            	    	
		            	
		            }else if(arr[0]=='l'){ //���� �غ���¸� ������ �ڽ��� �غ���¸� �������ش�		            	
		            	msg=msg.substring(1, arr.length);		            	
		            	getReady(msg);  		            	
			            		         
		            }else if(arr[0]=='8'){////��ΰ� �غ�ƴٴ� ��ȣ 3���Ŀ� �����ϰ� �����-������ �޾ƿ��� ���� ����		            	
		            	cnt=3;
		            	ReadyRoom.rchatt.append(cnt+"�ʵڿ� �����մϴ�");
		            	
		            	try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
		            	while(cnt>0){	
		            	try {
		            		ReadyRoom.rchatt.append("\r\n"+cnt);
							Thread.sleep(1000);							
							cnt--;		            		
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            	}
		            	msg=msg.substring(1, arr.length);
			            getRandom(msg);	            
			           
		            }else if(arr[0]=='a') {   	
		            	msg=msg.substring(1, arr.length);
		            	getOpen(msg);//������ ����� ��ư�� ������ �� �������� ������ �޽���
		            }else if(arr[0]=='b'){
		            	msg=msg.substring(1, arr.length);
		            	if(msg.charAt(0)=='s'){
		            		getFirst();
		            	}else{
		            		getAnother();          			            	
		            	}
		            }else if(arr[0]=='c'){
	                    msg=msg.substring(1, arr.length);
	                    getReMove(msg);
				        }else if(arr[0]=='d'){//openī�带 ��� closeī��� �ٽ� �־��ش�
				        getCardReset();
			        }else if(arr[0]=='0'||din==null){break;
			        
			        }
		            
		            }//while end
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			try {
				din.close();
				dout.close();
				System.out.println("Ŭ���̾�Ʈ����");					
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
	}
//////////////////////////////////////������� Ŭ���̾�Ʈ �޼ҵ�////////////////////////////////////
	



	private void getCard5() {
		// TODO Auto-generated method stub
		for(int j=0;j<5;j++){
			PlayScreen.leftcard1++;// �� ���� ī����� �����Ѵ�		
	  	     	  
	          PlayScreen.mylist.add(PlayScreen.closelist.get(0));            
	             for(int i=0;i<PlayScreen.mylist.size();i++){//���� ī��
	                PlayScreen.me.add(PlayScreen.mylist.get(i));            
	                 PlayScreen.mylist.get(i).setBounds(PlayScreen.x, 10, 70,100);    
	                 PlayScreen.mylist.get(i).addActionListener(ps);
	                 PlayScreen.mylist.get(i).setEnabled(true);  
	                 PlayScreen.x+=20;
	             }
	             for(int c=0;c<PlayScreen.mylist.size();c++){  //���ġ     
	                 PlayScreen.me.add(PlayScreen.mylist.get(c));     
	                 PlayScreen.mylist.get(c).setEnabled(true);
	                 PlayScreen.mylist.get(c).setBounds(PlayScreen.location1, 10, 70,100);     
	                 PlayScreen.location1+=20;              
	            }            
	             PlayScreen.location1=40;
	             sendLeftCard(PlayScreen.leftcard1+"");
	             System.out.println("��Ŀ��5��Կ���");
	               String su=0+"";//closelist�� ù��° ī�� �����ҰŴ�                 
	               Client.sendReMove(su);
	             System.out.println("closeī��"+PlayScreen.closelist.size());
			}        
	}

	private void getCard3() {
		// TODO Auto-generated method stub
		for(int j=0;j<3;j++){
			PlayScreen.leftcard1++;// �� ���� ī����� �����Ѵ�
			System.out.println("A�μ���Կ���");	  	      	  
	          PlayScreen.mylist.add(PlayScreen.closelist.get(0));            
	             for(int i=0;i<PlayScreen.mylist.size();i++){//���� ī��
	                PlayScreen.me.add(PlayScreen.mylist.get(i));            
	                 PlayScreen.mylist.get(i).setBounds(PlayScreen.x, 10, 70,100);    
	                 PlayScreen.mylist.get(i).addActionListener(ps);
	                 PlayScreen.mylist.get(i).setEnabled(true);  
	                 PlayScreen.x+=20;
	             }
	             for(int c=0;c<PlayScreen.mylist.size();c++){  //���ġ     
	                 PlayScreen.me.add(PlayScreen.mylist.get(c));     
	                 PlayScreen.mylist.get(c).setEnabled(true);
	                 PlayScreen.mylist.get(c).setBounds(PlayScreen.location1, 10, 70,100);     
	                 PlayScreen.location1+=20;              
	            }            
	             PlayScreen.location1=40;
	             Client.sendLeftCard(PlayScreen.leftcard1+"");       
	               String su=0+"";//closelist�� ù��° ī�� �����ҰŴ�                 
	               Client.sendReMove(su);
	             System.out.println("closeī��"+PlayScreen.closelist.size());
			}        
	}

	private void getCard2() {
		// TODO Auto-generated method stub
		for(int j=0;j<2;j++){
		PlayScreen.leftcard1++;// �� ���� ī����� �����Ѵ�		
  	  
  	  System.out.println("2�ε���Կ���");
          PlayScreen.mylist.add(PlayScreen.closelist.get(0));            
             for(int i=0;i<PlayScreen.mylist.size();i++){//���� ī��
                PlayScreen.me.add(PlayScreen.mylist.get(i));            
                 PlayScreen.mylist.get(i).setBounds(PlayScreen.x, 10, 70,100);    
                 PlayScreen.mylist.get(i).addActionListener(ps);
                 PlayScreen.mylist.get(i).setEnabled(true);  
                 PlayScreen.x+=20;
             }
             for(int c=0;c<PlayScreen.mylist.size();c++){  //���ġ     
                 PlayScreen.me.add(PlayScreen.mylist.get(c));     
                 PlayScreen.mylist.get(c).setEnabled(true);
                 PlayScreen.mylist.get(c).setBounds(PlayScreen.location1, 10, 70,100);     
                 PlayScreen.location1+=20;              
            }            
             PlayScreen.location1=40;
             sendLeftCard(PlayScreen.leftcard1+"");
               String su=0+"";//closelist�� ù��° ī�� �����ҰŴ�                 
               Client.sendReMove(su);
             System.out.println("closeī��"+PlayScreen.closelist.size());
		}        
	}

	private void getCardReset() {
		 CardReset cr=new CardReset();
         for(int i=0;i<PlayScreen.openlist.size()-1;i++){                 
        	 PlayScreen.main.remove(PlayScreen.openlist.get(i));
        	 PlayScreen.openlist.get(i).setEnabled(true);                 
            //���¸���Ʈ ������ش�       
        	 PlayScreen.closelist.add(CardReset.resetlist.get(i));                  
         }

         int size=PlayScreen.openlist.size();                            
         for(int i=0;i<size-1;i++){                  
        	 PlayScreen.openlist.remove(0);      
         }   
		
	}

	private void getReMove(String msg3) {
		int su=msg.charAt(1)-'0';
		//int id=msg.charAt(0)-'0';
		
		PlayScreen.closelist.remove(su);
		
		System.out.println("Ŭ���̾�Ʈ closelist�� ������?"+PlayScreen.closelist.size());
	}
		
	

	public void getFirstNum(String num) {
		Thread r2=new Thread(new Random2(num));
		Random2.getNum=num;
		r2.start();
	}

	public void settingPlayer(String msg) {		
		
		 StringTokenizer st=new StringTokenizer(msg,", ") {
	      };
	      readyMember.removeAll(readyMember);
	      readylight.removeAll(readylight);	      
	      while(st.hasMoreTokens()){
	    	  String tmp=st.nextToken();	    	
	    	  readyMember.add(tmp);//�غ�ǿ� �ִ� ��ܿ� �ϳ��� �߰��Ѵ�.  
	      	  
	      }	      
	      ReadyRoom.tf1.setText(readyMember.get(0));
	      readylight.add(ReadyRoom.p1);
		   if(readyMember.size()>1){
			   ReadyRoom.tf2.setText(readyMember.get(1));
			   readylight.add(ReadyRoom.p2);
		   }
		   if(readyMember.size()>2) {
			   ReadyRoom.tf3.setText(readyMember.get(2));
			   readylight.add(ReadyRoom.p3);
		   }
		   if(readyMember.size()>3) { 
			   ReadyRoom.tf4.setText(readyMember.get(3));
			   readylight.add(ReadyRoom.p4);
		   }
		   rr.setVisible(true);	  
		   
	}
	
	
	
	
	static public void setList(String msg){
		ArrayList <String> tmp=new ArrayList<String>();//id�� �޾Ƽ� ����
	      StringTokenizer st=new StringTokenizer(msg,", ") {
	      };      
	      while(st.hasMoreTokens()){
	         tmp.add(st.nextToken());	          
	      }
	      
	      WaitingRoom.member.setText("�����ڸ��\r\n");
	        WaitingRoom.member.append("--------------------------------------------\r\n");
	        for(int i=0;i<tmp.size();i++){
	            WaitingRoom.member.append(tmp.get(i)+"\r\n");
	            }
	        member.removeAll(member);
	        for(int i=0;i<tmp.size();i++){
	        member.add(tmp.get(i));
	        }	        
	}
	
	static public void sendMsg(String msg2) {
		try {
			dout.writeUTF("2"+id+":"+msg2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getRandom(String num){
		Thread r=new Thread(new Random(num));
		Random.getNum=num;
		r.start(); 	
		rr.dispose();
		ps=new CardMove(id);
		
		PlayScreen.choice=new Dialog(ps, "�� ���ϱ�");
		Thread thr4=new Thread(ps);//������ �ݺ���Ű�� ���� ������

		PlayScreen.choice.setVisible(true);
        thr4.start();
      //���ī�� closelist�� add
        
        if(PlayScreen.leftcard1==0){  //////////PlayScreen�� ������ ������ ���⼭ ����    
            String wl="�¸�"; 
//            TwoLabel.StopSound();
            TwoLabel.sound("win.wav", false);
            Thread thr3=new Thread(new BattleRecord(id, ps.player1, ps.player2, ps.player3,  wl));
            thr3.start();         
            Client.sendBackWait();   
            PlayScreen.x=0;
         }else if(PlayScreen.ccnt1.getText().equals("0")||PlayScreen.ccnt2.getText().equals("0")||PlayScreen.ccnt3.getText().equals("0")){            
            String wl="�й�";
//            TwoLabel.StopSound();
            TwoLabel.sound("lose.wav", false);
            Thread thr3=new Thread(new BattleRecord(id, ps.player1, ps.player2, ps.player3,  wl));
            thr3.start();
            PlayScreen.x=0;
            Client.sendBackWait();   
         }   
	}

	public static void sendEnd() {
		// TODO Auto-generated method stub
		try {
			dout.writeUTF("0"+id);			
			if(ps!=null){
			ps.dispose();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getReady(String msg) {	
		
		//���� msg ������ 0 1 2 3 ó�� �غ�� ����� idNum�� ���������� �� String
		
		int su=0;
		StringTokenizer ltst=new StringTokenizer(msg,", ");
		
		if(rcnt==0) {
		ReadyRoom.p1.setBackground(Color.LIGHT_GRAY);
		ReadyRoom.p2.setBackground(Color.LIGHT_GRAY);
		ReadyRoom.p3.setBackground(Color.LIGHT_GRAY);
		ReadyRoom.p4.setBackground(Color.LIGHT_GRAY);
		rcnt++;
		}
		while(ltst.hasMoreTokens()) {
			su=Integer.parseInt(ltst.nextToken());
		switch(su) {
		case 0:			
			ReadyRoom.p1.setBackground(Color.GREEN);
		break;
		case 1:
			ReadyRoom.p2.setBackground(Color.GREEN);
		break;
		case 2:
			ReadyRoom.p3.setBackground(Color.GREEN);
		break;
		case 3:
			ReadyRoom.p4.setBackground(Color.GREEN);
		break;			
		}
		}		
	}	
	
	public static void getCancle(String msg) {		
		int su=Integer.parseInt(msg);
		switch(su) {
		case 0:			
		ReadyRoom.p1.setBackground(Color.LIGHT_GRAY);
		break;
		case 1:
		ReadyRoom.p2.setBackground(Color.LIGHT_GRAY);
		break;
		case 2:
		ReadyRoom.p3.setBackground(Color.LIGHT_GRAY);
		break;
		case 3:
		ReadyRoom.p4.setBackground(Color.LIGHT_GRAY);
		break;			
		}
		
	}		
		
	
	public static void sendReady() {		
		try {
//			System.out.println("Ŭ���̾�Ʈ���� sendReady�� �����ִ� idNum"+idNum);//����� �� ������
			dout.writeUTF("3"+idNum);			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}	
	
	public static void sendCancle() {
		try {
			dout.writeUTF("4"+idNum);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}	
	
	public static void sendEnterReady() {// �غ�ǿ� �� �� 7�� �ش�	
		
			 rr=new ReadyRoom(id);
		  		Thread thr3=new Thread(rr,"�����");
		  		thr3.start();					
		try {
			dout.writeUTF("7"+id);			
    		dout.writeUTF("l"+id);//�����ϸ� ���⸦ ��ġ�Ƿ� ������ ���� ���� �����ڵ��� �غ���¸� �޾ƺ��� ���� ��ȣ�� ����		        			
    	
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	
	public static void sendBackWait() {
		// TODO Auto-generated method stub	
		Thread thr1=new Thread(new WaitingRoom(id),"����");//���Ƿ� �̵�
		thr1.start();
		ps.dispose();
		 WaitingRoom.member.setText("\t         �����ڸ��\r\n");
	        WaitingRoom.member.append("----------------------------------\r\n");
	        for(int i=0;i<member.size();i++){
	        WaitingRoom.member.append(member.get(i)+"\r\n");
	        }	
	}	
	
public static void getOpen(String msg){
		
		int su=msg.charAt(1)-'0';
		int tmpIdNum=msg.charAt(0)-'0';
				
		/////////////////////////////////////////////��������
		for(int i=0;i<4;i++){			
		}
		if(su==0){		//���Ƿ� 0�϶��� ���̶�� �����Ѵ�	
			  PlayScreen.choicep.remove(PlayScreen.cho.get(0).dec2);                  
			  PlayScreen.choicep.add(PlayScreen.choed.get(num2[0]));   
			  PlayScreen.choed.get(num2[0]).setBounds(30+60*0, 10,50,60);
			  tmpMap.put(num2[0], tmpIdNum);
			  suncnt++;			  
		}if(su==1){
			  PlayScreen.choicep.remove(PlayScreen.cho.get(1).dec2);                  
			  PlayScreen.choicep.add(PlayScreen.choed.get(num2[1]));   
			  PlayScreen.choed.get(num2[1]).setBounds(30+60*1, 10,50,60);
			  tmpMap.put(num2[1], tmpIdNum);
			  suncnt++;
		}if(su==2){
			  PlayScreen.choicep.remove(PlayScreen.cho.get(2).dec2);                  
			  PlayScreen.choicep.add(PlayScreen.choed.get(num2[2]));   
			  PlayScreen.choed.get(num2[2]).setBounds(30+60*2, 10,50,60);
			  tmpMap.put(num2[2], tmpIdNum);
			  suncnt++;
		}if(su==3){
			  PlayScreen.choicep.remove(PlayScreen.cho.get(3).dec2);                  
			  PlayScreen.choicep.add(PlayScreen.choed.get(num2[3]));   
			  PlayScreen.choed.get(num2[3]).setBounds(30+60*3, 10,50,60);
			  tmpMap.put(num2[3], tmpIdNum);
			  suncnt++;
		}   
		
		if(suncnt==4){
			if(tmpMap.get(0)==idNum){
				getFirst();
			}else{
				getAnother();
			}
			
		}
	
	}

		      

	
	public static void sendOpen(String msg) {
		
		try {
			dout.writeUTF("a"+idNum+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	
	public static void getFirst() {
		// TODO Auto-generated method stub
		PlayScreen.timer.setText("5�ʵڿ� �����մϴ�");		
			
		plays=new Thread(new Player(turn),"�÷���");//���ӽ���		
		plays.start();
		
		try {
		 	Thread thr=Thread.currentThread();
			thr.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ps.validate();
		turn++;
	}	
	
	public static void getAnother(){
		PlayScreen.timer.setText("5�ʵڿ� �����մϴ�");	
		try {
			dout.writeUTF("t");
		} catch (IOException e) {
			e.printStackTrace();
		}
					    		
      
        waits=new Thread(new Waiting(turn),"������");
        waits.start();
       
        try {
		 	Thread thr=Thread.currentThread();
			thr.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ps.validate();
        System.out.println("�� �ƴ�");			
		turn++;	
	}
	
	
	public static void sendCard(String cardNum) {

		try {			
			dout.writeUTF("6"+idNum+cardNum);
			Thread wait=new Thread(new Waiting(turn));
			wait.start();			
			turn++;
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}	
	
	public static synchronized void getCard(String cardNum) {
		
		char[] arr=cardNum.toCharArray();
		PlayScreen.timer.setText(readyMember.get(cardNum.charAt(0)-'0')+"�� ����");
		int openCard=Integer.parseInt(cardNum.substring(1,arr.length));
		
		
		if(openCard!=70) {//ī�带 ������ ������ ��ȣ 70// ���� ī�带 �����ٸ� ��� �ڱ� ī�尡 ���¸���Ʈ�� ���� �ȴ�	
		PlayScreen.openPanel.removeAll();
		Thread ocs=new Thread(new OpenCardSetting(openCard));
		ocs.start();		
		}
		try {
		 	Thread thr=Thread.currentThread();
			thr.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        ps.validate();
		if(cardNum.charAt(0)-'0'==idNum){//���� �޽����� nextId�� ���� idNum�� ���� ���!
		
				Thread thr=new Thread(new Player(turn));
				thr.start();				
			turn++;
			System.out.println("ī�崩���� Ŭ���̾�Ʈ���� �����ϳ�?");		
			 try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        ps.validate();
		}
	}

	public static void sendLeftCard(String leftcard) {
		
		try {
			dout.writeUTF("f"+idNum+leftcard);			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		}

	
	private void getLeftCard(String leftcard) {	
		int cardId=leftcard.charAt(0)-'0';
		int cs=leftcard.charAt(1)-'0';
		Thread left=new Thread(new LeftCard(cardId, cs),"����ī�����üũ");
		left.start();
		
	}

	public static void sendReMove(String su) {
		try {
			dout.writeUTF("c"+idNum+su);			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	public static void sendCardReset() {
		try {
			dout.writeUTF("d");			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
