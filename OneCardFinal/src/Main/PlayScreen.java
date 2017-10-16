package Main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.GrayFilter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Data.BattleRecord;

import Network.Client;
import Utility.OneColor;
import Utility.OneIcon;
import Utility.OneLabel;
import Utility.OneLabel2;
import Utility.OneMenu;
import Utility.Random2;
import Utility.TwoLabel;


public class PlayScreen extends Frame implements Runnable, ActionListener{
   
   GridBagLayout gb=new GridBagLayout();
   public static GridBagConstraints gbc=new GridBagConstraints();
   
   protected Thread thr1;//30�� Ÿ�̸� ������ - �� ���ེ������ �����ڸ��� ����
   protected Thread thr2;//�� ���� ������ - ������ �������� �������(0->1->2->3 �ݺ�)
   
   public static String[] idnames=new String[4];
   public ArrayList<Object> players=new ArrayList<Object>(); 
   public static Dialog choice;  
   public static Dialog dl7;
   public String player1;
   public String player2;
   public String player3;
   public String ownPlayer;
   public static int leftcard1=7;   
   static int clcnt;//�������� �ѹ��� �ϱ� ���� ��ġ
   public static OneLabel2 ol;
   protected String idname;
   public JTextArea chatta;
   protected TextField chattf;
   OneColor oc;
   public static Panel me;
   Panel play1;
   Panel play2;
   Panel play3;   
   Panel chat;
   public static Panel main;
   Panel center;
   static public Panel dlp;
   static public Panel openPanel =new Panel();
    
   public static TextField timer=new TextField("���ѽð�:30 ��");    ;
   int ran;
   TwoLabel tl;
   int time;
   int turn;
   public String num;
   
   public static ArrayList<JButton> mylist=new ArrayList<JButton>();  //���� �迭
   public static ArrayList<JButton> openlist=new ArrayList<JButton>();  //���µ� ī�� �迭
   public static ArrayList<JButton> closelist=new ArrayList<JButton>(); //������ ī�� �迭      
   public JButton spade,heart,dia,club;
   public static ArrayList <OneLabel> cho=new ArrayList<OneLabel>();
   public static ArrayList <JLabel> choed=new ArrayList<JLabel>();//����� ī�� �������� ��
   public static Panel choicep = new Panel(null);
   public JLabel first;   
   public JLabel fail1;         
   public JLabel fail2;         
   public JLabel fail3;         
   public static JTextField ccnt1 = new JTextField();
   public static JTextField ccnt2 = new JTextField();
   public static JTextField ccnt3 = new JTextField();
   public static JTextField ccnt4 = new JTextField(); //�� �� ī�����ǥ���ϴ� �ڽ� ���� ;
   public static JTextField nname1; //�̸� �ڽ�.
   public static JTextField nname2;
   public static JTextField nname3;
   public static JTextField nname4;
   
   
   private int cnt3;   
   public static int x=40;//ī����ǥ��
   public static int location1=40;//ī����ǥ��
   protected static int cnt=1;
   protected static int cnt2=0;
   public PlayScreen(String idname){
	   super(idname+"���� ��� ��ī�� �÷���");  
	   this.idname=idname;
      
   }
   @Override
   public void run(){
      ownPlayer=idname;     
	  if(ownPlayer.equals(Client.readyMember.get(0))){
		  player1=Client.readyMember.get(1);
		  player2=Client.readyMember.get(2);
		  player3=Client.readyMember.get(3);
	  }else if(ownPlayer.equals(Client.readyMember.get(1))){
		  player1=Client.readyMember.get(0);
		  player2=Client.readyMember.get(1);
		  player3=Client.readyMember.get(2);
	  }else if(ownPlayer.equals(Client.readyMember.get(2))){
		  player1=Client.readyMember.get(0);
		  player2=Client.readyMember.get(1);
		  player3=Client.readyMember.get(3);
	  }else if(ownPlayer.equals(Client.readyMember.get(3))){
		  player1=Client.readyMember.get(0);
		  player2=Client.readyMember.get(1);
		  player3=Client.readyMember.get(2);
	  }
      
//	  player1="player1";
//	  player2="player2";
//	  player3="player3";
	  
	  
      
      time=30;///Ÿ�̸� - ���� �гο� ����ִ´�   
      
      Dimension res=Toolkit.getDefaultToolkit().getScreenSize();

      main=new Panel(null); 
      main.setBackground(Color.LIGHT_GRAY);
      oc=new OneColor();      
      play1=new Panel(null);
      play1.setBackground(oc.player1);      
      play2=new Panel(null);
      play2.setBackground(oc.player2);
      play3=new Panel(null);
      play3.setBackground(oc.player3);
      me=new Panel(null);
      me.setBackground(oc.player4);
      Button oneCard=new Button("��ī��");
      oneCard.addActionListener(this);      
      gbc.fill=GridBagConstraints.BOTH;
      
      
      center=new Panel(null);
      center.setBounds(190,180,150,150);
      center.setBackground(oc.player1);
      main.add(center);
      
 TwoLabel.sound("cardbackground.wav",true); //�������
      
      OneLabel ol1=new OneLabel();//�׸��� ���� �� ����.
      nname1 = new JTextField();// �г����� ���� �� ����.
      ccnt1 = new JTextField(); // ī�� �����ִ��� ī��Ʈ �� ����.
      play1.setBounds(190,0,150,150);
      nname1.setBounds(30,0,100,20);
      ccnt1.setBounds(25,50,30,30);
      ol1.deck.setBounds(60,40,70,100);
      play1.add(nname1); //��ġ
      play1.add(ccnt1); //��ġ
       play1.add(ol1.deck); //��ġ
      ccnt1.setColumns(2);
      ccnt1.setText("7��");
      ccnt1.setFocusable(false);
      nname1.setFocusable(false);
      nname1.setColumns(10);      
      play1.add(ol1.deck);
      main.add(play1);
      
      OneLabel ol2=new OneLabel();
      nname2 = new JTextField();
      ccnt2 = new JTextField();
      play2.setBounds(0,180,150,150);
      nname2.setBounds(30,0,100,20);
      ccnt2.setBounds(25,50,30,30);
      ol2.deck.setBounds(60,40,70,100);
      play2.add(nname2);
      play2.add(ccnt2);
       play2.add(ol2.deck);
      ccnt2.setColumns(2);
      ccnt2.setText("7��");
      ccnt2.setFocusable(false);
      nname2.setFocusable(false);
      nname2.setColumns(10);
      play2.add(ol2.deck);
      main.add(play2);
      
      
      openPanel.setVisible(true); 
      openPanel.setBackground(Color.WHITE);
      openPanel.setBounds(0,0,150,150); 
      main.add(openPanel);
      
      OneLabel ol3=new OneLabel();
      nname3 = new JTextField();
      ccnt3 = new JTextField();
      play3.setBounds(380,180,150,150);
      nname3.setBounds(30,0,100,20);
      ccnt3.setBounds(25,50,30,30);
      ol3.deck.setBounds(60,40,70,100);
      play3.add(nname3);
      play3.add(ccnt3);
       play3.add(ol3.deck);
      ccnt3.setColumns(2);
      ccnt3.setText("7��");
      ccnt3.setFocusable(false);
      nname3.setFocusable(false);
      nname3.setColumns(10);
      play3.add(ol3.deck);
      main.add(play3);
      
      switch(Client.idNum) {	///////////////////�� �гο� ���̵� �����ϴ� �ڵ�
      case 0:
			nname1.setText(Client.readyMember.get(1));
			nname2.setText(Client.readyMember.get(2));
			nname3.setText(Client.readyMember.get(3));
			
			break;
		case 1:
			nname1.setText(Client.readyMember.get(0));
			nname2.setText(Client.readyMember.get(2));
			nname3.setText(Client.readyMember.get(3));
			break;
		case 2:
			nname1.setText(Client.readyMember.get(0));
			nname2.setText(Client.readyMember.get(1));
			nname3.setText(Client.readyMember.get(3));
			break;
		case 3:
			nname1.setText(Client.readyMember.get(0));
			nname2.setText(Client.readyMember.get(1));
			nname3.setText(Client.readyMember.get(2));
			break;
			
		default:
			break;
		}	

      
      
      Panel timerP = new Panel(null);//Ÿ�̸�  
      timer=new TextField("���ѽð�:"+time);     
      timer.setFocusable(false);
      timer.setBounds(0,0,150,150);
//      timer.setBackground(Color.magenta);
      timerP.add(timer);
//      timerP.setBackground(Color.magenta);
      timerP.setBounds(190,330,150,50);
      timerP.setVisible(true);
      timer.setVisible(true);
      main.add(timerP);
      //////////////////////   
      
      
      me.setLayout(null);
      me.setBounds(0,380,550,170);
      
          
      play1.add(ccnt1);
      ccnt1.setColumns(2);
      ccnt1.setFocusable(false);      
      play2.add(ccnt2);
      ccnt2.setColumns(2);
      ccnt2.setFocusable(false);
    
      play3.add(ccnt3);
      ccnt3.setColumns(2);
      ccnt3.setFocusable(false);
      
      
      
      tl=new TwoLabel();      
        for(int i=0;i<7;i++){
            mylist.add(tl.sumlist.get(i));
            mylist.get(i).setEnabled(false);//my card ���ǵ�� 
         }
    
         for(int i=0;i<mylist.size();i++){//���� ī��
            me.add(mylist.get(i));
             mylist.get(i).setBounds(x, 10, 70,100);
             mylist.get(i).addActionListener(this);
             x+=20;
         }
     
      me.add(oneCard);
      oneCard.setBounds(430, 10, 70,50);
     ccnt4.setBounds(430, 70, 70,50);
     me.add(oneCard);
     me.add(ccnt4);    
        main.add(me);
      //ccnt4.setColumns(10);
      ccnt4.setFocusable(false);
      main.add(me);
      ccnt4.setText("7��");
      
      
     ////////////////// 
      
      Panel chat=new Panel(gb);
      chatta=new JTextArea(25,35);
      chatta.setEditable(false);
      chatta=WaitingRoom.chatt;
	  chatta.setText("--------------------�����߿� �弳 ����� �ﰡ�սô� ---------------------");
            
      chattf=new TextField(25);
      chattf.addActionListener(this);
      chattf=WaitingRoom.chat;
            
      JButton send=new JButton("����");
      send.addActionListener(this);
      setMenuBar(new OneMenu(idname));
      addToFrame(0,0,2,1,1.0,15.0); ///////////////////////////////////////
      chat.add(chatta,gbc);
      gbc.fill=GridBagConstraints.BASELINE;
      addToFrame(0,1,1,1,1.0,1.0);
      chat.add(chattf,gbc);      
      addToFrame(1,1,1,1,1.0,1.0);
      chat.add(send,gbc);

      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub            
            String wl="�й�";
//            TwoLabel.StopSound();
            TwoLabel.sound("lose.wav", false);
            Thread thr3=new Thread(new BattleRecord(idname, player1, player2, player3,  wl));
            thr3.start();
            Client.sendBackWait();
            x=40;            
            mylist.removeAll(mylist); 
            openlist.removeAll(openlist);
            closelist.removeAll(closelist);            
         }
      });
      
      addToFrame(1,1,1,1,1.0,1.0);         
      // ��� ī��     
     ol=new OneLabel2();
     center.add(ol.deck);
     ol.deck.setBounds(40, 20, 70,100);
     ol.deck.setVisible(true);         
     ol.deck.addActionListener(this);
     ol.deck.setEnabled(false);
     
     //closelist���� ������ ī�� ���¸���Ʈ�� add
    
     for(int i=28;i<tl.sumlist.size();i++){
         closelist.add(tl.sumlist.get(i));
         }  
     openlist.add(PlayScreen.closelist.get(0));   
       
         
      ////////////////////////////////����7ī�带 ���� �� �ߴ� ���̾�α�
     
      dlp=new Panel();
      
      club=new JButton(OneIcon.shape[0]);   
      club.addActionListener(this);// ������ �׼Ǹ�����      
      dlp.add(club);
      heart=new JButton(OneIcon.shape[1]);
      heart.addActionListener(this);
      dlp.add(heart);
      spade=new JButton(OneIcon.shape[2]);
      spade.addActionListener(this);
      dlp.add(spade);
      dia=new JButton(OneIcon.shape[3]);
      dia.addActionListener(this);
      dlp.add(dia);
     
       
      
      dl7=new Dialog(this,"�����ϱ�");
      dl7.add(PlayScreen.dlp);
	  dl7.setBounds(600,400,300,120);	
	  dl7.setVisible(false);
      
       ///////////////////////////////�� ���ϱ� ���� ���̾�α�
         
      int location99=30;
      for(int i=0;i<4;i++){
      cho.add(new OneLabel());      
      choicep.add(cho.get(i).dec2);
      cho.get(i).dec2.setBounds(location99+60*i, 10,50,60);
      cho.get(i).dec2.addActionListener(this);      
      }            
      first=new JLabel("���Դϴ�");      
      fail1=new JLabel("���Դϴ�");                  
      fail2=new JLabel("���Դϴ�");                  
      fail3=new JLabel("���Դϴ�");                  
      choed.add(first);
      choed.add(fail1);
      choed.add(fail2);
      choed.add(fail3);
      choice.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub      
               choice.setVisible(false);
         }
      });
      choice.add(choicep);
      choice.setBounds(res.width/5+res.width/6,res.height/3,300,120);
      choice.setVisible(true);//�ϴ� �ٷ� �����ϰ� �������.
      
      //////////////////////////////////////////
      
      add(main,BorderLayout.CENTER);
      add(chat,BorderLayout.EAST);
      setBounds(res.width/2-res.width/3,res.height/2-res.height/3,1000,600);
      setResizable(false);
      setVisible(true);
         
   }

   
    public void addToFrame(int x,int y, int width, int height, double weightx, double weighty) {
      gbc.gridx=x;
      gbc.gridy=y;
      gbc.gridwidth=width;
      gbc.gridheight=height;
      gbc.weightx=weightx;
      gbc.weighty=weighty;
      
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      
}

   
}