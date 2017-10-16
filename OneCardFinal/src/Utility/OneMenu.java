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
		Menu system=new Menu("�ý���");		
		MenuItem record=new MenuItem("���б��");
		MenuItem end=new MenuItem("����");
		record.addActionListener(this);
		end.addActionListener(this);
		system.add(record);
		system.add(end);
		
		Menu help=new Menu("����");
		MenuItem rule=new MenuItem("���ӹ��");
		MenuItem creater=new MenuItem("������");
		rule.addActionListener(this);
		creater.addActionListener(this);
		help.add(rule);
		help.add(creater);
		
		Menu sound=new Menu("����"); //���� �޴��� �����.
		MenuItem soundoff=new MenuItem("sound off");
		MenuItem soundon=new MenuItem("sound on");
		soundoff.addActionListener(this);
		soundon.addActionListener(this);
		sound.add(soundoff); //�޴��� �������� �޴� �߰�.
		sound.add(soundon); //�޴��� �������� �޴� �߰�.
		
		this.add(system);
		this.add(help);	
		this.add(sound); //���� �޴��� �߰�
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="���б��"){			
			new Record(idname);
		}
		if(e.getActionCommand()=="����"){
			System.exit(0);
		}
		if(e.getActionCommand()=="���ӹ��"){
			new GameRule();
		}
		if(e.getActionCommand()=="������"){			
			new Editors();
		}
		if(e.getActionCommand()=="sound off"){			//���ǲ���
//			TwoLabel.StopSound();
		}
		if(e.getActionCommand()=="sound on"){			//����Ű��.
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
