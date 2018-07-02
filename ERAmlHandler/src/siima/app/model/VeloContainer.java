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
	// 2018-07-01 added for caex 3
	public String vm_default_caex3 = "./configure/velocity/vmFile_default_caex3.vm";
	public String vm_ihierarchy_caex3 = "./configure/velocity/vmFile_ihierarchy_caex3_ontogen.vm";
	public String vm_systemunitclasslib_caex3 = "./configure/velocity/vmFile_systemunitclasslib_caex3_ontogen.vm";
	public String vm_roleclasslib_caex3 = "./configure/velocity/vmFile_roleclasslib_caex3_ontogen.vm";
	public String vm_interfaceclasslib_caex3 = "./configure/velocity/vmFile_interfaceclasslib_caex3_ontogen.vm";
	public String vm_attributetypelib_caex3 = "./configure/velocity/vmFile_atttypelib_caex3_ontogen.vm";
	public Map ontoVmFileMap;	
		
	public String outputFile= "./configure/velocity/generated_ontology.ttl";
	//private Model genRdfModel = ModelFactory.createDefaultModel();
	
	public VeloContainer(String caexVersion, String key, Object value){
		// caexVersions 2.51 | 3.0
		//resultWriter = new OutputStreamWriter(vmOutputStream);			
		vcontext.put(key, value);
		ontoVmFileMap = new HashMap<String,String>();
		
		if ("2.15".equals(caexVersion)) {
			logger.log(Level.INFO, "VeloContainer():Ontology generation for CAEX VERSION:" + caexVersion + " (ALLOWED 2.15/3.0) \n");
			ontoVmFileMap.put("default", vm_default);
			ontoVmFileMap.put("instancehierarchy", vm_ihierarchy);
			ontoVmFileMap.put("systemunitclasslib", vm_systemunitclasslib);
			ontoVmFileMap.put("roleclasslib", vm_roleclasslib);
			ontoVmFileMap.put("interfaceclasslib", vm_interfaceclasslib);
			// 2018-07-01 added for caex 3
			ontoVmFileMap.put("attributetypelib", vm_attributetypelib_caex3);
		} else if ("3.0".equals(caexVersion)) {
			logger.log(Level.INFO, "VeloContainer():Ontology generation for CAEX VERSION:" + caexVersion + " (ALLOWED 2.15/3.0) \n");
			ontoVmFileMap.put("default", vm_default_caex3);
			ontoVmFileMap.put("instancehierarchy", vm_ihierarchy_caex3);
			ontoVmFileMap.put("systemunitclasslib", vm_systemunitclasslib_caex3);
			ontoVmFileMap.put("roleclasslib", vm_roleclasslib_caex3);
			ontoVmFileMap.put("interfaceclasslib", vm_interfaceclasslib_caex3);
			// 2018-07-01 added for caex 3
			ontoVmFileMap.put("attributetypelib", vm_attributetypelib_caex3);
		} else {
			logger.log(Level.INFO, "VeloContainer():UNKNOWN CAEX VERSION:  OUTPUT:\n" + caexVersion + " (ALLOWED 2.15/3.0) \n");
		}
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
