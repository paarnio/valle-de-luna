<?xml version="1.0"?>
<!-- vpa: from http://edutechwiki.unige.ch/en/XSLT_to_generate_SVG_tutorial -->
<xsl:stylesheet version="1.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns="http://www.w3.org/2000/svg"
		>
<xsl:output
     method="xml"
     doctype-system="about:legacy-compat"
     omit-xml-declaration = "yes"
     encoding="UTF-8"
     indent="yes" />
  
  <xsl:template match="thing">
   <html xmlns="http://www.w3.org/1999/xhtml">
     <head>
       <meta charset="utf-8"></meta>
       <title>XHTML5 + SVG example</title>
     </head>
     <body>
       <p>This line is HTML, embedded SVG is below. Read the <a
       href="http://edutechwiki.unige.ch/en/XSLT_to_generate_SVG_tutorial">
       XSLT to generate SVG tutorial</a></p>
       <svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" >
	 <rect x="10" y="10" width="{width}" 
	       height="{height}" fill="red" stroke="black"/>  
       </svg>
     </body>
   </html>
  </xsl:template>
  
</xsl:stylesheet>
