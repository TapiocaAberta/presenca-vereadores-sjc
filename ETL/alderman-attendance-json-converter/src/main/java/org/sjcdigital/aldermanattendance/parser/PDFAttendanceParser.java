package org.sjcdigital.aldermanattendance.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
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
		Map<String, String> attendance = retrieveAttendance(pdfTextContent);
		String session = retrieveSessionType(pdfTextContent);
		Date date = retrieveSessionDate(pdfTextContent);
		return new AldermanAttendance(session, date, attendance);
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

	// TODO
	private String retrieveSessionType(String pdfTextContent) {
		return null;
	}

	// TODO
	private Map<String, String> retrieveAttendance(String pdfTextContent) {
		return null;
	}

	// TODO
	private Date retrieveSessionDate(String pdfTextContent) {
		return null;
	}

}