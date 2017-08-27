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

	final static AttendanceParser PDFParser = new PDFAttendanceParser();
	final static AttendanceParser XLSParser = new XLSAttendanceParser();

	/**
	 * Receives a file and return the alderman absence/presence on the given
	 * file session
	 * 
	 * @param targetFile
	 * @return
	 */
	public AldermanAttendance parse(Path targetFile);

	public static AttendanceParser getParserForType(Path path) {
		String pathStr = path.toString();
		if (pathStr.toLowerCase().endsWith("pdf"))
			return PDFParser;
		if (pathStr.toLowerCase().endsWith("xls"))
			return XLSParser;
		throw new IllegalArgumentException("Could not find a suitable parser for file: " + path);
	}

}