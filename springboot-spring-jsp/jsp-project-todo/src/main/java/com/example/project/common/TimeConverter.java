package com.example.project.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeConverter {
	
	public static LocalDateTime fromTimestamp(Timestamp timestamp) {
		
		if (timestamp == null) return null;
		
		return timestamp.toLocalDateTime();
		
	}
	
	public static Timestamp toTimestamp(LocalDateTime localDateTime) {
		
		if (localDateTime == null) return null;
		
		return Timestamp.valueOf(localDateTime);
		
	}
	
}
