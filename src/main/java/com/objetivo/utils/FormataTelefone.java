package com.objetivo.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class FormataTelefone {
	
	public static String format(Object value) {
		try {
			MaskFormatter mask = new MaskFormatter("(##) #####-####");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
