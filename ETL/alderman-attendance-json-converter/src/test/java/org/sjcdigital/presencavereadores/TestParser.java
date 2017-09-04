package org.sjcdigital.presencavereadores;

import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Test;
import org.sjcdigital.aldermanattendance.parser.AttendanceParser;
import org.sjcdigital.aldermanattendance.parser.PDFAttendanceParser;
import org.sjcdigital.aldermanattendance.parser.XLSAttendanceParser;

public class TestParser {

	@Test
	public void testFactory() {
		AttendanceParser pdf = AttendanceParser.getParserForType(Paths.get("something.pdf"));
		AttendanceParser xls = AttendanceParser.getParserForType(Paths.get("something.xls"));
		assertTrue(pdf instanceof PDFAttendanceParser);
		assertTrue(xls instanceof XLSAttendanceParser);
	}
	
}