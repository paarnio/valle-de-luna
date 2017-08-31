/* MainAppController.java
 * 
 * //---- CAEX 3.0 WOULD REQUIRE CHANGES
 * 
 */

package siima.app.control;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.event.TreeSelectionListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import it.unical.mat.wrapper.DLVInvocationException;
import siima.app.asp.AspDlvReasoner;
import siima.app.asp.AspModelContainer;
import siima.app.gui.MainFrame;
//OPTIONAL CEAX SCHEMA VERSIONS 2.15 AND 3.0
//---- CAEX 3.0 Would REQUIRE CHANGES
import siima.app.model.JaxbContainer;
import siima.app.model.JaxbContainerCaex3;
//import siima.app.model.JaxbContainerCaex3;
import siima.app.model.JaxbContainerInterface;
import siima.app.model.RdfContainer;
import siima.app.model.VeloContainer;
import siima.app.model.helper.AnyTypeValueHelper;
import siima.app.model.helper.UriHelper;
import siima.app.model.tree.ElementModel;
import siima.app.model.tree.ElementNode;
import siima.app.model.tree.ElementTree;
import siima.models.jaxb.caex.CAEXFile;
import siima.spin.CommandFileSpinMng;
import siima.util.FileUtil;

public class MainAppController {
	private static final Logger logger = Logger.getLogger(MainAppController.class.getName());
	//---- CAEX 3.0 WOULD REQUIRE CHANGES
	public JaxbContainerInterface graphbuilder;
	//public JaxbContainerCaex graphbuilder;
	//public JaxbContainerCaex3 graphbuilder;
	public MainFrame viewFrame;
	// TREE: CAEXFile with InternalElement Hierarchy
	public ElementTree instanceHtree;
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
	
	private RdfContainer rdfContainer;
	private CommandFileSpinMng spinMng;

	public MainAppController(MainFrame viewFrame) {
		this.viewFrame = viewFrame;
		this.project = new ERAProject();
		this.project.parseInitFile();
		String lastproject = this.project.parseExitBackupFile("ResentProjectHome:");
		if(lastproject!=null)
			viewFrame.setEraProjectHomeDirectory(lastproject);
		
		this.xslt = new XSLTransform();
		this.aspReasoner = new AspDlvReasoner();
		
		/* NOTE: this.graphbuilder and this.rdfContainer
		 * objects are created in methods
		 * createNewProject() and openProject()
		 * REASON: this.graphbuilder is CAEX version dependent
		 */
		//TODO: Read from ERAinit file which version to use!!
		//---- CAEX 3.0 WOULD REQUIRE CHANGES
		/*this.graphbuilder = new JaxbContainer(); //CEAX V. 2.15
		this.graphbuilder = new JaxbContainerCaex3(); //CEAX V. 3.0
		this.graphbuilder.setValidationSchemaFile(this.project.getCaexValidationSchema());
		this.rdfContainer = new RdfContainer(graphbuilder);	//---- CAEX 3.0 WOULD REQUIRE CHANGES
		*/
	}

	public void saveCSMCommandsToJsonFile(String jsonfile){
		
		StringBuffer sbuf = getCSMCommandContent(false);
		FileUtil.writeTextFile(sbuf.toString(), jsonfile);
		
	}
	
	public StringBuffer getCSMCommandContent(boolean updated){
		//If updated = true, returns content only if CSMCommands
		// have been resently updated in json root object.
		StringBuffer sbuf =null;
		if(updated){
			if(spinMng.isCsmcommand_updated()){
				sbuf=spinMng.parseCSMCommandJsonRoot();
				spinMng.setCsmcommand_updated(false);
			}
		} else {
			sbuf=spinMng.parseCSMCommandJsonRoot();
		}
		
		return sbuf;
	}
	
	public void updateCSMHeaderJsonObject(Map<String,String> fieldKeyDataMap ){
		//Update only CSMHeader i.e.indexes sequence
		spinMng.updateCSMHeaderJsonObject(fieldKeyDataMap);//update inxsequence
		
	}
	
	public void updateCSMCommandJsonObject(Map<String,String> fieldKeyDataMap ){
		//Update CSMCommands and CSMHeader i.e.indexes sequence
		spinMng.updateCSMCommandJsonObject(fieldKeyDataMap);
		updateCSMHeaderJsonObject(fieldKeyDataMap);//update inxsequence
		//spinMng.updateCSMHeaderJsonObject(fieldKeyDataMap);
		
	}
	
	public Map<String,String> searchCSMCommandContent(String idcode, String index, String commandtype){
		//TODO:
		logger.info("searchCommandJsonString()");
			
		//StringBuffer sbuf = new StringBuffer();
		Map<String,String> fieldKeyDataMap = spinMng.searchCSMCommandContent(idcode, index, commandtype);
		//String jsonstr = spinMng.searchCommandJsonString(null,null,"loadKnowledgeBase");
		//spinMng.searchCommandJsonString("R1_T2_CIM_1",null,null);
		//spinMng.searchCommandJsonString(null,"2",null);
		//sbuf.append(jsonstr);
		return fieldKeyDataMap;
	}
	
	public StringBuffer invokeCSMCommandWorkflow(){
		//TODO: 
		logger.info("invokeCSMCommandWorkflow() invoking CSMCommand workflow: ");
		if(spinMng!=null){
		spinMng.setWorkflowResults(new StringBuffer());
		StringBuffer stepnotebuffer= spinMng.mainInvokeCommandWorkflow();
		StringBuffer workflowResults = spinMng.getWorkflowResults();
		//System.out.println("-----invokeCSMCommandWorkflow()----\n" + workflowResults.toString());
		String wflowsketch = "\n(start)-" + stepnotebuffer.toString() + "->(end)";
		return workflowResults.append(wflowsketch);
		}
		return null;
	}
	
	
	
	public StringBuffer initCommandFileSpinMng(String commandfile){
		//TODO: 
		logger.info("initCommandFileSpinMng() reading spin command file: " + commandfile);
		StringBuffer sbuf = FileUtil.readTextFile("\n", commandfile);
		
		spinMng = new CommandFileSpinMng();
		spinMng.mainInitSpinManager(commandfile);
		spinMng.setWorkflowResults(new StringBuffer());
		//spinMng.mainInvokeCommandWorkflow();
		//StringBuffer workflowResults = spinMng.getWorkflowResults();
		
		return sbuf;
	}
	
	
	
	public boolean genereteCaexOntologyModel(String modelkey) {
		boolean ok = true;
		this.rdfContainer.genereteCaexOntologyModel(modelkey);
		return ok;

	}

	public String  getSerializeRdfModel(String format) {
		/* format: e.g. "TURTLE"; TTL; RDFXML; (RDFJSON; NTRIPLES)
		 * https://jena.apache.org/documentation/io/rdf-output.html#formats
		 */
		
		String serialized = this.rdfContainer.getSerializedRdfModel(format);
		logger.log(Level.INFO,"getSerializeRdfModel()");
		return serialized;
	}
	
	public void clearRDFModels(boolean partials, boolean combined, boolean merged){
		
		this.rdfContainer.clearRDFModels(partials, combined, merged);
		
	}
	
	public boolean openCaexFile(String xmlfile, String caexVersion){
		// TODO: Set version before calling EI TAIDA Onnistua näin
		boolean ok = true;
		buildJaxbModel(xmlfile);
		return ok;
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
			this.instanceHtree = new ElementTree(this.treemodel);
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

		return instanceHtree;
	}

	public void setValidationSchema(String schemafile) {

		this.graphbuilder.setValidationSchemaFile(schemafile);

	}

	public void mergeExistingRDFModels(){
		
		rdfContainer.mergeRDFModels();
		
	}
	
	public void saveCaexOntologyModel(String ontologyfile) {
		// System.out.println("= MainAppController: saveCaexOntologyModel() SAVING to a
		// File: " + ontologyfile);
		//velocity.writeRdfModelToFile(ontologyfile);
		rdfContainer.writeRdfModelToFile(ontologyfile);

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

		this.xslt.doSpecificTransform("contextSrc2Trout", null, null);
		logger.info("invokeXslContextTransform()");
	}

	public void initXslContext(File[] xslContextfiles) {

		this.xslt.setXslContex(xslContextfiles);
		logger.info("initXslContext()");
	}

	public StringBuffer invokeAspReasoner() {
		
		this.aspReasoner.invokeAspRules();
		logger.info("invokeAspReasoner()");
		// Params: (int nrmodels, boolean all) : number of models to access OR all of them; 
		StringBuffer strmodels = this.aspReasoner.getAspModelsAsString(1, true); //how many models included
		//System.out.println("==== MainAppController: invokeAspReasoner() ===\n " + strmodels.toString());
		return strmodels;
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
	
	public boolean openProjectInFolder(String openProjectDirectory){
		boolean ok=false;
		ok = this.project.openProject(openProjectDirectory);
		if(ok){
			String caexVersion= this.project.getCaexSchemaVersion();
			if("2.15".equals(caexVersion)){
				this.graphbuilder = new JaxbContainer(); //CEAX V. 2.15
			} else if("3.0".equals(caexVersion)){
				this.graphbuilder = new JaxbContainerCaex3(); //CEAX V. 3.0
			} else {
				logger.info("openProjectInFolder(): ??? CAEX Schema version not suported: " + caexVersion);
			}
			this.graphbuilder.setValidationSchemaFile(this.project.getCaexValidationSchema());
			this.rdfContainer = new RdfContainer(graphbuilder);	
			//clearRDFModels(true,true,true);
			logger.info("openProjectInFolder(): Project Opened in folder:" + openProjectDirectory + "with version " + caexVersion);
		 } else {
			logger.info("openProjectInFolder(): ??? NOT a Project Home Directory: project.meta does not exist " + openProjectDirectory);
		 }
		return ok;
	}
	
	public void saveProject(){
		//SAVING files into the current project directory
		logger.info("saveProject()");
		saveProjectInFolder(null);
		
	}
	
	public boolean createNewProject(String newProjectHomeDirectory, String caexVersion) {
		// IF newProjectHomeDirectory ==null SAVE files into current project
		boolean ok = false;
		if (newProjectHomeDirectory != null) {
			// Creating new project folders and copying common files
			//ok = this.project.createSubDirectoriesAndCopyFiles(newProjectHomeDirectory);
			ok = this.project.createNewProject(newProjectHomeDirectory, caexVersion);
			if (ok){
				this.project.setCaexSchemaVersion(caexVersion);
				if("2.15".equals(caexVersion)){
					this.graphbuilder = new JaxbContainer(); //CEAX V. 2.15
				} else if("3.0".equals(caexVersion)){
					this.graphbuilder = new JaxbContainerCaex3(); //CEAX V. 3.0
				}
				this.graphbuilder.setValidationSchemaFile(this.project.getCaexValidationSchema());
				this.rdfContainer = new RdfContainer(graphbuilder);		
				//clearRDFModels(true,true,true);
				logger.info("createNewProject() New Project Home Directory created: " + newProjectHomeDirectory);
			}
		}
		return ok;
	}
	
	public boolean saveProjectInFolder(String newProjectHomeDirectory) {
		// IF newProjectHomeDirectory ==null SAVE files into current project
		boolean ok = false;
		if (newProjectHomeDirectory != null) {
			// Creating new project folders and copying common files
			ok = this.project.createSubDirectoriesAndCopyFiles(newProjectHomeDirectory);
			if (ok){
				this.viewFrame.setEraProjectHomeDirectory(newProjectHomeDirectory);
				clearRDFModels(true,true,true);
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
		this.instanceHtree = null;
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
	
	public ElementTree getInstanceHtree() {
		return instanceHtree;
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
