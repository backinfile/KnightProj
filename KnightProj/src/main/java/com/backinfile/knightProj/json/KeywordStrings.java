package com.backinfile.knightProj.json;

public class KeywordStrings {
	public String[] NAMES;
	public String DESCRIPTION;

	public static KeywordStrings getDefaultKeywordString() {
		KeywordStrings strings = new KeywordStrings();
		strings.NAMES = new String[] { "[MISSING-NAMES]" };
		strings.DESCRIPTION = "[MISSING-DESCRIPTION]";
		return strings;
	}
}
