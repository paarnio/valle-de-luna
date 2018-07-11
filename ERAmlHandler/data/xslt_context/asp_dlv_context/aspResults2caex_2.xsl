<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- filtering_asp_models.xsl test-version-->
<!-- -->			
<!-- method xml-->
<xsl:output method="xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">
	<CAEXFile FileName="RecourseAllocResults.aml" SchemaVersion="2.15" xsi:noNamespaceSchemaLocation="CAEX_ClassModel_V2.15.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<AdditionalInformation>
		<WriterHeader>
			<WriterName>ERAmlHandler</WriterName>
			<LastWritingDateTime>2018-07-11T17:20:17.8668017+03:00</LastWritingDateTime>
			<WriterProjectTitle>unspecified</WriterProjectTitle>
			<WriterProjectID>unspecified</WriterProjectID>
		</WriterHeader>
	</AdditionalInformation>
	<AdditionalInformation AutomationMLVersion="2.0" />
	<InstanceHierarchy Name="ProductionResourcesIH1">
		<Version>1.0.0</Version>				
    <xsl:apply-templates select="aspmodels"/>
	</InstanceHierarchy>
	</CAEXFile>
</xsl:template>

<xsl:template match="aspmodels">
    <xsl:apply-templates select="./model" mode="mainbranch" />
</xsl:template>


<xsl:template match="model" mode="mainbranch">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:apply-templates select="./literal[(@predicate='hasMacType')]"/>
</xsl:template>

<xsl:template match="literal[(@predicate='hasMacType')]">
	<xsl:variable name="MacNr" select="./arg[@num='0']"/>
	<xsl:variable name="MacType" select="./arg[@num='1']"/>	
	<xsl:variable name="ACapas" select="//literal[(@predicate='alloc')and(./arg[@num='0']=$MacNr)]/arg[@num='1']"/>
	
	
	<xsl:element name="InternalElement">
		<xsl:attribute name="Name"><xsl:value-of select="concat('Machine_',$MacType)"/></xsl:attribute>
		
		<xsl:element name="Attribute">
			<xsl:attribute name="Name"><xsl:value-of select="concat('MachineNumber')"/></xsl:attribute>
			<xsl:element name="Value"><xsl:value-of select="$MacNr"/>
			</xsl:element>
		</xsl:element>
		<xsl:for-each select="$ACapas">
		<xsl:variable name="scapa" select="."/>	
		<xsl:element name="InternalElement">
			<xsl:attribute name="Name"><xsl:value-of select="concat('Capability_',$scapa)"/></xsl:attribute>
		</xsl:element>
		</xsl:for-each>
	</xsl:element>
</xsl:template>


</xsl:stylesheet>