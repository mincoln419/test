package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Network.Client;



public class ReadyRoom extends JFrame implements Runnable, ActionListener{
   

	   JPanel mainP;
	   public static JTextArea rchatt;
	   public static TextField tf1= new TextField();////////////////////////실시간으로 클라인트에서 변하는 값은 static으로 줄때 필드에 객체선언을 할것! 안그러면 널포인트
	   public static TextField tf2= new TextField();
	   public static TextField tf3= new TextField();
	   public static TextField tf4= new TextField();
	   public TextField rchat;
	   JButton rbtn;        //준비버튼
	   JButton sbtn;        //준비해제 버튼
	   JButton sendbtn;     
	   public static JPanel p1= new JPanel();
	   public static JPanel p2= new JPanel();
	   public static JPanel p3= new JPanel();
	   public static JPanel p4= new JPanel();
	   public static String msg;
	   Thread thr4;
	   String idname;
	 
	   
	   
public ReadyRoom(String idname) {
	
	this.idname=idname;
		
}
   @Override
   public void run() {
   	// TODO Auto-generated method stub
	   
	   Dimension res=Toolkit.getDefaultToolkit().getScreenSize();
	   setBounds(res.width/2-res.width/4,res.height/2-res.height/4,660,500);
	 
      setTitle(idname+"접속 - 게임준비");
     
		
		mainP = new JPanel();
		mainP.setBackground(new Color(173, 216, 230));
		mainP.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainP);
		mainP.setLayout(null);
		
		
		tf1.setBounds(347, 63, 180, 35);
		mainP.add(tf1);
		tf1.setColumns(10);
		tf1.setFocusable(false);
				
		
		tf2.setBounds(347, 139, 180, 35);
		mainP.add(tf2);
		tf2.setColumns(10);
		tf2.setFocusable(false);
		
		tf3.setBounds(347, 215, 180, 35);
		mainP.add(tf3);
		tf3.setColumns(10);
		tf3.setFocusable(false);
		
		tf4.setBounds(347, 287, 180, 35);
		mainP.add(tf4);
		tf4.setColumns(10);		
		tf4.setFocusable(false);
		rchat = new TextField();
		rchat.addActionListener(this);
		rchat.setBounds(12, 395, 240, 28);		
		mainP.add(rchat);
		rchat.setColumns(10);
		
		rbtn = new JButton("준    비");
		rbtn.addActionListener(this);
		rbtn.setBounds(347, 366, 114, 57);
		mainP.add(rbtn);
		
		sbtn = new JButton("준비해제");
		sbtn.addActionListener(this);
		sbtn.setBounds(507, 366, 114, 57);
		sbtn.setEnabled(false);
		mainP.add(sbtn);
		
		sendbtn = new JButton("전송");
		sendbtn.addActionListener(this);
		sendbtn.setBounds(254, 394, 66, 29);		
		mainP.add(sendbtn);
		
		
		JLabel play1 = new JLabel("1Player");
		play1.setBounds(347, 48, 57, 15);
		mainP.add(play1);
		
		JLabel play2 = new JLabel("2Player");
		play2.setBounds(347, 123, 57, 15);
		mainP.add(play2);
		
		JLabel play3 = new JLabel("3Player");
		play3.setBounds(347, 199, 57, 15);
		mainP.add(play3);
		
		JLabel play4 = new JLabel("4Player");
		play4.setBounds(347, 272, 57, 15);
		mainP.add(play4);
		
		
		
		p1.setBounds(539, 63, 82, 35);
		mainP.add(p1);
		
		
		
		p2.setBounds(539, 139, 82, 35);
		mainP.add(p2);
		
		
		
		p3.setBounds(541, 215, 82, 35);
		mainP.add(p3);
		
		
		
		p4.setBounds(541, 287, 82, 35);
		mainP.add(p4);		
	
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(12, 48, 308, 337);
		mainP.add(scroll);
		
		
		  rchatt=WaitingRoom.chatt;
		  scroll.setViewportView(rchatt);
		  rchatt.setText("준비가 되셨으면 준비 버튼을 눌러주시기 바랍니다. \r\n ");
		  rchatt.setEditable(false);
		      setVisible(false);
		      setResizable(false);
		
		addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	            // TODO Auto-generated method stub         
	           Client.sendEnd();
	           Client.sendCancle();	           
	           dispose();
	         }
	      });  	
	      setVisible(true);
	      setResizable(false);
	     
 }


   
@Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource()==rbtn){
         Client.sendReady();
         rbtn.setEnabled(false);
         sbtn.setEnabled(true);
      }else if(e.getSource()==sbtn){
    	  Client.sendCancle();
    	  rbtn.setEnabled(true);
          sbtn.setEnabled(false);
      }else {
    	  Client.sendMsg(rchat.getText()+"\r\n");
    	  rchat.setText("");
    	  
      }
      
   }
 
}