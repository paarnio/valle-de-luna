package siima.app.control;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import siima.util.FileUtil;

public class ERAProject {
	private static final Logger logger = Logger.getLogger(ERAProject.class.getName());
	
	public static String initfile = "./configure/eraproject.ini";
	public static String exitbackupfile = "./configure/exitbackup.meta";
	public String caexValidationSchema; //"configure/schema/caex_2.1.5_orig/CAEX_ClassModel_V2.15.xsd";
	public static List<String> copydirsrcpaths = new ArrayList<String>();
	public static List<String> copydirtrgpaths = new ArrayList<String>();
	
	public static List<String> copyfilesrcpaths = new ArrayList<String>();
	public static List<String> copyfiletrgpaths = new ArrayList<String>();
	
	public boolean initfileparsed=false;
	
	
	public boolean createSubDirectoriesAndCopyFiles(String projectHomeDirectory) {
		boolean ok = false;

		// Prevent Project saving under this tool directory (ERAmlHandler)
		File toolHome = new File(".");
		String toolpath_dot = toolHome.getAbsolutePath();
		String toolpath = toolpath_dot.substring(0, toolpath_dot.length() - 2);
		if (projectHomeDirectory.startsWith(toolpath)) {
			logger.info("saveProjectInFolder() TOOL PATH FORBIDDEN: " + toolpath);
			return ok;
		}

		if (initfileparsed) {
			// Create/copy Project Sub Directories
			if ((!copydirsrcpaths.isEmpty()) && (copydirsrcpaths.size() == copydirtrgpaths.size())) {
				for (int i = 0; i < copydirsrcpaths.size(); i++) {
					Path source = Paths.get(copydirsrcpaths.get(i));
					if(source.toFile().exists()){
						Path target = Paths.get(projectHomeDirectory + copydirtrgpaths.get(i));
						ok = siima.util.CopyFiles.copyFile(source, target, false, true);
					} else {		
						logger.log(Level.ERROR,"saveProjectInFolder() SOURCE DIRECTORY TO COPY DOES NOT EXIST: " + source.toString());
					}
				}
			}
			// Copy Files
			if ((ok) && (!copyfilesrcpaths.isEmpty()) && (copyfilesrcpaths.size() == copyfiletrgpaths.size())) {
				for (int i = 0; i < copyfilesrcpaths.size(); i++) {
					Path source = Paths.get(copyfilesrcpaths.get(i));
					if(source.toFile().exists()){
						Path target = Paths.get(projectHomeDirectory + copyfiletrgpaths.get(i));
						ok = siima.util.CopyFiles.copyFile(source, target, false, true);
					} else {		
						logger.log(Level.ERROR,"saveProjectInFolder() SOURCE DIRECTORY TO COPY DOES NOT EXIST: " + source.toString());
					}
				}

			}
		}
		return ok;
	}
	
	
	public void parseInitFile(){
		logger.info("parseInitFile() reading initfile: " + initfile);
		StringBuffer sbuf = FileUtil.readTextFile("\n", initfile);
		//System.out.println("==INIFILE CONTENT: " + sbuf.toString());
		
		String[] blocks = sbuf.toString().split("#");
		for (int i = 0; i < blocks.length; i++) {
			String block = blocks[i];
			
			if (block.startsWith("CAEXValidation:")) {
				StringBuffer dirblockbuf = new StringBuffer();
				dirblockbuf.append(block);
				this.caexValidationSchema= parseBlockBuffer(dirblockbuf, "CAEXValidation:", null, null);
				
			} else if (block.startsWith("CopyDirectories:")) {
				StringBuffer dirblockbuf = new StringBuffer();
				dirblockbuf.append(block);
				parseBlockBuffer(dirblockbuf, "CopyDirectories:", copydirsrcpaths, copydirtrgpaths);
				
			} else if (block.startsWith("CopyFiles:")) {
				StringBuffer fileblockbuf = new StringBuffer();
				fileblockbuf.append(block);
				parseBlockBuffer(fileblockbuf, "CopyFiles:", copyfilesrcpaths, copyfiletrgpaths);
				
			}
			
		}
		initfileparsed=true;
	}

	private String parseBlockBuffer(StringBuffer sbuf, String blocktype, List<String> copysrcpaths,
			List<String> copytrgpaths) {
		String singleValue=null;
		
		String[] lines = sbuf.toString().split("\n");
		// System.out.println("==LINES #: " + lines.length);
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (("CopyDirectories:".equalsIgnoreCase(blocktype))||("CopyFiles:".equalsIgnoreCase(blocktype))) {
				String src = null;
				String trg = null;
				// System.out.println("==LINE: " + i + " = " + line);
				if (i < lines.length - 1) {
					if (line.startsWith("copysource:")) {
						src = line.split("copysource:")[1];
						// System.out.println("==SRC: " + src);
						if (lines[i + 1].startsWith("copytarget:")) {
							trg = lines[i + 1].split("copytarget:")[1];
							i++;
							// System.out.println("==TRG: " + trg);
						}
					}
					if ((src != null) && (trg != null)) {
						copysrcpaths.add(src);
						copytrgpaths.add(trg);
					}
				}
			} else if (("CAEXValidation:".equalsIgnoreCase(blocktype))) {
				if (line.startsWith("caexschema:")) {
					singleValue = line.split("caexschema:")[1];
				}

			} else if (("ResentProjectHome:".equalsIgnoreCase(blocktype))) {
				if (line.startsWith("directory:")) {
					singleValue = line.split("directory:")[1];
				}

			}
		}
		return singleValue;
	}
	
	
	public String parseExitBackupFile(String linekey) {
		// linekey e.g. ResentProjectHome: (note colon)
		logger.info("parseExitBackupFile() reading exitbackup: " + exitbackupfile);
		String value = null;
		StringBuffer sbuf = FileUtil.readTextFile("\n", exitbackupfile);
		// System.out.println("==INIFILE CONTENT: " + sbuf.toString());

		if ((sbuf!=null)&&(sbuf.length() > 0)) {
			String[] blocks = sbuf.toString().split("#");
			for (int i = 0; i < blocks.length; i++) {
				String block = blocks[i];

				if (block.startsWith(linekey)) { // ResentProjectHome:
					StringBuffer dirblockbuf = new StringBuffer();
					dirblockbuf.append(block);					
					value = parseBlockBuffer(dirblockbuf, linekey, null, null);

				}

			}
		}
		logger.info("parseExitBackupFile() " + linekey + " value=" + value);
		return value;
	}
	
	public boolean tODOcreateDirectories(String newProjectHomeDirectory) { // NOT
																			// IN
																			// USE
		boolean ok = false;
		String subFolderProject = "ERAProject";
		String subFolderConfigure = "ERAProject/configure"; // "ERAProject\\configure";
		String subFolderCaex = "caex";
		String subFolderData = "data";
		String subFolderXsl = "data/xsl_context"; // "data\\xsl_context";

		String maincaexname = null;
		
		// Prevent Project saving under this tool directory (ERAmlHandler)
		File toolHome = new File(".");
		String toolpath_dot = toolHome.getAbsolutePath();
		String toolpath = toolpath_dot.substring(0, toolpath_dot.length() - 2);
		if (newProjectHomeDirectory.startsWith(toolpath)) {
			logger.info("saveProjectInFolder() TOOL PATH FORBIDDEN: " + toolpath);
			return ok;
		}
		ok = true;
		logger.info("saveProjectInFolder() New Project Home Directory" + newProjectHomeDirectory);
		File projectDir = new File(newProjectHomeDirectory);
		if (projectDir.exists()) {
			File subDir = new File(newProjectHomeDirectory + "/" + subFolderProject); // "\\"
			if (!subDir.exists()) {
				if (subDir.mkdir()) {
					logger.info("saveProjectInFolder() New Project Sub Directory" + subDir.toString());
					subDir = new File(newProjectHomeDirectory + "/" + subFolderConfigure);
					if (!subDir.exists()) {
						if (subDir.mkdir()) {
							logger.info("saveProjectInFolder() New Project Sub Directory" + subDir.toString());
						}
					} else
						logger.info("saveProjectInFolder() Sub Directory already exists: " + subDir.toString());
				}
			} else
				logger.info("saveProjectInFolder() Sub Directory already exists: " + subDir.toString());

			subDir = new File(newProjectHomeDirectory + "/" + subFolderCaex);
			if (!subDir.exists()) {
				if (subDir.mkdir()) {
					logger.info("saveProjectInFolder() New Project Sub Directory: " + subDir.toString());
				}
			} else
				logger.info("saveProjectInFolder() Sub Directory already exists: " + subDir.toString());

			subDir = new File(newProjectHomeDirectory + "/" + subFolderData);
			if (!subDir.exists()) {
				if (subDir.mkdir()) {
					logger.info("saveProjectInFolder() New Project Sub Directory" + subDir.toString());
					subDir = new File(newProjectHomeDirectory + "/" + subFolderXsl);
					if (!subDir.exists()) {
						if (subDir.mkdir()) {
							logger.info("saveProjectInFolder() New Project Sub Directory" + subDir.toString());
						}
					} else
						logger.info("saveProjectInFolder() Sub Directory already exists: " + subDir.toString());
				}
			} else
				logger.info("saveProjectInFolder() Sub Directory already exists: " + subDir.toString());
		}
		return ok;
	}
	
	
	
	public String getCaexValidationSchema() {
		return caexValidationSchema;
	}


	public static void main(String[] args) {
		ERAProject era = new ERAProject();
		era.parseInitFile();
		String newprojecthome ="C:/javaopenlab/ecl_work_neon/tmp/eraproject";
		era.createSubDirectoriesAndCopyFiles(newprojecthome);
	}

}
