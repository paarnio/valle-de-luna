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
	private String xslContextTroutFile; //transform result file .trout
	

	public void doSpecificTransform(String transformtype, String sourcefile, String troutfile) {
		/*
		 * Implemented specific transform types:"caex2jmonkey"; "caex2aspfacts"; 
		 * Implemented generic transform: "contextSrc2Trout"
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
		} else if ("contextSrc2Trout".equalsIgnoreCase(transformtype)) {
			sourcefile = this.getXslContextSrcFile();
			troutfile = this.getXslContextTroutFile();
			String xslfile = this.getXslContextXslFile();
			xslstream = new StreamSource(this.getXslContextXslFile());
			if((xslfile==null)||(sourcefile==null)){
				logger.info("doSpecificTransform: All XSL Context files not specified for transform: " + transformtype);
				return;
			}			
			if(troutfile==null){ //Transform result/out file name based on source name extended with .trout
			//Note: source file extension can be .xml OR .aml
				if(sourcefile.endsWith(".xml")){
					troutfile = sourcefile.replaceAll("\\.xml", "_trout.trout");
				} else if(sourcefile.endsWith(".aml"))	{
					troutfile = sourcefile.replaceAll("\\.aml", "_trout.trout");
				} else {
					logger.log(Level.INFO, "doSpecificTransform(): Wrong XML source file extension. It should be EITHER .xml OR .aml");
				}
			}
			
		} 

		if (xslstream != null) {
			Transformer transformer;
			try {
				transformer = factory.newTransformer(xslstream);
				Source xml = new StreamSource(sourcefile); // "data/caex_lego/Lego_example_mod1.aml"
				Result result = new StreamResult(troutfile); // "data/generated/CAEXLego2monkey3_results.xml");
				transformer.transform(xml, result);
				logger.info("doSpecificTransform: Transformation result file (.trout) saved: " + troutfile);

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
		boolean troutbool = false;
		
		if (ctxfiles != null) {
			
			for (int i = 0; i < ctxfiles.length; i++) {
				File file = ctxfiles[i];
				String path = file.getAbsolutePath();
				if ((path.contains(".xml"))||(path.contains(".aml"))) {
					setXslContextSrcFile(path);
					srcbool = true;
				} else if (path.contains(".xsl")) {
					setXslContextXslFile(path);
					xslbool = true;
				} else if ((path.contains("trout"))||(path.contains(".txt"))) {
					//Note: allows result files e.g:
					//XSLT_result_trout.db, XSLT_result.txt, XSLT_result.trout  
					setXslContextTroutFile(path);
					troutbool = true;
				}			
			}
			
		}
		logger.info("setXslContex: loaded XSL(.xsl);XML(.xml | .aml); RESULT(.trout | trout.??? | .txt ):(" + srcbool + ";" + xslbool + ";" + troutbool + ")");
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


	public String getXslContextTroutFile() {
		return xslContextTroutFile;
	}


	public void setXslContextTroutFile(String xslContextTroutFile) {
		this.xslContextTroutFile = xslContextTroutFile;
	}

}
