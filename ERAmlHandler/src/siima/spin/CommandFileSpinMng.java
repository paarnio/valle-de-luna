/* CommandFileSpinMng.java
 * 2017-07-04 COPY FROM ContextMngBySpin project
 * (2016-02-10 TOIMII)
 * 
 * 
 */
package siima.spin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Note: Jena (old) com.hp.hpl. package updated to (new) org.apache.
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Resource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.texen.util.FileUtil;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.topbraid.spin.system.SPINLabels;

import siima.app.control.MainAppController;
import siima.spin.ModelSpinManager;

public class CommandFileSpinMng {
	private static final Logger logger = Logger.getLogger(CommandFileSpinMng.class.getName());
	private JSONParser parser;
	private JSONObject jsonrootobj;

	// from InteractiveSpinMang.java
	private ModelSpinManager mng;
	private List<String> urls = new ArrayList<String>();
	private List<String> altlocs = new ArrayList<String>();
	private boolean spinRegistryUpdated = false;

	//public UIPrompt ui;
	public BufferedReader _input;

	public String mainModelLocalName; // "bicycle";
	public List<String> prefixlines;
	public boolean prefixlinesfilled = false;
	public boolean kb_loaded = false;
	
	//(2017-07-06) 
	private Map<String,String> cmdTypeObjectMap;
	private JSONObject targetCSMCommandObject=null;
	public boolean csmcommand_updated = false;
	
	/* =======================================
	 * 
	 * CONSTRUCTOR
	 * 
	 * =======================================
	 */
	
	public CommandFileSpinMng() {
		this.parser = new JSONParser();
		// from InteractiveSpinMang.java
		//this.ui = new UIPrompt();
		this._input = new BufferedReader(new InputStreamReader(System.in));

		this.mng = new ModelSpinManager();
		this.urls = new ArrayList<String>();
		this.altlocs = new ArrayList<String>();
		this.prefixlines = new ArrayList<String>();
		//(2017-07-05) 
		//this.workflowResults = new StringBuffer();
		defineCmdTypeObjectMap();
		
	}

	/* =======================================
	 * 
	 * CSM COMMAND METHODS
	 * 
	 * =======================================
	 */
	
	public void checkConstraintsCommands(JSONObject comobj){
		//TODO: testing
		logger.log(Level.INFO, "----+ Command: checkConstraintsCommands");
		JSONObject subobj = (JSONObject) comobj.get("constraints");
		//if(subobj!=null){
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		Boolean addrdf = (Boolean) subobj.get("addrdf");
		logger.log(Level.INFO, "----+----+checkConstraintsCommands name:" + name + " and type:" + type + " and addrdf:" + addrdf);
		//}
		if(!spinRegistryUpdated){ mng.createInferredModelAndRegister(); 
		this.spinRegistryUpdated =true;
		} 
		
		if("all".equalsIgnoreCase(type)){
			if((addrdf!=null)&&(addrdf)) mng.checkSPINConstraints(mng.getInferredTriples()); // Save results to model
			else mng.checkSPINConstraints(null); 
		} else if("resource".equalsIgnoreCase(type)){
			String uri = (String) subobj.get("uri");
			logger.log(Level.INFO, "TEST:" + uri);
			if(uri!=null){
			Resource resource = mng.getMainOntModel().getResource(uri);//"http://siima.net/ont/bicycle#Bicycle_4"						
			logger.log(Level.INFO, "Resource " + SPINLabels.get().getLabel(resource));
			if((addrdf!=null)&&(addrdf)) mng.checkSPINConstraintForResource(resource, mng.getInferredTriples());	// Save results to model			
			else mng.checkSPINConstraintForResource(resource, null);
			}
		}	
	}
	
	
	
	public void runSpinConstructorsCommand(JSONObject comobj){
		logger.log(Level.INFO, "----+ Command: runSpinConstructorsCommand");
		JSONObject subobj = (JSONObject) comobj.get("constructors");
		if(subobj!=null){
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		logger.log(Level.INFO, "----+----+runSpinConstructorsCommand name:" + name + " and type:" + type);
		}
		runSpinConstructors();
		
	}
	
	public void templateCallCommand(JSONObject comobj) {
		logger.log(Level.INFO, "----+ Command: templateCallCommand");
		Map<String, RDFNode> argumentNodeMap = new HashMap<String, RDFNode>();
		String queryvariable = null;

		JSONObject jsontemplate = (JSONObject) comobj.get("template");
		String name = (String) jsontemplate.get("name");
		String type = (String) jsontemplate.get("type");
		logger.log(Level.INFO, "----+----+Template name:" + name + " and type:" + type);
		JSONArray argums = (JSONArray) jsontemplate.get("args");
		if(argums!=null){
		for (int i = 0; i < argums.size(); i++) {
			JSONObject argu = (JSONObject) argums.get(i);
			String arguname = (String) argu.get("name");
			logger.log(Level.INFO, i + ":----+----+----+Argument name:" + arguname);
			String argutype = (String) argu.get("type");
			logger.log(Level.INFO, i + ":----+----+----+Argument type:" + argutype);
			String arguvalue = (String) argu.get("value");
			logger.log(Level.INFO, i + ":----+----+----+Argument value:" + arguvalue);
			argumentNodeMap = mng.addArgumentNodeToMap(argumentNodeMap, arguname, argutype, arguvalue, arguvalue, null);
		} }

		List<String> queryVars=null; 
		if ("select".equalsIgnoreCase(type)){ 
			JSONArray jsonqueryvars = (JSONArray) jsontemplate.get("queryVars");
			queryVars = (List)jsonqueryvars;
		}
		mng.callTemplateByName(name, argumentNodeMap, type, queryVars); //queryvariable);

	}
	
	public void createNewTemplateCommand(JSONObject comobj) {
		//2016-02-18
		logger.log(Level.INFO, "----+ Command: createNewTemplateCommand");
		Map<String, RDFNode> argumentNodeMap = new HashMap<String, RDFNode>();
		Map<String, String> argumentCommentMap = new HashMap<String, String>();
		String queryvariable = null;

		JSONObject subobj = (JSONObject) comobj.get("template");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		
		String namespace = (String) subobj.get("ns");
		String templateURI=namespace+name;
		
		StringBuffer queryStr = new StringBuffer();
		String preprefixes = (String) subobj.get("preprefixes");				
		if ("yes".equalsIgnoreCase(preprefixes)) {
			prefillQueryPrefixList();
			for (int i = 0; i < this.prefixlines.size(); i++)
				queryStr.append(this.prefixlines.get(i));
		}
		
		/*
		 *  Build the Query String for the template
		 *  
		 */
		switch (type) {
		case "select": {	
			// Example:"SELECT * WHERE { ?s rdf:type ?o . } LIMIT 10 "
			queryStr.append(" SELECT " + subobj.get("select") + " WHERE { " + subobj.get("where") + " }");
			String limit = (String) subobj.get("limit");
			if (limit != null)
				queryStr.append(" LIMIT " + limit);
			logger.log(Level.INFO, "----QueryString: " + queryStr.toString() );
					
		}
			break;
		case "update": {
			//Delete . 
			if(subobj.get("delete")!=null)
				queryStr.append(" DELETE { " + subobj.get("delete") + " } ");
			if(subobj.get("insert")!=null)
				queryStr.append(" INSERT { " +  subobj.get("insert") + " } ");
			if(subobj.get("where")!=null)
				queryStr.append(" WHERE { " + subobj.get("where") + " }");
			logger.log(Level.INFO, "----QueryString: " + queryStr.toString());
		}
			break;
		case "construct": {
			// Example: CONSTRUCT { ?wouri a workcoreplus:WorkOrder . ?wouri
			// workcoreplus:work_order_id 666 . }
			// WHERE { BIND
			// (IRI(fn:concat("http://ssp4t5.net/mimosa/workcoreplus#WorkOrder_",
			// str(666))) AS ?wouri) .}
			String addToModel = (String) subobj.get("resultTriples");
			queryStr.append(" CONSTRUCT {" + subobj.get("construct") + " } WHERE { " + subobj.get("where") + " }");
			logger.log(Level.INFO, "----QueryString: " + queryStr.toString());

		}
			break;
		case "describe": {
			queryStr.append(" DESCRIBE " + subobj.get("describe") + " WHERE { " + subobj.get("where") + " }");
			logger.log(Level.INFO, "----QueryString: " + queryStr.toString());
		}
		 break;

		default: {
			logger.log(Level.INFO, "----+???? Sparql query for template name: " + name + " has UNKNOWN type: " + type + " ?????");
		}
		}
		
		/*
		 *  Build the ArgumentNodeMap for the template
		 */				
		logger.log(Level.INFO, "----+----+Template name:" + name + " and type:" + type);
		JSONArray argums = (JSONArray) subobj.get("args");
		if(argums!=null){
		for (int i = 0; i < argums.size(); i++) {
			JSONObject argu = (JSONObject) argums.get(i);
			String arguname = (String) argu.get("name");
			logger.log(Level.INFO, i + ":----+----+----+Argument name:" + arguname);
			String argutype = (String) argu.get("type");
			logger.log(Level.INFO, i + ":----+----+----+Argument type:" + argutype);
			String argucomment = (String) argu.get("argComment");
			logger.log(Level.INFO, i + ":----+----+----+Argument comment:" + argucomment);
			
			argumentNodeMap=mng.addSPLArgumentDeclarationToMap(argumentNodeMap, arguname, argutype);
			argumentCommentMap.put(arguname, argucomment);
		} }

		/*
		 *  Creating the new template
		 */		
		mng.createTemplate(mng.getMainOntModel(), queryStr.toString(), templateURI, argumentNodeMap, argumentCommentMap);
		logger.log(Level.INFO, "===== New Template Created ======");
		
	}

	

	public void runInferencesCommand(JSONObject comobj) {
		logger.log(Level.INFO, "----+ Command: runInferencesCommand");
		JSONObject subobj = (JSONObject) comobj.get("inference");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		logger.log(Level.INFO, "----+----+ name:" + name);
		switch (name) {
		case "pre_inference": {

			if ("iterative".equalsIgnoreCase(type))
				runInferences(false);
			else if ("singlepass".equalsIgnoreCase(type))
				runInferences(true);
			else
				logger.log(Level.INFO, "----+????+ type:" + type + " Unknown????");

		}
			break;
		case "post_inference":
			break;
		default: {
			logger.log(Level.INFO, "----+????+ name:" + name + " Unknown?????");
		}
		}

	}

	public void execAttachedQueryCommand(JSONObject comobj) {
		//TODO: Only attached Select query exec implemented.
		//Example in class: "http://siima.net/ont/bicycle#Bicycle"
		logger.log(Level.INFO, "----+ Command: execAttachedQueryCommand");
		JSONObject subobj = (JSONObject) comobj.get("query");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		String clsuri = (String) subobj.get("classuri");
		Boolean reasoner=(Boolean)subobj.get("reasoner");
		logger.log(Level.INFO, "----+----+ name:" + name + "\n----+----+ type:" + type);
		logger.log(Level.INFO, "----+----+ class:" + clsuri + "\n----+----+ reasoner:" + reasoner);
		
		if("select".equalsIgnoreCase(type)){
			OntModel querymodel;
			if((reasoner!=null)&&(reasoner)) querymodel = mng.getOntModelWithReasoner();
			else querymodel = mng.getMainOntModel();
		
			JSONArray jsonqueryvars = (JSONArray) subobj.get("queryVars");
			List queryVars = (List)jsonqueryvars;			
			Resource cls = querymodel.getResource(clsuri); //mng.getMainOntModel()
				
			mng.execAttachedQuery(cls, querymodel, queryVars); //mng.getMainOntModel()
		} else if("construct".equalsIgnoreCase(type)){
			OntModel querymodel;
			if((reasoner!=null)&&(reasoner)) querymodel = mng.getOntModelWithReasoner();
			else querymodel = mng.getMainOntModel();
						
			Resource cls = querymodel.getResource(clsuri); 
			//TODO: executes only attached select queries		
			//mng.execAttachedQuery(cls, querymodel, null); 
			
			
		} else logger.log(Level.INFO, "????? Only SELECT type Attached query implemented. Type was: " + name ); 
		
		
	}
	
	public void addQueryPrefixesCommand(JSONObject comobj){
		//2017-09-15
		logger.log(Level.INFO, "----+ Command: addQueryPrefixesCommand");
		JSONObject subobj = (JSONObject) comobj.get("prefix");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		String newprefixes=(String)subobj.get("prefixlist");
		logger.log(Level.INFO, "----+----+ name:" + name + "\n----+----+ type:" + type);
		List<String> newPrefixLines = new ArrayList<String>();
		String[] lines = newprefixes.split("\\n"); //TODO test		
		//System.out.println("TEST: addQueryPrefixesCommand() #lines" + lines.length);
		//System.out.println("TEST 1.LINE:" + lines[0]);
		for(int i=0;i<lines.length;i++){
			String line = lines[i];
			if (!line.endsWith(" ")) line = line + " ";
			newPrefixLines.add(line);
		}
		prefillQueryPrefixList();
		addToQueryPrefixList(newPrefixLines);
	}
	
	
	public void sparqlQueryCommand(JSONObject comobj) {
		//VPA: reasoner option added
		logger.log(Level.INFO, "----+ Command: sparqlQueryCommand");
		JSONObject subobj = (JSONObject) comobj.get("query");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		Boolean reasoner=(Boolean)subobj.get("reasoner");
		logger.log(Level.INFO, "----+----+ name:" + name + "\n----+----+ reasoner:" + reasoner);
		OntModel querymodel;
		if((reasoner!=null)&&(reasoner)) querymodel = mng.getOntModelWithReasoner();
		else querymodel = mng.getMainOntModel();
		
		StringBuffer queryStr = new StringBuffer();
		String preprefixes = (String) subobj.get("preprefixes");
		if ("yes".equalsIgnoreCase(preprefixes)) {
			prefillQueryPrefixList();
			for (int i = 0; i < this.prefixlines.size(); i++)
				queryStr.append(this.prefixlines.get(i));
		}

		switch (type) {
		case "select": {	
			// Example:"SELECT * WHERE { ?s rdf:type ?o . } LIMIT 10 "
			queryStr.append(" SELECT " + subobj.get("select") + " WHERE { " + subobj.get("where") + " }");
			String limit = (String) subobj.get("limit");
			if (limit != null)
				queryStr.append(" LIMIT " + limit);
			// logger.log(Level.INFO, "----QueryString: " + queryStr.toString() );
			JSONArray jsonqueryvars = (JSONArray) subobj.get("queryVars");
			List queryVars = (List)jsonqueryvars;			
			mng.sparqlSelectQuery(querymodel, queryStr, queryVars); //mng.getMainOntModel()
			
		}
			break;
		case "update": {
			//Delete TOIMII ainakin bicycle mallissa. 
			if(subobj.get("delete")!=null)
				queryStr.append(" DELETE { " + subobj.get("delete") + " } ");
			if(subobj.get("insert")!=null)
				queryStr.append(" INSERT { " +  subobj.get("insert") + " } ");
			if(subobj.get("where")!=null)
				queryStr.append(" WHERE { " + subobj.get("where") + " }");
			logger.log(Level.INFO, "----QueryString: " + queryStr.toString());
			mng.sparqlUpdateQuery(querymodel, queryStr); //mng.getMainOntModel()
		}
			break;
		case "construct": {
			// Example: CONSTRUCT { ?wouri a workcoreplus:WorkOrder . ?wouri
			// workcoreplus:work_order_id 666 . }
			// WHERE { BIND
			// (IRI(fn:concat("http://ssp4t5.net/mimosa/workcoreplus#WorkOrder_",
			// str(666))) AS ?wouri) .}
			String addToModel = (String) subobj.get("resultTriples");
			queryStr.append(" CONSTRUCT {" + subobj.get("construct") + " } WHERE { " + subobj.get("where") + " }");
			logger.log(Level.INFO, "----QueryString: " + queryStr.toString());
			if ("add".equalsIgnoreCase(addToModel))
				mng.sparqlConstructQuery(querymodel, mng.getInferredTriples(), queryStr); //mng.getMainOntModel()
			else
				mng.sparqlConstructQuery(querymodel, null, queryStr); //mng.getMainOntModel()
		}
			break;
		case "describe": {
			queryStr.append(" DESCRIBE " + subobj.get("describe") + " WHERE { " + subobj.get("where") + " }");
			logger.log(Level.INFO, "----QueryString: " + queryStr.toString());
			mng.sparqlDescribeQuery(querymodel, null, queryStr); // mng.getMainOntModel()
		}
		 break;

		default: {
			logger.log(Level.INFO, "----+???? Sparql query by name: " + name + " has UNKNOWN type: " + type + " ?????");
		}
		}

	}

	public void listTemplatesCommand(JSONObject comobj){
		logger.log(Level.INFO, "----+ Command: listTemplatesCommand");
		JSONObject subobj = (JSONObject) comobj.get("listing");
		if(subobj!=null){
			String name = (String) subobj.get("name");
			String type = (String) subobj.get("type");
			logger.log(Level.INFO, "----+----+listTemplatesCommand name:" + name + " and type:" + type);
			// Listing all templates
			mng.getTemplates();
			// Detailed info 
			JSONArray infolist = (JSONArray) subobj.get("info");
			for(Object nameuriobj : infolist){
				JSONObject jsonnameuri = (JSONObject)nameuriobj;
				String localName=(String)jsonnameuri.get("localname");
				String uri=(String)jsonnameuri.get("uri");
				mng.printTemplateInfo(localName, uri); //String uri)
				
			}		
		}	
	}
	
	public void listFunctionsCommand(JSONObject comobj){
		logger.log(Level.INFO, "----+ Command: listFunctionsCommand");
		JSONObject subobj = (JSONObject) comobj.get("listing");
		if(subobj!=null){
			String name = (String) subobj.get("name");
			String type = (String) subobj.get("type");
			logger.log(Level.INFO, "----+----+listFunctionsCommand name:" + name + " and type:" + type);
			// Listing all templates
			mng.getFunctions();
			
		}	
	}
	
	public void listNsPrefixesCommand(JSONObject comobj){
		logger.log(Level.INFO, "----+ Command: listNsPrefixesCommand");
		JSONObject subobj = (JSONObject) comobj.get("listing");
		if(subobj!=null){
			String name = (String) subobj.get("name");
			String type = (String) subobj.get("type");
			logger.log(Level.INFO, "----+----+listNsPrefixesCommand name:" + name + " and type:" + type);
			if("modelprefixes".equalsIgnoreCase(type)){
				logger.log(Level.INFO, "\n----+----+ Listing Model Prefixes with Namespaces +----+----+");
			 mng.getNsPrefixeMap(mng.getMainOntModel()); //returns Map<String,String> prefixMap			 
			} else if("sparqlprefixes".equalsIgnoreCase(type)){
				if(!this.prefixlinesfilled) prefillQueryPrefixList();
				logger.log(Level.INFO, "\n----+----+ Listing Predefined Sparql query Prefixes with Namespaces +----+----+");
				for(int i=0; i<this.prefixlines.size();i++){
					logger.log(Level.INFO, "("+(i+1)+") " + this.prefixlines.get(i));
				}
			}
			
		}	
	}
	
	
	
	public void userPromptCommand(JSONObject comobj){
		logger.log(Level.INFO, "----+ Command: userPromptCommand");
		JSONObject subobj = (JSONObject) comobj.get("userprompt");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		String prompttext = (String) subobj.get("prompt");
		String defaults = (String) subobj.get("default");		
		String prompt = "Q" + prompttext + ">>>?";
		//String reply=ui.getInputOrDefault(prompt, _input, defaults);
		//logger.log(Level.INFO, "----+ User Reply: " + reply + "\n-------------------");
	}
	
	public void writeModelCommand(JSONObject comobj) {
		logger.log(Level.INFO, "----+ Command: writeModelCommand");
		JSONObject subobj = (JSONObject) comobj.get("write");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type"); // console/file
		String filepath = (String) subobj.get("filePath"); // data/inferences/inferred.ttl
		String modelcat = (String) subobj.get("modelcat"); // inferred/main/mainall/merged
		String format = (String) subobj.get("format"); // https://jena.apache.org/documentation/io/rdf-output.html
		if(format==null) format="TURTLE"; //Default format

		logger.log(Level.INFO, "----+----+ KB name:" + name);

		try {

			if (("file".equalsIgnoreCase(type)) && (filepath != null)) {
				FileWriter out = new FileWriter(filepath); // "C:/Temp/test.ttl");
				logger.log(Level.INFO, "\n---- Printing Inferred Triples or models to File:" + filepath + " ----\n");
				if ("inferred".equalsIgnoreCase(modelcat))
					mng.getInferredTriples().write(out, format);
				else if ("main".equalsIgnoreCase(modelcat))
					mng.getMainOntModel().write(out, format);
				else if ("mainall".equalsIgnoreCase(modelcat))
					mng.getMainOntModel().writeAll(out, format);
				else if ("merged".equalsIgnoreCase(modelcat)){
					mng.mergeMainModelAndInferredTriples().write(out, format);
				}
					
			} else if ("console".equalsIgnoreCase(type)) {
				logger.log(Level.INFO, "\n---- Printing Inferred Triples or models to Console ----\n"
						+ "------------------------------------------------");
				if ("inferred".equalsIgnoreCase(modelcat))
					mng.getInferredTriples().write(System.out, format);
				else if ("main".equalsIgnoreCase(modelcat))
					mng.getMainOntModel().write(System.out, format);
				else if ("mainall".equalsIgnoreCase(modelcat))
					mng.getMainOntModel().writeAll(System.out, format);
				else if ("merged".equalsIgnoreCase(modelcat)){
					mng.mergeMainModelAndInferredTriples().write(System.out, format);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createInferenceModelCommand(JSONObject comobj){
		logger.log(Level.INFO, "----+ Command: createInferenceModelCommand");
		JSONObject subobj = (JSONObject) comobj.get("create");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		logger.log(Level.INFO, "----+----+ name:" + name);
		mng.createInferredModelAndRegister();
		this.spinRegistryUpdated = true;
		
	}
	/*
	public void createInferredModelAndRegister() {
		logger.log(Level.INFO, "\n---- OPERATION: createInferredModelAndRegister ----\n");
		mng.createInferredModelAndRegister();
		this.spinRegistryUpdated = true;
		logger.log(Level.INFO, "\n---- --------- ----\n");
	}
	*/
	
	public void loadKnowledgeBaseCommand(JSONObject comobj) {
		logger.log(Level.INFO, "----+ Command: loadKnowledgeBaseCommand");
		StringBuffer sbuff= new StringBuffer();
		JSONObject subobj = (JSONObject) comobj.get("knowledgeBase");
		String name = (String) subobj.get("name");
		String type = (String) subobj.get("type");
		logger.log(Level.INFO, "----+----+ KB name:" + name);
		
		if("predefined".equalsIgnoreCase(type)){
		
		switch (name) {
		case "context_mimosa": {
			startPredefinedKB_ssp4t5net();
		}
			break;
		case "bicycle": {
			startPredefinedKB_Bicycle();
		}
			break;
		default: {
			logger.log(Level.INFO, "----+????+ name:" + name + " Unknown?????");
		}
		}
		} else if("file&uri".equalsIgnoreCase(type)){
			
			JSONObject mainOntology = (JSONObject) subobj.get("ontology");
			JSONArray  imports = (JSONArray)  subobj.get("imports");
			
			String ontname = (String) mainOntology.get("name");
			String ontfolder = (String) mainOntology.get("folder");
			String ontfile = (String) mainOntology.get("file");
			String onturi = (String) mainOntology.get("uri");
			altlocs.add(ontfolder + "/" + ontfile);
			urls.add(onturi);
			
			sbuff.append("-ontname:"+ ontname + " -ontfolder:" + ontfolder + " -ontfile: " + ontfile + " -onturi: " + onturi+ "\n");
			
			if(imports!=null){
			for(Object importobj : imports){				
				JSONObject jsonimport = (JSONObject) importobj;				
				String impname = (String) jsonimport.get("name");
				String impfolder = (String) jsonimport.get("folder");
				String impfile = (String) jsonimport.get("file");
				String impuri = (String) jsonimport.get("uri");				
				if(impfolder==null) impfolder=ontfolder;
				altlocs.add(impfolder + "/" + impfile);
				urls.add(impuri);
				sbuff.append("-impname:"+ impname + " -impfolder:" + impfolder + " -impfile: " + impfile + " -impuri: " + impuri+ "\n");				
				
			}
			}
			logger.log(Level.INFO, "\n-----loadKnowledgeBaseCommand() Knowledge Base Info  ----------\n" + sbuff.toString());
			/* ----------- Build Models ----------*/
			OntModel baseont = mng.loadModelWithImports(urls, altlocs);
			// Set mainOntModel
			mng.setMainOntModel(baseont);
			// Set ntModelWithReasoner
			mng.setOntModelWithReasoner(mng.createReasonerModel(mng
							.getMainOntModel()));
			 
			logger.log(Level.INFO, "\n-----loadKnowledgeBaseCommand() Knowledge Base Ready  ----------");
			
		} else if("preloaded".equalsIgnoreCase(type)){
			// Using current preloaded KB
			OntModel baseont = mng.getMainOntModel();
			if(baseont!=null){
				logger.log(Level.INFO, "\n-----loadKnowledgeBaseCommand() Using Preloaded Knowledge Base ----------");
			} else {
				logger.log(Level.INFO, "\n?????? loadKnowledgeBaseCommand() Knowledge Base has NOT been Preloaded????");
			}
			
		}
	}
	
	/* **************************
	 * 
	 * CMSCommand selection
	 * 
	 * *************************/
	

	public void runCMSCommand(JSONObject comobj) {
		String ctype = (String) comobj.get("commandtype");
		switch (ctype) {
		case "loadKnowledgeBase": {
			if (!kb_loaded)
				loadKnowledgeBaseCommand(comobj);
			kb_loaded = true;
		}
			break;
		case "createInferenceModel": {
			if (kb_loaded)
				createInferenceModelCommand(comobj);
		}
			break;
		case "runInferences": {
		if (kb_loaded)
			runInferencesCommand(comobj);
		}		
			break;
		case "createNewTemplate": {
			if (kb_loaded)
				createNewTemplateCommand(comobj);

		}
			break;
		case "templateCall": {
			if (kb_loaded)
				templateCallCommand(comobj);

		}
			break;
		case "sparqlQuery": {
			if (kb_loaded)
				sparqlQueryCommand(comobj);

		}
		break;
		case "execAttachedQuery": {
		if (kb_loaded)
			execAttachedQueryCommand(comobj);

		} 
		break;
		case "runSpinConstructors": {
		if (kb_loaded)
			runSpinConstructorsCommand(comobj);

		} 
		break; 
		case "checkConstraints": {
			if (kb_loaded)
				checkConstraintsCommands(comobj);

		}
			break;
		case "writeModel": {
			if (kb_loaded)
				writeModelCommand(comobj);

		}
			break;
		case "listTemplates": {
			if (kb_loaded)
				listTemplatesCommand(comobj);

		} 
		break;
		case "listFunctions": {
		if (kb_loaded)
			listFunctionsCommand(comobj);

		}
		break;
		case "listNsPrefixes": {
		if (kb_loaded)
			listNsPrefixesCommand(comobj);
		} 
		break;
		case "userinput": {
		if (kb_loaded)
			userPromptCommand(comobj);

		} 
		break;
		case "addNsPrefixes": {
			if (kb_loaded)
				addQueryPrefixesCommand(comobj);

			} 
			break;
		default: {
			logger.log(Level.INFO, "???????????Command:" + ctype + " Unknown????????");
		}
		}
	}
	
	
	/* =======================================
	 * 
	 * Interface methods for MainAppController
	 * 2017-09-16
	 * =======================================
	 */
	
	public void preLoadKBWithMergedAmlOntology(OntModel baseont){
		// 2018-07-27		
		mng.setMainOntModel(baseont);				
	}
	
	public void preLoadOntologyModel(File[] ontfiles){
		/* 2017-09-26 "SELECT ONTOLOGY FILES (.ttl)(the main ontology file (importing others) name should contain substring 'main')");
		 * PROBLEM: files sorted alphabetically, not in selection order???
		 * SOLUTION: (the name of the main ontology file (importing others)  
		 * should contain substring '_main' OR '_imports' OR be the first in alphabetical order)");
		 * 
		 * 2017-09-16
		 * 1.) Parsing ontology URI from the file (.ttl assumed)
		 * 2.) Parsing file location URLs
		 * 3.) Loading Ontology Model
		 * TEST: with Ontology files as in Command 0 in data/common/json/csmCommands_caex_v3_import_test2.json
		 * 
  		 * 
  		 * -- CSMCommand JSON--
		"knowledgeBase": {
          "name": "cranfield",
          "type": "file&uri",
          "ontology": {
            "name": "caex_ontology",
            "folder": "data/common/ontology",
            "file": "caex_ontology_mod2_importing_owl.ttl",
            "uri": "http://data.ifs.tuwien.ac.at/aml/ontology"
          },
          "imports": [
            {
              "name": "cranfield",
              "folder": "data/common/ontology",
              "file": "cranfield_ont_noimps1.ttl",
              "uri": "http://siima.net/ontologies/2017/caex/"
            }
		 */
		
		//Finding the main ontology importing the others
		int mainfileidx = 0;
		for(int i=0; i< ontfiles.length; i++){
			File file = ontfiles[i];
			String name = file.getName();
			if((name.contains("_main"))||(name.contains("_imports"))) mainfileidx =i;			
		}
		if(mainfileidx!=0){
			File orig_first = ontfiles[0];
			File new_first = ontfiles[mainfileidx];
			ontfiles[0] = new_first;
			ontfiles[mainfileidx] = orig_first;
		}
		
		List<String> ontologyUris = new ArrayList<String>();
		List<String> locationUrls = new ArrayList<String>();
		
		for(int i=0; i< ontfiles.length; i++){
			String uristr =null;
			File file = ontfiles[i];
	
			uristr = parseOntologyUriFromFile(file);
			if(uristr!=null)  ontologyUris.add(uristr);
			System.out.println("====URI:" + uristr);
			
			/* 2.) Parsing file location URL
			 *  File URIs examples:
			 *  (0)file:/C:/Users/Pekka%20Aarnio/git/valle-de-luna/ERAmlHandler/data/common/ontology/caex_ontology_mod2_importing_owl.ttl
			 *  (1)file:/C:/Users/Pekka%20Aarnio/git/valle-de-luna/ERAmlHandler/data/common/ontology/cranfield_ont_noimps1.ttl
			 */		
				String pathstr = file.getAbsolutePath(); 
				//https://stackoverflow.com/questions/34434042/convert-windows-path-to-uri-in-java
				String locUrl = file.toURI().toString();
				locationUrls.add(locUrl);
				System.out.println("====URL:" + locUrl);
		}
		
		/*
		 * LoadingModels in files to ModelSpinManager
		 * Testing: URLs and URIs found for all files
		 */
		int filecount = ontfiles.length;
		if((locationUrls.size()==filecount)&&(ontologyUris.size()==filecount)){
			
			System.out.println("====URL&&URI OK!");
			//Load models:
			OntModel baseont = mng.loadModelWithImports(ontologyUris, locationUrls);
			// Set mainOntModel
			mng.setMainOntModel(baseont);
			// Set ntModelWithReasoner			
			mng.setOntModelWithReasoner(mng.createReasonerModel(mng.getMainOntModel()));		
			logger.log(Level.INFO, "\n-------preLoadOntologyModel(): Knowledge Base Ready  -----");
		}
		
		
	}
	
	
	public String parseOntologyUriFromFile(File ontologyFile) {

		/*
		 * 1.) From .ttl ontology 
		 * 2.) From .owl ontology 
		 * -----------------------
		 * Searching ttl-file lines containing: '# baseURI: ' '@base ' '@prefix: '
		 * ('# imports: ')
		 * 
		 * EXAMPLES:
		 *  * ----bicycle.ttl-------
		 * # baseURI: http://siima.net/ont/bicycle
			# imports: http://semwebquality.org/ontologies/dq-constraints#
			# imports: http://siima.net/ont/accessories
			# imports: http://spinrdf.org/spl
		 * ----accessories.ttl---
		 * # baseURI: http://siima.net/ont/accessories
		 * --------------
		 * -----caex_ontology_mod2_importing_owl.ttl-----
		 * @prefix : <http://data.ifs.tuwien.ac.at/aml/ontology#> .
		 * @base <http://data.ifs.tuwien.ac.at/aml/ontology> .
			<http://data.ifs.tuwien.ac.at/aml/ontology>
  				rdf:type owl:Ontology;
  				owl:imports <http://siima.net/ontologies/2017/caex/> ;
  		 * ---cranfield_ont_noimps1.ttl----
  			@prefix :      <http://siima.net/ontologies/2017/caex/> .
			@prefix siima: <http://siima.net/ontologies/2017/caex/> .
  			siima:  a       owl:Ontology .
  		 * ------------------------
  		 * ---in  .owl files ------
  		 * ---- caex_ontology_mod1_owl.owl -----
  		 * 
  		 * <rdf:RDF	xmlns="http://data.ifs.tuwien.ac.at/aml/ontology#"
  		 * <rdf:Description rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology">
			<rdf:type rdf:resource="http://www.w3.org/2002/07/owl#Ontology"/>
  		 * 
		 */

		Boolean ttl = ontologyFile.getName().endsWith(".ttl");
		Boolean owl = ontologyFile.getName().endsWith(".owl");

		String[] ttl_patterns = { "# baseURI: ", "@base ", "@prefix : " };
		String uristr = null;

		if (ttl) {
			// 1.) Parsing ontology URI from the file (.ttl)
			Boolean match = false;
			for (int pi = 0; pi < ttl_patterns.length; pi++) {
				if (!match) {
					List<String> matchlines = siima.util.FileUtil.searchLinesTextFile(ttl_patterns[pi],
							ontologyFile.getAbsolutePath());
					if (!matchlines.isEmpty()) {
						match = true;
						System.out.println("====MATCHLINE:" + matchlines.get(0));
						String line = matchlines.get(0);
						String[] lineparts1 = line.split("<");
						if (lineparts1.length > 1) {
							String[] lineparts2 = lineparts1[1].split(">");
							if (lineparts2.length > 1) {
								uristr = lineparts2[0];
								System.out.println("====URI:" + uristr);
							}
						}
					}
				}
			}
		} else if (owl) {
			// 2.) Parsing ontology URI from the file (.owl)
			System.out.println("==== URI FOR .OWL FILE??");
			/*
			 * Calling: siima.util.FileUtil.searchLineByTwoPatternsTextFile()
			 * Returns the last matchlineA that is before the matchlineB.
			 * Example: Searching the line (1) containing ontology uri using
			 * patterns: patternA = "rdf:Description rdf:about=" patternB =
			 * "rdf:type rdf:resource='http://www.w3.org/2002/07/owl#Ontology'"
			 * Targeted File (.owl) fragment: 1.<rdf:Description
			 * rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology">
			 * 2.<rdf:type
			 * rdf:resource="http://www.w3.org/2002/07/owl#Ontology"/>
			 * 
			 */
			String patternA = "rdf:Description rdf:about=";
			//String patternB = "rdf:type rdf:resource='http://www.w3.org/2002/07/owl#Ontology'";
			String patternB = "http://www.w3.org/2002/07/owl#Ontology";
			
			String matchlineA = siima.util.FileUtil.searchLineByTwoPatternsTextFile(patternA, patternB,
					ontologyFile.getAbsolutePath());
			System.out.println("====MATCHLINE A:" + matchlineA);
			// Checking if both patterns on this same line
			if (matchlineA != null) {
				String[] linepartsAB = matchlineA.split(patternB);
				//System.out.println("====MATCHLINE A CONTAINS ALSO PATTERN-B if length >1:" + linepartsAB.length);
				String[] linepartsA = linepartsAB[0].split(patternA);
				int partc = linepartsA.length;
				String partwithuri = linepartsA[partc - 1];
				//System.out.println("====MATCHLINE partwithuri:" + partwithuri);
				if (partwithuri.length() > 0) {
					String[] poss = partwithuri.split("\""); // "http://data.ifs.tuwien.ac.at/aml/ontology"...
					//System.out.println("====MATCHLINE poss[0]:" +  poss[0]);
					int pp = poss.length;
					if (pp > 1)
						uristr = poss[1];
					System.out.println("====MATCHLINE A URI:" + uristr);
				}

			}

		}

		return uristr;
	}

	/*
	 * =======================================
	 * 
	 * from InteractiveSpinMng.java
	 * 
	 * =======================================
	 */

	public void runSpinConstructors(){
		/* RUNS ONLY SPIN:CONSTRUCTORS (not spin rules) */
			
		logger.log(Level.INFO, "\n---- OPERATION: Run Constructors ----\n");	
				
		if(!spinRegistryUpdated){ mng.createInferredModelAndRegister(); 
			this.spinRegistryUpdated =true;
		}
		mng.runConstructors(mng.getMainOntModel(), mng.getInferredTriples());
		logger.log(Level.INFO, "\n---- --------- ----\n");
	}
	
	
	
	public void runInferences(boolean singlePass) {
		logger.log(Level.INFO, "\n---- OPERATION: Run Inferences ----\n");
		mng.createInferredModelAndRegister();
		mng.runAllSpinInferences(singlePass);// singlePass=false: run
												// iteratively
		this.spinRegistryUpdated = true;
		logger.log(Level.INFO, "\n---- --------- ----\n");
	}

	public void addToQueryPrefixList(List<String> newPrefixLines) {
	//2017-09-15 For new command type addQueryPrefixesCommand()
		if (!this.prefixlinesfilled) {
			this.prefixlines = new ArrayList<String>();
			this.prefixlinesfilled = true;
		} 		
		if((newPrefixLines!=null)&&(!newPrefixLines.isEmpty())){
			//this.prefixlines.addAll(newPrefixLines);
			for(String line : newPrefixLines){				
				this.prefixlines.add(line);
			}
			
		}
	}
	
	public void prefillQueryPrefixList() {

		if (!this.prefixlinesfilled) {
			this.prefixlines = new ArrayList<String>();
			// FOR general and spin ontologies
			this.prefixlines.add("PREFIX owl: <http://www.w3.org/2002/07/owl#> ");
			this.prefixlines.add("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
			this.prefixlines.add("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ");
			this.prefixlines.add("PREFIX sp: <http://spinrdf.org/sp#> ");
			this.prefixlines.add("PREFIX spif: <http://spinrdf.org/spif#> ");
			this.prefixlines.add("PREFIX spin: <http://spinrdf.org/spin#> ");
			this.prefixlines.add("PREFIX spl: <http://spinrdf.org/spl#> ");
			this.prefixlines.add("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> ");
			// FOR ssp4t5.net ontologies
			this.prefixlines.add("PREFIX context_mimosa: <http://ssp4t5.net/test/context_mimosa#> ");
			this.prefixlines.add("PREFIX afn: <http://jena.hpl.hp.com/ARQ/function#> ");
			this.prefixlines.add("PREFIX arg: <http://spinrdf.org/arg#> ");
			this.prefixlines.add("PREFIX combined: <http://ssp4t5.net/mimosa/combined#> ");
			this.prefixlines.add("PREFIX diagplus: <http://ssp4t5.net/mimosa/diagplus#> ");
			this.prefixlines.add("PREFIX fn: <http://www.w3.org/2005/xpath-functions#> ");
			this.prefixlines.add("PREFIX mctxcore: <http://ssp4t5.net/context/mctxcore#> ");
			this.prefixlines.add("PREFIX mctxsimple: <http://ssp4t5.net/context/mctxsimple#> ");
			// also
			this.prefixlines.add("PREFIX workcoreplus: <http://ssp4t5.net/mimosa/workcoreplus#> ");
			this.prefixlines.add("PREFIX cmcoreplus: <http://ssp4t5.net/mimosa/cmcoreplus#> ");
			this.prefixlines.add("PREFIX regasset: <http://ssp4t5.net/mimosa/regasset#> ");
			this.prefixlines.add("PREFIX regcore: <http://ssp4t5.net/mimosa/regcore#> ");

			// FOR bicycle ontologies
			this.prefixlines.add("PREFIX access: <http://siima.net/ont/accessories#> ");
			this.prefixlines.add("PREFIX bicycle: <http://siima.net/ont/bicycle#> ");
			
			// FOR "external isa-95 clone model
			this.prefixlines.add("PREFIX extproseg: <http://external.net/isa95/prosegmodel#> "); //external.net
			//this.prefixlines.add("PREFIX proxyproseg: <http://ssp4t5.net/proxy/isa95/prosegmodel#> ");
			this.prefixlines.add("PREFIX proxy: <http://ssp4t5.net/external/proxy#> "); 

			this.prefixlinesfilled = true;
		}
	}
	public void startPredefinedKB_Bicycle(){
		/* --- Main ontology --- */
		//(2017-07-10) changed
		String ont_folder = "data/common/owl_models/importing_models"; 
		//TOIMII MYÖS: String ont_folder = "file:/C:/Users/Pekka%20Aarnio/git/valle-de-luna/ERAmlHandler/data/common/owl_models/importing_models/";
		
		String main_ont_file= "bicycle.ttl"; 
		String main_ont_url="http://siima.net/ont/bicycle"; 
		
		altlocs.add(ont_folder + "/" + main_ont_file);
		urls.add(main_ont_url);
		
		/* --- Imported ontologies --- */		
		String imp_ont_file="accessories.ttl"; 
		String imp_ont_url= "http://siima.net/ont/accessories"; 
		
		altlocs.add(ont_folder + "/" + imp_ont_file);
		urls.add(imp_ont_url);
		
		/* ----------- Build Models ----------*/
		//VPA: 2016-02-15 Testing a new Model Build  methods
		//now in dump.txt: OntModel baseont = mng.test();
		//now in dump.txt: OntModel baseont = mng.loadModelWithSubModels(urls, altlocs); //EI TOIMI
		OntModel baseont = mng.loadModelWithImports(urls, altlocs);
		// Set mainOntModel
		mng.setMainOntModel(baseont);
		// Set ntModelWithReasoner
		
		mng.setOntModelWithReasoner(mng.createReasonerModel(mng.getMainOntModel()));
		
		this.mainModelLocalName="bicycle"; 
		logger.log(Level.INFO, "\n-------startPredefinedKB_Bicycle(): Knowledge Base Ready  ------");
		
	}
	
	
	private void startPredefinedKB_ssp4t5net() {
		/* --- Main ontology --- */
		String ont_folder = "data/models/ssp4t5net";

		String main_ont_file = "context_mimosa.ttl";
		String main_ont_url = "http://ssp4t5.net/test/context_mimosa";

		altlocs.add(ont_folder + "/" + main_ont_file);
		urls.add(main_ont_url);

		/* --- Imported ontologies --- */

		altlocs.add(ont_folder + "/" + "mctxsimple.ttl");
		urls.add("http://ssp4t5.net/context/mctxsimple");

		altlocs.add(ont_folder + "/" + "combined.ttl");
		urls.add("http://ssp4t5.net/mimosa/combined");

		altlocs.add(ont_folder + "/" + "mctxcore.ttl");
		urls.add("http://ssp4t5.net/context/mctxcore");

		altlocs.add(ont_folder + "/" + "cmcoreplus.ttl");
		urls.add("http://ssp4t5.net/mimosa/cmcoreplus");

		altlocs.add(ont_folder + "/" + "diagplus.ttl");
		urls.add("http://ssp4t5.net/mimosa/diagplus");

		altlocs.add(ont_folder + "/" + "regasset.ttl");
		urls.add("http://ssp4t5.net/mimosa/regasset");

		altlocs.add(ont_folder + "/" + "regcore.ttl");
		urls.add("http://ssp4t5.net/mimosa/regcore");

		altlocs.add(ont_folder + "/" + "workcoreplus.ttl");
		urls.add("http://ssp4t5.net/mimosa/workcoreplus");

		/* ----------- Build Models ---------- */
		OntModel baseont = mng.loadModelWithImports(urls, altlocs);
		// Set mainOntModel
		mng.setMainOntModel(baseont);
		// Set ntModelWithReasoner
		mng.setOntModelWithReasoner(mng.createReasonerModel(mng.getMainOntModel()));

		this.mainModelLocalName = "context_mimosa";
		logger.log(Level.INFO, "\n----------- Knowledge Base Ready  ----------");

	}

	public void mainInitSpinManager(String json_commandFile) {
		//(2017-07-05) copied from the orig main method
		boolean kb_loaded = false;
		StringBuffer strbuff = new StringBuffer();
		StringBuffer stepnotebuffer = new StringBuffer();

		System.out.println("================ CommandFileSpinMng Initilized ===============\n");
		try {

			this.jsonrootobj = (JSONObject) this.parser.parse(new FileReader(json_commandFile));
			JSONObject header = (JSONObject) jsonrootobj.get("CSMHeader");

			strbuff.append("--------------------------------------------------\n" + "+'CSMHeader':\n+----+'filename': '"
					+ header.get("filename") + "'\n" + "+----+'created': '" + header.get("created") + "'\n"
					+ "+----+'updated': '" + header.get("updated") + "'\n" + "+----+'history': '"
					+ header.get("history") + "'\n" + "--------------------------------------------------\n");
			System.out.println("\n" + strbuff.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public StringBuffer mainInvokeCommandWorkflow(){
		// (2017-07-05) copied from the orig main method
		boolean kb_loaded = false;
		StringBuffer strbuff = new StringBuffer();
		StringBuffer stepnotebuffer = new StringBuffer();
		
		if(this.jsonrootobj!=null) {
			logger.log(Level.INFO, "================ CommandFileSpinMng Command Workflow running... ===============\n");
			
			//JSONObject jsonrootobj = (JSONObject) this.parser.parse(new FileReader(json_commandFile));
			JSONObject header = (JSONObject) this.jsonrootobj.get("CSMHeader");
			
		/*	strbuff.append("--------------------------------------------------\n"
					+ "+'CSMHeader':\n+----+'filename': '" + header.get("filename") + "'\n"
					+ "+----+'created': '" + header.get("created") + "'\n"
					+ "+----+'updated': '" + header.get("updated") + "'\n"
					+ "+----+'history': '" + header.get("history") + "'\n"
					+ "--------------------------------------------------\n");
			System.out.println("\n" + strbuff.toString());
		*/	
			JSONObject workflow = (JSONObject) header.get("workflow");
			String wftype = (String) workflow.get("type");
			logger.log(Level.INFO, "--Reading commands from csm command file (json) ... \n\n");
			JSONArray commands = (JSONArray) jsonrootobj.get("CSMCommands");

			if ("indexed".equalsIgnoreCase(wftype)) {
				JSONArray indexes = (JSONArray) workflow.get("indexes");
				for (Object indobj : indexes) {
					Number ind = (Number) indobj;
					JSONObject comobj = (JSONObject) commands.get(ind.intValue());
					String stepnote = (String) comobj.get("stepnote");
					stepnotebuffer.append("->| " + stepnote + "(" + ind.intValue() + ") |-");
					logger.log(Level.INFO, "\n\n========== Next Command Index:(" + ind + "): " + stepnote + "========== ");					
					this.runCMSCommand(comobj);
				}
			} else if ("all".equalsIgnoreCase(wftype)) {
				/* running all Commands */

				for (int ci = 0; ci < commands.size(); ci++) {

					JSONObject comobj = (JSONObject) commands.get(ci);
					String ctype = (String) comobj.get("commandtype");
					logger.log(Level.INFO, "----+ Command type:" + ctype);
					this.runCMSCommand(comobj);
				}
			} else {
				logger.log(Level.INFO, "????Workflow type:" + wftype + " Unknown????????");
			} // end if
			
			logger.log(Level.INFO, "\n\n========== WORKFLOW STEPS LOG ==========\n");
			logger.log(Level.INFO, "(start)-" + stepnotebuffer.toString() + "->(end)");
			
		} else {
			
			logger.log(Level.INFO, "NOTICE: CommandFileSpinMng not initialized (Command Json file not loaded)\n");
		}
		
		return stepnotebuffer;
	}
	
	public StringBuffer parseCSMCommandJsonRoot(){
		StringBuffer jsonstr=null;
		if(this.jsonrootobj!=null){
			 jsonstr = new StringBuffer();
			 String pretty = this.jsonrootobj.toString().replaceAll(",", ",\n");
			 pretty = pretty.replaceAll("}", "}\n");
			 pretty = pretty.replaceAll("\\[", "\\[\n"); 
			 jsonstr.append(pretty);
		}
		return jsonstr;
	}
	
	public void updateCSMHeaderJsonObject(Map<String, String> fieldKeyDataMap){
		System.out.println("-----updateCSMHeaderJsonObject(): update inxsequence ");
		try {
		JSONObject header = (JSONObject) jsonrootobj.get("CSMHeader");
		JSONObject workflow = (JSONObject) header.get("workflow");
		//JSONArray indexes = (JSONArray) workflow.get("indexes");
		
		JSONArray newindexes = (JSONArray) parser.parse((String)fieldKeyDataMap.get("idxsequence"));
		workflow.replace("indexes", newindexes);
		
		System.out.println("----updateCSMHeaderJsonObject() NEW INDEXES: " + newindexes.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCSMCommandJsonObject(Map<String, String> fieldKeyDataMap) {
		// (2017-07-07) If index value has not been changed, i.e. index values are equal do
		// DO UPDATE,
		// else DO INSERT in array location defined by the new index value.
		// If new_index=null save into CSM_STORE/UNCATEGORIZED JSONArray
	
		boolean update = false;
		boolean addtocsmstore = false;
		boolean newindexnull=false;
		
		if((fieldKeyDataMap.get("index") == null)
				|| ("null".equalsIgnoreCase(fieldKeyDataMap.get("index")))
				|| ("".equalsIgnoreCase(fieldKeyDataMap.get("index")))) newindexnull=true;
		try {
			//System.out.println("updateCSMCommandJsonObject() index: " + fieldKeyDataMap.get("index"));
			if (this.targetCSMCommandObject != null) { // Normally it cannot be null

			if ((this.targetCSMCommandObject.get("index") != null)&&(!newindexnull)) {
				Number trginx = (Number) this.targetCSMCommandObject.get("index");
				String trginxstr = trginx.toString();
				String upinxstr = fieldKeyDataMap.get("index");
				if (trginxstr.equals(upinxstr))
					update = true; // DO target update
			} else if (newindexnull) { 
				addtocsmstore = true; // ADD to CSMStore Array
			}
		}
		
		if (update) { // DO target update; equal index values or both null
			//indexes are equal
			this.targetCSMCommandObject.put("idcode", fieldKeyDataMap.get("idcode"));
			this.targetCSMCommandObject.put("commandtype", fieldKeyDataMap.get("commandtype"));
			this.targetCSMCommandObject.put("stepnote", fieldKeyDataMap.get("stepnote"));
			this.targetCSMCommandObject.put("comment", fieldKeyDataMap.get("comment"));

			String bodyobjectkey = fieldKeyDataMap.get("bodyobjectkey");
			if (this.targetCSMCommandObject.containsKey(bodyobjectkey)) {
				String bodycontentstr = fieldKeyDataMap.get(bodyobjectkey);				
				JSONObject newbody = (JSONObject) parser.parse(bodycontentstr);
				this.targetCSMCommandObject.replace(bodyobjectkey, newbody);

			}

		} else if(!addtocsmstore){ // DO insert to CSMCommands JSONArray

			JSONArray commands = (JSONArray) this.jsonrootobj.get("CSMCommands");
			
			String newinxstr = fieldKeyDataMap.get("index");
			if ((newinxstr != null) && (!"null".equalsIgnoreCase(newinxstr))) {
				Integer newindnum = Integer.valueOf(newinxstr);
				JSONObject newcommand = new JSONObject();
				
				newcommand.put("index", newindnum); 
				newcommand.put("idcode", fieldKeyDataMap.get("idcode"));
				newcommand.put("commandtype", fieldKeyDataMap.get("commandtype"));
				newcommand.put("stepnote", fieldKeyDataMap.get("stepnote"));
				newcommand.put("comment", fieldKeyDataMap.get("comment"));

				String bodyobjectkey = fieldKeyDataMap.get("bodyobjectkey");
				String bodycontentstr = fieldKeyDataMap.get(bodyobjectkey);				
				JSONObject newbody = (JSONObject) parser.parse(bodycontentstr);
				newcommand.put(bodyobjectkey, newbody);			
				
				//INSERT new object to the CSMCommands list
				commands.add(newindnum.intValue(), newcommand);
				
				//Update all index values to their order in the list
				for (int i = 0; i < commands.size(); i++) {
					Integer idx = Integer.valueOf(i);
					JSONObject comobj = (JSONObject) commands.get(i);
					comobj.replace("index", idx);					
				}
			}
		} else if(addtocsmstore){ 
			// ADD to CSMStore JSONArray with index = null
			// NOTE:Does not check if the same idcode already exists
			
			System.out.println("--- updateCSMCommandJsonObject() ADDING TO CSMSTORE...");
			JSONArray store = (JSONArray) this.jsonrootobj.get("CSMStore");
			JSONObject newcommand = new JSONObject();
			
			newcommand.put("index", null); 
			newcommand.put("idcode", fieldKeyDataMap.get("idcode"));
			newcommand.put("commandtype", fieldKeyDataMap.get("commandtype"));
			newcommand.put("stepnote", fieldKeyDataMap.get("stepnote"));
			newcommand.put("comment", fieldKeyDataMap.get("comment"));

			String bodyobjectkey = fieldKeyDataMap.get("bodyobjectkey");
			String bodycontentstr = fieldKeyDataMap.get(bodyobjectkey);				
			JSONObject newbody = (JSONObject) parser.parse(bodycontentstr);
			newcommand.put(bodyobjectkey, newbody);			
			
			store.add(newcommand);
			
		}

		this.csmcommand_updated = true;
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,String> searchCSMCommandContent(String idcode, String index, String commandtype){
		// (2017-07-05) Returning only the first match
		logger.log(Level.INFO, "searchCommandJsonString()");
		Map<String,String> fieldKeyDataMap =new HashMap<String,String>();
				
		Map<String,String> typeobjectmap= this.getCmdTypeObjectMap();
		
		List<JSONObject> matches= searchCommandObject(idcode, index,  commandtype);
		if((matches!=null)&&(matches.size()>0)){

			//fieldKeyDataMap = new HashMap<String,String>();
			JSONObject comobj = matches.get(0);
			targetCSMCommandObject = comobj;
			Number indxnum = (Number) comobj.get("index");
			if(indxnum!=null){
			String indexstr = indxnum.toString();
			fieldKeyDataMap.put("index", indexstr);
			} else {
				fieldKeyDataMap.put("index", "null");
			}
			String idcodestr = (String) comobj.get("idcode");
			fieldKeyDataMap.put("idcode", idcodestr);
			String commandtypestr = (String) comobj.get("commandtype");
			fieldKeyDataMap.put("commandtype", commandtypestr);
			String stepnotestr = (String) comobj.get("stepnote");
			fieldKeyDataMap.put("stepnote", stepnotestr);
			String commentstr = (String) comobj.get("comment");
			fieldKeyDataMap.put("comment", commentstr);
			
			String bodyobjectkey = typeobjectmap.get(commandtypestr);
			fieldKeyDataMap.put("bodyobjectkey", bodyobjectkey);
			
			JSONObject bodyobject = (JSONObject) comobj.get(bodyobjectkey);
			fieldKeyDataMap.put(bodyobjectkey, bodyobject.toString());
			
			//System.out.println("\n----------ONE MATCHING COMMAND: type: " + commandtypestr + " body: " + bodyobjectkey);
			//System.out.println("\nCOMMAND: " + bodyobject.toString());
			
		}
		//TODO: Loading also Workflow indexes sequence to the map
		JSONObject header = (JSONObject) jsonrootobj.get("CSMHeader");
		JSONObject workflow = (JSONObject) header.get("workflow");
		JSONArray sequence = (JSONArray) workflow.get("indexes");
		fieldKeyDataMap.put("idxsequence", sequence.toString());
		
		return fieldKeyDataMap;
	}
	
	private List<JSONObject> searchCommandObject(String idcode, String index, String commandtype){
		//(2017-07-05) Searching from "CSMCommands" and "CSMStore"
		JSONArray commands = (JSONArray) jsonrootobj.get("CSMCommands");
		JSONArray store = (JSONArray) jsonrootobj.get("CSMStore");	
		List<JSONObject> matches = new ArrayList<JSONObject>();		
			for (int i=0; i<commands.size();i++) {			
				JSONObject comobj = (JSONObject) commands.get(i);
				Number indxnum = (Number) comobj.get("index");
				String indx = indxnum.toString();
				String id = (String) comobj.get("idcode");
				String type = (String) comobj.get("commandtype");
				//System.out.println("(id; indx; type)(" + id + ";" + indx+ ";" + type + ")");								
				if((idcode!=null)&&(!idcode.isEmpty())&&(id!=null)&&((idcode.equalsIgnoreCase(id))||(id.startsWith(idcode)))){
					matches.add(comobj);
				} else if((index!=null)&&(index.equalsIgnoreCase(indx))){
					matches.add(comobj);
				} else if((commandtype!=null)&&(commandtype.equalsIgnoreCase(type))){
					matches.add(comobj);
				}								
			}
			if((index==null)||("null".equalsIgnoreCase(index))||(index.isEmpty())){
				System.out.println("\nSEARCHing.from CSMStore...");	
				for (int i=0; i<store.size();i++) {
					
					JSONObject comobj = (JSONObject) store.get(i);
					//Number indxnum = (Number) comobj.get("index");
					//String indx = indxnum.toString();
					String id = (String) comobj.get("idcode");
					String type = (String) comobj.get("commandtype");
					//System.out.println("(id; indx; type)(" + id + ";" + indx+ ";" + type + ")");
									
					if((idcode!=null)&&(!idcode.isEmpty())&&(id!=null)&&((idcode.equalsIgnoreCase(id))||(id.startsWith(idcode)))){
						matches.add(comobj);
					} else if((commandtype!=null)&&(commandtype.equalsIgnoreCase(type))){
						matches.add(comobj);
					}				
				//System.out.println("\nSEARCHing...");					
				}
				
			}
			
		return matches;
	}
	
	private void defineCmdTypeObjectMap(){
		//This mapping should comply with that used in runCMSCommand()
		//and in different command execution methods.
		
		cmdTypeObjectMap = new HashMap<String,String>();
		
		cmdTypeObjectMap.put("loadKnowledgeBase", "knowledgeBase");
		cmdTypeObjectMap.put("createInferenceModel", "create");
		cmdTypeObjectMap.put("runInferences", "inference");
		cmdTypeObjectMap.put("createNewTemplate", "template");
		cmdTypeObjectMap.put("templateCall", "template");
		cmdTypeObjectMap.put("sparqlQuery", "query");
		cmdTypeObjectMap.put("execAttachedQuery", "query");
		cmdTypeObjectMap.put("runSpinConstructors", "constructors");
		cmdTypeObjectMap.put("checkConstraints", "constraints");
		cmdTypeObjectMap.put("writeModel", "write");
		cmdTypeObjectMap.put("listTemplates", "listing");
		cmdTypeObjectMap.put("listFunctions", "listing");
		cmdTypeObjectMap.put("listNsPrefixes", "listing");
		cmdTypeObjectMap.put("userinput", "userprompt");
		cmdTypeObjectMap.put("addNsPrefixes", "prefix"); //2017-09-15	
		
	}
	
	
	public Map<String, String> getCmdTypeObjectMap() {
		return cmdTypeObjectMap;
	}

	public StringBuffer getWorkflowResults() {
		//(2017-07-05)
		
		return mng.getWorkflowResults();
				
	}

	public void setWorkflowResults(StringBuffer workflowResults) {
		//(2017-07-05)
		 mng.setWorkflowResults(workflowResults);
		
	}

	public boolean isCsmcommand_updated() {
		return csmcommand_updated;
	}

	public void setCsmcommand_updated(boolean csmcommand_updated) {
		this.csmcommand_updated = csmcommand_updated;
	}
	
	
	
	/*
	 * =======================================
	 * 
	 *  MAIN
	 * 
	 * =======================================
	 */
	
	

	public static void main(String[] args) {

		CommandFileSpinMng csm = new CommandFileSpinMng();
		//InteractiveSpinMng ism = new InteractiveSpinMng();
		BufferedReader _input = new BufferedReader(new InputStreamReader(
				System.in));
		boolean kb_loaded = false;
		StringBuffer strbuff = new StringBuffer();
		StringBuffer stepnotebuffer = new StringBuffer();
		
		try {
			System.out.println("================ CommandFileSpinMng running... ===============\n");
			System.out.println("\n------------ Command Workflow file?  -----------");
			String prompt = ">>>> Give Workflow filepath ?>";
			String defaults = "data/json/csmCommands.json"; 
			//String ont_folder=ism.ui.getInputOrDefault(prompt, _input, defaults);
			
			String ont_folder = "TODO TEMP"; 
			JSONObject jsonrootobj = (JSONObject) csm.parser.parse(new FileReader(ont_folder));
			JSONObject header = (JSONObject) jsonrootobj.get("CSMHeader");
			
			strbuff.append("--------------------------------------------------\n"
					+ "+'CSMHeader':\n+----+'filename': '" + header.get("filename") + "'\n"
					+ "+----+'created': '" + header.get("created") + "'\n"
					+ "+----+'updated': '" + header.get("updated") + "'\n"
					+ "+----+'history': '" + header.get("history") + "'\n"
					+ "--------------------------------------------------\n");
			System.out.println("\n" + strbuff.toString());
			
			JSONObject workflow = (JSONObject) header.get("workflow");
			String wftype = (String) workflow.get("type");
			System.out.println("--Reading commands from csm command file (json) ... \n\n");
			JSONArray commands = (JSONArray) jsonrootobj.get("CSMCommands");

			if ("indexed".equalsIgnoreCase(wftype)) {
				JSONArray indexes = (JSONArray) workflow.get("indexes");
				for (Object indobj : indexes) {
					Number ind = (Number) indobj;
					JSONObject comobj = (JSONObject) commands.get(ind.intValue());
					String stepnote = (String) comobj.get("stepnote");
					stepnotebuffer.append("->| " + stepnote + "(" + ind.intValue() + ") |-");
					System.out.println("\n\n========== Next Command Index:(" + ind + "): " + stepnote + "========== ");					
					csm.runCMSCommand(comobj);
				}
			} else if ("all".equalsIgnoreCase(wftype)) {
				/* running all Commands */

				for (int ci = 0; ci < commands.size(); ci++) {

					JSONObject comobj = (JSONObject) commands.get(ci);
					String ctype = (String) comobj.get("commandtype");
					System.out.println("----+ Command type:" + ctype);
					csm.runCMSCommand(comobj);
				}
			} else {
				System.out.println("????Workflow type:" + wftype + " Unknown?????");
			} // end if
			
			System.out.println("\n\n========== WORKFLOW STEPS LOG ==========\n");
			System.out.println(" (start)-" + stepnotebuffer.toString() + "->(end)");					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
