package Data;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.Buffer;
import java.util.Date;

import Main.PlayScreen;

public class Record extends Frame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8754254394182389199L;
	String msg="";
	String idname;
	static TextArea ta;
	String text="";
	public Record(String idname) {		
		super("승패전적");	
		new RecordReading(idname);
		this.idname=idname;
		Panel p=new Panel(new GridLayout());
		ta=new TextArea();
		ta.setEditable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {				
				dispose();
			}
		});		
		p.add(ta);
		getMsg();
		add(p);
		setBounds(800,400,400,400);
		setVisible(true);		
				
	}
	
	void getMsg(){
		
		File f=new File("./Records/OneCardRecord_"+idname+".txt");
		FileReader fr=null;
		BufferedReader br=null;		
		
		if(!f.exists()){
			ta.setText("기록이 없습니다");
			return;
		}
		try {
			
			fr=new FileReader(f);
			br=new BufferedReader(fr);
			while(true){
				String msg=br.readLine();
				if(msg==null){break;}
	            text+=msg+"\r\n"+
	                    "------------------------------------------------------------" //점선 추가, 줄넘기기 추가
	                          +"\r\n";
			}			
			ta.setText(text);//try-catch문에서 만든 데이터값을 보내려면 static으로!
			br.close();
			fr.close();			
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
}
