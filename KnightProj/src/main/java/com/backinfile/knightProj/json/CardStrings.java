package com.backinfile.knightProj.json;

public class CardStrings {
	public String NAME;

	public static CardStrings getEmptyCardStrings() {
		CardStrings strings = new CardStrings();
		return strings;
	}

	public static CardStrings getDefaultCardString() {
		CardStrings strings = new CardStrings();
		strings.NAME = "[MISSING-NAME]";
		return strings;
	}
}
