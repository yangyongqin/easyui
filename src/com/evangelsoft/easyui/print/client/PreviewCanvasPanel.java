//package com.evangelsoft.easyui.print.client;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//
//import javax.swing.JPanel;
//
//
///**
// * <b> �������ƴ�ӡԤ�������panel  </b>
// *
// * <p> �������ݣ�1ҳ����ҳ
// *
// * </p>
// *
// * Create at 2008-7-22 ����09:22:21
// *
// * @author bq
// * @since V5.5
// */
//
//public class PreviewCanvasPanel extends JPanel{
//
//	private static final long serialVersionUID = 2851786985769151296L;
//
//	public static final double BORDERSPACE = 20d;//pt
//	public static final double PAGESPAGE = 10d;
//
//	/**
//	 * ���ƽ�������ݣ��ɶ�ҳ
//	 */
//	private MergedPage[][] pages = null;
//
//	/**
//	 * Ԥ���õ�ֽ��
//	 */
//	private Paper pageSize = null;
//
//	/**
//	 * ����
//	 */
//	private float scale = 1f;
//
//	private OutputContext context = null;
//
//	/**
//	 * ���沿��Ԥ�����
//	 */
//	private ThreshholdPool<MergedPage, Image> pagePool = new ThreshholdPool<MergedPage, Image>();
//
//	public PreviewCanvasPanel(OutputContext context, Paper pageSize) {
//		this.pageSize = pageSize;
//		this.context = context;
//	}
//
//	public PreviewCanvasPanel(OutputContext context, MergedPage page, Paper pageSize) {
//		this.context = context;
//		pages = new MergedPage[1][1];
//		pages[0][0] = page;
//		this.pageSize = pageSize;
//	}
//
//	public PreviewCanvasPanel(MergedPage[][] pages, Paper pageSize) {
//		this.pages = pages;
//		this.pageSize = pageSize;
//	}
//
//	/**
//	 * �������������»���
//	 *
//	 * @param pages
//	 */
//	public void update(MergedPage[][] pages) {
//		this.pages = pages;
//
//		updateUI();
//	}
//
//	/**
//	 * �����������»���
//	 *
//	 * @param page
//	 */
//	public void upate(MergedPage page) {
//		pages = new MergedPage[1][1];
//		pages[0][0] = page;
//
//		updateUI();
//	}
//
//	@Override
//	protected void paintComponent(Graphics g) {
//		// TODO ���ű���
//		if(pages == null || pages.length == 0 || pages[0].length == 0)
//			return;
//
////		OutputContext context = new OutputContext();
//
//		Graphics2D g2 = (Graphics2D) g;
//
//		double borderSpace = PreviewCanvasPanel.BORDERSPACE;// ���ݺ�panel��ļ��
//		double pageSpace = PreviewCanvasPanel.PAGESPAGE;
//
//		// ֽ��ԭʼ�ߴ�
//		double orignPageWidth = pageSize.getSize().getWidth().getPt();
//		double orignPageHeight = pageSize.getSize().getHeight().getPt();
////		boolean isVer = pageSize.isDirection();
////
////		// ��������
////		if(!pageSize.isCustom()) {
////			double max = Math.max(orignPageWidth, orignPageHeight);
////			double min = Math.min(orignPageWidth, orignPageHeight);
////			orignPageWidth = min;
////			orignPageHeight = max;
////		}
////
////		if(!isVer) {
////			double tmp = orignPageWidth;
////			orignPageWidth = orignPageHeight;
////			orignPageHeight = tmp;
////		}
//
//		// ֽ�����ź�ĳߴ�
//		double pageWidth = orignPageWidth*scale;
//		double pageHeight = orignPageHeight*scale;
//
//		// actual size �㷨
//		double contentWidth = pageWidth*pages[0].length + (pages[0].length - 1)*pageSpace + borderSpace * 2;
//		double contentHeight = pageHeight*pages.length + (pages.length - 1)*pageSpace + borderSpace * 2;
//
//		setPreferredSize(new Dimension((int)contentWidth, (int)contentHeight));
//		revalidate();
//
//		// panel size
//		float panelWidth = (float) getSize().getWidth();
//		float panelHeight = (float) getSize().getHeight();
//
//		// ������ı���ɫ
//		paintPageBg(g2, Color.LIGHT_GRAY, panelWidth, panelHeight);
//
//		// ҳ����ʾλ�þ���
//		// ����ֽ�����ݵĳߴ磬������
//		double tx = (panelWidth - contentWidth)/2 + borderSpace;
//		double ty = (panelHeight - contentHeight)/2 + borderSpace;
//		g2.translate(tx, ty);
//
//		int rowsNum = pages.length;
//		int colsNum = pages[0].length;
//		double y = 0d;
//		for(int i = 0; i < rowsNum; i++) {
//			double x = 0d;
//			for(int j = 0; j < colsNum; j++) {
//				try {
//					if(pages[i][j] != null){
//						context.setCurMergePage(pages[i][j]);
//						pages[i][j].setPageSize(pageSize);// ����ʱ����ֽ�ųߴ磬������ʼ�ձ�����mergedpage�У�������Ԥ���������ֽ�ųߴ�
//
//						/* ����ֽ�ű��� */
////						Graphics pzG = new PreviewGraphics2D((Graphics2D) g2, scale);
////						paintPageZone((Graphics2D) g2, (int)pageWidth, (int)pageHeight);
//
//						/* �������ݵ�Ԥ����� */
//						Image image = null;
//
//						paintPageZone(g2, (int)pageWidth, (int)pageHeight);
//
//						if(pagePool.get(pages[i][j]) != null)
//							image = pagePool.get(pages[i][j]);
//						else{
//							image = new BufferedImage((int)pageWidth, (int)pageHeight,BufferedImage.TYPE_INT_RGB);
//							// ����ݣ�������Щģ��
////							RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
////							offg.setRenderingHints(renderHints);
//							// ��ֽ�ű���ɫ
////							paintPageZone((Graphics2D) offg, (int)orignPageWidth, (int)orignPageHeight);
//							// ��ֽ������
//							Graphics2D offg = new PreviewGraphics2D((Graphics2D) image.getGraphics(), scale);
//							paintPageBg(offg, Color.WHITE, (int)orignPageWidth, (int)orignPageHeight);
//							pages[i][j].paint((Graphics2D) offg,context);
//
//							if(scale == 1)
//								pagePool.put(pages[i][j], image);
//						}
//
//						if(image != null)
////							g2.drawImage(image, 0, 0, (int)pageWidth, (int)pageHeight, 0, 0, (int)orignPageWidth, (int)orignPageHeight, null);
//							g2.drawImage(image, 0, 0, null);
//
//					}
//				} catch (Exception e) {
//					// FIXME ������쳣�����Ⱑ
//					e.printStackTrace();
//				}
//
//				g2.translate(pageWidth + pageSpace, 0);
//				x = x + pageWidth + pageSpace;
//			}
//
//			g2.translate(-x, pageHeight + pageSpace);
//			y = y + pageHeight + pageSpace;
//		}
//		g2.translate(0, -y);
//
//		g2.translate(-tx, -ty);
//	}
//
//	protected void paintPageZone(Graphics2D g, float width, float height) {
//		Color oldColor = g.getColor();
//
//		// ���
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, (int)(width), (int)(height));
//		g.setColor(Color.GRAY);
//		g.fillRect(3, 3, (int)width + 3, (int)height + 3);
//
//		// �߿�
//		g.setColor(Color.BLACK);
//		g.drawRect(-1, -1, (int)(width) + 1, (int)(height) + 1);
//
//		g.setColor(oldColor);
//	}
//
//	protected void paintPageBg(Graphics2D g, Color color,  float width, float height) {
//		Color oldColor = g.getColor();
//
//		g.setColor(color);
//		g.fillRect(0, 0, (int)(width), (int)(height));
//
//		g.setColor(oldColor);
//	}
//
//	public MergedPage[][] getPages() {
//		return pages;
//	}
//
//	public void setPages(MergedPage[][] pages) {
//		this.pages = pages;
//	}
//
//	public Paper getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(Paper pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public float getScale() {
//		return scale;
//	}
//
//	public void setScale(float scale) {
//		this.scale = scale;
//	}
//
//	/**
//	 * ����ֽ�Ż���
//	 */
//	public void clearPageCache(){
//		pagePool.clear();
//	}
//
////	public PaintContext getContext() {
////		return context;
////	}
////
////	public void setContext(PaintContext context) {
////		this.context = context;
////	}
//
//}