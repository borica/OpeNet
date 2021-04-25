package com.br.opet.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DateUtils {
	
	public static List<Integer> getListAnos() {
		
		List<Integer> listAnos = new ArrayList<Integer>(); 
		Locale locale = new Locale("pt", "BR");
		Calendar cal = Calendar.getInstance(locale);
		int anos = 48;
		
		for (int i = 0; i <= anos; i++) {
			listAnos.add(cal.get(Calendar.YEAR));
			cal.add(Calendar.YEAR, -1);
		}
		
		return listAnos;
	}

}
