package org.sjcdigital.aldermanattendance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.sjcdigital.aldermanattendance.model.AldermanAttendance;
import org.sjcdigital.aldermanattendance.parser.AttendanceParser;

public class TransforAttendanceFilesInformationToJson {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("The path to the files downloaded from city council page is required.");
			System.exit(0);
		}
		String pathToFiles = args[0];
		Path filsPath = Paths.get(pathToFiles);
		List<AldermanAttendance> attendance = Files.walk(filsPath).filter(p -> !Files.isDirectory(p))
				.map(f -> AttendanceParser.getParserForType(f).parse(f)).collect(Collectors.toList());
		// TODO: After implementing the parsers we must export it to JSON to be
		// used in the web page
	}

}