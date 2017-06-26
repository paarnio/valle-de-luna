package siima.app.model;

import java.util.HashMap;
import java.util.Map;

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
	
	public RdfContainer(JaxbContainer graphbuilder){
		
		this.graphbuilder=graphbuilder;
		rdfModelMap = new HashMap<String,Model>();
		rdfModelMap.put("default", defaultRdfModel);
		rdfModelMap.put("instancehierarchy", instanceHRdfModel);
		rdfModelMap.put("systemunitclasslib", systemUnitClassLRdfModel);
		rdfModelMap.put("roleclasslib", roleClassLRdfModel);
	}
	
	public void genereteCaexOntologyModel(String modelkey) {
		//String modelkey = "instancehierarchy";
		modelkey=modelkey.toLowerCase();
		initVelocity();
		evaluateVelocityEngine(modelkey);
		Model genmodel= (Model)rdfModelMap.get(modelkey);
		genmodel= velocity.getGenRdfModel();
		logger.log(Level.INFO, "Genereted RDF Model type: " + modelkey + " size# " + genmodel.size());
		
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

	public void evaluateVelocityEngine(String modelkey) {
		//Ontology submodel keys: default; instancehierarchy"; systemunitclasslib; roleclasslib
		velocity.evaluateEngine(modelkey);//"instancehierarchy");
	}

	public String  getSerializeRdfModel(String format) {
		/* format "TURTLE"
		 * format: e.g. "TURTLE"; TTL; RDFXML; RDFJSON; NTRIPLES
		 * https://jena.apache.org/documentation/io/rdf-output.html#formats
		 */
		String defFormat = "TURTLE";
		if(format==null) format= defFormat;
		String serialized = velocity.getSerializedRdfModel(format);
		logger.log(Level.INFO,"getSerializeRdfModel()");
		return serialized;
	}
	
	
	

}
