<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- Caex2AspFacts.xsl version 1-->
<!-- Transform Lego_example_mod2.aml CAEXFile to LogoTower ASP fact file (.db) -->	
<!-- Modified from CaexLego2Monkey3.xsl -->		
<xsl:output method="text" indent="no"/>
<xsl:strip-space elements="*"/>

<!-- In XSLT 1.0 the upper-case() and lower-case() functions are not available. 
If you're using a 1.0 stylesheet the common method of case conversion is translate()
EXAMPLE: xsl:value-of select="translate(doc, $smallcase, $uppercase)
-->
<xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyzåäö'" />
<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ'" />


<xsl:template match="/">  
            <xsl:apply-templates select="CAEXFile"/>
</xsl:template>

<xsl:template match="CAEXFile">
	<xsl:variable name="HeaderLine">
		<xsl:text>% LegoTower ASP facts (.db) generated from CAEX by ERAmlHandler (Caex2AspFacts.xsl v1.0)&#xA;</xsl:text>
	</xsl:variable>	
	<xsl:value-of select="$HeaderLine"/>
    <xsl:apply-templates select="//InternalElement"/>
	<xsl:apply-templates select="//InternalLink"/>
</xsl:template>

<xsl:template match="InternalElement">
	<xsl:variable name="IEguid" select="./@ID"/>
	<xsl:variable name="IEname" select="./@Name"/>
	<xsl:variable name="SUCPath" select="./@RefBaseSystemUnitPath"/>
	<!-- lowercase -->
	<xsl:variable name="iename" select="concat(translate(substring($IEname,1,1),$uppercase, $smallcase),substring($IEname,2))"/>
	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="SUClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($SUCPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>
	<xsl:variable name="IEcolor" select="./Attribute[@Name='Color']/Value/text()"/>
	
	<!-- CREATE rectange(leg). & square(leg2). facts -->
	<!--(SystemUnitClass:localname InternalElement:name Attribute:color:value 
	 + for rectange legos: Attribute:orientation:value) -->
	 
	 <xsl:choose>
		<xsl:when test="string($SUClass)='RectangleLego'">
			<xsl:variable name="IEorientation" select="./Attribute[@Name='Orientation']/Value/text()"/>
			<!--xsl:variable name="CRERect" select="concat('create ', $SUClass,' ',$IEname,' ',$IEcolor,' ',$IEorientation)"/-->
			<xsl:variable name="CRERect" select="concat('rectangle(',$iename,').')"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$CRERect"/>
		</xsl:when>
		<xsl:when test="string($SUClass)='SquareLego'">
			<xsl:variable name="CRESquare" select="concat('square(',$iename,').')"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$CRESquare"/>
		</xsl:when>
		<!--xsl:otherwise>
             <xsl:value-of select="concat('Unknown type: ',$SUClass)" />
         </xsl:otherwise-->
	 </xsl:choose>
	
<!--	<xsl:for-each select="./ExternalInterface">
		<xsl:text>&#xA;</xsl:text>
			<xsl:variable name="IEinterface" select="./@Name"/>	
			<xsl:variable name="CREATE" select="concat('create ', $SUClass,' ',$IEname,' ',$IEcolor)"/>
		<xsl:value-of select="$CREATE"/>
	</xsl:for-each>
	<xsl:text>&#xA;</xsl:text>
	-->
</xsl:template>


<xsl:template match="InternalLink">
<!-- link(leg1,topA,leg2,botA-->
	<!-- link predicate atoms should start with a lowercase letter  -->
	<!--xsl:value-of select="translate('ISOJA', $uppercase, $smallcase)"/-->
	<!-- Side A  -->
	<xsl:variable name="Aside" select="./@RefPartnerSideA"/>
	<xsl:variable name="Aguid" select="substring-before($Aside,':')"/>
	<xsl:variable name="Ainterface" select="substring-after($Aside,':')"/>
	<xsl:variable name="ainterface" select="concat(translate(substring($Ainterface,1,1),$uppercase, $smallcase),substring($Ainterface,2))"/>
	<xsl:variable name="Aname" select="/CAEXFile//InternalElement[@ID=string($Aguid)]/@Name"/>	
	<xsl:variable name="aname" select="concat(translate(substring($Aname,1,1),$uppercase, $smallcase),substring($Aname,2))"/>
	<!-- Side B  -->
	<xsl:variable name="Bside" select="./@RefPartnerSideB"/>
	<xsl:variable name="Bguid" select="substring-before($Bside,':')"/>
	<xsl:variable name="Binterface" select="substring-after($Bside,':')"/>
	<xsl:variable name="binterface" select="concat(translate(substring($Binterface,1,1),$uppercase, $smallcase),substring($Binterface,2))"/>
	<xsl:variable name="Bname" select="/CAEXFile//InternalElement[@ID=string($Bguid)]/@Name"/>	
	<xsl:variable name="bname" select="concat(translate(substring($Bname,1,1),$uppercase, $smallcase),substring($Bname,2))"/>
	<!-- link predicate (atoms should start with a lowercase letter  -->
	<xsl:variable name="LINK" select="concat('link(',$aname,',',$ainterface ,',',$bname,',',$binterface,').')"/>
	
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$LINK"/>
</xsl:template>

<!-- Modified from: http://p2p.wrox.com/xslt/31664-functions-replace-tokenize-not-found.html -->

<xsl:template name="lastpart">
        <xsl:param name="string"/>
        <xsl:param name="search"/>
        <xsl:choose>
              <xsl:when test="contains( $string, $search)">
                <xsl:variable name="before" select="substring-before( $string, $search)"/>
                <xsl:variable name="after" select="substring-after($string,$search)"/>
                       <xsl:call-template name="lastpart">
						<xsl:with-param name="string" select="string($after)"/>
						<xsl:with-param name="search" select="string($search)"/>
					</xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                     <xsl:value-of select="$string" />
            </xsl:otherwise>
        </xsl:choose>
   </xsl:template>


</xsl:stylesheet>