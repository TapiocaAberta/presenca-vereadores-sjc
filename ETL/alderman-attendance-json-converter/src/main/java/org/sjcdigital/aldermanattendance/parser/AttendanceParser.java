package org.sjcdigital.aldermanattendance.parser;

import java.nio.file.Path;

import org.sjcdigital.aldermanattendance.model.AldermanAttendance;

/**
 * 
 * Parse session presence files coming from the city council website
 * 
 * @author william
 *
 */
public interface AttendanceParser {

	final static AttendanceParser ANYParser = new AnyDocumentAttendanceParser();
	final static AttendanceParser PDFParser = new PDFAttendanceParser();

	/**
	 * Receives a file and return the alderman absence/presence on the given
	 * file session. Keeping for history because we will likely use the ANYParser for all files
	 * 
	 * @param targetFile
	 * @return
	 */
	public AldermanAttendance parse(Path targetFile);

	public static AttendanceParser getParserForType(Path path) {
		String pathStr = path.toString();
		if (pathStr.toLowerCase().endsWith("pdf"))
			return PDFParser;
		else
			return ANYParser;
	}

}