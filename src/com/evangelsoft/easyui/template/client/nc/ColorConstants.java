package com.evangelsoft.easyui.template.client.nc;

import java.awt.*;


/**
 * �˴�������������.
 * ��������:(2003-3-19 9:00:05)
 */
public class ColorConstants {
	/**
	 * The color default. Ĭ��
	 */
	public static final int COLOR_DEFAULT = -1;
	/**
	 * The color black. ��ɫ
	 */
	public static final int COLOR_BLACK = 0;
	/**
	 * The color white. ��ɫ
	 */
	public static final int COLOR_WHITE = 1;
	/**
	 * The color light gray.ǳ��ɫ
	 */
	public static final int COLOR_LIGHTGRAY = 2;
	/**
	 * The color gray.  ��ɫ
	 */
	public static final int COLOR_GRAY = 3;
	/**
	 * The color dark gray.���ɫ
	 */
	public static final int COLOR_DARKGRAY = 4;
	/**
	 * The color red. ��ɫ
	 */
	public static final int COLOR_RED = 5;
	/**
	 * The color pink.��ɫ
	 */
	public static final int COLOR_PINK = 6;
	/**
	 * The color orange. ��ɫ
	 */
	public static final int COLOR_ORANGE = 7;
	/**
	 * The color yellow.��ɫ
	 */
	public static final int COLOR_YELLOW = 8;
	/**
	 * The color green. ��ɫ
	 */
	public static final int COLOR_GREEN = 9;
	/**
	 * The color magenta.//�Ϻ�ɫ
	 */
	public static final int COLOR_MAGENTA = 10;
	/**
	 * The color cyan.   //����ɫ
	 */
	public static final int COLOR_CYAN = 11;
	/**
	 * The color blue.��ɫ
	 */
	public static final int COLOR_BLUE = 12;
	/**
	 * The color blue.����ɫ
	 */
	public static final int COLOR_DARKBLUE = 13;
	private static ColorStruc[] colorStrucs =
			{
//				new ColorStruc(COLOR_DEFAULT, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000182")/*@res "Ĭ��"*/, null, "default"),
//				new ColorStruc(COLOR_BLACK, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000457")/*@res "��ɫ"*/, Color.black, "black"),
//				new ColorStruc(COLOR_WHITE, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000458")/*@res "��ɫ"*/, Color.white, "white"),
//				new ColorStruc(COLOR_LIGHTGRAY, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000459")/*@res "ǳ��ɫ"*/, Color.lightGray, "lightGray"),
//				new ColorStruc(COLOR_GRAY, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000460")/*@res "��ɫ"*/, Color.gray, "gray"),
//				new ColorStruc(COLOR_DARKGRAY, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000461")/*@res "���ɫ"*/, Color.darkGray, "darkGray"),
//				new ColorStruc(COLOR_RED, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000462")/*@res "��ɫ"*/, Color.red, "red"),
//				new ColorStruc(COLOR_PINK, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000463")/*@res "��ɫ"*/, Color.pink, "pink"),
//				new ColorStruc(COLOR_ORANGE, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000464")/*@res "��ɫ"*/, Color.orange, "orange"),
//				new ColorStruc(COLOR_YELLOW, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000465")/*@res "��ɫ"*/, Color.yellow, "yellow"),
//				new ColorStruc(COLOR_GREEN, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000466")/*@res "��ɫ"*/, Color.green, "green"),
//				new ColorStruc(COLOR_MAGENTA, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000467")/*@res "�Ϻ�ɫ"*/, Color.magenta, "magenta"),
//				new ColorStruc(COLOR_CYAN, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000468")/*@res "����ɫ"*/, Color.cyan, "cyan"),
//				new ColorStruc(COLOR_BLUE, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000469")/*@res "��ɫ"*/, Color.blue, "blue"),
//				new ColorStruc(COLOR_DARKBLUE, nc.ui.ml.NCLangRes.getInstance().getStrByID("_Bill","UPP_Bill-000470")/*@res "����ɫ"*/, new Color(52, 102, 153), "darkBlue"),
			new ColorStruc(COLOR_DEFAULT, "UPP_Bill-000182"/*@res "Ĭ��"*/, null, "default"),
			new ColorStruc(COLOR_BLACK, "UPP_Bill-000457"/*@res "��ɫ"*/, Color.black, "black"),
			new ColorStruc(COLOR_WHITE, "UPP_Bill-000458"/*@res "��ɫ"*/, Color.white, "white"),
			new ColorStruc(COLOR_LIGHTGRAY, "UPP_Bill-000459"/*@res "ǳ��ɫ"*/, Color.lightGray, "lightGray"),
			new ColorStruc(COLOR_GRAY, "UPP_Bill-000460"/*@res "��ɫ"*/, Color.gray, "gray"),
			new ColorStruc(COLOR_DARKGRAY, "UPP_Bill-000461"/*@res "���ɫ"*/, Color.darkGray, "darkGray"),
			new ColorStruc(COLOR_RED,"UPP_Bill-000462"/*@res "��ɫ"*/, Color.red, "red"),
			new ColorStruc(COLOR_PINK, "UPP_Bill-000463"/*@res "��ɫ"*/, Color.pink, "pink"),
			new ColorStruc(COLOR_ORANGE, "UPP_Bill-000464"/*@res "��ɫ"*/, Color.orange, "orange"),
			new ColorStruc(COLOR_YELLOW, "UPP_Bill-000465"/*@res "��ɫ"*/, Color.yellow, "yellow"),
			new ColorStruc(COLOR_GREEN, "UPP_Bill-000466"/*@res "��ɫ"*/, Color.green, "green"),
			new ColorStruc(COLOR_MAGENTA, "UPP_Bill-000467"/*@res "�Ϻ�ɫ"*/, Color.magenta, "magenta"),
			new ColorStruc(COLOR_CYAN, "UPP_Bill-000468"/*@res "����ɫ"*/, Color.cyan, "cyan"),
			new ColorStruc(COLOR_BLUE, "UPP_Bill-000469"/*@res "��ɫ"*/, Color.blue, "blue"),
			new ColorStruc(COLOR_DARKBLUE, "UPP_Bill-000470"/*@res "����ɫ"*/, new Color(52, 102, 153), "darkBlue"),
			};

	private static class ColorStruc {
		int id;
		String name;
		Color color;
		String enname;
		public ColorStruc(int id, String name, Color color, String enname) {
			this.id = id;
			this.name = name;
			this.color = color;
			this.enname = enname;
		}
	}

	/**
	 *
	 * ��������:(2003-3-19 9:21:34)
	 * @param color int
	 * @return java.lang.String
	 */
	public static int[] getAllColorIDs() {
		int[] indices = new int[colorStrucs.length];
		for (int i = 0; i < colorStrucs.length; i++) {
			indices[i] = colorStrucs[i].id;
		}
		return indices;
	}
	/**
	 *
	 * ��������:(2003-3-19 9:21:34)
	 * @param color int
	 * @return java.lang.String
	 */
	public static Color getColor(int colorID) {
		Color color;
		for (int i = 0; i < colorStrucs.length; i++) {
			if (colorStrucs[i].id == colorID) {
				if (colorStrucs[i].id == COLOR_DEFAULT)
					color = getLabelDefaultColor();
				else
					color = colorStrucs[i].color;
				return color;
			}
		}
		return null;
	}
	/**
	 *
	 * ��������:(2003-3-19 9:21:34)
	 * @param color int
	 * @return java.lang.String
	 */
	public static Color getColor(String colorName) {
		Color color;
		for (int i = 0; i < colorStrucs.length; i++) {
//			String name = NCLangRes.getInstance().getStrByID("_Bill",colorStrucs[i].name);
			String name="�ǺǺ�";
			if (name.equals(colorName) || colorStrucs[i].enname.equals(colorName)) {
				if (colorStrucs[i].id == COLOR_DEFAULT)
					color = getLabelDefaultColor();
				else
					color = colorStrucs[i].color;
				return color;
			}
		}
		return null;
	}
	/**
	 *
	 * ��������:(2003-3-19 9:21:34)
	 * @param color int
	 * @return java.lang.String
	 */
	public static int getColorID(String colorName) {
		for (int i = 0; i < colorStrucs.length; i++) {
//			if (NCLangRes.getInstance().getStrByID("_Bill",colorStrucs[i].name).equals(colorName)) {
//				return colorStrucs[i].id;
//			}
		}
		return COLOR_DEFAULT;
	}
	/**
	 *
	 * ��������:(2003-3-19 9:21:34)
	 * @param color int
	 * @return java.lang.String
	 */
	public static String getColorName(int colorIndex) {
		for (int i = 0; i < colorStrucs.length; i++) {
//			if (colorStrucs[i].id == colorIndex)
//				return NCLangRes.getInstance().getStrByID("_Bill",colorStrucs[i].name);
		}
		return null;
	}
	/**
	 *
	 * ��������:(2003-3-19 9:21:34)
	 * @param color int
	 * @return java.lang.String
	 */
	public static String[] getColorNames(int[] colorIDs) {
		if (colorIDs == null)
			return null;
		String[] names = new String[colorIDs.length];
		for (int j = 0; j < colorIDs.length; j++) {
			for (int i = 0; i < colorStrucs.length; i++) {
				if (colorStrucs[i].id == colorIDs[j]) {
//					names[j] = NCLangRes.getInstance().getStrByID("_Bill",colorStrucs[i].name);
//					break;
				}
			}
		}
		return names;
	}
//	/**
//	 *
//	 * ��������:(2003-3-19 9:21:34)
//	 * @param color int
//	 * @return java.lang.String
//	 */
//	public static Color[] getColors(int[] colorIndices) {
//		if (colorIndices == null)
//			return null;
//		Color[] colors = new Color[colorIndices.length];
//		for (int j = 0; j < colorIndices.length; j++) {
//			for (int i = 0; i < colorStrucs.length; i++) {
//				if (colorStrucs[i].id == colorIndices[j]) {
//					if (colorStrucs[i].id == COLOR_DEFAULT)
//						colors[j] = getLabelDefaultColor();
//					else
//						colors[j] = colorStrucs[i].color;
//				}
//				break;
//			}
//		}
//		return colors;
//	}
	/**
	 *
	 * ��������:(2003-3-19 9:21:34)
	 * @param color int
	 * @return java.lang.String
	 */
	public static Color getLabelDefaultColor() {
		return Color.black;

		//Color color = null;
		//color = nc.ui.pub.style.Style.getColor("Label.foreground");
		//if (color == null)
		//color = nc.ui.pub.style.Style.getColor("��ǩ.ǰ��");
		//if (color == null)
		//color = Color.black;
		//return color;
	}
//	public static String[][] getColorNamesAndEnNames() {
//		String[][] names = new String[colorStrucs.length - 1][2];
//		for (int i = 1; i < colorStrucs.length; i++) {
//			names[i - 1][0] = colorStrucs[i].enname;
//			names[i - 1][1] = NCLangRes.getInstance().getStrByID("_Bill",colorStrucs[i].name);
//		}
//		return names;
//	}
}