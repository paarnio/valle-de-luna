package siima.app.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import siima.app.model.helper.AnyTypeValueHelper;
import siima.app.model.helper.UriHelper;
import siima.models.jaxb.caex.CAEXFile;

public class RdfContainer {
	private static final Logger logger=Logger.getLogger(RdfContainer.class.getName());
	private VeloContainer velocity;
	private JaxbContainer graphbuilder;
	private Model defaultRdfModel = ModelFactory.createDefaultModel();
	private Model instanceHRdfModel = ModelFactory.createDefaultModel();
	private Model systemUnitClassLRdfModel = ModelFactory.createDefaultModel();
	private Model roleClassLRdfModel = ModelFactory.createDefaultModel();
	private Map rdfModelMap;
	private Model genRdfModel; //latest genereted model
	private Model combinedRdfModel;
	private Model mergedRdfModel;
	private String curModelKey; //latest model category key
	
	public RdfContainer(JaxbContainer graphbuilder){
		
		this.graphbuilder=graphbuilder;
		rdfModelMap = new HashMap<String,Model>();
		rdfModelMap.put("default", defaultRdfModel);
		rdfModelMap.put("instancehierarchy", instanceHRdfModel);
		rdfModelMap.put("systemunitclasslib", systemUnitClassLRdfModel);
		rdfModelMap.put("roleclasslib", roleClassLRdfModel);
	}
	
	public void genereteCaexOntologyModel(String modelkey) {
		// String modelkey = "instancehierarchy"; ; default; "combined"
		modelkey = modelkey.toLowerCase();
		this.curModelKey = modelkey;
		initVelocity();

		if ("allmodels".equalsIgnoreCase(modelkey)) { 
			// combining all model categories
			combinedRdfModel = ModelFactory.createDefaultModel();
			for (String curkey : (Set<String>) rdfModelMap.keySet()) {
				if (!"default".equalsIgnoreCase(curkey)) {
					genRdfModel = ModelFactory.createDefaultModel();
					evaluateVelocityEngine(curkey, genRdfModel);
					Model categorymodel = (Model) rdfModelMap.get(curkey);
					if (categorymodel != null) { // TODO: Two options ?
						categorymodel.add(genRdfModel);
						// categorymodel = this.genRdfModel;
						combinedRdfModel.add(genRdfModel);
					}

				}

			}
			logger.log(Level.INFO, "Genereted RDF Model category: " + modelkey + " with total size# " + combinedRdfModel.size());
		} else {

			genRdfModel = ModelFactory.createDefaultModel();
			evaluateVelocityEngine(modelkey, genRdfModel);
			Model categorymodel = (Model) rdfModelMap.get(modelkey);
			if (categorymodel != null) { // TODO: Two options ?
				categorymodel.add(genRdfModel);
				// categorymodel = this.genRdfModel;
			}
			logger.log(Level.INFO, "Genereted RDF Model category: " + modelkey + " size# " + genRdfModel.size());
		}

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
		AnyTypeValueHelper anyContentHelper = new AnyTypeValueHelper(this.graphbuilder);
		velocity.putVelocityContext("AnyValueHelper", anyContentHelper); 
	}

	public void evaluateVelocityEngine(String modelkey, Model rdfmodel) {
		//Ontology submodel keys: default; instancehierarchy"; systemunitclasslib; roleclasslib
		velocity.evaluateEngine(modelkey, rdfmodel);//"instancehierarchy");
	}


	public void mergeRDFModels(){
		// Merging all previously generated category models
		// Merging also combined model (potential redundancy)
		logger.log(Level.INFO, "mergeRDFModels(): Merging existing generated RDF models.");
		this.curModelKey = "merged";
		
		mergedRdfModel = ModelFactory.createDefaultModel();
		for (String curkey : (Set<String>) rdfModelMap.keySet()) {
			if (!"default".equalsIgnoreCase(curkey)) {
				Model categorymodel = (Model) rdfModelMap.get(curkey);
				if (categorymodel != null) { 
					mergedRdfModel.add(categorymodel);
				}

			}

		}
		// Merging also combined model (potential redundancy)
		if(combinedRdfModel!=null) mergedRdfModel.add(combinedRdfModel);
		logger.log(Level.INFO, "mergeRDFModels(): Merged model total size# " + mergedRdfModel.size());
	}
	
	
	public void writeRdfModelToFile(String newOntologyFile) {
		Model savemodel;
		try {//
			if(("allmodels".equalsIgnoreCase(curModelKey))||("combined".equalsIgnoreCase(curModelKey))){
				savemodel = combinedRdfModel;
			} else if("merged".equalsIgnoreCase(curModelKey)){
				savemodel = mergedRdfModel;
			} else { //else the latest generated model
				savemodel = genRdfModel;
			}
			FileOutputStream outputStream = new FileOutputStream(newOntologyFile);
			if (savemodel != null) {
				if ((newOntologyFile.contains(".ttl"))||(newOntologyFile.contains(".TTL"))) {
					savemodel.write(outputStream, "TURTLE");
					logger.log(Level.INFO, "writeRdfModelToFile():TURTLE format TO File: " + newOntologyFile);
				} else if ((newOntologyFile.contains(".owl"))||(newOntologyFile.contains(".OWL"))) { 
					savemodel.write(outputStream, "RDF/XML");
					logger.log(Level.INFO, "writeRdfModelToFile():RDF/XML Format TO File: " + newOntologyFile);
				} else {
					logger.log(Level.INFO, "writeRdfModelToFile():UNKNOWN suffix (expected .ttl or .owl): " + newOntologyFile);
					//System.out.println(
					//		"TODO: VeloContainer: writeRdfModelToFile -- Not Turtle file suffix: " + newOntologyFile);
				}
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}
	
	public String getSerializedRdfModel(String format) {
		/* 
		 *  format: e.g. "TURTLE"; TTL; RDFXML; RDFJSON; NTRIPLES
		 *  https://jena.apache.org/documentation/io/rdf-output.html#formats
		 */
		Model sermodel;
		String serialized = null;
		String defFormat = "TURTLE";
		if(format==null) format= defFormat;
		
		if(("allmodels".equalsIgnoreCase(curModelKey))||("combined".equalsIgnoreCase(curModelKey))){
			sermodel = combinedRdfModel;
		} else if("merged".equalsIgnoreCase(curModelKey)){
			sermodel = mergedRdfModel;
		} else {
			sermodel = genRdfModel;
		}
		
		if (sermodel != null) {
				StringWriter strWriter = new StringWriter();
				sermodel.write(strWriter, format);
				serialized = strWriter.toString();
				logger.log(Level.INFO,"getSerializedRdfModel: serialized is empty: " + serialized.isEmpty());
				//this.rdfModel.write(System.out, format);			

		}
		
		return serialized;
	}
	
	
	

}
