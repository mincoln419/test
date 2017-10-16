package Utility;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.peer.WindowPeer;

public class Editors extends Frame{   
   StringBuffer msg;
   public Editors() {
      super("어썸 원카드 ver 1.0.0");
      Font f=new Font("BOLD",1,18); //글씨체
      Dimension res=Toolkit.getDefaultToolkit().getScreenSize();
      Panel show=new Panel(new GridLayout());
      TextArea editor=new TextArea("개발자", 30,30,TextArea.SCROLLBARS_NONE);
      editor.setFont(f);
      
      editor.setEditable(false); //못바꾸게 설정,.
      editor.setBackground(new OneColor().chat);
      msg=new StringBuffer();
      msg.append("\t\t어썸원카드 ver 1.0.0\r\n\r\n");
      msg.append(" 안녕하세요. 개발자입니다.\r\n");
      msg.append(" 프로젝트를 통해 누구나 쉽게 즐길 수 있는\r\n 원카드 게임!!\r\n");
      msg.append(" 카드 먹이기 부터, 공격카드로 연계까지!\r\n 그때 그 시절 룰을 그대로! 구현했습니다!!!\r\n");
      msg.append(" MT나 캠프에서 즐거운 시간을 책임져 \r\n 주었던 원카드! \r\n");
      msg.append(" 온라인 모드로 그때의 추억을 되살려봅시다!!!!\r\n\r\n");      
      msg.append("\t\t“어~~썸! 원카드!!”\r\n");      
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub
            dispose();
         }
      });
      editor.setText(msg.toString());
      show.add(editor);
      add(show);
      setBounds(res.width/4,res.height/4,450,300);      
      setVisible(true);
      setResizable(false); //창크기 못바꾸게 설정
      
   }
}