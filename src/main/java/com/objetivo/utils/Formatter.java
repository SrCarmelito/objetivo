package com.objetivo.utils;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class Formatter {
	
	public static String telephoneMask(Object value) {
		try {
			MaskFormatter mask;
			if(value.toString().length() == 10){
				mask = new MaskFormatter("(##) ####-####");
			} else if(value.toString().length() == 11){
				mask = new MaskFormatter("(##) #####-####");
			} else {
				mask = new MaskFormatter("#########");
			}
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("%s possui dados inválidos, verifique", value));
		}
	}

	public static String cpfCnpjMask(Object value) {
		try {
			MaskFormatter mask;
			if(value.toString().length() == 14){
				mask = new MaskFormatter("##.###.###/####-##");
			} else if(value.toString().length() == 11){
				mask = new MaskFormatter("###.###.###-##");
			} else {
				mask = new MaskFormatter("##########");
			}
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("%s possui dados inválidos, verifique", value));
		}
	}

	public static String cepMask(Object value) {
		try {
			MaskFormatter mask;
			if(value.toString().length() == 8){
				mask = new MaskFormatter("#####-###");
			} else {
				mask = new MaskFormatter("########");
			}
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("%s possui dados inválidos, verifique", value));
		}
	}

}
