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
import java.io.Writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VeloContainer {
	
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
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			
			engine.evaluate(vcontext,  resultWriter,  "caexRdf", new InputStreamReader(vmFileInput));
			resultWriter.close();
			
			System.out.println("=========VELOCITY OUTPUT:\n" + vmOutputStream.toString());
			
			ByteArrayInputStream modelInputStream = new ByteArrayInputStream(vmOutputStream.toByteArray());
			
			/* RDF Model */
			rdfModel.read(modelInputStream,null,"RDF/XML");
			rdfModel.write(outputStream,"TURTLE");
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
