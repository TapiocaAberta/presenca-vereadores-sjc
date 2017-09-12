package org.sjcdigital.aldermanattendance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.sjcdigital.aldermanattendance.model.AldermanAttendance;
import org.sjcdigital.aldermanattendance.parser.AnyDocumentAttendanceParser;
import org.sjcdigital.aldermanattendance.parser.ParserUtils;

public class TransformAttendanceFilesInformationToJson {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("The path to the files downloaded from city council page is required.");
			System.exit(0);
		}
		String pathToFiles = args[0];
		Path filsPath = Paths.get(pathToFiles);
		AnyDocumentAttendanceParser anyDocumentParser = new AnyDocumentAttendanceParser();
		List<AldermanAttendance> attendance = Files.walk(filsPath).filter(p -> !Files.isDirectory(p))
				.map(anyDocumentParser::parse).filter(a -> a != null).collect(Collectors.toList());
		StringBuffer sbJson = new StringBuffer();
		sbJson.append("{");
		sbJson.append("\"sessionAttendance\": [");
		for (AldermanAttendance a : attendance) {
			sbJson.append("{");
			sbJson.append("\"session\": \"");
			sbJson.append(a.getSession());
			sbJson.append("\", ");
			sbJson.append("\"legislature\": \"");
			sbJson.append(a.getLegislature());
			sbJson.append("\", ");
			sbJson.append("\"date\": \"");
			sbJson.append(a.getDate());
			sbJson.append("\", ");
			
			sbJson.append("\"attendance\": {");
			a.getAttendance().forEach((name, presence) -> {
				sbJson.append("\"");
				sbJson.append(name);
				sbJson.append("\":");
				sbJson.append("\"");
				sbJson.append(presence);
				sbJson.append("\",");
			});
			sbJson.deleteCharAt(sbJson.length() - 1);
			sbJson.append("}},");
		}
		sbJson.deleteCharAt(sbJson.length() - 1);
		sbJson.append("]}");
		String outputJson = sbJson.toString();
		System.out.println(sbJson.toString());
		System.out.println(ParserUtils.aldermans.stream().collect(Collectors.joining()));
		Files.write(Paths.get("./sessionAttendance.json"), outputJson.getBytes(StandardCharsets.UTF_8));
	}

}