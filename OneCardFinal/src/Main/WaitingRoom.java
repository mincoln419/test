package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Network.Client;
import Utility.OneMenu;

public class WaitingRoom extends Frame implements Runnable,ActionListener{

   public static String msg;
   String idname;
   private JPanel mainp;
   public static TextField chat;
   public static JTextArea member;
   public static JTextArea chatt;
   JButton ready;
   JButton cancle;
   static Thread thr2;
   Thread thr3;
   Client client;

   public WaitingRoom(String idname) {
      super(idname+"님 접속중     대기실 - 어썸 원카드 ver 1.0.0");
      Dimension res=Toolkit.getDefaultToolkit().getScreenSize();

      this.idname=idname;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 630, 600);
      mainp = new JPanel();
      mainp.setBackground(new Color(153, 204, 255));
      mainp.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(mainp);
      mainp.setLayout(null);

      chat = new TextField(60);
      chat.setBounds(12, 500, 340, 30);
      chat.setBackground(new Color(224, 255, 255));
      mainp.add(chat);
      chat.setColumns(50);

      JButton send = new JButton("전송");
      send.setBounds(354, 499, 68, 31);
      mainp.add(send);

      ready = new JButton("입         장");
      ready.setBounds(434, 451, 178, 39);
      mainp.add(ready);

      cancle = new JButton("방 만들기");
      cancle.setBounds(434, 495, 178, 39);
      mainp.add(cancle);

      JLabel la1 = new JLabel("AWESOME ONECARD");
      la1.setBounds(12, 10, 137, 15);
      mainp.add(la1);
      
      JLabel la2 = new JLabel("PLAYER LIST");
      la2.setBounds(434, 10, 90, 15);
      mainp.add(la2);
      
      chatt = new JTextArea(idname+"님 환영합니다! 어썸원카드입니다!\r\n");
      chatt.setBackground(new Color(224, 255, 255));
      chatt.setBounds(12, 35, 410, 451);
      mainp.add(chatt);
      chatt.setEditable(false);

      member = new JTextArea(idname);
      member.setBackground(new Color(224, 255, 255));
      member.setBounds(434, 35, 178, 401);
      mainp.add(member);
      member.setEditable(false);

      
      for(int i=0; i<Client.member.size(); i++){
         member.append(Client.member.get(i));
      }

      ready.addActionListener(this);
      cancle.addActionListener(this);

      send.addActionListener(this);
      chat.addActionListener(this);

      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub         
           Client.sendEnd();
           dispose();
         }
      });   

      setMenuBar(new OneMenu(idname));
      add(mainp);
      setBounds(res.width/2-res.width/4,res.height/2-res.height/3,630,600);
      setVisible(true);
      setResizable(false);

   }

   private void setDefaultCloseOperation(int exitOnClose) {
      // TODO Auto-generated method stub

   }

   private void setContentPane(JPanel mainp2) {
      // TODO Auto-generated method stub

   }

   @Override   public void run() {      
      if(PlayScreen.clcnt==0){
         client=new Client(idname);
         PlayScreen.clcnt++;
         Thread thr2=new Thread(client,"통신스레드");//통신접속         
         thr2.start();      
      }
   }         

   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getActionCommand()=="입         장") {   
         Client.sendEnterReady();                     
         dispose();         


      }else{         
         msg = chat.getText()+"\r\n";         
         Client.sendMsg(msg);   
         chat.setText("");      
      }
   }
}