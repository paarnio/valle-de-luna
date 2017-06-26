/*
 * see book. p. 332
 * 
 */
package siima.app.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import siima.util.FileUtil;

public class VeloContainer {
	private static final Logger logger=Logger.getLogger(VeloContainer.class.getName());
	private VelocityEngine engine = new VelocityEngine();
	private VelocityContext vcontext = new VelocityContext();
	ByteArrayOutputStream vmOutputStream = new ByteArrayOutputStream();
	Writer resultWriter;
	public String vmFile = "./configure/velocity/vmFile.vm";
	public String vm_ihierarchy = "./configure/velocity/vmFile_ihierarchy_ontogen.vm";
	public String vm_systemunitclasslib = "./configure/velocity/vmFile_systemunitclasslib_ontogen.vm";
	public String vm_roleclasslib = "./configure/velocity/vmFile_roleclasslib_ontogen.vm";
	public Map ontoVmFileMap;	
		
	public String outputFile= "./configure/velocity/generated_ontology.ttl";
	private Model genRdfModel = ModelFactory.createDefaultModel();
	
	public VeloContainer(String key, Object value){
		resultWriter = new OutputStreamWriter(vmOutputStream);
		vcontext.put(key, value);
		ontoVmFileMap = new HashMap<String,String>();
		ontoVmFileMap.put("default", vmFile);
		ontoVmFileMap.put("instancehierarchy", vm_ihierarchy);
		ontoVmFileMap.put("systemunitclasslib", vm_systemunitclasslib);
		ontoVmFileMap.put("roleclasslib", vm_roleclasslib);
		
		
	}

	public void putVelocityContext(String key, Object value){
		//context.put("StringUtils", org.apache.commons.lang3.StringUtils.class);
		vcontext.put(key, value);
	}
	
	public void evaluateEngine(String modelkey){
		try {
			String vm_file_path = (String)ontoVmFileMap.get(modelkey); //"default");
			if(vm_file_path!=null){
			FileInputStream vmFileInput = new FileInputStream(vm_file_path); //(vmFile);
			//FileOutputStream outputStream = new FileOutputStream(outputFile);
			
			engine.evaluate(vcontext,  resultWriter,  "caexRdf", new InputStreamReader(vmFileInput));
			resultWriter.close();
			
			//System.out.println("=========VELOCITY OUTPUT:\n" + vmOutputStream.toString());
			logger.log(Level.INFO, "evaluateEngine():VELOCITY OUTPUT:\n" + vmOutputStream.toString());
			//TEMP TEST
			//FileUtil.writeTextFile(vmOutputStream.toString(), "./velocity_output.txt");
			
			ByteArrayInputStream modelInputStream = new ByteArrayInputStream(vmOutputStream.toByteArray());
			
			/* RDF Model */
			genRdfModel.read(modelInputStream,null,"RDF/XML"); // (in, base, lang)
			//rdfModel.write(outputStream,"TURTLE");
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public void writeRdfModelToFile(String newOntologyFile) {
		try {
			FileOutputStream outputStream = new FileOutputStream(newOntologyFile);
			if (genRdfModel != null) {
				if ((newOntologyFile.contains(".ttl"))||(newOntologyFile.contains(".TTL"))) {
					genRdfModel.write(outputStream, "TURTLE");
					logger.log(Level.INFO, "writeRdfModelToFile():TURTLE format TO File: " + newOntologyFile);
				} else if ((newOntologyFile.contains(".owl"))||(newOntologyFile.contains(".OWL"))) { 
					genRdfModel.write(outputStream, "RDF/XML");
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
		String serialized = null;
		if (this.genRdfModel != null) {
				StringWriter strWriter = new StringWriter();
				this.genRdfModel.write(strWriter, format);
				serialized = strWriter.toString();
				logger.log(Level.INFO,"getSerializedRdfModel: serialized is empty: " + serialized.isEmpty());
				//this.rdfModel.write(System.out, format);			

		}
		
		return serialized;
	}
	
	
	public Model getGenRdfModel() {
		return genRdfModel;
	}

	public void setVmFile(String vmFile) {
		this.vmFile = vmFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

}
