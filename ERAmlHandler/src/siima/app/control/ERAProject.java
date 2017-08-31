package siima.app.control;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import siima.util.FileUtil;

public class ERAProject {
	private static final Logger logger = Logger.getLogger(ERAProject.class.getName());
	
	public static String initfile = "./configure/eraproject.ini";
	public static String exitbackupfile = "./configure/exitbackup.meta";
	
	public String currentProjectHome;
	public String caexValidationSchema = "configure/schema/caex_2.1.5_orig/CAEX_ClassModel_V2.15.xsd";
	public String caexSchemaVersion = "2.15"; // 2.15 or 3.0
	public static List<String> copydirsrcpaths = new ArrayList<String>();
	public static List<String> copydirtrgpaths = new ArrayList<String>();
	
	public static List<String> copyfilesrcpaths = new ArrayList<String>();
	public static List<String> copyfiletrgpaths = new ArrayList<String>();
	
	public boolean initfileparsed=false;
	
	public boolean openProject(String openProjectDirectory){
		// Open project.meta file to read latest configuration.
		boolean ok=false;
		File metafile = new File(openProjectDirectory + "/" + "project.meta");
		if(metafile.exists()){//project.meta file should exist, If correct era-project folder
			setCurrentProjectHome(openProjectDirectory);
			String caexVersion = parseProjectMetaFile(metafile.getAbsolutePath(), "caex_version");
			setCaexSchemaVersion(caexVersion);
			ok=true;
			logger.info("openProject(): Project Opened in folder:" + openProjectDirectory + " caex version:" + caexVersion);
			
		 } else {
			logger.info("openProject() ??? NOT a Project Home Directory: project.meta does not exist " + openProjectDirectory);
		 }
		return ok;
	}
	
	public boolean createNewProject(String newProjectHomeDirectory, String caexVersion) {
		boolean ok = false;
		ok = createSubDirectoriesAndCopyFiles(newProjectHomeDirectory);
		if(ok){			
			createProjectMetaFile(newProjectHomeDirectory, caexVersion);
			setCurrentProjectHome(newProjectHomeDirectory);
			setCaexSchemaVersion(caexVersion);
		}
		
		return ok;
	}
	
	public boolean createProjectMetaFile(String projectHomeDirectory, String caexVersion) {
		boolean ok = false;
		// Creating and Saving project.meta file
		StringBuffer metabuf = new StringBuffer();
		Path folderpath = Paths.get(projectHomeDirectory);
		folderpath.getFileName();
		metabuf.append("% ERAmlHandler project metadata file.");
		metabuf.append("\n#Project:");
		metabuf.append("\nname:" + folderpath.getFileName());
		// http://howtodoinjava.com/core-java/date-time/java-get-current-datetime-examples/
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedDateTime = today.format(formatter);
		metabuf.append("\ncreated:" + formattedDateTime);
		metabuf.append("\ncaex_version:" + caexVersion); //this.caexSchemaVersion);
		metabuf.append("\n#end\n");
		FileUtil.writeTextFile(metabuf.toString(), projectHomeDirectory + "/" + "project.meta");
		logger.info("createProjectMetaFile() project.meta file created.");
		return ok = true;
	}
	
	public boolean createSubDirectoriesAndCopyFiles(String projectHomeDirectory) {
		boolean ok = false;

		// Prevent Project saving under this tool directory (ERAmlHandler)
		File toolHome = new File(".");
		String toolpath_dot = toolHome.getAbsolutePath();
		String toolpath = toolpath_dot.substring(0, toolpath_dot.length() - 2);
		if (projectHomeDirectory.startsWith(toolpath)) {
			logger.info("createSubDirectoriesAndCopyFiles() TOOL PATH FORBIDDEN: " + toolpath);
			return false;
		}
		
		//createProjectMetaFile(projectHomeDirectory);
		
		/* (2017-07-13) Creating and Saving project.meta file
		StringBuffer metabuf = new StringBuffer();		
		Path folderpath = Paths.get(projectHomeDirectory);
		folderpath.getFileName();
		metabuf.append("% ERAmlHandler project metadata file.");
		metabuf.append("\n#Project:");
		metabuf.append("\nname:" + folderpath.getFileName());
		// http://howtodoinjava.com/core-java/date-time/java-get-current-datetime-examples/
		LocalDateTime today = LocalDateTime.now();	 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedDateTime = today.format(formatter);		
		metabuf.append("\ncreated:" + formattedDateTime);
		metabuf.append("\ncaex_version:" + "?");
		metabuf.append("\n#end\n");
		FileUtil.writeTextFile(metabuf.toString(), projectHomeDirectory + "/" + "project.meta");
		*/
		
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
			
			if (block.startsWith("CAEXSchemaVersion:")) {
				StringBuffer dirblockbuf = new StringBuffer();
				dirblockbuf.append(block);
				this.caexSchemaVersion= parseBlockBuffer(dirblockbuf, "CAEXSchemaVersion:", null, null);
				
			} else if (block.startsWith("CAEXValidation:")) {
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
				//Block in eraproject.ini file
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
			} else if (("CAEXSchemaVersion:".equalsIgnoreCase(blocktype))) {
				//Block in eraproject.ini file
				if (line.startsWith("version:")) {
					singleValue = line.split("version:")[1];
				}

			} else if (("CAEXValidation:".equalsIgnoreCase(blocktype))) {
				//Block in eraproject.ini file
				if (line.startsWith("caexschema:")) {
					singleValue = line.split("caexschema:")[1];
				}

			} else if (("ResentProjectHome:".equalsIgnoreCase(blocktype))) {
				//Block in exitbackup.meta file
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
	
	public String parseProjectMetaFile(String metafile, String fieldkey){
		//project.meta 
		// fieldkey e.g. 'caex_version'
		String singleValue=null;
		
		logger.info("parseProjectMetaFile() reading metafile: " + metafile);
		StringBuffer sbuf = FileUtil.readTextFile("\n", metafile);
		//System.out.println("==INIFILE CONTENT: " + sbuf.toString());
		
		String[] blocks = sbuf.toString().split("#");
		for (int i = 0; i < blocks.length; i++) {
			String block = blocks[i];			
			if (block.startsWith("Project:")) {
				StringBuffer blockbuf = new StringBuffer();
				blockbuf.append(block);
				String[] lines = blockbuf.toString().split("\n");
				// System.out.println("==LINES #: " + lines.length);
				for (int k = 0; k < lines.length; k++) {
					String line = lines[k];
					if (line.startsWith(fieldkey + ":")) { //"caex_version:"
						singleValue = line.split(fieldkey + ":")[1];
						 
					}
				}
			}
		}
		return singleValue;
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
	
	
	
	public String getCaexSchemaVersion() {
		return caexSchemaVersion;
	}

	public void setCaexSchemaVersion(String caexSchemaVersion) {
		this.caexSchemaVersion = caexSchemaVersion;
	}

	public String getCaexValidationSchema() {
		return caexValidationSchema;
	}

	

	public String getCurrentProjectHome() {
		return currentProjectHome;
	}

	public void setCurrentProjectHome(String currentProjectHome) {
		this.currentProjectHome = currentProjectHome;
	}

	public void setCaexValidationSchema(String caexValidationSchema) {
		this.caexValidationSchema = caexValidationSchema;
	}

	public static void main(String[] args) {
		ERAProject era = new ERAProject();
		era.parseInitFile();
		String newprojecthome ="C:/javaopenlab/ecl_work_neon/tmp/eraproject";
		era.createSubDirectoriesAndCopyFiles(newprojecthome);
	}

}
