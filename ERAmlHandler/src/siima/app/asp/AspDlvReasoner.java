/* AspDlvReasoner.java
 * 2017-04-27 TOIMII
 * 
 * See egit_scheduler_dlv project: SchedulerDlvWrapper AspDlvReasoner.java
 * See http://www.dlvsystem.com/html/DLV_User_Manual.html
 * 
 */
package siima.app.asp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import it.unical.mat.dlv.program.Atom;
import it.unical.mat.dlv.program.Conjunction;
import it.unical.mat.dlv.program.Literal;
import it.unical.mat.dlv.program.NormalAtom;
import it.unical.mat.dlv.program.Program;
import it.unical.mat.dlv.program.Query;
import it.unical.mat.dlv.program.Rule;
import it.unical.mat.wrapper.DLVError;
import it.unical.mat.wrapper.DLVInputProgram;
import it.unical.mat.wrapper.DLVInputProgramImpl;
import it.unical.mat.wrapper.DLVInvocation;
import it.unical.mat.wrapper.DLVInvocationException;
import it.unical.mat.wrapper.DLVWrapper;
import it.unical.mat.wrapper.FactHandler;
import it.unical.mat.wrapper.Model;
import it.unical.mat.wrapper.ModelBufferedHandler;
import it.unical.mat.wrapper.ModelHandler;
import it.unical.mat.wrapper.ModelResult;
import it.unical.mat.wrapper.Predicate;
import it.unical.mat.wrapper.PredicateHandler;
import it.unical.mat.wrapper.PredicateHandlerWithName;

import it.unical.mat.wrapper.PredicateResult;
import siima.app.control.MainAppController;
import siima.util.FileUtil;
import it.unical.mat.wrapper.FactResult;

public class AspDlvReasoner {
	private static final Logger logger = Logger.getLogger(AspDlvReasoner.class.getName());
	public String dlv_solver_path = "C:/Special_Programs/dlv/dlv.mingw.exe";
	public AspModelContainer aspContainer;
	
	public AspDlvReasoner() {
		aspContainer = new AspModelContainer();
	}
	
	public StringBuffer getAspModelsAsString(int nrmodels, boolean all) {
		// Param nrmodels: number of models to access
		logger.log(Level.INFO, "getAspModelsAsString():");
		int mcount = 0;
		StringBuffer buffer = new StringBuffer();
		List<Model> aspmodels = aspContainer.getResultAspModels();
		if (aspmodels != null) {

			for (Model onemod : aspmodels) {
				++mcount;
				if ((all)||(mcount <= nrmodels)) {
					buffer.append("MODEL_" + mcount + " {\n");
					buffer.append(onemod.toString());
					buffer.append("\n}\n");
				}
			}
		}
		return buffer;
	}
	
	
	public StringBuffer parseAspModelToXML(List<Model> models) {
		logger.log(Level.INFO, "parseAspModelToXML():");
		StringBuffer buffer = new StringBuffer();
		boolean hasModel = false;
		boolean negativeLit = false;
		/* Scroll all models computed */
		int h = 0; // models
		int i = 0; // predicates
		int j = 0; // literals
		buffer.append("<?xml version='1.0' encoding='UTF-8'?>");
		if ((models != null) && (!models.isEmpty())) {
			hasModel = true;
			buffer.append("\n<aspmodels>");
			for (Model model : models) {
				h++;
				i = 0;
				j = 0;
				buffer.append("\n<model num='" + h + "'>\n");
				Predicate firstPred = model.firstPredicate();
				i++;
				// System.out.println("--firstPred:" + firstPred.toString());
				while (firstPred.hasMoreLiterals()) {
					Literal literal = firstPred.nextLiteral();
					j++;					
					StringBuffer litxml = parseLiteralToXML(literal);
					buffer.append(litxml);
					//System.out.println(j + ": Literal*: " + literal.toString());
				}
				while (model.hasMorePredicates()) {
					Predicate predicate = model.nextPredicate();
					i++;
					j = 0;
					while (predicate.hasMoreLiterals()) {
						Literal literal = predicate.nextLiteral();
						j++;
						StringBuffer litxml = parseLiteralToXML(literal);
						buffer.append(litxml);
						//System.out.println(j + ": Literal**: " + literal.toString());
					}
				}
				buffer.append("\n</model>");

			}
			buffer.append("\n</aspmodels>\n");
		}
		logger.log(Level.INFO, "parseAspModel(): ASP Model exist: " + hasModel);
		//if (hasModel)
		//	System.out.println("=AspDlvReasoner: MODELS:PREDICATES:LITERALS=(" + h + ":" + i + ":" + j + ")");
		return buffer;
	}

	
	public StringBuffer parseLiteralToXML(Literal literal){
		StringBuffer buffer = new StringBuffer();
		boolean negativeLit = false;
		
		// negated literal?
		if ("-".equalsIgnoreCase(literal.toString().substring(0, 1)))
			negativeLit = true;
		// parsing arguments
		String[] argset = null;
		String[] litstr = literal.toString().split("\\(");
		if ((litstr != null) && (litstr.length > 1)) {
			String argstr = litstr[1].split("\\)")[0];
			argset = argstr.split(",");
		}
		// buffer.append("<" + h + ":" + i + ":" + j + ">");
		buffer.append("<literal predicate='" + literal.getName() + "' atom='" + literal.getAtom()
				+ "' negative='" + negativeLit + "'>");
		if ((argset != null)) {
			
			for (int iarg = 0; iarg < argset.length; iarg++) {
				//TODO: Testing if arg value strings have an extra space ' ' at the beginning
				String argvalue =  argset[iarg];
				if(argvalue.startsWith(" ")){ 
					argvalue = argvalue.substring(1);
					//System.out.println("=AspDlvReasoner: XXXXXXX EXTRA SPACE REMOVED from arg value" + argvalue);
				}
				
				buffer.append("\n\t<arg num='" + iarg + "'>" + argvalue + "</arg>");
			}
			buffer.append("\n</literal>");
		}
		
		return buffer;
	}
	
	
	
	public boolean invokeAspRules() {
		boolean ok=false;
		if(!(new File(dlv_solver_path).exists())){
			logger.log(Level.INFO, "invokeAspRules(): ASP Solver NOT exists!");
			return false;
		}
		
		if (this.aspContainer.isRulesAndFactsLoaded()) {
			String rulefile = this.aspContainer.getRulefile();
			String factfile = this.aspContainer.getFactfile();
			int numOfModels = this.aspContainer.getNumOfModels();

			//System.out.println("=AspDlvReasoner:invokeAspRules: Invoking Dlv solver...");
			logger.log(Level.INFO, "invokeAspRules():Invoking DLV solver...");
			try {
				List<Model> asp_models = this.invokeDlvReasoning(rulefile, factfile, numOfModels);
				if ((asp_models != null) && (!asp_models.isEmpty())) {
					//System.out.println("=AspDlvReasoner:invokeAspRules: Result models #" + asp_models.size());
					logger.log(Level.INFO, "invokeAspRules(): Result models #" + asp_models.size());
					//Saving ASP models in ASP format.
					aspContainer.setResultAspModels(asp_models);
					//Parsing ASP models into XML format.
					StringBuffer xml_modelbuf = parseAspModelToXML(asp_models);
					//Saving ASP models in XML format.
					aspContainer.setResultModelsAsXML(xml_modelbuf);
					logger.log(Level.INFO, "invokeAspRules(): Result models saved in ASP and XML formats!");
					ok=true;
				} else{
					//System.out.println("=AspDlvReasoner:invokeAspRules: Result models NULL?");
					logger.log(Level.INFO, "invokeAspRules(): Result models NULL?");
				}
					

			} catch (DLVInvocationException | IOException e) {
				logger.log(Level.ERROR, e.getMessage());
				e.printStackTrace();
			}
		} else{
			//System.out.println("=AspDlvReasoner:invokeAspRules: Rules and Facts files NOT Loaded!");
			logger.log(Level.INFO, "invokeAspRules(): Rules and Facts files NOT Loaded!");
		}
			
		
		return ok;
				
	}

	public List<Model> invokeDlvReasoning(String rules_dlv_file, String facts_db_file, int numOfModels)
			throws DLVInvocationException, IOException { // TOIMII: huom
		//NOTE: tiedostossa po.	rivi #maxint = ?. TAI sen voi asettaa invocation.setMaxint(20);											
															
		logger.log(Level.INFO, "invokeDlvReasoning():");
		boolean hasModel = false;

		/* I create a new instance of DLVInputProgram */
		DLVInputProgram inputProgram = new DLVInputProgramImpl();

		if (rules_dlv_file != null)
			inputProgram.addFile(rules_dlv_file); // TOIMII:kun lisäsin #maxint
													// = 2 .
		if (facts_db_file != null)
			inputProgram.addFile(facts_db_file);

		DLVInvocation invocation = DLVWrapper.getInstance().createInvocation(dlv_solver_path);
		// "C:/Special_Programs/dlv/dlv.mingw.exe"); WorkPC
		// "C:/SpecialPrograms/dlv_db/dldb_win32.exe"); //in HP

		/* VPA ADDIN: */
		invocation.setInputProgram(inputProgram);
		invocation.setNumberOfModels(numOfModels); // 10

		/*
		 * FILTERS TOIMII List<String> filters=new ArrayList();
		 * filters.add("subbranch"); filters.add("mainbranch");
		 * invocation.setFilter(filters, true); // OPTIONS TOIMII
		 * invocation.addOption("-nofacts");
		 */

		/*
		 * QUERY TOIMII // NormalAtom(boolean trueNegated,String name); Atom
		 * atom = new NormalAtom(false,"lego(leg1)");
		 * //NormalAtom(true,"lego(leg1)") -> HAS MODEL: false
		 * invocation.setQuery(new Query(new Conjunction(new Literal(true,
		 * atom))));
		 */

		/*
		 * MODE 1: TOIMII I create a new observer that receive a notification
		 * for the models computed and store them in a list
		 * 
		 * final List<ModelResult> modresults = new ArrayList<ModelResult>();
		 * ModelHandler modelHandler = new ModelHandler() { final public void
		 * handleResult(DLVInvocation obsd, ModelResult res) {
		 * modresults.add(res);
		 * System.out.println("=AspDlvReasoner: MODEL RESULTS SAVED:\n" +
		 * res.toString()); } }; invocation.subscribe(modelHandler);
		 */

		/*
		 * MODE 2: TOIMII Observer creation
		 * 
		 *
		 * PredicateHandler predicateHandler = new PredicateHandler() { public
		 * void handleResult(DLVInvocation obsd, PredicateResult res) {
		 * System.out.println("MODELID:" + res.getModelID() + " PREDICATE====" +
		 * res.toString()); } }; // Observer registration
		 * invocation.subscribe(predicateHandler);
		 */

		/*
		 * MODE 3: TOIMII Another mode to scroll the computed models is to use
		 * the ModelBufferedHandler that is a concrete observer that work like
		 * Enumeration. NOTE: the ModelBufferedHandler subscribe itself to the
		 */
		ModelBufferedHandler modelBufferedHandler = new ModelBufferedHandler(invocation);

		/*--- RUNING DLV: In this moment I can start the DLV execution ---*/
		invocation.run();

		/* Scroll all models computed */
		int h = 0; // models
		int i = 0; // predicates
		int j = 0; // literals

		List<Model> models = new ArrayList<Model>();
		while (modelBufferedHandler.hasMoreModels()) {
			Model model = modelBufferedHandler.nextModel();
			// Saving models to a list
			models.add(model);
			/*
			 * parsing predicates and literals for printing Predicate firstPred
			 * = model.firstPredicate(); i++;
			 * System.out.println("----------------firstPred:" +
			 * firstPred.toString()); h++; while (firstPred.hasMoreLiterals()) {
			 * Literal literal = firstPred.nextLiteral(); j++;
			 * System.out.println(j + ": Literal*: " + literal.toString()); }
			 * while (model.hasMorePredicates()) { Predicate predicate =
			 * model.nextPredicate(); i++; while (predicate.hasMoreLiterals()) {
			 * Literal literal = predicate.nextLiteral(); j++; System.out
			 * .println(j + ": Literal**: " + literal.toString()); } }
			 */
		}

		/* If i wont to wait the finish of execution, i can use thi method */
		invocation.waitUntilExecutionFinishes();
		/*
		 * At the term of execution, I can control the errors created by DLV
		 * invocation
		 */
		List<DLVError> errors = invocation.getErrors();// line 0: can't open
														// input??
		if ((errors != null) && (!errors.isEmpty())) {
			//System.out.println("????ERRORS Exists");
			logger.log(Level.ERROR, "invokeDlvReasoning(): DLV ERROR? ");
			for (DLVError err : errors) {
				logger.log(Level.ERROR, "invokeDlvReasoning(): "  + err.getText());
				//System.out.println("ERROR: " + err.getText());
			}
		}

		hasModel = invocation.haveModel();
		//System.out.println("HAS MODEL: " + invocation.haveModel());
		logger.log(Level.INFO, "invokeDlvReasoning(): MODEL EXITS: " + invocation.haveModel());
		//if (hasModel)
			//System.out.println("=AspDlvReasoner: MODELS:PREDICATES:LITERALS=(" + h + ":" + i + ":" + j + ")");
		return models;
	}

	public void saveAspModelToFile(String filepath) {

		StringBuffer modelbuf = aspContainer.getResultModelsAsXML();
		if (modelbuf != null) {
			writeAspModelToFile(modelbuf, filepath);
			//System.out.println("=AspDlvReasoner:saveAspModelToFile: ASP Models saved to file:" + filepath);
			logger.log(Level.INFO, "saveAspModelToFile(): ASP Models saved to file:" + filepath);
		}

	}

	public void writeAspModelToFile(StringBuffer modelbuf, String filepath) {

		FileUtil.writeTextFile(modelbuf.toString(), filepath);

	}

	public AspModelContainer getAspContainer() {
		return aspContainer;
	}



	public void setDlv_solver_path(String dlv_solver_path) {
		this.dlv_solver_path = dlv_solver_path;
	}

	public static void main(String[] args) { // throws DLVInvocationException,
												// IOException {

		String rules_dlv_file = "configure/asp_dlv/legotower/legotower2rules.dlv";
		String facts_db_file = "configure/asp_dlv/legotower/legotower2facts.db";
		String filepath = "data/generated/asp_models/asp_testresults.txt";

		AspDlvReasoner asp = new AspDlvReasoner();
		try {
			int numOfModels = 2;
			List<Model> models = asp.invokeDlvReasoning(rules_dlv_file, facts_db_file, numOfModels);
			StringBuffer modelbuf = asp.parseAspModelToXML(models);
			asp.writeAspModelToFile(modelbuf, filepath);

		} catch (DLVInvocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
