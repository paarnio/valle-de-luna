/* XSLTransform.java
 * Tested in egit: TRAXExamples/PlainTransform
 */
package siima.app.control;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class XSLTransform {
	private static final Logger logger=Logger.getLogger(XSLTransform.class.getName());
	private TransformerFactory factory = TransformerFactory.newInstance();
	
	private String xslContextSrcFile; //source .xml
	private String xslContextXslFile; //.xsl
	private String xslContextTrgFile; //target file .trg
	

	public void doSpecificTransform(String transformtype, String sourcefile, String targetfile) {
		/*
		 * Implemented specific transform types:"caex2jmonkey"; "caex2aspfacts"; 
		 * Implemented generic transform: "contextSrc2Trg"
		 * 
		 */
		System.out.println("-XSLTransform: doTransform(): " + transformtype);

		String caex2jmonkey_xsl = "configure/xslt/CaexLego2monkey3.xsl";
		String caex2aspfacts_xsl = "configure/xslt/Caex2AspFacts.xsl";
		Source xslstream = null;

		if ("caex2jmonkey".equalsIgnoreCase(transformtype)) {
			xslstream = new StreamSource(caex2jmonkey_xsl);
		} else if ("caex2aspfacts".equalsIgnoreCase(transformtype)) {
			xslstream = new StreamSource(caex2aspfacts_xsl);
		} else if ("contextSrc2Trg".equalsIgnoreCase(transformtype)) {
			sourcefile = this.getXslContextSrcFile();
			targetfile = this.getXslContextTrgFile();
			String xslfile = this.getXslContextXslFile();
			xslstream = new StreamSource(this.getXslContextXslFile());
			if((xslfile==null)||(sourcefile==null)){
				logger.info("doSpecificTransform: All XSL Context files not specified for transform: " + transformtype);
				return;
			}			
			if(targetfile==null){ //Target file name based on source name extended with .trg
			targetfile = sourcefile.replaceAll("\\.xml", "_target.trg");
				
			}
			
		} 

		if (xslstream != null) {
			Transformer transformer;
			try {
				transformer = factory.newTransformer(xslstream);
				Source xml = new StreamSource(sourcefile); // "data/caex_lego/Lego_example_mod1.aml"
				Result result = new StreamResult(targetfile); // "data/generated/CAEXLego2monkey3_results.xml");
				transformer.transform(xml, result);
				logger.info("doSpecificTransform: Transformation target saved: " + targetfile);

			} catch (TransformerConfigurationException e) {
				logger.log(Level.ERROR, e.getMessage());
				e.printStackTrace();
			} catch (TransformerException e) {
				logger.log(Level.ERROR, e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public void setXslContex(File[] ctxfiles) {
		boolean srcbool = false;
		boolean xslbool = false;
		boolean trgbool = false;
		
		if (ctxfiles != null) {
			
			for (int i = 0; i < ctxfiles.length; i++) {
				File file = ctxfiles[i];
				String path = file.getAbsolutePath();
				if (path.contains(".xml")) {
					setXslContextSrcFile(path);
					srcbool = true;
				} else if (path.contains(".xsl")) {
					setXslContextXslFile(path);
					xslbool = true;
				} else if (path.contains(".trg")) {
					setXslContextTrgFile(path);
					trgbool = true;
				}			
			}
			
		}
		logger.info("setXslContex: loaded (.xml;.xsl;.trg):(" + srcbool + ";" + xslbool + ";" + trgbool + ")");
	}
	
	public String getXslContextSrcFile() {
		return xslContextSrcFile;
	}


	public void setXslContextSrcFile(String xslContextSrcFile) {
		this.xslContextSrcFile = xslContextSrcFile;
	}


	public String getXslContextXslFile() {
		return xslContextXslFile;
	}


	public void setXslContextXslFile(String xslContextXslFile) {
		this.xslContextXslFile = xslContextXslFile;
	}


	public String getXslContextTrgFile() {
		return xslContextTrgFile;
	}


	public void setXslContextTrgFile(String xslContextTrgFile) {
		this.xslContextTrgFile = xslContextTrgFile;
	}

}
