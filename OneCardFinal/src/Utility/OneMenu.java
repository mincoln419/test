package Utility;

import java.awt.Dialog;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Data.Record;
import Data.RecordReading;

public class OneMenu extends MenuBar implements ActionListener{
		int i, j;
		String idname;
		
	public OneMenu(String idname){
		this.idname=idname;
		Menu system=new Menu("시스템");		
		MenuItem record=new MenuItem("승패기록");
		MenuItem end=new MenuItem("종료");
		record.addActionListener(this);
		end.addActionListener(this);
		system.add(record);
		system.add(end);
		
		Menu help=new Menu("도움말");
		MenuItem rule=new MenuItem("게임방법");
		MenuItem creater=new MenuItem("제작자");
		rule.addActionListener(this);
		creater.addActionListener(this);
		help.add(rule);
		help.add(creater);
		
		Menu sound=new Menu("음량"); //음량 메뉴바 만들기.
		MenuItem soundoff=new MenuItem("sound off");
		MenuItem soundon=new MenuItem("sound on");
		soundoff.addActionListener(this);
		soundon.addActionListener(this);
		sound.add(soundoff); //메뉴바 눌렀을때 메뉴 추가.
		sound.add(soundon); //메뉴바 눌렀을때 메뉴 추가.
		
		this.add(system);
		this.add(help);	
		this.add(sound); //음량 메뉴바 추가
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="승패기록"){			
			new Record(idname);
		}
		if(e.getActionCommand()=="종료"){
			System.exit(0);
		}
		if(e.getActionCommand()=="게임방법"){
			new GameRule();
		}
		if(e.getActionCommand()=="제작자"){			
			new Editors();
		}
		if(e.getActionCommand()=="sound off"){			//음악끄기
//			TwoLabel.StopSound();
		}
		if(e.getActionCommand()=="sound on"){			//음악키기.
			try {
				TwoLabel.StartSound("cardbackground.wav");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
