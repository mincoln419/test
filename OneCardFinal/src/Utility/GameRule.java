package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextPane;
import javax.swing.text.Style;

public class GameRule extends Frame{
   StringBuffer msg;
   
   public GameRule() {
      super("어썸 원카드 ver 1.0.0");
      Font f=new Font("bold",1,14);       //폰트 
      Dimension res=Toolkit.getDefaultToolkit().getScreenSize();
      Panel show=new Panel(new GridLayout());
      TextArea rule=new TextArea("게임룰", 30,30,TextArea.SCROLLBARS_NONE);
      rule.setFont(f);
      
      rule.setEditable(false); //화면 수정 못하게.
      rule.setBackground(new OneColor().chat2);
      msg=new StringBuffer();
      msg.append("\t\t\t\t\t\t\t\t\t어썸원카드 ver 1.0.0\r\n\r\n");
      msg.append("\t\t\t============게임룰 설명============\r\n");
      msg.append("\r\n");
      msg.append("1. 한 사람당 7장의 카드를 받고 시작\r\n");
      msg.append("\r\n");
      msg.append("2. 중앙에 오픈된 카드와 문양(♠  ♥  ♣  ◆)이나 숫자(A,2,3~,Q,K)가 있으면\r\n 오픈된 카드위에 버리기\r\n");
      msg.append("\r\n");
      msg.append("3. 그렇게 카드를 버리다가 한 장이 남으면 --원카드--버튼 클릭! \r\n");
      msg.append("\r\n");
      msg.append("4. '원카드'버튼을 3초간 안누르거나 다른 사람이 먼저누르면 1장을 먹습니다 \r\n");
      msg.append("\r\n");
      msg.append("5. '원카드'버튼 누르기에 성공하고 다음턴에 마지막 한장을 버리면 승리! \r\n");
      msg.append("\r\n");
      msg.append("6. 특수카드\r\n");
      msg.append(" - 카드 ' 2 ' 를 내면 다음 순서의 사람에게 2장을 먹입니다.\r\n");
      msg.append(" - 카드 ' A ' 를 내면 다음 순서의 사람에게 3장을 먹입니다.\r\n");
      msg.append(" - 카드 ' 조커 ' 를 내면 다음 순서의 사람에게 5장을 먹입니다. \r\n - 조커카드는 오픈 카드의 문양과 숫자와 관계없이 낼 수 있습니다. \r\n");
      msg.append(" - 카드 ' 7 ' 을 내면 앞으로 진행할 카드 문양(♠  ♥  ♣  ◆)을 바꿀 수 있습니다.\r\n");
      msg.append(" - 카드 ' Q ' 를 내면 진행순서를 반대로 바꿉니다.\r\n");
      msg.append(" - 카드 ' J ' 를 내면 다음사람의 순서를 건너뜁니다.\r\n");      
      msg.append(" - 카드 ' K ' 를 내면 그 턴에 같은 문양(♠  ♥  ♣  ◆)이나 ' K '를 한장 더 낼 수 있습니다.\r\n");
      msg.append(" - 카드 ' 2 '로 공격이 들어오면 ' 2 ', ' A ' , '조커' 로 다음 차례에 넘길 수 있습니다.\r\n");
      msg.append(" - 카드 ' A '로 공격이 들어오면 ' A ', ' 조커 '로 다음 차례에 넘길 수 있습니다. \r\n ' 2 ' 로는 ' A '를 넘기지 못합니다.\r\n");
      msg.append(" - 카드 ' 조커 '로 공격이 들어오면 ' 조커 '로만 다음차례에 넘길 수 있습니다.\r\n");
      msg.append(" - 공격이 차례로 넘겨졌을 때 최종 차례의 사람은 누적된 수만큼의 카드를 먹습니다.\r\n ex) ' 2(2) ', ' A(3) ', ' 조커(5) '를 넘겨받으면 2+3+5=10장을 먹습니다.\r\n");
      
            
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub
            dispose();
         }
      });
      
      rule.setText(msg.toString());
      show.add(rule);
      add(show);
      setBounds(res.width/4,res.height/4,600,480);   //화면 크기 바꿈,.
      setVisible(true);
      setResizable(false); //화면창 못움직이게
      
   }
}