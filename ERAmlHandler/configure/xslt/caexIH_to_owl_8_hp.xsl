<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:html="http://www.w3.org/1999/xhtml"
            xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
			xmlns:owl="http://www.w3.org/2002/07/owl#"
            xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
            xmlns:foaf="http://xmlns.com/foaf/spec/"
			xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
            xmlns:siima="http://siima.net/ontologies/2017/caex/"
			xmlns:amlont="http://data.ifs.tuwien.ac.at/aml/ontology#">
<!-- 2017-05-19 caexIH_to_owl_8_hp.xsl tested to RunningExample_SimpleIH.xml & Lego_example_mod2.xml (with ExternalInterfaces) -->
<!-- 2017-05-19 generates IE URI by concatenating IH & IE names (using tamplate-call with-param)-->
<!-- ontology uri changed and import caex ontology added -->
<!-- NOTE: MUST have namespace declaration in element definitions (internalElement) -->
<xsl:output method="xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<!-- VPA: MUISTA 4 ekaa kirjainta määrittää muuttujan nimen -->
<xsl:variable name="OntNS" select="'http://data.ifs.tuwien.ac.at/aml/ontology#'"/>
<xsl:variable name="SiiNS" select="'http://siima.net/ontologies/2017/caex/'"/>
<xsl:variable name="DTString" select="'http://www.w3.org/2001/XMLSchema#string'"/>

<xsl:template match="/">
<!-- NOTE: xml:base & xmlns declarations must be  in the following rdf:RDF element-->
<!-- The ns-declarations in stylesheet root element are also copied to the result xml -->
	<rdf:RDF xmlns="http://siima.net/ontologies/2017/caex/" xml:base="http://siima.net/ontologies/2017/caex/" xmlns:amlont="http://data.ifs.tuwien.ac.at/aml/ontology#">
	 	<xsl:comment>VPA: CAEX InstanceHierarchy Translation to caex OWL ontology </xsl:comment>
		<xsl:comment>VPA: CAEX InstanceHierarchy source FileName="RunningExample_SimpleIH.aml" </xsl:comment>
		<owl:Ontology rdf:about="http://siima.net/ontologies/2017/caex/">
			<!-- PROTEGE ei pysty avaamaan <owl:imports rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology"/> -->
		</owl:Ontology>
			<xsl:text>&#xA;</xsl:text>
			<xsl:comment> owl:Classes </xsl:comment>
			<xsl:text>&#xA;</xsl:text>
			<!-- CAEX Classes copied from CAEX owl -->
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#InstanceHierarchy">			
			<rdfs:subClassOf rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXObject"/>
			</owl:Class>
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement">			
			<rdfs:subClassOf rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#SystemUnitClass"/>
			</owl:Class>
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#SystemUnitClass">			
			<rdfs:subClassOf rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXObject"/>
			</owl:Class>
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXObject">			
			<rdfs:subClassOf rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXBasicObject"/>
			</owl:Class>
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface">			
			<rdfs:subClassOf rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InterfaceClass"/>
			</owl:Class>
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#InterfaceClass">			
			<rdfs:subClassOf rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXObject"/>
			</owl:Class>
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#InternalLink">			
			<rdfs:subClassOf rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXObject"/>
			</owl:Class>
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXBasicObject">
			</owl:Class>
			
			
			<xsl:text>&#xA;</xsl:text>
			<!-- CAEX ObjectProperties copied from CAEX owl -->
			<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#internalElement"/>
			<xsl:text>&#xA;</xsl:text>
			<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#externalInterface"/>
			<xsl:text>&#xA;</xsl:text>
			<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#internalLink"/>
			<xsl:text>&#xA;</xsl:text>
			<!-- CAEX DatatypeProperties copied from CAEX owl -->
			<owl:DatatypeProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#name">
				<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#string"/>
			</owl:DatatypeProperty>
			<owl:DatatypeProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#iD">
				<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#string"/>
			</owl:DatatypeProperty>
			<!-- Apply Templates -->
            <!--xsl:apply-templates select="/CAEXFile/InstanceHierarchy" mode="IElements"/-->
			<xsl:apply-templates select="/CAEXFile" />
			
	</rdf:RDF>
</xsl:template>

<xsl:template match="CAEXFile">
<!-- TODO: add CAEXFile instance with filename property -->
	<xsl:apply-templates select="./InstanceHierarchy"/>
</xsl:template>

<!--xsl:template match="InstanceHierarchy" mode="IElements"-->
<xsl:template match="InstanceHierarchy">
<xsl:variable name="IHName" select="./@Name"/>
	<owl:NamedIndividual rdf:about="{$SiiNS}{$IHName}">   	
        <rdf:type rdf:resource="{$OntNS}InstanceHierarchy"/>
		<!-- property statements -->
		<xsl:element name="name" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:datatype">
				<xsl:value-of select="string($DTString)"/>
			</xsl:attribute><xsl:value-of select="string($IHName)"/></xsl:element>
		<!-- link children -->
		<xsl:for-each select="./InternalElement">
		<xsl:variable name="IEName" select="./@Name"/>
		<!-- NOTE: MUST have namespace declaration in element definition (internalElement) -->
		<!-- generates IE URI by concatenating IH & IE names -->
		<xsl:element name="internalElement" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">			
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($SiiNS),'IH_',string($IHName),'_IE_',string($IEName))"/>
			</xsl:attribute>
		</xsl:element>
	</xsl:for-each>
	</owl:NamedIndividual>
	<!-- create InternalElement children -->
	<xsl:for-each select="./InternalElement">
	<xsl:call-template name="IElementTemplate">
		<xsl:with-param name="IHParentName" select="string($IHName)"/>
	</xsl:call-template>
	</xsl:for-each>
	<!--xsl:apply-templates select="./InternalElement" mode="IEnesting"/-->
</xsl:template>

<!--xsl:template match="InternalElement" mode="IEnesting"-->
<xsl:template name="IElementTemplate">
<xsl:param name="IHParentName"/>
<xsl:variable name="IEName" select="./@Name"/>
<xsl:variable name="IEID" select="./@ID"/>
<!-- generates IE URI by concatenating IH & IE names -->
<xsl:variable name="IEResource" select="concat(string($SiiNS),'IH_',string($IHParentName),'_IE_',string($IEName))"/>
	<owl:NamedIndividual rdf:about="{$IEResource}"> 
		<rdf:type rdf:resource="{$OntNS}InternalElement"/>
		<!-- property statements -->
		<xsl:element name="name" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:datatype">
				<xsl:value-of select="string($DTString)"/>
			</xsl:attribute><xsl:value-of select="string($IEName)"/></xsl:element>
		<xsl:element name="iD" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:datatype">
				<xsl:value-of select="string($DTString)"/>
			</xsl:attribute><xsl:value-of select="string($IEID)"/></xsl:element>
				
		<!-- link children InternalElement-->	
		<xsl:for-each select="./InternalElement">
		<xsl:variable name="IEName" select="./@Name"/>
		<!-- NOTE: MUST have namespace declaration in element definition (internalElement) -->
		<!-- generates IE URI by concatenating IH & IE names -->		
		<xsl:element name="internalElement" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($SiiNS),'IH_',string($IHParentName),'_IE_',string($IEName))"/>
			</xsl:attribute>
		</xsl:element>
		</xsl:for-each>		
		<!-- link children ExternalInterface-->
		<xsl:for-each select="./ExternalInterface">
		<xsl:variable name="ExtIName" select="./@Name"/>
		<!-- NOTE: MUST have namespace declaration in element definition (internalElement) -->
		<!-- generates IE URI by concatenating IH & IE names -->		
		<xsl:element name="externalInterface" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($SiiNS),'IH_',string($IHParentName),'_EXT_INTERFACE_',string($IEID),':',string($ExtIName))"/>
			</xsl:attribute>
		</xsl:element>
		</xsl:for-each>

	<!-- link children InternalLink-->
		<xsl:for-each select="./InternalLink">
		<xsl:variable name="ILinkName" select="./@Name"/>
		<!-- NOTE: MUST have namespace declaration in element definition (internalElement) -->
		<!-- generates IE URI by concatenating IH & IE names -->		
		<xsl:element name="internalLink" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($SiiNS),'IH_',string($IHParentName),'_ILINK_',string($ILinkName))"/>
			</xsl:attribute>
		</xsl:element>
		</xsl:for-each>				
	</owl:NamedIndividual>
	
	
	<!-- RECURSIVE CALL -->
	<!--xsl:apply-templates select="./InternalElement" mode="IEnesting"/-->
	<xsl:for-each select="./InternalElement">
	<xsl:call-template name="IElementTemplate">
		<xsl:with-param name="IHParentName" select="string($IHParentName)"/>
	</xsl:call-template>
	</xsl:for-each>
	
	<!-- CALL TO create ExternalInterfaces -->
	<xsl:for-each select="./ExternalInterface">
	<xsl:call-template name="EXTInterfaceTemplate">
		<xsl:with-param name="IHParentURI" select="concat(string($SiiNS),'IH_',string($IHParentName))"/>
		<xsl:with-param name="IEParentID" select="string($IEID)"/>
	</xsl:call-template>
	</xsl:for-each>	
	
	<!-- CALL TO create InternalLink -->
	<xsl:for-each select="./InternalLink">
	<xsl:call-template name="InternalLinkTemplate">
		<xsl:with-param name="IHParentURI" select="concat(string($SiiNS),'IH_',string($IHParentName))"/>
	</xsl:call-template>
	</xsl:for-each>	
</xsl:template>

<xsl:template name="EXTInterfaceTemplate">
	<xsl:param name="IHParentURI"/>
	<xsl:param name="IEParentID"/>
	<xsl:variable name="ExtIName" select="./@Name"/>
	<xsl:variable name="ExtIResource" select="concat(string($IHParentURI),'_EXT_INTERFACE_',string($IEParentID),':',string($ExtIName))"/>
	<owl:NamedIndividual rdf:about="{$ExtIResource}"> 
		<rdf:type rdf:resource="{$OntNS}ExternalInterface"/>
	</owl:NamedIndividual>
</xsl:template>

<xsl:template name="InternalLinkTemplate">
	<xsl:param name="IHParentURI"/>
	<xsl:variable name="ILinkName" select="./@Name"/>
	<!-- Link Reference value consists of concatenated (InternalElement's ID, ':', ExternalInterface name)-->
	<xsl:variable name="RefA" select="./@RefPartnerSideA"/>
	<xsl:variable name="RefB" select="./@RefPartnerSideB"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:variable name="ILinkResource" select="concat(string($IHParentURI),'_ILINK_',string($ILinkName))"/>
	<owl:NamedIndividual rdf:about="{$ILinkResource}"> 
		<rdf:type rdf:resource="{$OntNS}InternalLink"/>
		<xsl:element name="refPartnerSideA" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($IHParentURI),'_EXT_INTERFACE_',string($RefA))"/>
			</xsl:attribute>
		</xsl:element>
		<xsl:element name="refPartnerSideB" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">		
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($IHParentURI),'_EXT_INTERFACE_',string($RefB))"/>
			</xsl:attribute>
		</xsl:element>
	</owl:NamedIndividual>
</xsl:template>

</xsl:stylesheet>