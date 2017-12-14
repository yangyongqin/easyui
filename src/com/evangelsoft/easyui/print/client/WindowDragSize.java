package com.evangelsoft.easyui.print.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class WindowDragSize {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final JFrame frame = new JFrame();
		frame.setUndecorated(true);
		Border b = new CompoundBorder(new EtchedBorder(),new LineBorder(Color.RED));
		final JPanel panel = new JPanel();
		panel.setSize(200,100);
		panel.setBorder(b);


		panel.addMouseMotionListener(new MouseAdapter() {
			private Point draggingAnchor = null;
			int direction=0;


			public void mouseMoved(MouseEvent e) {
				draggingAnchor = new Point(e.getX() + panel.getX(), e.getY() + panel.getY());
				if(e.getPoint().getY() <=3
						&&e.getPoint().getX() <=3 ){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR ));
					direction=Direction.LEFT_UP;
				}else if(e.getPoint().getY() <=10
						&&frame.getWidth()- e.getPoint().getX() <=10 ){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR  ));
					direction=Direction.RIGHT_UP;
				}
				else if(frame.getHeight()-e.getPoint().getY() <=3
						&&e.getPoint().getX() <=3 ){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR  ));
					direction=Direction.LEFT_DOWN;
				}
				else if(frame.getHeight()- e.getPoint().getY() <=3
						&&frame.getWidth()- e.getPoint().getX() <=3  ){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR  ));
					direction=Direction.RIGHT_DOWN;
				}
				else if(e.getPoint().getY()<=3){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					direction=Direction.UP;
				}else if(frame.getSize().getHeight()-e.getPoint().getY() <= 3 ){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					direction=Direction.DOWN;
				}else if(e.getPoint().getX() <=3 ){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					direction =Direction.LEFT ;
				}else if(frame.getSize().getWidth()-e.getPoint().getX() <=3 ){
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					direction =Direction.RIGHT;
				}else{
					//设置为默认
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					direction=0;
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				//如果是缩小
//				if(e.getX()<draggingAnchor.x ){
//					if(e.getX()<=0){
//						e.consume();
//						return;
//					}
//				}
				Dimension dimension = frame.getSize();

				switch(direction){
				case Direction.LEFT_UP:
					dimension.setSize(dimension.getWidth()-e.getX() ,dimension.getHeight()-e.getY());
//					frame.setSize(dimension);
					frame.setLocation(frame.getLocationOnScreen().x + e.getX(), frame.getLocationOnScreen().y + e.getY());
					break;
				case Direction.RIGHT_UP:
					dimension.setSize(dimension.getWidth()-dimension.getWidth()+e.getX() ,dimension.getHeight()-e.getY());
//					frame.setSize(dimension);
					frame.setLocation(frame.getLocationOnScreen().x , frame.getLocationOnScreen().y + e.getY());
					break;
				case Direction.UP:
					dimension.setSize(dimension.getWidth() ,dimension.getHeight()-e.getY());
//					frame.setSize(dimension);
					frame.setLocation(frame.getLocationOnScreen().x, frame.getLocationOnScreen().y + e.getY());
					break;
				case Direction.LEFT:
					dimension.setSize(dimension.getWidth() - e.getX() ,dimension.getHeight() );
//					frame.setSize(dimension);
					frame.setLocation(frame.getLocationOnScreen().x + e.getX(),frame.getLocationOnScreen().y );
					break;
				case Direction.LEFT_DOWN:
//					System.out.println( e.getX());
					frame.setLocation(frame.getLocationOnScreen().x + e.getX(),frame.getLocationOnScreen().y );
					dimension.setSize(dimension.getWidth()-e.getX() ,e.getY());
					break;
				case Direction.RIGHT:
					dimension.setSize(e.getX(),dimension.getHeight());
//					frame.setSize(dimension);

					break;
				case Direction.RIGHT_DOWN:
					frame.setLocation(frame.getLocationOnScreen().x,frame.getLocationOnScreen().y  );
					dimension.setSize(dimension.getWidth()-dimension.getWidth()+e.getX() ,e.getY());

					break;
				case Direction.DOWN:
					dimension.setSize(dimension.getWidth() , e.getY());
//					frame.setSize(dimension);

					break;
				default :
					frame.setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
				}
				//最小为0，要不然就看不到了
				dimension.setSize(dimension.getWidth()<=5?5:dimension.getWidth(),dimension.getHeight()<=5?5:dimension.getHeight());
				frame.setSize(dimension);

			}


		});

		frame.setLocation(200,100);
		frame.getContentPane().add(panel);
		frame.setSize(200,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();

	}

}