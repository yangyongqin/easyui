package com.evangelsoft.easyui.print.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.event.MouseInputListener;

/**
* �Զ�����򴰿ڣ�������ק�ƶ���λ�á�
* @author Jeby Sun
*
*/
public class MyFrame extends JWindow {

  private static final long serialVersionUID = 1L;

  JLabel titleLbl;

  public MyFrame() {
    //���ñ�����ɫ����ֱ�ӵ�����setBackground��������Ҫ������ContentPane�ı�����ɫ��
    this.getContentPane().setBackground(new Color(0x99FF66));
    this.setBounds(100,100,600,400);
    this.setLayout(null);

    titleLbl = new JLabel(" �Զ��崰�ڱ�����");
    titleLbl.setOpaque(true);
    titleLbl.setBackground(new Color(0x66CC00));
    titleLbl.setBounds(0, 0, 600, 30);
    this.add(titleLbl);
    //����¼�������
    MouseEventListener mouseListener = new MouseEventListener(this);
    titleLbl.addMouseListener(mouseListener);
    titleLbl.addMouseMotionListener(mouseListener);

    this.setVisible(true);
  }

  /**
  * ����¼�����
  * @author Jeby Sun
  *
  */
  class MouseEventListener implements MouseInputListener {

    Point origin;
    //�����ק��Ҫ�ƶ���Ŀ�����
    MyFrame frame;

    public MouseEventListener(MyFrame frame) {
      this.frame = frame;
      origin = new Point();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
    * ��¼��갴��ʱ�ĵ�
    */
    @Override
    public void mousePressed(MouseEvent e) {
      origin.x = e.getX();
      origin.y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
    * ����ƽ�������ʱ���������ͼ��Ϊ�ƶ�ͼ��
    */
    @Override
    public void mouseEntered(MouseEvent e) {
      this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    /**
    * ����Ƴ�������ʱ���������ͼ��ΪĬ��ָ��
    */
    @Override
    public void mouseExited(MouseEvent e) {
      this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
    * ����ڱ�������קʱ�����ô��ڵ�����λ��
    * �����µ�����λ�� = �ƶ�ǰ����λ��+�����ָ�뵱ǰ����-��갴��ʱָ���λ�ã�
    */
    @Override
    public void mouseDragged(MouseEvent e) {
      Point p = this.frame.getLocation();
      this.frame.setLocation(
        p.x + (e.getX() - origin.x),
        p.y + (e.getY() - origin.y));
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

  }

  public static void main(String[] args) {
    new MyFrame();
  }

}