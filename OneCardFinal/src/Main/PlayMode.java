package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.tools.DocumentationTool.Location;

import Network.Client;



public class PlayMode extends Frame implements ActionListener{
	GridBagLayout gb=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	Dialog editor;
	public static String idname;
	TextField id;
	static Thread thr1;
	static Thread thr2;
	Dimension res;
	Dialog dl;
	StringTokenizer st;
	String token;
	
	PlayMode(){		
		super("플레이-어썸 원카드 ver 1.0.0");
		
		res=Toolkit.getDefaultToolkit().getScreenSize();
		Font mainf=new Font("굴림", 1, 10);
		ImageIcon icon=new ImageIcon("./Image/mainImg1.jpg");
		JLabel MainLabel=new JLabel(icon);	
		MainLabel.setBounds(100,0,100,100);		
		Label idl=new Label("사용하실 아이디",Label.RIGHT);			
		idl.setFont(mainf);
		Panel mainp=new Panel(gb);		
		Panel loginp=new Panel();
		JButton idchk=new JButton("로그인");
		idchk.addActionListener(this);
		id=new TextField(30);	
		id.setText("아이디입력");
		
		mainp.setLayout(new BorderLayout());		
		gbc.fill=GridBagConstraints.ABOVE_BASELINE;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		loginp.add(idl);		
		loginp.add(id);
		addtoframe(0,1,6,1,1.0,1.0);
		loginp.add(idchk,gbc);
		loginp.setBackground(Color.LIGHT_GRAY);
		mainp.add(loginp);		
		add(MainLabel,BorderLayout.NORTH);
		add(mainp,BorderLayout.CENTER);
		setBounds(res.width/4,res.height/4,800,500);
		setVisible(true);
		
		//////////////////////////////아이디 입력안했을때 경고창
		id.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				id.setText("");
			}
		} 
		);
		id.setSelectionEnd(5);	



		///////////////

		///////////////////////////////
		dl=new Dialog(this,"아이디가 공란입니다");
		Panel alram=new Panel(gb);
		TextArea alramt=new TextArea("아이디를 입력하세요");
		alramt.setEditable(false);
		alramt.setFocusable(!isFocusable());
		alram.add(alramt);
		dl.add(alram);
		dl.setBounds(res.width/2-res.width/12,res.height/2,200,100);
		dl.setVisible(false);		
		////////////////////////////////////
		dl.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dl.setVisible(false);
			}
		});			
	}
	private void addtoframe(int x,int y, int width, int height, double weightx, double weighty) {
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=width;
		gbc.gridheight=height;
		gbc.weightx=weightx;
		gbc.weighty=weighty;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		idname=id.getText();	
		char[] arr=idname.trim().toCharArray();		
		if(arr.length!=0&&!idname.equals("아이디입력")){


/*Client client=new Client(idname);         
		    Thread thr2=new Thread(client,"통신스레드");//통신접속         
		    thr2.start(); 
		    try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    if(chk==true) {
			client.din.close();
			client.dout.close();
			client.socket.close();
		    	WaitingRoom wr=new WaitingRoom();
		    	dispose();
		    }else{		    
			Dialog dl=new Dialog(this,"중복된 아이디가 존재합니다");
        	Panel pp=new Panel();
        	dl.setBounds(800,500,300,200);
        	dl.setVisible(true);
        	Button btn=new Button("뒤로가기");
        	btn.addActionListener(new ActionListener() {							
				@Override
				public void actionPerformed(ActionEvent e) {
					dl.setVisible(false);												
				}
			});
        	pp.add(btn);
        	dl.add(pp);
        	dl.addWindowListener(new WindowAdapter() {
        		@Override
        		public void windowClosing(WindowEvent e) {
        			dl.setVisible(false);   		
        		}
			});*/





			thr1=new Thread(new WaitingRoom(idname),"대기실");//대기실로 이동
			thr1.start();		
			dispose();
		}else{
			dl.setVisible(true);
		}
		
		}
	}	


