package siima.app.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.event.TreeSelectionListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.unical.mat.wrapper.DLVInvocationException;
import siima.app.asp.AspDlvReasoner;
import siima.app.asp.AspModelContainer;
import siima.app.gui.MainFrame;
import siima.app.model.JaxbContainer;
import siima.app.model.VeloContainer;
import siima.app.model.helper.UriHelper;
import siima.app.model.tree.ElementModel;
import siima.app.model.tree.ElementNode;
import siima.app.model.tree.ElementTree;
import siima.models.jaxb.caex.CAEXFile;
import siima.util.FileUtil;

public class MainAppController {
	private static final Logger logger = Logger.getLogger(MainAppController.class.getName());

	public JaxbContainer graphbuilder;
	public MainFrame viewFrame;
	// TREE: CAEXFile with InternalElement Hierarchy
	public ElementTree tree;
	public ElementModel treemodel;
	// TREE: CAEXFile with SystemUnitClassLib Hierarchy
	public ElementTree sucltree;
	public ElementModel sucltreemodel;
	// TREE: CAEXFile with RoleClassLib Hierarchy
	public ElementTree rolecltree;
	public ElementModel rolecltreemodel;
	// TREE: CAEXFile with RoleClassLib Hierarchy
	public ElementTree interfacecltree;
	public ElementModel interfacecltreemodel;

	private XSLTransform xslt;
	private AspDlvReasoner aspReasoner;
	// private AspModelContainer aspModel;

	private VeloContainer velocity;
	private ERAProject project;

	public MainAppController(MainFrame viewFrame) {
		this.viewFrame = viewFrame;
		this.graphbuilder = new JaxbContainer();
		this.xslt = new XSLTransform();
		this.aspReasoner = new AspDlvReasoner();
		this.project = new ERAProject();
		this.project.parseInitFile();
		this.graphbuilder.setValidationSchemaFile(this.project.getCaexValidationSchema());
		String lastproject = this.project.parseExitBackupFile("ResentProjectHome:");
		viewFrame.setEraProjectHomeDirectory(lastproject);
	}

	public void genereteCaexOntologyModel() {

		initVelocity();
		evaluateVelocityEngine();

	}

	public void initVelocity() {
		Object root = this.graphbuilder.getCaexRootObject();
		CAEXFile caex = (CAEXFile) root;
		velocity = new VeloContainer("caexfile", caex);
		/*
		 * FOR STRING FUNCTIONS: TOIMII
		 * https://stackoverflow.com/questions/6998412/velocity-string-function
		 * context.put("StringUtils", org.apache.commons.lang3.StringUtils.class);
		 * Functions can be seen from reference libraries class methods list (in package explorer on the left):
		 * 
		 */
		//TO SEARCH FUNCTIONS (nx100#):StringUtils.repreplace(text, searchString, replacement)
		velocity.putVelocityContext("StringUtils", org.apache.commons.lang3.StringUtils.class);
		/*
		 * CALLING MY OWN CLASS METHOD: TOIMII
		 * https://stackoverflow.com/questions/20786403/calling-a-java-method-in-velocity-template
		 */
		UriHelper helper = new UriHelper();
		velocity.putVelocityContext("UriHelper", helper); //siima.app.model.helper.UriHelper.class);
	}

	public void evaluateVelocityEngine() {

		velocity.evaluateEngine();
	}

	public ElementTree buildJaxbModel(String xmlfile) {
		// Example file: "data/caex_exs/RunningExample_SimpleIH.aml"
		Path path = Paths.get(xmlfile);
		// this.graphbuilder = new JaxbContainer(path);
		graphbuilder.loadData(path);
		ElementNode ieRootElement = graphbuilder.buildElementGraphFromXML();
		int iecount = ieRootElement.getChildCount();
		if (iecount > 0) {
			// Construct the jtree for InternalElement.
			this.treemodel = new ElementModel(ieRootElement);
			this.tree = new ElementTree(this.treemodel);
		}

		// Construct the jtree for SystemUnitClassLib hierarchy.
		ElementNode suclRootElement = graphbuilder.getSuclRootElement();
		int succount = suclRootElement.getChildCount();
		if (succount > 0) {
			this.sucltreemodel = new ElementModel(graphbuilder.getSuclRootElement());
			this.sucltree = new ElementTree(this.sucltreemodel);
		}

		// Construct the jtree for RoleClassLib hierarchy.
		ElementNode roleclRootElement = graphbuilder.getRoleclRootElement();
		int roleccount = roleclRootElement.getChildCount();
		if (roleccount > 0) {
			this.rolecltreemodel = new ElementModel(graphbuilder.getRoleclRootElement());
			this.rolecltree = new ElementTree(this.rolecltreemodel);
		}

		// Construct the jtree for RoleClassLib hierarchy.
		ElementNode interfaceclRootElement = graphbuilder.getIfaceclRootElement();
		int ifaceccount = interfaceclRootElement.getChildCount();
		if (ifaceccount > 0) {
			this.interfacecltreemodel = new ElementModel(graphbuilder.getIfaceclRootElement());
			this.interfacecltree = new ElementTree(this.interfacecltreemodel);
		}

		return tree;
	}

	public void setValidationSchema(String schemafile) {

		this.graphbuilder.setValidationSchemaFile(schemafile);

	}

	public void saveAspModel(String aspfile) {
		// System.out.println("= MainAppController: saveAspModel() SAVING to a
		// File: " + aspfile);
		this.aspReasoner.saveAspModelToFile(aspfile);

	}

	public void saveXMLModel(String xmlfile) {
		// System.out.println("= MainAppController: saveXMLModel() SAVING to a
		// File: " + xmlfile);
		this.graphbuilder.saveData(xmlfile);
		logger.info("saveXMLModel() SAVING to file: " + xmlfile);

	}

	public void xslTransform(String transformtype, String sourcefile, String targetfile) {
		/* transformtypes:"caex2jmonkey"; "caex2aspfacts" */
		if (sourcefile == null)
			sourcefile = graphbuilder.getMainFilePath().toString();
		if ((transformtype != null) && (sourcefile != null) && (targetfile != null)) {
			xslt.doSpecificTransform(transformtype, sourcefile, targetfile);
			// System.out.println("= MainAppController: xslTransform(): " +
			// transformtype);
			logger.info("xslTransform(): " + transformtype);
		}

	}

	public void invokeXslContextTransform() {

		this.xslt.doSpecificTransform("contextSrc2Trg", null, null);
		logger.info("invokeXslContextTransform()");
	}

	public void initXslContext(File[] xslContextfiles) {

		this.xslt.setXslContex(xslContextfiles);
		logger.info("initXslContext()");
	}

	public void invokeAspReasoner() {

		this.aspReasoner.invokeAspRules();
		logger.info("invokeAspReasoner()");

	}

	public void initAspModel(File[] aspfiles) {

		this.aspReasoner.getAspContainer().setRulesAndFacts(aspfiles);
		logger.info("initAspModel()");
	}
	
	public void setAspSolverEngine(String aspenginepath) {
		// exe file
		this.aspReasoner.setDlv_solver_path(aspenginepath);
		logger.info("setAspSolverEngine()");
	}
	
	public void openProjectInFolder(String openProjectDirectory){
		// TODO: open projects metadata(?) file to read latest configuration.
		logger.info("openProjectInFolder():");
		
	}
	
	public void saveProject(){
		//SAVING files into the current project directory
		logger.info("saveProject()");
		saveProjectInFolder(null);
		
	}
	
	
	public boolean saveProjectInFolder(String newProjectHomeDirectory) {
		// IF newProjectHomeDirectory ==null SAVE files into current project
		boolean ok = false;
		if (newProjectHomeDirectory != null) {
			// Creating new project folders and copying common files
			ok = this.project.createSubDirectoriesAndCopyFiles(newProjectHomeDirectory);
			if (ok){
				this.viewFrame.setEraProjectHomeDirectory(newProjectHomeDirectory);
				logger.info("saveProjectInFolder() New Project Home Directory created: " + newProjectHomeDirectory);
			}
		}
		// SAVING WORK FILES TO CURRENT OR NEW PROJECT FOLDERS
		String homedir = this.viewFrame.getEraProjectHomeDirectory();
		if ((homedir != null) && (!".".equalsIgnoreCase(homedir))) {
			// Saving essential files in work memory
			String subFolderCaex = "/data/caex";
			String maincaexname = null;
			Path maincaexpath = graphbuilder.getMainFilePath();
			// TODO: save all loaded files if not exist already (if not the same location)
			//List<Path> allLoadedPaths = graphbuilder.getLoadedCaexFilePaths();
					

			if (maincaexpath != null) {
				maincaexname = maincaexpath.getFileName().toString();
				String copyloc = homedir + subFolderCaex + "/" + maincaexname;
				if(copyloc.equalsIgnoreCase(maincaexpath.toString())){ //TODO?
					logger.info("saveProjectInFolder() Saveing to origal location?: " + copyloc);
				} else {
					logger.info("saveProjectInFolder() Saveing to DIFFERENT locations?:\n ORIG: " + maincaexpath.toString() + "\n NEW: "  + copyloc);
				}
				
				saveXMLModel(copyloc);
				logger.info("saveProjectInFolder() Main CAEX file saved into project: " + copyloc);
			}
		}
		return ok;

	}
	

	public boolean clearProject() {
		// TODO: to be called when new project is created
		boolean treesCleared = true;
		// Clear loaded file paths
		//Path mainfile = graphbuilder.getMainFilePath();
		List<Path> allfiles = graphbuilder.getLoadedCaexFilePaths();
		graphbuilder.setMainFilePath(null);
		graphbuilder.setLoadedCaexFilePaths(null);
		logger.info("clearProject():Cleared main File path");
		this.viewFrame.setEraProjectHomeDirectory("."); //New project dir undefined.
		// Clear Trees

		graphbuilder.clearRootElements();
		this.treemodel = null;
		this.tree = null;
		this.sucltreemodel = null;
		this.sucltree = null;
		this.rolecltreemodel = null;
		this.rolecltree = null;
		this.interfacecltreemodel = null;
		this.interfacecltree = null;
		logger.info("clearProject() All tree models cleared!");
		return treesCleared;
	}

	public void writeTextArea() {

		// viewFrame.
	}

	public String getSelectedElementInfo(int tabnumber) {
		String basicinfo = "";
		StringBuffer strbuff = new StringBuffer();
		strbuff.append("======= SELECTED ELEMENT ====== (TAB:" + (tabnumber + 1) + ")");
		ElementNode node = null;
		if (tabnumber == 0)
			node = treemodel.getLastSelectedNode();
		if (tabnumber == 1)
			node = sucltreemodel.getLastSelectedNode();
		if (tabnumber == 2)
			node = rolecltreemodel.getLastSelectedNode();
		if (tabnumber == 3)
			node = interfacecltreemodel.getLastSelectedNode();
		
		if (node != null) {
			// System.out.println("= MainAppController:
			// getSelectedElementInfo(): " + node.getName());
			logger.info("getSelectedElementInfo(): " + node.getName());
			
			if("CAEXFile".equals(node.getNodetype())){				
				String fileJaxbObject = (String)node.getJaxbObject();
				basicinfo = fileJaxbObject;
			} else {			
				Object element = node.getJaxbObject();
				basicinfo = graphbuilder.getBasicInfo(element);
			}
			strbuff.append(basicinfo);

		}
		strbuff.append("\n");

		return strbuff.toString();
	}

	public boolean exitBackup(){
		//eraProjectHomeDirectory
		String latestHomeDir = viewFrame.getEraProjectHomeDirectory();
		StringBuffer buf = new StringBuffer();
		buf.append("% Backup of the latest project run.\n");
		buf.append("#ResentProjectHome:\n");
		buf.append("directory:" + latestHomeDir + "\n");
		buf.append("#end\n");
		FileUtil.writeTextFile(buf.toString(), "./configure/exitbackup.meta");
		
		return true;
	}
	
	public ElementTree getTree() {
		return tree;
	}

	public ElementTree getSucltree() {
		return sucltree;
	}

	public ElementTree getRolecltree() {
		return rolecltree;
	}

	public ElementTree getInterfacecltree() {
		return interfacecltree;
	}

}