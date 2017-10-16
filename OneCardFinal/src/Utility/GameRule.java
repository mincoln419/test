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
      super("��� ��ī�� ver 1.0.0");
      Font f=new Font("bold",1,14);       //��Ʈ 
      Dimension res=Toolkit.getDefaultToolkit().getScreenSize();
      Panel show=new Panel(new GridLayout());
      TextArea rule=new TextArea("���ӷ�", 30,30,TextArea.SCROLLBARS_NONE);
      rule.setFont(f);
      
      rule.setEditable(false); //ȭ�� ���� ���ϰ�.
      rule.setBackground(new OneColor().chat2);
      msg=new StringBuffer();
      msg.append("\t\t\t\t\t\t\t\t\t����ī�� ver 1.0.0\r\n\r\n");
      msg.append("\t\t\t============���ӷ� ����============\r\n");
      msg.append("\r\n");
      msg.append("1. �� ����� 7���� ī�带 �ް� ����\r\n");
      msg.append("\r\n");
      msg.append("2. �߾ӿ� ���µ� ī��� ����(��  ��  ��  ��)�̳� ����(A,2,3~,Q,K)�� ������\r\n ���µ� ī������ ������\r\n");
      msg.append("\r\n");
      msg.append("3. �׷��� ī�带 �����ٰ� �� ���� ������ --��ī��--��ư Ŭ��! \r\n");
      msg.append("\r\n");
      msg.append("4. '��ī��'��ư�� 3�ʰ� �ȴ����ų� �ٸ� ����� ���������� 1���� �Խ��ϴ� \r\n");
      msg.append("\r\n");
      msg.append("5. '��ī��'��ư �����⿡ �����ϰ� �����Ͽ� ������ ������ ������ �¸�! \r\n");
      msg.append("\r\n");
      msg.append("6. Ư��ī��\r\n");
      msg.append(" - ī�� ' 2 ' �� ���� ���� ������ ������� 2���� ���Դϴ�.\r\n");
      msg.append(" - ī�� ' A ' �� ���� ���� ������ ������� 3���� ���Դϴ�.\r\n");
      msg.append(" - ī�� ' ��Ŀ ' �� ���� ���� ������ ������� 5���� ���Դϴ�. \r\n - ��Ŀī��� ���� ī���� ����� ���ڿ� ������� �� �� �ֽ��ϴ�. \r\n");
      msg.append(" - ī�� ' 7 ' �� ���� ������ ������ ī�� ����(��  ��  ��  ��)�� �ٲ� �� �ֽ��ϴ�.\r\n");
      msg.append(" - ī�� ' Q ' �� ���� ��������� �ݴ�� �ٲߴϴ�.\r\n");
      msg.append(" - ī�� ' J ' �� ���� ��������� ������ �ǳʶݴϴ�.\r\n");      
      msg.append(" - ī�� ' K ' �� ���� �� �Ͽ� ���� ����(��  ��  ��  ��)�̳� ' K '�� ���� �� �� �� �ֽ��ϴ�.\r\n");
      msg.append(" - ī�� ' 2 '�� ������ ������ ' 2 ', ' A ' , '��Ŀ' �� ���� ���ʿ� �ѱ� �� �ֽ��ϴ�.\r\n");
      msg.append(" - ī�� ' A '�� ������ ������ ' A ', ' ��Ŀ '�� ���� ���ʿ� �ѱ� �� �ֽ��ϴ�. \r\n ' 2 ' �δ� ' A '�� �ѱ��� ���մϴ�.\r\n");
      msg.append(" - ī�� ' ��Ŀ '�� ������ ������ ' ��Ŀ '�θ� �������ʿ� �ѱ� �� �ֽ��ϴ�.\r\n");
      msg.append(" - ������ ���ʷ� �Ѱ����� �� ���� ������ ����� ������ ����ŭ�� ī�带 �Խ��ϴ�.\r\n ex) ' 2(2) ', ' A(3) ', ' ��Ŀ(5) '�� �Ѱܹ����� 2+3+5=10���� �Խ��ϴ�.\r\n");
      
            
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
      setBounds(res.width/4,res.height/4,600,480);   //ȭ�� ũ�� �ٲ�,.
      setVisible(true);
      setResizable(false); //ȭ��â �������̰�
      
   }
}