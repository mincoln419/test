package Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;

import Main.PlayScreen;


public class BattleRecord extends Thread{
	String tmp="";
	String idname;
	String player1,player2,player3;
	String wl;	
	
	public BattleRecord(String idname,String player1, String player2, String player3, String wl){
		this.idname=idname;
		this.player1=player1;
		this.player2=player2;
		this.player3=player3;
		this.wl=wl;
	}	
		
	@Override
	public void run() {	
		File f=new File("./Records/BattleRecords_"+idname+".txt");
		FileWriter fw=null;	
		BufferedWriter bw=null;
		PrintWriter pw=null;				
		try {				
			fw=new FileWriter(f,true);
			bw=new BufferedWriter(fw);
			pw=new PrintWriter(bw);
			if(!f.exists()){
				f.createNewFile();
			}				
			tmp=player1+","+player2+","+player3+"와의 경기 결과:"+wl;			
			pw.println(tmp);				
			System.out.println(player1+","+player2+","+player3+"와의 경기 결과:"+wl);		
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				pw.close();
				bw.close();
				fw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
