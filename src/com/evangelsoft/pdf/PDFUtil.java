package com.evangelsoft.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PDFUtil {

	public static void main(String[] args) throws Exception {
		imageWaterMark(createPDF(), "F://title.png");
	}

	/**
	* ����PDF�ĵ�
	* @return
	* @throws Exception
	* @throws docException
	*/
	public static String createPDF() throws Exception {

		// ���·��
		String outPath = "F://test.pdf";// DataUtil.createTempPath(".pdf");

		// ����ֽ��
		Rectangle rect = new Rectangle(PageSize.A4);

		// �����ĵ�ʵ��
		Document doc = new Document(rect);

		// �����������
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

		// ����������ʽ
		Font textFont = new Font(bfChinese, 11, Font.NORMAL); // ����
		Font redTextFont = new Font(bfChinese, 11, Font.NORMAL, Color.RED); // ����,��ɫ
		Font boldFont = new Font(bfChinese, 11, Font.BOLD); // �Ӵ�
		Font redBoldFont = new Font(bfChinese, 11, Font.BOLD, Color.RED); // �Ӵ�,��ɫ
		Font firsetTitleFont = new Font(bfChinese, 22, Font.BOLD); // һ������
		Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD); // ��������
		Font underlineFont = new Font(bfChinese, 11, Font.UNDERLINE); // �»���б��

		// ��ָͼƬ
		Image hand = Image.getInstance("F:\\hand.png");

		// ���������
		PdfWriter.getInstance(doc, new FileOutputStream(new File(outPath)));

		doc.open();
		doc.newPage();

		// ����
		Paragraph p1 = new Paragraph();
		// ����
		Phrase ph1 = new Phrase();
		// ��
		Chunk c1 = new Chunk("*********", boldFont);
		Chunk c11 = new Chunk("�����ñ����ṩ����logo��", textFont);
		// ������ӵ�����
		ph1.add(c1);
		ph1.add(c11);
		// ��������ӵ�����
		p1.add(ph1);
		// ��������ӵ�����
		doc.add(p1);

		p1 = new Paragraph();
		ph1 = new Phrase();
		Chunk c2 = new Chunk("�����ţ�", boldFont);
		Chunk c22 = new Chunk("SN-201604010001", textFont);
		ph1.add(c2);
		ph1.add(c22);
		p1.add(ph1);
		doc.add(p1);

		p1 = new Paragraph("��ҵ���ñ���", firsetTitleFont);
		p1.setLeading(50);
		p1.setAlignment(Element.ALIGN_CENTER);
		doc.add(p1);

		p1 = new Paragraph("����ҵ�棩", textFont);
		p1.setLeading(20);
		p1.setAlignment(Element.ALIGN_CENTER);
		doc.add(p1);

		p1 = new Paragraph();
		p1.setLeading(20);
		p1.setAlignment(Element.ALIGN_CENTER);
		ph1 = new Phrase();
		Chunk c3 = new Chunk("��ѯʱ�䣺", boldFont);
		Chunk c33 = new Chunk("2016-04-01 00:00:00", textFont);
		Chunk c4 = new Chunk(leftPad("��ѯ�ˣ�", 10), boldFont);
		Chunk c44 = new Chunk("admin���û���¼����", textFont);
		ph1.add(c3);
		ph1.add(c33);
		ph1.add(c4);
		ph1.add(c44);
		p1.add(ph1);
		doc.add(p1);

		p1 = new Paragraph("����˵��", secondTitleFont);
		p1.setLeading(50);
		p1.setAlignment(Element.ALIGN_CENTER);
		doc.add(p1);

		p1 = new Paragraph(" ");
		p1.setLeading(30);
		doc.add(p1);

		p1 = new Paragraph();
		ph1 = new Phrase();
		Chunk c5 = new Chunk("1.��������", textFont);
		Chunk c6 = new Chunk("****������Ϣ��������", underlineFont);
		c6.setSkew(0, 30);
		Chunk c7 = new Chunk(" ���ߣ����ݽ�ֹ����ʱ��С΢��ҵ������Ϣ���ݿ��¼����Ϣ���ɡ��������ע�Ͳ�ѯ��¼�⣬�����е���Ϣ������ر�����������Ϣ�����ṩ��", textFont);
		Chunk c8 = new Chunk("����֤����ʵ�Ժ�׼ȷ�ԣ�����ŵ����Ϣ���ϡ����ܡ�չʾ��ȫ�����б��ֿ͹ۡ������ĵ�λ��", textFont);
		ph1.add(c5);
		ph1.add(c6);
		ph1.add(c7);
		ph1.add(c6);
		ph1.add(c8);
		p1.add(ph1);
		doc.add(p1);

		p1 = new Paragraph();
		ph1 = new Phrase();
		Chunk c9 = new Chunk("2.�����ע��", textFont);
		Chunk c10 = new Chunk(" �Ա����е���Ϣ��¼�����Ϣ����������˵����", textFont);
		ph1.add(c9);
		ph1.add(c6);
		ph1.add(c10);
		p1.add(ph1);
		doc.add(p1);

		p1 = new Paragraph("3.��Ϣ����˵������Ϣ����Ա��������ṩ����Ϣ��¼�����ļ�Ҫ˵����", textFont);
		doc.add(p1);

		p1 = new Paragraph();
		ph1 = new Phrase();
		Chunk c12 = new Chunk("4.��Ϣ������Ȩ�Ա������е�����������顣�������飬����ϵ����������Ҳ�ɵ�", textFont);
		Chunk c13 = new Chunk(" ����������롣", textFont);
		ph1.add(c12);
		ph1.add(c6);
		ph1.add(c13);
		p1.add(ph1);
		doc.add(p1);

		p1 = new Paragraph("5.������ѯ�����µ�ͻ���������********��", textFont);
		doc.add(p1);

		p1 = new Paragraph("��Ϣ�ſ�", secondTitleFont);
		p1.setSpacingBefore(30);
		p1.setSpacingAfter(30);
		p1.setAlignment(Element.ALIGN_CENTER);
		doc.add(p1);

		p1 = new Paragraph("��Ϣ����ʵ���ʱ�**��Ԫ��****��**��**�չɶ�****��˾��***��˾ת��**��Ԫ��Ȩ��", textFont);
		p1.setFirstLineIndent(23);
		p1.setSpacingAfter(15);
		doc.add(p1);

		p1 = new Paragraph(
				"��Ϣ������20**���״�����ڻ��������Ŵ���ϵ���������ڣ�����**�ҽ��ڻ���������Ŵ�ҵ��Ŀǰ**�ҽ��ڻ�������Ϣ�������Ź�**��Ԫ��**�ҽ��ڻ���**��Ԫ����δ���塣�ṩ���ⵣ��****��Ԫ��",
				textFont);
		p1.setFirstLineIndent(23);
		p1.setSpacingAfter(15);
		doc.add(p1);

		p1 = new Paragraph(
				"�������ڣ���Ϣ���干��**���������б��ü�¼��**����������ü�¼��**���������ű��ü�¼��**���ʼಿ�ű��ü�¼��**��˰���ű��ü�¼��**���������ű��ü�¼��**�����̲��ű��ü�¼��**���������ű��ü�¼��**�����ز��ű��ü�¼��**���������ű��ü�¼��",
				textFont);
		p1.setFirstLineIndent(23);
		p1.setSpacingAfter(15);
		doc.add(p1);

		p1 = new Paragraph(
				"�������ڣ���Ϣ���干��**���������д�����¼��**������������¼��**���������Ŵ�����¼��**���ʼಿ�Ŵ�����¼��**��˰���Ŵ�����¼��**���������Ŵ�����¼��**�����̲��Ŵ�����¼��**���������Ŵ�����¼��**�����ز��Ŵ�����¼��**���������Ŵ�����¼������**��Ƿ˰��¼��**��Ƿˮ/��/����¼��**����Ժ�о�ִ�м�¼��",
				textFont);
		p1.setFirstLineIndent(23);
		doc.add(p1);

		p1 = new Paragraph("��Ϣ�������һ�ν�����ᱣ��Ϊ****��**��**�գ����һ�ν��ɹ�����Ϊ****��**��**�ա�", textFont);
		p1.setFirstLineIndent(23);
		doc.add(p1);

		p1 = new Paragraph("Ŀǰ�������й���*����������˵����*����Ϣ����˵����*����������˵����", textFont);
		p1.setFirstLineIndent(23);
		p1.setSpacingAfter(15);
		doc.add(p1);

		p1 = new Paragraph();
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c14 = new Chunk(hand, 0, 0);
		Chunk c15 = new Chunk(leftPad("�����Ϣ", 7), boldFont);
		ph1.add(c14);
		ph1.add(c15);
		p1.add(ph1);
		doc.add(p1);

		// ����һ����4�еı��
		PdfPTable table = new PdfPTable(4);
		table.setTotalWidth(new float[] { 105, 170, 105, 170 }); // �����п�
		table.setLockedWidth(true); // �����п�

		PdfPCell cell;
		cell = new PdfPCell(new Phrase("��ҵ����", boldFont));
		cell.setBorderWidthLeft(3);
		cell.setBorderWidthTop(3);
		cell.setMinimumHeight(30); // ���õ�Ԫ��߶�
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell();
		cell.setBorderWidthRight(3);
		cell.setBorderWidthTop(3);
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		cell.setColspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("ͳһ������ô��루��֯�������룩", boldFont));
		cell.setBorderWidthLeft(3);
		cell.setMinimumHeight(40); // ���õ�Ԫ��߶�
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", textFont));
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("ע���ַ", boldFont));
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", textFont));
		cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("��ҵ����", boldFont));
		cell.setBorderWidthLeft(3);
		cell.setMinimumHeight(30); // ���õ�Ԫ��߶�
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", textFont));
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("ʵ���ʱ�", boldFont));
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", textFont));
		cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("������Դ", boldFont));
		cell.setBorderWidthLeft(3);
		cell.setBorderWidthBottom(3);
		cell.setMinimumHeight(30); // ���õ�Ԫ��߶�
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", textFont));
		cell.setBorderWidthBottom(3);
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("�ɼ�ʱ��", boldFont));
		cell.setBorderWidthBottom(3);
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" ", textFont));
		cell.setBorderWidthRight(3);
		cell.setBorderWidthBottom(3);
		cell.setUseAscender(true); // ���ÿ��Ծ���
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����
		table.addCell(cell);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c17 = new Chunk(hand, 0, 0);
		Chunk c18 = new Chunk(leftPad("�������", 7), boldFont);
		ph1.add(c17);
		ph1.add(c18);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(9);
		table.setTotalWidth(new float[] { 80, 60, 60, 50, 60, 60, 60, 60, 60 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table,
				new String[] { "���ʷ�����", "֤������", "֤������", "����", "���ʽ��", "���ʷ�ʽ", "����ռ��", "������Դ", "�ɼ�ʱ��" }, 2, 9);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c62 = new Chunk(hand, 0, 0);
		Chunk c63 = new Chunk(leftPad("����������Ϣ", 9), boldFont);
		ph1.add(c62);
		ph1.add(c63);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(10);
		table.setTotalWidth(new float[] { 55, 55, 55, 55, 55, 55, 55, 55, 55, 55 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table, new String[] { "���������", "�ö���������", "����ʱ��", "Υ����Υ����Ϊ����", "�漰���", "�������", "��������",
				"�������", "������Դ", "�ɼ�ʱ��" }, 2, 10);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c66 = new Chunk(leftPad("˾����Ϣ", 7), boldFont);
		ph1.add(c65);
		ph1.add(c66);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(7);
		table.setTotalWidth(new float[] { 70, 80, 80, 80, 80, 80, 80 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table, new String[] { "����", "������Ժ", "��������", "����", "ִ�б��", "ִ�б�Ľ��", "��ִ�б��" }, 2, 7);
		table = createCell(table, new String[] { "��ִ�б�Ľ��", "ִ�б������", "����״̬", "�᰸����", "ִ�н᰸��ʽ", "������Դ", "�ɼ�ʱ��" }, 2, 7);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c68 = new Chunk(hand, 0, 0);
		Chunk c69 = new Chunk(leftPad("��������", 7), boldFont);
		ph1.add(c68);
		ph1.add(c69);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(8);
		table.setTotalWidth(new float[] { 40, 100, 75, 75, 75, 60, 60, 60 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table, new String[] { "���", "���Ž��ڻ���", "���Ŷ��", "��ʼ����", "��ֹ����", "�����ڲ��������", "������Դ", "�ɼ�ʱ��" },
				2, 8);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c71 = new Chunk(hand, 0, 0);
		Chunk c72 = new Chunk(leftPad("���д���", 7), boldFont);
		ph1.add(c71);
		ph1.add(c72);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(10);
		table.setTotalWidth(new float[] { 30, 55, 75, 55, 55, 55, 55, 55, 55, 55 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table, new String[] { " ", "���", "������ڻ���", "�������", "��������", "������", "�弶����", "ǷϢ���", "������Դ",
				"�ɼ�ʱ��" }, 1, 10);
		table = createCell(table, null, 3, 10);
		table = createCell(table, null, 3, 10);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c74 = new Chunk(hand, 0, 0);
		Chunk c75 = new Chunk(leftPad("����", 5), boldFont);
		ph1.add(c74);
		ph1.add(c75);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(8);
		table.setTotalWidth(new float[] { 40, 155, 60, 60, 60, 60, 60, 60 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table, new String[] { "���", "���ֽ��ڻ���", "���ֽ��", "������", "������", "�弶����", "������Դ", "�ɼ�ʱ��" }, 2, 8);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c85 = new Chunk(hand, 0, 0);
		Chunk c86 = new Chunk(leftPad("��Ϣ����˵��", 9), boldFont);
		ph1.add(c85);
		ph1.add(c86);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(1);
		table.setTotalWidth(new float[] { 500 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table, null, 1, 1);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c87 = new Chunk(hand, 0, 0);
		Chunk c88 = new Chunk(leftPad("��ѯ��¼", 7), boldFont);
		ph1.add(c87);
		ph1.add(c88);
		p1.add(ph1);
		doc.add(p1);

		table = new PdfPTable(4);
		table.setTotalWidth(new float[] { 220, 100, 100, 100 }); // �����п�
		table.setLockedWidth(true); // �����п�

		table = createCell(table, new String[] { "���", "��ѯ����", "��ѯ�û�", "��ѯԭ��" }, 3, 4);
		doc.add(table);

		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c89 = new Chunk("�����ע�����ڼ�¼��Ϣ�����ĳ����Ϣ������鴦�����������ÿ����Ϣ���·�����", redBoldFont);
		Chunk c90 = new Chunk(
				"��Ϣ������2016��**��**��������飺�ҹ�˾��δ������***��ҵ����������2016��**��**���ύ˵�����ñ���Ϣȷʵ���ڣ���Ϣ������2016��**��**������������ñ���ϢΪ�ҹ�˾***���¡�����ÿһ�������ϸ��¼��Ϣ����������鴦����̺ͽ������",
				redTextFont);
		ph1.add(c89);
		ph1.add(c90);
		p1.add(ph1);
		doc.add(p1);

		doc.close();
		return outPath;
	}

	/**
	* ������Ԫ��
	* @param table
	* @param row
	* @param cols
	* @return
	* @throws IOException 
	* @throws DocumentException 
	*/
	private static PdfPTable createCell(PdfPTable table, String[] title, int row, int cols) throws DocumentException,
			IOException {
		// �����������
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(bfChinese, 11, Font.BOLD);

		for (int i = 0; i < row; i++) {

			for (int j = 0; j < cols; j++) {

				PdfPCell cell = new PdfPCell();

				if (i == 0 && title != null) {// ���ñ�ͷ
					cell = new PdfPCell(new Phrase(title[j], font)); // ������ͷ���ܾ���
					if (table.getRows().size() == 0) {
						cell.setBorderWidthTop(3);
					}
				}

				if (row == 1 && cols == 1) { // ֻ��һ��һ��
					cell.setBorderWidthTop(3);
				}

				if (j == 0) {// ������ߵı߿���
					cell.setBorderWidthLeft(3);
				}

				if (j == (cols - 1)) {// �����ұߵı߿���
					cell.setBorderWidthRight(3);
				}

				if (i == (row - 1)) {// ���õײ��ı߿���
					cell.setBorderWidthBottom(3);
				}

				cell.setMinimumHeight(40); // ���õ�Ԫ��߶�
				cell.setUseAscender(true); // ���ÿ��Ծ���
				cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // ����ˮƽ����
				cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // ���ô�ֱ����

				table.addCell(cell);
			}
		}

		return table;
	}

	/**
	 * ��ˮӡ���ַ�����
	 * @param inputFile ��Ҫ��ˮӡ��PDF·��
	 * @param outputFile �������PDF��·��
	 * @param waterMarkName ˮӡ�ַ�
	 */
	public static void stringWaterMark(String inputFile, String waterMarkName) {
		try {
			String[] spe = DataUtil.separatePath(inputFile);
			String outputFile = spe[0] + "_WM." + spe[1];

			PdfReader reader = new PdfReader(inputFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

			// �����������
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			int total = reader.getNumberOfPages() + 1;

			PdfContentByte under;
			int j = waterMarkName.length();
			char c = 0;
			int rise = 0;
			// ��ÿһҳ��ˮӡ
			for (int i = 1; i < total; i++) {
				rise = 400;
				under = stamper.getUnderContent(i);
				under.beginText();
				under.setFontAndSize(bfChinese, 30);
				under.setTextMatrix(200, 120);
				for (int k = 0; k < j; k++) {
					under.setTextRise(rise);
					c = waterMarkName.charAt(k);
					under.showText(c + "");
				}

				// ���ˮӡ����
				under.endText();
			}
			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ˮӡ��ͼƬ��
	 * @param inputFile ��Ҫ��ˮӡ��PDF·��
	 * @param outputFile �������PDF��·��
	 * @param imageFile ˮӡͼƬ·��
	 */
	public static void imageWaterMark(String inputFile, String imageFile) {
		try {
			String[] spe = DataUtil.separatePath(inputFile);
			String outputFile = spe[0] + "_WM." + spe[1];

			PdfReader reader = new PdfReader(inputFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

			int total = reader.getNumberOfPages() + 1;

			Image image = Image.getInstance(imageFile);
			image.setAbsolutePosition(-100, 0);// ����
			image.scaleAbsolute(800, 1000);// �Զ����С
			// image.setRotation(-20);//��ת ����
			// image.setRotationDegrees(-45);//��ת �Ƕ�
			// image.scalePercent(50);//���ձ�������

			PdfGState gs = new PdfGState();
			gs.setFillOpacity(0.2f);// ����͸����Ϊ0.2

			PdfContentByte under;
			// ��ÿһҳ��ˮӡ
			for (int i = 1; i < total; i++) {
				under = stamper.getUnderContent(i);
				under.beginText();
				// ���ˮӡͼƬ
				under.addImage(image);
				under.setGState(gs);
			}
			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������߾�
	 * @param str
	 * @param i
	 * @return
	 */
	public static String leftPad(String str, int i) {
		int addSpaceNo = i - str.length();
		String space = "";
		for (int k = 0; k < addSpaceNo; k++) {
			space = " " + space;
		};
		String result = space + str;
		return result;
	}

	/**
	 * ����ģ������
	 * @param list
	 * @param num
	 */
	public static void add(List<String> list, int num) {
		for (int i = 0; i < num; i++) {
			list.add("test" + i);
		}
	}

	/**
	 * ���ü��
	 * @param tmp
	 * @return
	 */
	public static String printBlank(int tmp) {
		String space = "";
		for (int m = 0; m < tmp; m++) {
			space = space + " ";
		}
		return space;
	}

}