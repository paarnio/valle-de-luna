<?xml version="1.0"?>

<!-- keep all three namespaces, xlink may not be needed, but the others are -->
<xsl:stylesheet version="1.0" 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:xlink="http://www.w3.org/1999/xlink" 
   xmlns="http://www.w3.org/2000/svg"
   >

<!-- **** output/input configuration -->

<xsl:output
     method="xml"
     doctype-system="about:legacy-compat"
     omit-xml-declaration = "yes"
     encoding="UTF-8"
     indent="yes" />

<!-- must remove white spaces within the list element, 
     otherwise count will not work -->
<xsl:strip-space elements="list"/>


<!-- **** The main template -->

 <xsl:template match="/">
   <html xmlns="http://www.w3.org/1999/xhtml">
     <head>
       <meta charset="utf-8"></meta>
       <title>XHTML5 + SVG example</title>
     </head>
     <body>
	 XHTML5 contents below are generated from XML with XSLT. Look
	 at the source of this page for the XML and at the <a
	 href="http://tecfa.unige.ch/guides/svg/ex/html5-xslt/intro-html5.xsl">web-links.xsl</a>
	 file.  Read the <a
       href="http://edutechwiki.unige.ch/en/XSLT_to_generate_SVG_tutorial">
       XSLT to generate SVG tutorial</a>
	 <hr/>
       <h1>
	 Look at the data !
       </h1>
       <svg xmlns="http://www.w3.org/2000/svg" width="100" height="110" >
      <rect x="1" 
	    y="1" 
	    width="98" 
	    height="108" 
	    fill="yellow" stroke="blue"/>
	 <xsl:apply-templates/>
       </svg>
     </body>
   </html>
 </xsl:template>

 <!-- **** data processing -->

 <!-- within the list element, 
      generate a rectangle that is a wide as the N of items * 10 -->
 <xsl:template match="list">
   <rect x="10" y="105" width="{10 * count(item)}" 
	 height="5" fill="black" stroke="red"/>
   <xsl:apply-templates/>
 </xsl:template>

 <!-- for each item
   * x position = its position in the list
   - y position = 100 minus its height -->

 <xsl:template match="item">
   <rect x="{10*position()}" y="{100 - .}" width="10"
	 height="{.}" fill="red" stroke="black"/>  
 </xsl:template>

</xsl:stylesheet>
