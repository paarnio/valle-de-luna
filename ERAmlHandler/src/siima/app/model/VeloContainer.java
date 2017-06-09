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

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VeloContainer {
	private static final Logger logger=Logger.getLogger(VeloContainer.class.getName());
	private VelocityEngine engine = new VelocityEngine();
	private VelocityContext vcontext = new VelocityContext();
	ByteArrayOutputStream vmOutputStream = new ByteArrayOutputStream();
	Writer resultWriter;
	public String vmFile = "./configure/velocity/vmFile.vm";
	public String outputFile= "./configure/velocity/generated_ontology.ttl";
	private Model rdfModel = ModelFactory.createDefaultModel();
	
	public VeloContainer(String key, Object value){
		resultWriter = new OutputStreamWriter(vmOutputStream);
		vcontext.put(key, value);
	}

	public void putVelocityContext(String key, Object value){
		//context.put("StringUtils", org.apache.commons.lang3.StringUtils.class);
		vcontext.put(key, value);
	}
	
	public void evaluateEngine(){
		try {
			FileInputStream vmFileInput = new FileInputStream(vmFile);
			//FileOutputStream outputStream = new FileOutputStream(outputFile);
			
			engine.evaluate(vcontext,  resultWriter,  "caexRdf", new InputStreamReader(vmFileInput));
			resultWriter.close();
			
			System.out.println("=========VELOCITY OUTPUT:\n" + vmOutputStream.toString());
			
			ByteArrayInputStream modelInputStream = new ByteArrayInputStream(vmOutputStream.toByteArray());
			
			/* RDF Model */
			rdfModel.read(modelInputStream,null,"RDF/XML"); // (in, base, lang)
			//rdfModel.write(outputStream,"TURTLE");
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public void writeRdfModelToFile(String newOntologyFile) {
		try {
			FileOutputStream outputStream = new FileOutputStream(newOntologyFile);
			if (rdfModel != null) {
				if (newOntologyFile.contains(".ttl")) {
					rdfModel.write(outputStream, "TURTLE");
				} else {

					System.out.println(
							"TODO: VeloContainer: writeRdfModelToFile -- Not Turtle file suffix: " + newOntologyFile);
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
		if (this.rdfModel != null) {
				StringWriter strWriter = new StringWriter();
				this.rdfModel.write(strWriter, format);
				serialized = strWriter.toString();
				logger.log(Level.INFO,"getSerializedRdfModel: serialized is empty: " + serialized.isEmpty());
				//this.rdfModel.write(System.out, format);			

		}
		
		return serialized;
	}
	
	
	public Model getRdfModel() {
		return rdfModel;
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
