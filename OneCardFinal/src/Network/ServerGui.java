package Network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Main.WaitingRoom;

public class ServerGui extends JFrame implements ActionListener{

	
	private JPanel mainP;

	JButton startbtn;  //서버실행 버튼
	JButton closebtn;  //서버닫기 버튼
	public static JTextArea serverta;
	public static JTextArea listta;
	Server server=new Server();

	public ServerGui(){
		setTitle("OneCard_Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainP = new JPanel();
		mainP.setBackground(new Color(70, 130, 180));
		mainP.setForeground(Color.BLACK);
		mainP.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainP);
		mainP.setLayout(null);

		JScrollPane scroll1 = new JScrollPane();
		scroll1.setBounds(12, 33, 209, 219);
		mainP.add(scroll1);

		serverta = new JTextArea();
		scroll1.setViewportView(serverta);
		serverta.setBackground(new Color(224, 255, 255));
		serverta.setText("서버대기중..."+"\r\n");
	    serverta.setFocusable(false);

		JScrollPane scroll2 = new JScrollPane();
		scroll2.setBounds(233, 33, 189, 130);
		mainP.add(scroll2);

		listta = new JTextArea();
		scroll2.setViewportView(listta);
		listta.setBackground(new Color(224, 255, 255));
		listta.setFocusable(false);
		
		
		JLabel sever = new JLabel("서버현황");
		sever.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		sever.setForeground(new Color(230, 230, 250));
		sever.setBounds(12, 10, 57, 15);
		mainP.add(sever);

		JLabel list = new JLabel("서버접속자");
		list.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		list.setForeground(new Color(230, 230, 250));
		list.setBounds(233, 10, 97, 15);
		mainP.add(list);

		startbtn = new JButton("서버실행");
		startbtn.addActionListener(this);
		startbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		startbtn.setBounds(282, 185, 97, 23);
		mainP.add(startbtn);

		closebtn = new JButton("서버종료");
		closebtn.addActionListener(this);
		closebtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		closebtn.setBounds(282, 218, 97, 23);
		mainP.add(closebtn);
		
		addWindowListener(new WindowAdapter(){
		@Override		
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			dispose();
		}
		 
		});
		setVisible(true);
		setResizable(false);
		server.setGui(this);
	

	}
	public static void main(String[] args) {
		
		new ServerGui();		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==startbtn){
			Thread thr =new Thread(server);
			thr.start();
			serverta.append("서버가동중"+"\r\n");
		}else if(e.getSource()==closebtn){
			try {
				Server.serversocket.close();
				serverta.append("서버종료"+"\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		}
			
	}

}
