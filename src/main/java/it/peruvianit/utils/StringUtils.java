package it.peruvianit.utils;

public final class StringUtils {
	public static StringBuilder createStringBuilder(String ... strings ){
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : strings) {
			stringBuilder.append(string);
			string = null;
		}
		
		return stringBuilder;
	}
}
