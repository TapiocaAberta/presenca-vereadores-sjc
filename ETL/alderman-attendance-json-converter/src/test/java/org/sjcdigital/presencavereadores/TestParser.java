package org.sjcdigital.presencavereadores;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.sjcdigital.aldermanattendance.model.AldermanAttendance;
import org.sjcdigital.aldermanattendance.parser.AnyDocumentAttendanceParser;
import org.sjcdigital.aldermanattendance.parser.AttendanceParser;
import org.sjcdigital.aldermanattendance.parser.PDFAttendanceParser;

public class TestParser {

	@Test
	public void testFactory() {
		AttendanceParser pdf = AttendanceParser.getParserForType(Paths.get("something.pdf"));
		AttendanceParser any = AttendanceParser.getParserForType(Paths.get("something.unknown"));
		assertTrue(pdf instanceof PDFAttendanceParser);
		assertTrue(any instanceof AnyDocumentAttendanceParser);
	}
	
	@Test
	public void testPdfParser() throws URISyntaxException {
		Path path = Paths.get(TestParser.class.getResource("/test.pdf").toURI());
		AldermanAttendance parse = AttendanceParser.getParserForType(path).parse(path);
		assertEquals("17ª Legislatura", parse.getLegislature());
		assertEquals("17ª Sessão Ordinária", parse.getSession());
		assertEquals("04 de Abril de 2017", parse.getDate());
		assertEquals(21, parse.getAttendance().size());
		assertEquals("NÃO", parse.getAttendance().get("AMÉLIA NAOMI"));
		assertEquals("SIM", parse.getAttendance().get("CYBORG"));
	}
	
}