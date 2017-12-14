//package com.evangelsoft.easyui.print.client;
//import java.awt.AlphaComposite;
//import java.awt.Color;
//import java.awt.GradientPaint;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.Transparency;
//import java.awt.image.BufferedImage;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//
//import com.sun.imageio.plugins.common.ImageUtil;
//
//@SuppressWarnings("serial")
//public class ReflectedImage extends JPanel {
//    private BufferedImage img;
//
//    public ReflectedImage() {
//        img = ImageUtil.loadImage("resources/code.png");
//        img = createReflectedImage(img);
//        setBackground(Color.BLACK);
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//
//        g2d.drawImage(img, 30, 30, null);
//    }
//
//    // ��ΪSwing��back-buffer�ǲ�͸���ģ�����Ҫ�Լ�����һ��͸��ͼƬ��������
//    public static BufferedImage createReflectedImage(BufferedImage img) {
//        int w = img.getWidth();
//        int h = img.getHeight();
//        BufferedImage reflect = null;
//
//        // ʹ���豸��ص�buffered image
//        reflect = ImageUtil.createCompatibleImage(w, h * 2, Transparency.TRANSLUCENT);
//        // reflect = new BufferedImage(w, h * 2, BufferedImage.TYPE_INT_ARGB); // ƽ̨�޹�
//
//        Graphics2D g2d = reflect.createGraphics();
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//
//        // ����ԭͼ��
//        g2d.drawImage(img, 0, 0, null);
//        // ���Ʒ����ͼ��
//        g2d.translate(0, h + 1);
//        g2d.drawImage(img, 0, 0, w, (int) (h / 1.5), 0, h, w, 0, null);
//
//        // ����͸���Ľ���
//        g2d.setPaint(new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, (int) (h / 2),
//            new Color(1.0f, 1.0f, 1.0f, 0.0f)));
//        // �ؼ�����DstIn��ֻʹ��Դͼ���͸���ȣ�Ŀ��ͼ��͸���ĵ���������͸��:
//        // Ar = Ad * As
//        // Cr = Cd * As
//        g2d.setComposite(AlphaComposite.DstIn);
//        g2d.fillRect(0, 0, w, h);
//
//        g2d.dispose();
//
//        return reflect;
//    }
//
//    private static void createGuiAndShow() {
//        JFrame frame = new JFrame("Reflected Image");
//        frame.getContentPane().add(new ReflectedImage());
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 520);
//        frame.setLocationRelativeTo(null);
//        frame.setAlwaysOnTop(true);
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                createGuiAndShow();
//            }
//        });
//    }
//}