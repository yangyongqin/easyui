package com.evangelsoft.easyui.template.client.nc;

import java.util.HashSet;


/**
 * Created by IntelliJ IDEA.
 * User: hxr
 * Date: 2004-4-30
 * Time: 9:30:28
 * To change this template use File | Settings | File Templates.
 */
public class FormulaWordSorter extends AbstractWordSorter {
	public String getEndingAdhesiveDeliminiter(String deliminiter) {
		if (deliminiter.equals("\"")) {
			return deliminiter;
		}
		//返回为null,表示粘连分割符的作用域为到本行结束
		return null;
	}
	public int getWordType(String word) {
		if (word.startsWith("\"")) {
			return IWordType.STRING;
		} else if (word.startsWith("//") || word.startsWith("/*")) {
			return IWordType.NOTE;
		}
		return super.getWordType(word);
	}
	protected void initWordSet() {
		deliminiters = new HashSet();
		keywords = new HashSet();
		operators = new HashSet();
		//~!@%^&*()-+=|\/{}[]:;"'<> ,	.?
//	deliminiters.add("~");
//	deliminiters.add("!");
//	deliminiters.add("@");
//	deliminiters.add("\\");
//	deliminiters.add("/*");
//	deliminiters.add("*/");
//	deliminiters.add("//");
//	deliminiters.add("{");
//	deliminiters.add("}");
	deliminiters.add("[");
	deliminiters.add("]");
	deliminiters.add("(");
	deliminiters.add(")");
	deliminiters.add(":");
	deliminiters.add(";");
	deliminiters.add("->");

	deliminiters.add("\"");
	deliminiters.add("'");
	deliminiters.add(",");
	deliminiters.add(" ");
	deliminiters.add("	");
	deliminiters.add(".");
	deliminiters.add("?");
	deliminiters.add("-");
	deliminiters.add("+");
	deliminiters.add("*");
	deliminiters.add("/");
	deliminiters.add("<");
	deliminiters.add(">");
	deliminiters.add("%");
	deliminiters.add("^");
	deliminiters.add("=");
//	//
	operators.add("-");
	operators.add("+");
	operators.add("*");
	operators.add("/");
//	operators.add("<");
//	operators.add(">");
//	operators.add("%");
//	operators.add("^");
//	//
//	keywords.add("abstract");
//	keywords.add("break");
//	keywords.add("byte");
//	keywords.add("boolean");
//	keywords.add("catch");
//	keywords.add("case");
//	keywords.add("class");
//	keywords.add("char");
//	keywords.add("continue");
//	keywords.add("default");
//	keywords.add("double");
//	keywords.add("do");
//	keywords.add("else");
//	keywords.add("extends");
//	keywords.add("false");
//	keywords.add("final");
//	keywords.add("float");
//	keywords.add("for");
//	keywords.add("finally");
//	keywords.add("if");
//	keywords.add("import");
//	keywords.add("implements");
//	keywords.add("int");
//	keywords.add("interface");
//	keywords.add("instanceof");
//	keywords.add("long");
//	keywords.add("length");
//	keywords.add("native");
//	keywords.add("new");
//	keywords.add("null");
//	keywords.add("package");
//	keywords.add("private");
//	keywords.add("protected");
//	keywords.add("public");
//	keywords.add("final");
//	keywords.add("return");
//	keywords.add("switch");
//	keywords.add("synchronized");
//	keywords.add("short");
//	keywords.add("static");
//	keywords.add("super");
//	keywords.add("try");
//	keywords.add("true");
//	keywords.add("this");
//	keywords.add("throw");
//	keywords.add("throws");
//	keywords.add("threadsafe");
//	keywords.add("transient");
//	keywords.add("void");
//	keywords.add("volatile");
//	keywords.add("while");
	}
	void addDeliminiter(String delim) {
		if (delim != null)
			deliminiters.add(delim);
	}
	void addOperator(String orerator) {
		if (orerator != null)
			deliminiters.add(orerator);
	}
	void addKeyword(String keyword) {
		if (keywords != null)
			keywords.add(keyword);
	}
	void removeDeliminiter(String delim) {
		if (delim != null)
			deliminiters.remove(delim);
	}
	void removeOperator(String orerator) {
		if (orerator != null)
			deliminiters.remove(orerator);
	}
	void removeKeyword(String keyword) {
		if (keywords != null)
			keywords.remove(keyword);
	}
}
