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
      super("��� ��ī�� ver 1.0.0");
      Font f=new Font("BOLD",1,18); //�۾�ü
      Dimension res=Toolkit.getDefaultToolkit().getScreenSize();
      Panel show=new Panel(new GridLayout());
      TextArea editor=new TextArea("������", 30,30,TextArea.SCROLLBARS_NONE);
      editor.setFont(f);
      
      editor.setEditable(false); //���ٲٰ� ����,.
      editor.setBackground(new OneColor().chat);
      msg=new StringBuffer();
      msg.append("\t\t����ī�� ver 1.0.0\r\n\r\n");
      msg.append(" �ȳ��ϼ���. �������Դϴ�.\r\n");
      msg.append(" ������Ʈ�� ���� ������ ���� ��� �� �ִ�\r\n ��ī�� ����!!\r\n");
      msg.append(" ī�� ���̱� ����, ����ī��� �������!\r\n �׶� �� ���� ���� �״��! �����߽��ϴ�!!!\r\n");
      msg.append(" MT�� ķ������ ��ſ� �ð��� å���� \r\n �־��� ��ī��! \r\n");
      msg.append(" �¶��� ���� �׶��� �߾��� �ǻ�����ô�!!!!\r\n\r\n");      
      msg.append("\t\t����~~��! ��ī��!!��\r\n");      
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
      setResizable(false); //âũ�� ���ٲٰ� ����
      
   }
}