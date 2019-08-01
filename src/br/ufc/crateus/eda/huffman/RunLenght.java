package br.ufc.crateus.eda.huffman;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunLenght {
	public static String compact(String string) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			int runLength = 1;
			while (i + 1 < string.length() && string.charAt(i) == string.charAt(i + 1)) {
				runLength++;
				i++;
			}
			// buff.append('@');
			buff.append(runLength);
			buff.append(string.charAt(i));
		}
		return buff.toString();
	}

	public static String decompact(String source) {
		StringBuffer buff = new StringBuffer();
		Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]+| ");
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			int number = Integer.parseInt(matcher.group());
			matcher.find();
			while (number-- != 0)
				buff.append(matcher.group());
		}
		return buff.toString();
	}

	public static void main(String[] args) {
		// TextFile reader = new TextFile("D://runlen.txt");
		// String s = reader.readString();
		String s = "";
		System.out.println(compact(s));
		System.out.println(decompact(compact(s)));

	}
}
