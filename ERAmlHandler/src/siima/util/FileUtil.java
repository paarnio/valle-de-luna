package siima.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	public static StringBuffer readTextFile(String line_end, String filepath) {
		StringBuffer sbuf = new StringBuffer();
		String jsonString;
		try {
			// Does file exist?
			File pfile = new File(filepath);
			if (pfile.exists()) {

				BufferedReader br = new BufferedReader(new FileReader(filepath));
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					// System.out.println(sCurrentLine + line_end);
					sbuf.append(sCurrentLine + line_end);
				}
			} else {
				System.out.println("- FileUtil:readTextFile() File does not exist: " + filepath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbuf;
	}

	public static void writeTextFile(String text, String filepath) {
		File fl = new File(filepath);

		try {
			// FileOutputStream fout=new FileOutputStream(fl);
			// BufferedOutputStream bfout=new BufferedOutputStream(fout);
			FileWriter fwr = new FileWriter(fl);
			fwr.write(text);
			fwr.flush();
			fwr.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileUtil.writeTextFile("teksti", "./data/generated/asp_models/asp_testresults.txt");
	}

}
