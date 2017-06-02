<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- CAEXLego2monkey3.xsl version 3-->
<!-- Transform Lego_example_mod2.aml CAEXFile to jmonkey assembly commands defined by SS-->			
<xsl:output method="text" indent="no"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">  
            <xsl:apply-templates select="CAEXFile"/>
</xsl:template>

<xsl:template match="CAEXFile">
    <xsl:apply-templates select="//InternalElement"/>
	<xsl:apply-templates select="//InternalLink"/>
</xsl:template>

<xsl:template match="InternalElement">
	<xsl:variable name="IEguid" select="./@ID"/>
	<xsl:variable name="IEname" select="./@Name"/>
	<xsl:variable name="SUCPath" select="./@RefBaseSystemUnitPath"/>
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="SUClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($SUCPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>
	<xsl:variable name="IEcolor" select="./Attribute[@Name='Color']/Value/text()"/>
	
	<!-- CREATE SystemUnitClass:localname InternalElement:name Attribute:color:value 
	 + for rectange legos: Attribute:orientation:value -->
	 
	 <xsl:choose>
		<xsl:when test="string($SUClass)='RectangleLego'">
			<xsl:variable name="IEorientation" select="./Attribute[@Name='Orientation']/Value/text()"/>
			<xsl:variable name="CRERect" select="concat('create ', $SUClass,' ',$IEname,' ',$IEcolor,' ',$IEorientation)"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$CRERect"/>
		</xsl:when>
		<xsl:when test="string($SUClass)='SquareLego'">
			<xsl:variable name="CRESquare" select="concat('create ', $SUClass,' ',$IEname,' ',$IEcolor)"/>
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
	<!-- Side A  -->
	<xsl:variable name="Aside" select="./@RefPartnerSideA"/>
	<xsl:variable name="Aguid" select="substring-before($Aside,':')"/>
	<xsl:variable name="Ainterface" select="substring-after($Aside,':')"/>
	<xsl:variable name="Aname" select="/CAEXFile//InternalElement[@ID=string($Aguid)]/@Name"/>
	<!-- Side B  -->
	<xsl:variable name="Bside" select="./@RefPartnerSideB"/>
	<xsl:variable name="Bguid" select="substring-before($Bside,':')"/>
	<xsl:variable name="Binterface" select="substring-after($Bside,':')"/>
	<xsl:variable name="Bname" select="/CAEXFile//InternalElement[@ID=string($Bguid)]/@Name"/>	
	<!-- CONCAT COMMAND  -->
	<xsl:variable name="CONNECT" select="concat('connect ',$Aname,' ',$Ainterface ,' ',$Bname,' ',$Binterface)"/>
	<!--xsl:variable name="CONNECT" select="concat('connect ', $Aguid,':',$Aname,' ',$Ainterface ,' ', $Bguid,':',$Bname,' ',$Binterface)"/-->
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$CONNECT"/>
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