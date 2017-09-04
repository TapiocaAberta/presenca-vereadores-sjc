package org.sjcdigital.aldermanattendance.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.sjcdigital.aldermanattendance.model.AldermanAttendance;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/**
 * A specific parse
 * 
 * @author william
 *
 */
public class PDFAttendanceParser implements AttendanceParser {

	@Override
	public AldermanAttendance parse(Path targetFile) {
		String pdfTextContent = getPdfContent(targetFile.toString());
		System.out.println(pdfTextContent);
		System.out.println("--");
		AldermanAttendance aldermanAttendance = retrieveAldermanAttendance(pdfTextContent);
		return aldermanAttendance;
	}


	private String getPdfContent(String pdfFile) {
		try {
			PdfReader reader = new PdfReader(pdfFile);
			StringBuffer sb = new StringBuffer();
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
				sb.append(strategy.getResultantText());
			}
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			throw new IllegalArgumentException("Not able to read file " + pdfFile, e);
		}
	}

	// if we simply extract the text from XLS this method could be reused for XLS as well.
	private AldermanAttendance retrieveAldermanAttendance(String pdfTextContent) {
		AldermanAttendance aldermanAttendance;
		Map<String, String> attendance = new HashMap<>();
		String legislature = "", session = "", date = "";
		for (String line : pdfTextContent.split("\\n")) {
			for (String aldermanName : ConstantsData.aldermanNames) {
				if(line.startsWith(aldermanName)) {
					String attendanceStr = line.substring(aldermanName.length()).trim().substring(0, 3);
					attendance.put(aldermanName, attendanceStr);
				}
			}
			if(line.contains("Legislatura")) {
				String[] sessionData = line.split(" - ");
				legislature = sessionData[0];
				session = sessionData[1];
				date = sessionData[2];
			}
			
		}
		aldermanAttendance = new AldermanAttendance(session, date, legislature, attendance);
		System.out.println(aldermanAttendance);
		return aldermanAttendance;
	}
}