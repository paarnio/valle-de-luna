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
<!-- caexIH_to_owl_5.xsl TOIMII -->
<!-- NOTE: MUST have namespace declaration in element definitions (internalElement) -->
<xsl:output method="xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<!-- VPA: MUISTA 4 ekaa kirjainta määrittää muuttujan nimen -->
<xsl:variable name="OntNS" select="'http://data.ifs.tuwien.ac.at/aml/ontology#'"/>
<xsl:variable name="SiiNS" select="'http://siima.net/ontologies/2017/caex/'"/>

<xsl:template match="/">
<!-- NOTE: xml:base & xmlns declarations must be  in the following rdf:RDF element-->
<!-- The ns-declarations in stylesheet root element are also copied to the result xml -->
	<rdf:RDF xmlns="http://data.ifs.tuwien.ac.at/aml/ontology#" xml:base="http://data.ifs.tuwien.ac.at/aml/ontology" xmlns:amlont="http://data.ifs.tuwien.ac.at/aml/ontology#">
	 	<xsl:comment>VPA: CAEX InstanceHierarchy Translation to caex OWL ontology </xsl:comment>
		<xsl:comment>VPA: CAEX InstanceHierarchy source FileName="RunningExample_SimpleIH.aml" </xsl:comment>
		<owl:Ontology rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:comment> owl:Classes </xsl:comment>
			<xsl:text>&#xA;</xsl:text>
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
			<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXBasicObject">
			</owl:Class>
			<xsl:text>&#xA;</xsl:text>
			<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#internalElement"/>
			<xsl:text>&#xA;</xsl:text>
            <xsl:apply-templates select="/CAEXFile/InstanceHierarchy" mode="IElements"/>
			<!--xsl:apply-templates select="//InternalElement" mode="IEtypeDef"/-->
			<!--xsl:apply-templates select="//InternalElement" mode="IEnesting"/-->
	</rdf:RDF>
</xsl:template>

<xsl:template match="InstanceHierarchy" mode="IElements">
<xsl:variable name="IHName" select="./@Name"/>
	<owl:NamedIndividual rdf:about="{$SiiNS}{$IHName}">   	
        <rdf:type rdf:resource="{$OntNS}InstanceHierarchy"/>
		<xsl:for-each select="./InternalElement">
		<xsl:variable name="IEName" select="./@Name"/>
		<!-- NOTE: MUST have namespace declaration in element definition (internalElement) -->
		<xsl:element name="internalElement" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#"><!-- amlont:internalElement -->			
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($SiiNS),string($IEName))"/>
			</xsl:attribute>
		</xsl:element>
	</xsl:for-each>
	</owl:NamedIndividual>
	<xsl:apply-templates select="./InternalElement" mode="IEnesting"/>
</xsl:template>

<xsl:template match="InternalElement" mode="IEnesting">
<xsl:variable name="IEName" select="./@Name"/>
	<owl:NamedIndividual rdf:about="{$SiiNS}{$IEName}"> 
		<rdf:type rdf:resource="{$OntNS}InternalElement"/>
		<xsl:for-each select="./InternalElement">
		<xsl:variable name="IEName" select="./@Name"/>
		<!-- NOTE: MUST have namespace declaration in element definition (internalElement) -->
		<xsl:element name="internalElement" namespace="http://data.ifs.tuwien.ac.at/aml/ontology#">
		
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="concat(string($SiiNS),string($IEName))"/>
			</xsl:attribute>
		</xsl:element>
	</xsl:for-each>
	</owl:NamedIndividual>
	<xsl:apply-templates select="./InternalElement" mode="IEnesting"/>
</xsl:template>

<!--
<xsl:template match="InternalElement" mode="IEtypeDef">
<xsl:variable name="IEName" select="./@Name"/>
<xsl:variable name="IEGUID" select="./@ID"/>
	<owl:NamedIndividual rdf:about="{$SiiNS}{$IEName}">   	
        <rdf:type rdf:resource="{$OntNS}InternalElement"/>
    </owl:NamedIndividual>
</xsl:template>
-->


</xsl:stylesheet>