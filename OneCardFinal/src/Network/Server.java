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
   public static int cnt2;//����� ī�带 Ŭ���ϸ� 1�� ����
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

          while(true){///////////////���Ӵ��
             System.out.println("�����");
             socket=serversocket.accept();            
             System.out.println(socket.getInetAddress()+"����");            
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
             clientsMap.get(key).writeUTF("2"+msg);//2���� �޽��� ���� �ȵ�
          } catch (SocketException e2){        	 
          System.out.println("��������");
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
          System.out.println("��������");
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
             clientsMap.get(key).writeUTF("8"+0+msg);//8���� �޽��� ���� �ȵ�
             //���� ���۽�ȣ�� �ִ� ��.. +idnum���� ���� ���н�Ų�� �ϴ� idNum�� 0�λ���� ������ �Ѵ�
          } catch (SocketException e2){            
              System.out.println("��������");
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
            sendIdList();//���� �������� ���̵� ����Ʈ�� ������ �޼���

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
///////////////////////////////////////////������
      @Override
      public void run() {
         try {
            while(din!=null){
               msg=din.readUTF();
               char[] arr=msg.toCharArray();
               if(arr[0]=='1'){
                   msg=msg.substring(1, arr.length);
                   addClient(msg, dout);
               }else if(arr[0]=='a'){//�� ī�带 ������
            	   msg=msg.substring(1, arr.length);
            	   sendOpen(msg);           	             	   
            	   cnt2++;            
               }else if(arr[0]=='c'){//�� ������ ��� ī�带 ������ �� ������ �����ְ� ������ Ŭ���̾�Ʈ�� �� ī���ȣ�� �������ش�
            	   msg=msg.substring(1, arr.length);
            	   sendReMove(msg); 
               }else if(arr[0]=='d'){//ī�� �� ���� �� ���� �����ϴ� ��
            	   sendCardReset();   
                }else if(arr[0]=='2'){
                  msg=msg.substring(1, arr.length);
                  sendMsg(msg);  
                }else if(arr[0]=='7'){ //������ �غ�� ���� ��ܿ� ��ϵǰ�, ���ο��� idNumber�� ������. idNumber�� readyMember ����Ʈ�� �ε�����ȣ 
                    msg=msg.substring(1, arr.length);                	
                  	readyMember.add(msg);//ReadyMember��� arrayList�� �߰��Ѵ�
                  	sendIdNum(readyMember.indexOf(msg)+"");//������ ���濡�Ը� ���� idNumber�� ������.- >���̵� �ѹ� �ڵ�� 5
                  	sendReadyRoomMember();//��ü Ŭ���̾�Ʈ���� �غ���� �ɹ�����Ʈ�� ������. -> �ɹ�����Ʈ �ڵ�� 7
                  	sendReadyLight(msg);//���� �غ�� ������� �Һ� ������ �� �ѻ�����Ը� �����ش�
                }else if(arr[0]=='l'){  	
                  	msg=msg.substring(1,arr.length);
                  	sendReadyLight(msg);
                  	
                }else if(arr[0]=='9'){ //�غ�� ������ ��ܿ��� �����Ѵ�. ����� idNumber�� �� Ŭ���̾�Ʈ���� �����ش�. 
                    msg=msg.substring(1, arr.length);                	
                    readyMember.remove(msg);                   
                    sendReadyRoomMember();
                }else  if(arr[0]=='3'){//�غ�ƴٴ� ��ȣ�� cnt�� ī��Ʈ �Ѵ�. �׸��� �� ���̵� �غ�ƴٴ� ����� �� Ŭ���̾�Ʈ�� �����ش�.
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
             	   

                }else  if(arr[0]=='6'){//ī���� Ư����� 12(Queen)�϶��� ������/ 11(Jack)�϶��� Jump// K(King)�϶��� �ѹ���//
                	msg=msg.substring(1, arr.length); 
                	int card=Integer.parseInt(msg.substring(1,arr.length-1));
                	chkCardAbil(msg, card);
                	
  ///////////////////////////////////////////////////////////////////////////////////////////////////       ��������� Ư��ī�� ���       	
                                	
               }else if(arr[0]=='0'){
            	   
            	   msg=msg.substring(1, arr.length);            	   
            	   sendEnd(msg);
            	   sendIdList();            	   
               }  
            if(cnt==4){// ����� �غ񴩸��� ���� �������� ���� 
             Thread sr=new Thread(new ServerRandom());// �������ڸ� �޾Ƽ� �������� �����ش�
             sr.start(); 
             Thread sr2=new Thread(new ServerRandom2());// �������ڸ� �޾Ƽ� �������� �����ش�
             sr2.start();
             cnt=0;            
  
               }//while end 
            }
         } catch (IOException e) {
            removeClient(id, dout);
         }
      }    
      


////////////////////////////////////������� �żҵ�
      
      
		private void chkCardAbil(String msg, int card) {//ī��Ư���ɷ� �����ϴ� �޼ҵ�
        	 
         	switch(cycle){
         	case 0: ///////////////////ó�� ����
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
         	/////////////////////////////////////Q�� �ѹ� ���� ���� ����
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
     	             System.out.println("��������");
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
	            clientsMap.get(key).writeUTF("f"+idNum+leftcard);//1���� �޽��� ���� �ȵ�                        
	         } catch (SocketException e2){        	 
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	}
	
	private void addClient(String id, DataOutputStream dout) {
          sendMsg(id+"�� ����\r\n");
          ServerGui.serverta.append(id+"����\r\n");
          clientsMap.put(id, dout);          
       }   

       public void removeClient(String id, DataOutputStream dout){
          sendMsg(id+"���� �����̽��ϴ�."+"\r\n");
          clientsMap.remove(id,dout);
       }

   	private void sendEnd(String msg) {/////////////////////////////////////////////////////Ŭ���̾�Ʈ ���� ��ȣ �żҵ�
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
		 ServerGui.serverta.append(msg+"���� �����̽��ϴ�\r\n");
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("2"+msg+"���� �����̽��ϴ�");	            
	         } catch (SocketException e){        	 
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}

       public void sendIdList() {///////////////////////////////////////////// ������ ����Ʈ ������ �żҵ�
    	      Iterator<String> it = clientsMap .keySet().iterator();
    	      while(it.hasNext()){
    	         try {
    	            key=it.next();
    	            clientsMap.get(key).writeUTF("1"+clientsMap.keySet());//1���� �޽��� ���� �ȵ�	            
    	            ServerGui.listta.setText(""+Server.clientsMap.keySet());
    	         } catch (SocketException e2){        	 
    	             System.out.println("��������");
    	         } catch (IOException e) {
    	            e.printStackTrace();
    	         }
    	      }
    	   }
       
       
 		private void sendIdNum(String idNum) {///////////////////////////////////////�غ�ǿ� ���� �� id�ѹ� ������ �żҵ�
  			// TODO Auto-generated method stub
  			try {	            
	            clientsMap.get(id).writeUTF("5"+idNum);
	         } catch (SocketException e){        	 
	             System.out.println("��������");
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
	             System.out.println("��������");
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
             System.out.println("��������");
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
	             System.out.println("��������");
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
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}

	private void sendReady(String idnum) {/////////////////////////////////////////////Ư�� ���̵� �غ��ư �����ٴ�  �� �˷��ִ� �żҵ�
		 Iterator<String> it = clientsMap .keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("3"+idnum);
	         } catch (SocketException e){        	 
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}
	
	private void sendCancle(String idnum) {/////////////////////////////////////////Ư�����̵� �غ����������� �˷��ִ� �żҵ�
		 Iterator<String> it = clientsMap .keySet().iterator();
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("4"+idnum);
	         } catch (SocketException e){        	 
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }		
	}
	
	
	private void sendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//���� ������ ���� ���Ƿ� ����'0'�� ���� ���ڰ����� �ٲ��ش�
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
	             System.out.println("��������");
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
	             System.out.println("��������");
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
	             System.out.println("��������");
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
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }		
	}
	
	
	private void reverseSendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//���� ������ ���� ���Ƿ� ����'0'�� ���� ���ڰ����� �ٲ��ش�
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
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}
	private void jumpSendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//���� ������ ���� ���Ƿ� ����'0'�� ���� ���ڰ����� �ٲ��ش�
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
	             System.out.println("��������");
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
		
	}
	private void reverseJumpSendCard(String cardNum){
		 Iterator<String> it = clientsMap .keySet().iterator();
			int idNum=cardNum.charAt(0)-'0';//���� ������ ���� ���Ƿ� ����'0'�� ���� ���ڰ����� �ٲ��ش�
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
		             System.out.println("��������");
		         } catch (IOException e) {
		            e.printStackTrace();
		         }
		      }		
		
	}
	

	private void KingSendCard(String cardNum) {
		 Iterator<String> it = clientsMap .keySet().iterator();
		int idNum=cardNum.charAt(0)-'0';//���� ������ ���� ���Ƿ� ����'0'�� ���� ���ڰ����� �ٲ��ش�
			char[] arr=cardNum.toCharArray();
			cardNum=cardNum.substring(1,arr.length);		
			int nextIdNum=idNum;
			
	      while(it.hasNext()){
	         try {
	            key=it.next();
	            clientsMap.get(key).writeUTF("6"+nextIdNum+cardNum);
	         } catch (SocketException e){        	 
	             System.out.println("��������");
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
	             clientsMap.get(key).writeUTF("g"+msg);//8���� �޽��� ���� �ȵ�
	          } catch (SocketException e2){            
	              System.out.println("��������");
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
//	             clientsMap.get(key).writeUTF("t"+msg);//8���� �޽��� ���� �ȵ�
//	          } catch (SocketException e2){            
//	              System.out.println("��������");
//	          } catch (IOException e) {
//	             e.printStackTrace();
//	          }
//		
//	}	
//	}
	
}
