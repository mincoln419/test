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
	public static ArrayList <String> member=new ArrayList<String>();//id를 받아서 저장
	public static ArrayList <String> readyMember=new ArrayList<String>();//레디한 아이디
	ArrayList <JPanel> readylight=new ArrayList<JPanel>();//레디한 아이디
	public static Map<Integer, Integer> tmpMap=new HashMap<Integer, Integer>();
	
	/////////////////////////////////////////////////////////////////////
	
	
	
	public static int[] num=new int[54];
	public static int[] num2=new int[4];
	static int cnt;//새로운 아이디가 접속하면 cnt가 하나씩 늘어난다. 
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
			
			System.out.println("서버연결");			
			dout=new DataOutputStream(socket.getOutputStream());
			din =new DataInputStream(socket.getInputStream());
			  dout.writeUTF(id);
			  member.add(id);

			while(true){				
				  msg=din.readUTF();
				  
		            char[] arr=msg.toCharArray();
		            if(arr[0]=='2'){//메시지 받을 때
		               msg=msg.substring(1, arr.length);
		               WaitingRoom.chatt.append(msg);		               					
		            }else if(arr[0]=='1'){//아이디 리스트 받을 때 - 대기실(WatingRoom)의 member에 출력한다
		               msg=msg.substring(2, arr.length-1);
		               setList(msg);
//				}else if(arr[0]=='-') {/// 중복아이디 검사   	
//		            	PlayMode.chk=false;
//		            	sendEnd();		            	
//		            
			
		            }else if(arr[0]=='3'){// 다른 아이디에서 준비됐다는 신호
		            	msg=msg.substring(1, arr.length);
			            getReady(msg);	
			            
		            }else if(arr[0]=='4'){// 다른 아이디에서 준비를 해제했다는 신호
		            	msg=msg.substring(1, arr.length);
			            getCancle(msg);		
			            
		            }else if(arr[0]=='5'){    //준비실에 입장하면 고유 id 넘버를 서버에서 전달해준다
			            msg=msg.substring(1, arr.length);
			            idNum=Integer.parseInt(msg);//idnum int값
			           
			    		
		            }else if(arr[0]=='6'){    //나 혹은 다른 유저가 카드를 낼 때 (형식은 6+idNum+nextidNum+카드고유번호)         	   
		            	msg=msg.substring(1, arr.length);
			            getCard(msg);
			          
		            }else if(arr[0]=='f'){    //남은 카드수 보내기  	   
		            	msg=msg.substring(1, arr.length);
			            getLeftCard(msg);  
		            }else if(arr[0]=='g') {
			        	msg=msg.substring(1, arr.length);
			            getFirstNum(msg); 
			            

		            }else if(arr[0]=='p'){    //2카드로 두장먹기
		            	msg=msg.substring(1, arr.length);
		            	if(msg.equals(idNum)){
		            	getCard2();
		            	}
		            }else if(arr[0]=='q'){    //A카드로 세장먹기
		            	msg=msg.substring(1, arr.length);
		            	if(msg.equals(idNum)){
		            	getCard3();
		            	}
		            }else if(arr[0]=='r'){    //조커 카드로 다섯장먹기
		            	msg=msg.substring(1, arr.length);
		            	if(msg.equals(idNum)){
		            	getCard5();
		            	}
			            
		            }else if(arr[0]=='7'){    //준비실에 누군가가 들어오거나 나가면서 게임멤버에 변경이 생기면 준비실에 있는 리스트에 반영된다.		            	
		            	msg=msg.substring(1, arr.length);		            	
		            	settingPlayer(msg);		            	
		            	    	
		            	
		            }else if(arr[0]=='l'){ //기존 준비상태를 포함해 자신의 준비상태를 갱신해준다		            	
		            	msg=msg.substring(1, arr.length);		            	
		            	getReady(msg);  		            	
			            		         
		            }else if(arr[0]=='8'){////모두가 준비됐다는 신호 3초후에 시작하게 만든다-랜덤수 받아오고 게임 시작		            	
		            	cnt=3;
		            	ReadyRoom.rchatt.append(cnt+"초뒤에 시작합니다");
		            	
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
		            	getOpen(msg);//누군가 선잡기 버튼을 눌렀을 때 서버에서 보내는 메시지
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
				        }else if(arr[0]=='d'){//open카드를 섞어서 close카드로 다시 넣어준다
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
				System.out.println("클라이언트종료");					
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
	}
//////////////////////////////////////여기부터 클라이언트 메소드////////////////////////////////////
	



	private void getCard5() {
		// TODO Auto-generated method stub
		for(int j=0;j<5;j++){
			PlayScreen.leftcard1++;// 내 남은 카드수가 증가한다		
	  	     	  
	          PlayScreen.mylist.add(PlayScreen.closelist.get(0));            
	             for(int i=0;i<PlayScreen.mylist.size();i++){//나의 카드
	                PlayScreen.me.add(PlayScreen.mylist.get(i));            
	                 PlayScreen.mylist.get(i).setBounds(PlayScreen.x, 10, 70,100);    
	                 PlayScreen.mylist.get(i).addActionListener(ps);
	                 PlayScreen.mylist.get(i).setEnabled(true);  
	                 PlayScreen.x+=20;
	             }
	             for(int c=0;c<PlayScreen.mylist.size();c++){  //재배치     
	                 PlayScreen.me.add(PlayScreen.mylist.get(c));     
	                 PlayScreen.mylist.get(c).setEnabled(true);
	                 PlayScreen.mylist.get(c).setBounds(PlayScreen.location1, 10, 70,100);     
	                 PlayScreen.location1+=20;              
	            }            
	             PlayScreen.location1=40;
	             sendLeftCard(PlayScreen.leftcard1+"");
	             System.out.println("조커로5장먹였다");
	               String su=0+"";//closelist의 첫번째 카드 삭제할거다                 
	               Client.sendReMove(su);
	             System.out.println("close카드"+PlayScreen.closelist.size());
			}        
	}

	private void getCard3() {
		// TODO Auto-generated method stub
		for(int j=0;j<3;j++){
			PlayScreen.leftcard1++;// 내 남은 카드수가 증가한다
			System.out.println("A로세장먹였다");	  	      	  
	          PlayScreen.mylist.add(PlayScreen.closelist.get(0));            
	             for(int i=0;i<PlayScreen.mylist.size();i++){//나의 카드
	                PlayScreen.me.add(PlayScreen.mylist.get(i));            
	                 PlayScreen.mylist.get(i).setBounds(PlayScreen.x, 10, 70,100);    
	                 PlayScreen.mylist.get(i).addActionListener(ps);
	                 PlayScreen.mylist.get(i).setEnabled(true);  
	                 PlayScreen.x+=20;
	             }
	             for(int c=0;c<PlayScreen.mylist.size();c++){  //재배치     
	                 PlayScreen.me.add(PlayScreen.mylist.get(c));     
	                 PlayScreen.mylist.get(c).setEnabled(true);
	                 PlayScreen.mylist.get(c).setBounds(PlayScreen.location1, 10, 70,100);     
	                 PlayScreen.location1+=20;              
	            }            
	             PlayScreen.location1=40;
	             Client.sendLeftCard(PlayScreen.leftcard1+"");       
	               String su=0+"";//closelist의 첫번째 카드 삭제할거다                 
	               Client.sendReMove(su);
	             System.out.println("close카드"+PlayScreen.closelist.size());
			}        
	}

	private void getCard2() {
		// TODO Auto-generated method stub
		for(int j=0;j<2;j++){
		PlayScreen.leftcard1++;// 내 남은 카드수가 증가한다		
  	  
  	  System.out.println("2로두장먹였다");
          PlayScreen.mylist.add(PlayScreen.closelist.get(0));            
             for(int i=0;i<PlayScreen.mylist.size();i++){//나의 카드
                PlayScreen.me.add(PlayScreen.mylist.get(i));            
                 PlayScreen.mylist.get(i).setBounds(PlayScreen.x, 10, 70,100);    
                 PlayScreen.mylist.get(i).addActionListener(ps);
                 PlayScreen.mylist.get(i).setEnabled(true);  
                 PlayScreen.x+=20;
             }
             for(int c=0;c<PlayScreen.mylist.size();c++){  //재배치     
                 PlayScreen.me.add(PlayScreen.mylist.get(c));     
                 PlayScreen.mylist.get(c).setEnabled(true);
                 PlayScreen.mylist.get(c).setBounds(PlayScreen.location1, 10, 70,100);     
                 PlayScreen.location1+=20;              
            }            
             PlayScreen.location1=40;
             sendLeftCard(PlayScreen.leftcard1+"");
               String su=0+"";//closelist의 첫번째 카드 삭제할거다                 
               Client.sendReMove(su);
             System.out.println("close카드"+PlayScreen.closelist.size());
		}        
	}

	private void getCardReset() {
		 CardReset cr=new CardReset();
         for(int i=0;i<PlayScreen.openlist.size()-1;i++){                 
        	 PlayScreen.main.remove(PlayScreen.openlist.get(i));
        	 PlayScreen.openlist.get(i).setEnabled(true);                 
            //오픈리스트 섞어어준다       
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
		
		System.out.println("클라이언트 closelist의 갯수는?"+PlayScreen.closelist.size());
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
	    	  readyMember.add(tmp);//준비실에 있는 명단에 하나씩 추가한다.  
	      	  
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
		ArrayList <String> tmp=new ArrayList<String>();//id를 받아서 저장
	      StringTokenizer st=new StringTokenizer(msg,", ") {
	      };      
	      while(st.hasMoreTokens()){
	         tmp.add(st.nextToken());	          
	      }
	      
	      WaitingRoom.member.setText("접속자명단\r\n");
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
		
		PlayScreen.choice=new Dialog(ps, "선 정하기");
		Thread thr4=new Thread(ps);//게임을 반복시키기 위한 스레드

		PlayScreen.choice.setVisible(true);
        thr4.start();
      //가운데카드 closelist에 add
        
        if(PlayScreen.leftcard1==0){  //////////PlayScreen이 끝나는 조건을 여기서 설정    
            String wl="승리"; 
//            TwoLabel.StopSound();
            TwoLabel.sound("win.wav", false);
            Thread thr3=new Thread(new BattleRecord(id, ps.player1, ps.player2, ps.player3,  wl));
            thr3.start();         
            Client.sendBackWait();   
            PlayScreen.x=0;
         }else if(PlayScreen.ccnt1.getText().equals("0")||PlayScreen.ccnt2.getText().equals("0")||PlayScreen.ccnt3.getText().equals("0")){            
            String wl="패배";
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
		
		//받은 msg 형식은 0 1 2 3 처럼 준비된 사람의 idNum를 오름차순로 한 String
		
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
//			System.out.println("클라이언트에서 sendReady로 보내주는 idNum"+idNum);//여기는 잘 보내짐
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
	
	public static void sendEnterReady() {// 준비실에 들어갈 때 7로 준다	
		
			 rr=new ReadyRoom(id);
		  		Thread thr3=new Thread(rr,"레디룸");
		  		thr3.start();					
		try {
			dout.writeUTF("7"+id);			
    		dout.writeUTF("l"+id);//접속하면 여기를 거치므로 접속한 다음 기존 접속자들의 준비상태를 받아보기 위해 신호를 보냄		        			
    	
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	
	public static void sendBackWait() {
		// TODO Auto-generated method stub	
		Thread thr1=new Thread(new WaitingRoom(id),"대기실");//대기실로 이동
		thr1.start();
		ps.dispose();
		 WaitingRoom.member.setText("\t         접속자명단\r\n");
	        WaitingRoom.member.append("----------------------------------\r\n");
	        for(int i=0;i<member.size();i++){
	        WaitingRoom.member.append(member.get(i)+"\r\n");
	        }	
	}	
	
public static void getOpen(String msg){
		
		int su=msg.charAt(1)-'0';
		int tmpIdNum=msg.charAt(0)-'0';
				
		/////////////////////////////////////////////수정수정
		for(int i=0;i<4;i++){			
		}
		if(su==0){		//임의로 0일때를 선이라고 가정한다	
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
		PlayScreen.timer.setText("5초뒤에 시작합니다");		
			
		plays=new Thread(new Player(turn),"플레이");//게임시작		
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
		PlayScreen.timer.setText("5초뒤에 시작합니다");	
		try {
			dout.writeUTF("t");
		} catch (IOException e) {
			e.printStackTrace();
		}
					    		
      
        waits=new Thread(new Waiting(turn),"웨이팅");
        waits.start();
       
        try {
		 	Thread thr=Thread.currentThread();
			thr.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ps.validate();
        System.out.println("선 아님");			
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
		PlayScreen.timer.setText(readyMember.get(cardNum.charAt(0)-'0')+"님 차례");
		int openCard=Integer.parseInt(cardNum.substring(1,arr.length));
		
		
		if(openCard!=70) {//카드를 먹으면 보내는 신호 70// 따라서 카드를 눌렀다면 모두 자기 카드가 오픈리스트로 가게 된다	
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
		if(cardNum.charAt(0)-'0'==idNum){//받은 메시지의 nextId가 나의 idNum과 같을 경우!
		
				Thread thr=new Thread(new Player(turn));
				thr.start();				
			turn++;
			System.out.println("카드누르면 클라이언트에서 반응하나?");		
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
		Thread left=new Thread(new LeftCard(cardId, cs),"남은카드장수체크");
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
