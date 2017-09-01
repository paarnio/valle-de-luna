package siima.app.model;

import java.nio.file.Path;
import java.util.List;

import siima.app.model.tree.ElementNode;
import siima.models.jaxb.caex.AttributeType;
import siima.models.jaxb.caex.CAEXObject;

public interface JaxbContainerInterface {
	
	//Getters & Setters
	//---- CAEX 3.0 REQUIRED ADDITION
	public ElementNode getAttributetlRootElement();
	
	public ElementNode getIfaceclRootElement(); 

	public ElementNode getRoleclRootElement();
	
	public ElementNode getSuclRootElement();
	
	public ElementNode getIeRootElement();
	
	public void setIeRootElement(ElementNode rootElement);
		
	public int getCaexFileCount();
	
	public void setCaexFileCount(int caexFileCount);
	
	public Object getCaexRootObject(); 
	public Path getMainFilePath(); 
	
	public void setMainFilePath(Path mainFilePath); 
	public List<Path> getLoadedCaexFilePaths();

	public void setLoadedCaexFilePaths(List<Path> loadedCaexFilePaths); 
	public void setValidationSchemaFile(String validationSchemaFile); 
	
	//Basic methods

	public void clearRootElements();
	
	public ElementNode buildElementGraphFromXML();
	
	public boolean loadData(Path path);
	
	public boolean saveData(String filepath);
	
	public String getBasicInfo(Object nodeobject);


}
