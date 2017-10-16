package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.stream.BaseStream;

import Main.PlayScreen;

public class RecordReading {
	String text="\r\n";
	String idname;
	int win;
	int lose;

	public RecordReading(String idname) {
		
		this.idname=idname;
		File rec=new File("./Records/OneCardRecord_"+idname+".txt");	
		File brs=new File("./Records/BattleRecords_"+idname+".txt");
		if(!brs.exists()){return;}		
		
		// TODO Auto-generated constructor stub
		FileWriter fw=null;	
		FileReader fr=null;
		BufferedReader br=null;		 		
			try {
				if(!rec.exists()){
				rec.createNewFile();				
				}				
				fr=new FileReader(brs);
				br=new BufferedReader(fr);
				while(true){
					String msg=br.readLine();
					if(msg==null){break;}
					text+=msg+"\r\n";
				}				
				char[] arr=text.toCharArray();
				for(int i=0;i<arr.length;i++){
					if(arr[i]=='½Â'){
						win++;
					}
					if(arr[i]=='ÆÐ'){
						lose++;
					}
				}
				
				fw=new FileWriter(rec);								
	            fw.write("ÃÑ ÀüÀû:"+win+"½Â/"+lose+"ÆÐ\t"+"½Â·ü:"+(int)(win*100/(win+lose))+"%");         
	            fw.write("¿ª´ë ÀüÀû");
	            fw.write(text);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{				
				try {
					br.close();
					fr.close();
					fw.close();					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
	}
	}
