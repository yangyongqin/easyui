package com.evangelsoft.easyui.print.client;

import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFieldFormat;
import com.evangelsoft.econnect.dataformat.RecordFormat;

public class PrintElementLabel extends JLabel{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static RecordFormat format=new RecordFormat("@");
	static{
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.TYPE"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.X"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.Y"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.WIDTH"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.HEIGHT"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.FORECOLOR"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.BACKCOLOR"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.TEXT"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.EXPRESSION"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.FONT_NAME"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.FONT_SIZE"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.BOLD"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.ITALIC"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.UNDERLINE"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.STRIKETHROUGH"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.HORIZONTAL_ALIGNMENT"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.VERTICAL_ALIGNMENT"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.ROTATION"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.IMAGE_SCALE"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.LINE_DIRECTION"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.LINE_HEIGHT"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.BAR_TYPE"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.BAR_TYPE_DESC"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.TEXT_POSITION"));
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.EXEC_CODE"));

	}

	/**
	 * 类型， 0图表,1图片,2表格,3线条,4静态文本,5值控件
	 */
	private String type;
	private Record record;

	public PrintElementLabel(String text,String type){
		super(text);
		this.type=type;
		record=new Record(format);
		record.getField("TYPE").setString(type);
		DragSource dragSource = DragSource.getDefaultDragSource();
		//将srcLabel转换成拖放源，它能接受复制、移动两种操作
		dragSource.createDefaultDragGestureRecognizer(PrintElementLabel.this,
				DnDConstants.ACTION_MOVE, new DragGestureListener()
		{
			public void dragGestureRecognized(DragGestureEvent event)
			{
				//继续拖放操作,拖放过程中使用手状光标
				SourceItemTransferable sourceTransferable=new SourceItemTransferable(record);
				event.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
						sourceTransferable);
//				event.setDropTarget(new TransferHandler.SwingDropTarget(chartButton));

			}
		});

	}
	public PrintElementLabel(String text,Record record){
		super(text);
		this.record=record;
	}

	public static void main(String[] args) {
		String ip="192.168.1.123";
		String reg="192.168.*.*";
		 // 编译正则表达式
	    Pattern pattern = Pattern.compile(reg);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(ip);
	    // 字符串是否与正则表达式相匹配
	    System.out.println(matcher.matches());
	}
}
