package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.omg.Messaging.SyncScopeHelper;

import Main.WaitingRoom;


public class Server implements Runnable{
   static ServerSocket serversocket;
   private Socket socket;
   private ServerGui gui;
   private String msg;
   public static String id;
   public static String key;
   public static Map<String, DataOutputStream> clientsMap = new  HashMap<String, DataOutputStream>();
   public static ArrayList<String> readyMember = new ArrayList<String>();   
   public static ArrayList<String> readyLight =new ArrayList<String>();
   static int cnt;
   public static int cnt2;//선잡기 카드를 클릭하면 1씩 증가
   int cycle;   
  
   
   
   @Override
	public void run() {
		// TODO Auto-generated method stub
	   Server server=new Server();
	      server.setting();
	}  
 
   public void setting(){
       int port=7777;

       try {
          Collections.synchronizedMap(clientsMap);
          serversocket = new ServerSocket(port);

          while(true){///////////////접속대기
             System.out.println("대기중");
             socket=serversocket.accept();            
             System.out.println(socket.getInetAddress()+"접속");            
             Receiver receiver = new Receiver(socket);
             receiver.start();
          }
       } catch (IOException e) {
          e.printStackTrace();
       }
    }
  
   public final void setGui(ServerGui gui){
      this.gui = gui;
     
   }
   static public void sendMsg(String msg) {
       Iterator<String> it = clientsMap .keySet().iterator();
       while(it.hasNext()){
          try {
             key=it.next();
             clientsMap.get(key).writeUTF("2"+msg);//2빼면 메시지 전달 안됨
          } catch (SocketException e2){        	 
          System.out.println("서버에러");
          } catch (IOException e) {
             e.printStackTrace();
          }
       }
    }
   
   /*Iterator<String> it = clientsMap .keySet().iterator();
    * try{

    * 
    * clientMap.get(readyMember.get(0)).writeUTF("ra");
    * 

    * 
    * clientMap.get(readyMember.get(1)).writeUTF(         );
    * 

    * 
    * clientMap.get(readyMember.get(2)).writeUTF(         );
    * 
    * 
    * 
    * clientMap.get(readyMember.get(3)).writeUTF(         );
    * 
    * }catch (SocketException e2){        	 
          System.out.println("서버에러");
          } catch (IOException e) {
             e.printStackTrace();
          }
    * 
    * 
    * 
    * 
    * 
    * */
   
   
   static public void sendRandom(String msg) {
       Iterator<String> it = clientsMap .keySet().iterator();
       while(it.hasNext()){
          try {
             key=it.next();     
             int turn=0;           
             clientsMap.get(key).writeUTF("8"+0+msg);//8빼면 메시지 전달 안됨
             //여기 시작신호를 주는 것.. +idnum버로 선을 구분시킨다 일단 idNum이 0인사람을 선으로 한다
          } catch (SocketException e2){            
              System.out.println("서버에러");
          } catch (IOException e) {
             e.printStackTrace();
          }
       }
    }

  class Receiver extends Thread{

      private DataInputStream din;
      private DataOutputStream dout;
      
      public Receiver(Socket socket){
         try {
            dout=new DataOutputStream(socket.getOutputStream());
            din =new DataInputStream(socket.getInputStream());
            id= din.readUTF();            
            addClient(id, dout);               
            sendIdList();//현재 접속중인 아이디 리스트를 보내는 메서드

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
///////////////////////////////////////////응답대기
      @Override
      public void run() {
         try {
            while(din!=null){
               msg=din.readUTF();
               char[] arr=msg.toCharArray();
               if(arr[0]=='1'){
                   msg=msg.substring(1, arr.length);
                   addClient(msg, dout);
               }else if(arr[0]=='a'){//선 카드를 열었음
            	   msg=msg.substring(1, arr.length);
            	   sendOpen(msg);           	             	   
            	   cnt2++;            
               }else if(arr[0]=='c'){//한 유저가 가운데 카드를 눌렀을 때 서버로 보내주고 각각의 클라이언트의 그 카드번호를 삭제해준다
            	   msg=msg.substring(1, arr.length);
            	   sendReMove(msg); 
               }else if(arr[0]=='d'){//카드 다 냈을 때 새로 시작하는 것
            	   sendCardReset();   
                }else if(arr[0]=='2'){
                  msg=msg.substring(1, arr.length);
                  sendMsg(msg);  
                }else if(arr[0]=='7'){ //누군가 준비실 들어가면 명단에 기록되고, 본인에게 idNumber를 보낸다. idNumber는 readyMember 리스트의 인덱스번호 
                    msg=msg.substring(1, arr.length);                	
                  	readyMember.add(msg);//ReadyMember라는 arrayList에 추가한다
                  	sendIdNum(readyMember.indexOf(msg)+"");//접속한 상대방에게만 고유 idNumber를 보낸다.- >아이디 넘버 코드는 5
                  	sendReadyRoomMember();//전체 클라이언트에게 준비실의 맴버리스트를 보낸다. -> 맴버리스트 코드는 7
                  	sendReadyLight(msg);//기존 준비된 사람들의 불빛 정보도 그 한사람에게만 보내준다
                }else if(arr[0]=='l'){  	
                  	msg=msg.substring(1,arr.length);
                  	sendReadyLight(msg);
                  	
                }else if(arr[0]=='9'){ //준비실 나가면 명단에서 삭제한다. 변경된 idNumber를 각 클라이언트에게 보내준다. 
                    msg=msg.substring(1, arr.length);                	
                    readyMember.remove(msg);                   
                    sendReadyRoomMember();
                }else  if(arr[0]=='3'){//준비됐다는 신호를 cnt를 카운트 한다. 그리고 그 아이디가 준비됐다는 사실을 각 클라이언트에 보내준다.
              	   msg=msg.substring(1, arr.length);   
              	   readyLight.add(msg);
              	   sendReady(msg);              	   
              	   for(int i=0;i<readyLight.size();i++) {
              	   }
              	  cnt++;
              	
                }else  if(arr[0]=='4'){
             	   msg=msg.substring(1, arr.length);             	   
             	   sendCancle(msg);
             	  readyLight.remove(msg);
             	  cnt--;
             	 
                }else if(arr[0]=='f'){             	   
             	   msg=msg.substring(1, arr.length);            	   
             	   sendLeftCard(msg);             
             	   

                }else  if(arr[0]=='6'){//카드의 특수기능 12(Queen)일때는 리버스/ 11(Jack)일때는 Jump// K(King)일때는 한번더//
                	msg=msg.substring(1, arr.length); 
                	int card=Integer.parseInt(msg.substring(1,arr.length-1));
                	chkCardAbil(msg, card);
                	
  ///////////////////////////////////////////////////////////////////////////////////////////////////       여기까지가 특수카드 기능       	
                                	
               }else if(arr[0]=='0'){
            	   
            	   msg=msg.substring(1, arr.length);            	   
            	   sendEnd(msg);
            	   sendIdList();            	   
               }  
            if(cnt==4){// 몇명이 준비누르면 게임 실행할지 결정 
             Thread sr=new Thread(new ServerRandom());// 랜덤숫자를 받아서 랜덤수를 보내준다
             sr.start(); 
             Thread sr2=new Thread(new ServerRandom2());// 랜덤숫자를 받아서 랜덤수를 보내준다
             sr2.start();
             cnt=0;            
  
               }//while end 
            }
         } catch (IOException e) {
            removeClient(id, dout);
         }
      }    
      


////////////////////////////////////여기부터 매소드
      
      
		private void chkCardAbil(String msg, int card) {//카드특수능력 제어하는 메소드
        	 
         	switch(cycle){
         	case 0: ///////////////////처음 순서
         		if(card%13==11){
         		reverseSendCard(msg);
         		cycle=1;
         	}else if(card%13==10){
         		jumpSendCard(msg);
         	}else if(card%13==12){
         		KingSendCard(msg);
         	}else{
         		sendCard(msg);
         	}                	
         	break;
         	/////////////////////////////////////Q를 한번 냈을 때의 순서
         	case 1:
         	if(card%13==11){
         		sendCard(msg);
         		cycle=0;
         	}else if(card%13==10){
         		reverseJumpSendCard(msg);
         	}else if(card%13==12){
         		KingSendCard(msg);
         	}else{
         		reverseSendCard(msg);
         	}                	
         	break;    	
         	
         	}
         
	}
		
		
		private void sendOpen(String msg) {
     		Iterator<String> it = clientsMap.keySet().iterator();
     	      while(it.hasNext()){
     	         try {
     	            key=it.next();
     	            clientsMap.get(key).writeUTF("a"+msg);
     	         } catch (SocketException e){        	 
     	             System.out.println("서버에러");
     	         } catch (IOException e) {
     	            e.printStackTrace();
     	         }
     	      }		
     	}
         

	private void sendLeftCard(String msg) {
		char idNum=msg.charAt(0);
		char leftcard=msg.charAt(1);
		 Iterator<String> it = clientsMap .keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("f"+idNum+leftcard);//1빼면 메시지 전달 안됨                        
	         } catch (SocketException e2){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	}
	
	private void addClient(String id, DataOutputStream dout) {
          sendMsg(id+"님 접속\r\n");
          ServerGui.serverta.append(id+"접속\r\n");
          clientsMap.put(id, dout);          
       }   

       public void removeClient(String id, DataOutputStream dout){
          sendMsg(id+"님이 나가셨습니다."+"\r\n");
          clientsMap.remove(id,dout);
       }

   	private void sendEnd(String msg) {/////////////////////////////////////////////////////클라이언트 종료 신호 매소드
		 Iterator<String> it = clientsMap .keySet().iterator();
		 
		 try {
			clientsMap.get(msg).writeUTF("0");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 removeClient(msg,dout);
		 readyMember.remove(msg);
		 ServerGui.listta.setText(""+clientsMap .keySet());
		 ServerGui.serverta.append(msg+"님이 나가셨습니다\r\n");
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("2"+msg+"님이 나가셨습니다");	            
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}

       public void sendIdList() {///////////////////////////////////////////// 접속자 리스트 보내는 매소드
    	      Iterator<String> it = clientsMap .keySet().iterator();
    	      while(it.hasNext()){
    	         try {
    	            key=it.next();
    	            clientsMap.get(key).writeUTF("1"+clientsMap.keySet());//1빼면 메시지 전달 안됨	            
    	            ServerGui.listta.setText(""+Server.clientsMap.keySet());
    	         } catch (SocketException e2){        	 
    	             System.out.println("서버에러");
    	         } catch (IOException e) {
    	            e.printStackTrace();
    	         }
    	      }
    	   }
       
       
 		private void sendIdNum(String idNum) {///////////////////////////////////////준비실에 들어갔을 때 id넘버 보내는 매소드
  			// TODO Auto-generated method stub
  			try {	            
	            clientsMap.get(id).writeUTF("5"+idNum);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }  			
  		} 
	}

  
  
  
   
	private void sendReadyRoomMember() {
		// TODO Auto-generated method stub
		Iterator<String> it = clientsMap .keySet().iterator();
		
		String readyMembers="";
		for(int i=0;i<readyMember.size();i++){			
			readyMembers=readyMembers+readyMember.get(i)+", ";
		}
		
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("7"+readyMembers);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	}
	
	private void sendReadyLight(String id) {
		// TODO Auto-generated method stub
		
		String readyl="";
		for(int i=0;i<readyLight.size();i++){			
			readyl+=readyLight.get(i)+", ";				
		}					
		try {	            
            clientsMap.get(id).writeUTF("l"+readyl);
         } catch (SocketException e){        	 
             System.out.println("서버에러");
         } catch (IOException e) {
            e.printStackTrace();
         }
	}
	
	
	
	public void sendCardReset() {
		Iterator<String> it = clientsMap .keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("d");
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}

	public void sendReMove(String msg2) {
		Iterator<String> it = clientsMap .keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("c"+msg);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}

	private void sendReady(String idnum) {/////////////////////////////////////////////특정 아이디가 준비버튼 눌렀다는  걸 알려주는 매소드
		 Iterator<String> it = clientsMap .keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("3"+idnum);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}
	
	private void sendCancle(String idnum) {/////////////////////////////////////////특정아이디가 준비해제했음을 알려주는 매소드
		 Iterator<String> it = clientsMap .keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("4"+idnum);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }		
	}
	
	
	private void sendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//문자 형식의 수가 오므로 문자'0'을 빼서 숫자값으로 바꿔준다
		char[] arr=cardNum.toCharArray();
	
		cardNum=cardNum.substring(1,arr.length);
			int nextIdNum=0;
			if(idNum==0){
				nextIdNum=1;
			}else if(idNum==1){
				nextIdNum=2;
			}else if(idNum==2){
				nextIdNum=3;
			}else if(idNum==3){
				nextIdNum=0;
			}
			System.out.println(nextIdNum);
			
			int card=Integer.parseInt(cardNum)+1;
			if(card%13==1&&card!=52&&card!=53){
       		 sendPlus2(idNum+"");
       	 }else if(card%13==0&&card!=52&&card!=53){
       		 sendPlus3(idNum+"");
       	 }else if(card==52||card==53){
       		 sendPlus5(idNum+"");
       	 }
			
	      while(it.hasNext()){
	         try {
	            key=it.next();	         
	            clientsMap.get(key).writeUTF("6"+nextIdNum+cardNum);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}
	

	private void sendPlus5(String msg) {
		// TODO Auto-generated method stub
		Iterator<String> it = clientsMap.keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("r"+msg);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }		
	}
	private void sendPlus3(String msg) {
		// TODO Auto-generated method stub
		Iterator<String> it = clientsMap.keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("q"+msg);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }		
	}
	private void sendPlus2(String msg) {
		// TODO Auto-generated method stub
		Iterator<String> it = clientsMap.keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("p"+msg);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }		
	}
	
	
	private void reverseSendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//문자 형식의 수가 오므로 문자'0'을 빼서 숫자값으로 바꿔준다
		char[] arr=cardNum.toCharArray();
		cardNum=cardNum.substring(1,arr.length);
			int nextIdNum=0;
			if(idNum==0){
				nextIdNum=3;
			}else if(idNum==1){
				nextIdNum=0;
			}else if(idNum==2){
				nextIdNum=1;
			}else if(idNum==3){
				nextIdNum=2;
			}
			
			int card=Integer.parseInt(cardNum)+1;
			if(card%13==1&&card!=52&&card!=53){
       		 sendPlus2(idNum+"");
       	 }else if(card%13==0&&card!=52&&card!=53){
       		 sendPlus3(idNum+"");
       	 }else if(card==52||card==53){
       		 sendPlus5(idNum+"");
       	 }
			
		
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("6"+nextIdNum+cardNum);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}
	private void jumpSendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//문자 형식의 수가 오므로 문자'0'을 빼서 숫자값으로 바꿔준다
		char[] arr=cardNum.toCharArray();
		cardNum=cardNum.substring(1,arr.length);
			int nextIdNum=0;
			if(idNum==0){
				nextIdNum=2;
			}else if(idNum==1){
				nextIdNum=3;
			}else if(idNum==2){
				nextIdNum=0;
			}else if(idNum==3){
				nextIdNum=1;
			}
			
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("6"+nextIdNum+cardNum);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}
	private void reverseJumpSendCard(String cardNum){
		 Iterator<String> it = clientsMap .keySet().iterator();
			int idNum=cardNum.charAt(0)-'0';//문자 형식의 수가 오므로 문자'0'을 빼서 숫자값으로 바꿔준다
			char[] arr=cardNum.toCharArray();
			cardNum=cardNum.substring(1,arr.length);
				int nextIdNum=0;
				if(idNum==0){
					nextIdNum=2;
				}else if(idNum==1){
					nextIdNum=3;
				}else if(idNum==2){
					nextIdNum=0;
				}else if(idNum==3){
					nextIdNum=1;
				}
				
		      while(it.hasNext()){
		         try {
		            key=it.next();
		            clientsMap.get(key).writeUTF("6"+nextIdNum+cardNum);
		         } catch (SocketException e){        	 
		             System.out.println("서버에러");
		         } catch (IOException e) {
		            e.printStackTrace();
		         }
		      }		
		
	}
	

	private void KingSendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//문자 형식의 수가 오므로 문자'0'을 빼서 숫자값으로 바꿔준다
			char[] arr=cardNum.toCharArray();
			cardNum=cardNum.substring(1,arr.length);		
			int nextIdNum=idNum;
			
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("6"+nextIdNum+cardNum);
	         } catch (SocketException e){        	 
	             System.out.println("서버에러");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}

	public static void sendFirstNum(String msg) {
		   Iterator<String> it = clientsMap .keySet().iterator();
	       while(it.hasNext()){
	          try {
	             key=it.next();               
	             clientsMap.get(key).writeUTF("g"+msg);//8빼면 메시지 전달 안됨
	          } catch (SocketException e2){            
	              System.out.println("서버에러");
	          } catch (IOException e) {
	             e.printStackTrace();
	          }
		
	}
	
	
	}
//
//	public static void sendTime(String msg) {
//		   Iterator<String> it = clientsMap .keySet().iterator();
//	       while(it.hasNext()){
//	          try {
//	             key=it.next();               
//	             clientsMap.get(key).writeUTF("t"+msg);//8빼면 메시지 전달 안됨
//	          } catch (SocketException e2){            
//	              System.out.println("서버에러");
//	          } catch (IOException e) {
//	             e.printStackTrace();
//	          }
//		
//	}	
//	}
	
}
