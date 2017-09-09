package org.sjcdigital.aldermanattendance.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.sjcdigital.aldermanattendance.model.AldermanAttendance;

public class ParserUtils {
	
	public static HashSet<String> aldermans = new HashSet<>();
	
	public static AldermanAttendance fromText(String text) {
		AldermanAttendance aldermanAttendance;
		Map<String, String> attendance = new HashMap<>();
		String legislature = "", session = "", date = "";
		text = text.replaceAll("\\\t", "");
		for (String line : text.split("\\n")) {
			for (String aldermanName : ConstantsData.aldermanNames) {
				if (line.startsWith(aldermanName)) {
					String attendanceStr = line.substring(aldermanName.length()).trim().substring(0, 3);
					attendance.put(aldermanName, attendanceStr);
				}
			}
			if (line.contains("Legislatura")) {
				String[] sessionData = line.split(" - ");
				legislature = sessionData[0].replaceAll("\\s+$", "");
				session = sessionData[1];
				date = sessionData[2];
			}
		}
		aldermanAttendance = new AldermanAttendance(session, date, legislature, attendance);
		return aldermanAttendance;
	}
}
