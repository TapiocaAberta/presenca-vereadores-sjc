package org.sjcdigital.aldermanattendance.parser;

import java.io.IOException;
import java.nio.file.Path;

import org.sjcdigital.aldermanattendance.model.AldermanAttendance;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/**
 * A specific parse for PDF. Keeping here for history
 * 
 * @author william
 *
 */
public class PDFAttendanceParser implements AttendanceParser {

	@Override
	public AldermanAttendance parse(Path targetFile) {
		String pdfTextContent = getPdfContent(targetFile.toString());
		return ParserUtils.fromText(pdfTextContent);
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
}