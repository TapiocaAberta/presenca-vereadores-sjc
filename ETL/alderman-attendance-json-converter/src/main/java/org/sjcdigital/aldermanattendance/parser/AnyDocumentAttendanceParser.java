package org.sjcdigital.aldermanattendance.parser;

import java.io.IOException;
import java.nio.file.Path;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.sjcdigital.aldermanattendance.model.AldermanAttendance;

/**
 * Parser for any document using ApacheTika
 * @author wsiqueir
 *
 */
public class AnyDocumentAttendanceParser implements AttendanceParser {

	
	@Override
	public AldermanAttendance parse(Path targetFile) {
		try {
			Tika tika = new Tika();
			String content = tika.parseToString(targetFile.toFile());
			AldermanAttendance aldermanAttendance = ParserUtils.fromText(content);
			return aldermanAttendance;
		} catch (IOException | TikaException e) {
			e.printStackTrace();
			return null;
		}
	}

}
