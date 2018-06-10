package org.interview.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtil {
	public static String formatarDateTime(LocalDateTime dateTime) {
		return dateTime == null ? "" : dateTime.format(DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public static String formatarDate(LocalDate date) {
		return date == null ? "" : date.format(DateTimeFormatter
				.ofPattern("dd/MM/yyyy"));
	}
}
