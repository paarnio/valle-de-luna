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
	//ByteArrayOutputStream vmOutputStream = new ByteArrayOutputStream();
	//Writer resultWriter;
	public String vm_default = "./configure/velocity/vmFile_default.vm";
	public String vm_ihierarchy = "./configure/velocity/vmFile_ihierarchy_ontogen.vm";
	public String vm_systemunitclasslib = "./configure/velocity/vmFile_systemunitclasslib_ontogen.vm";
	public String vm_roleclasslib = "./configure/velocity/vmFile_roleclasslib_ontogen.vm";
	public String vm_interfaceclasslib = "./configure/velocity/vmFile_interfaceclasslib_ontogen.vm";
	public Map ontoVmFileMap;	
		
	public String outputFile= "./configure/velocity/generated_ontology.ttl";
	//private Model genRdfModel = ModelFactory.createDefaultModel();
	
	public VeloContainer(String key, Object value){
		//resultWriter = new OutputStreamWriter(vmOutputStream);
		vcontext.put(key, value);
		ontoVmFileMap = new HashMap<String,String>();
		ontoVmFileMap.put("default", vm_default);
		ontoVmFileMap.put("instancehierarchy", vm_ihierarchy);
		ontoVmFileMap.put("systemunitclasslib", vm_systemunitclasslib);
		ontoVmFileMap.put("roleclasslib", vm_roleclasslib);
		ontoVmFileMap.put("interfaceclasslib", vm_interfaceclasslib);
		
	}

	public void putVelocityContext(String key, Object value){
		//context.put("StringUtils", org.apache.commons.lang3.StringUtils.class);
		vcontext.put(key, value);
	}
	
	public void evaluateEngine(String modelkey, Model genModel){
		ByteArrayOutputStream vmOutputStream = new ByteArrayOutputStream();
		Writer resultWriter = new OutputStreamWriter(vmOutputStream);
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
			genModel.read(modelInputStream,null,"RDF/XML"); // (in, base, lang)
			//this.genRdfModel = genModel; //TEMP
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	

	public void setVmDefault(String vmFile) {
		this.vm_default = vmFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

}
