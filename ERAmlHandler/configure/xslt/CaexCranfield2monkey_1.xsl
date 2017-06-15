<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- CaexCranfield2monkey_1.xsl (See: https://www.youtube.com/watch?v=T0Fd0PTnIHA) -->
<!-- (based on CAEXLego2monkey3.xsl version 3) -->
<!-- Link reference consists of (IElement's ID ':' Interface's name)) -->
<!-- Transform (CaexCranfield benchmark) .aml CAEXFile to jmonkey assembly commands defined by SS-->			
<xsl:output method="text" indent="no"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">  
            <xsl:apply-templates select="CAEXFile"/>
</xsl:template>

<xsl:template match="CAEXFile">
	<!-- Container InternalElement Name="CranfieldBenchmark" NOT included -->
    <xsl:apply-templates select="//InternalElement[./Attribute/@Name ='orientation']" mode="create"/>
	<xsl:apply-templates select="//InternalLink"/>
	<xsl:apply-templates select="//InternalElement[./Attribute/@Name ='orientation']" mode="param"/>
</xsl:template>

<xsl:template match="InternalElement" mode="create" >
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
	<!-- Parsing the class library name from the path -->
	<xsl:variable name="PreSUCLib" select="substring-before($SUCPath,concat('/', $SUClass))"/>
	<xsl:variable name="SUCLib">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($PreSUCLib)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>	
	
	<xsl:variable name="IEcolor" select="./Attribute[@Name='color']/Value/text()"/> <!-- MOD: color -->
	<xsl:variable name="IEorientation" select="./Attribute[@Name='orientation']/Value/text()"/><!-- MOD: orientation -->
	<!-- CREATE SystemUnitClass:localname InternalElement:name Attribute:color:value Attribute:orientation:value -->
	<xsl:variable name="CREATE" select="concat('create ', $SUClass,' ',$IEname,' ',$IEcolor,' ',$IEorientation)"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$CREATE"/>
</xsl:template>


<xsl:template match="InternalElement" mode="param" >
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
	<!-- Parsing the class library name from the path -->
	<xsl:variable name="PreSUCLib" select="substring-before($SUCPath,concat('/', $SUClass))"/>
	<xsl:variable name="SUCLib">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($PreSUCLib)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>
	<!-- Checking distinctiveness -->
	<xsl:choose>
	<xsl:when test="not(./@RefBaseSystemUnitPath = ./preceding::InternalElement/@RefBaseSystemUnitPath)">
	
	<!-- Params should be accessed from system unit class library. Attributes DefaultValue -->
	<xsl:for-each select="/CAEXFile/SystemUnitClassLib[@Name = string($SUCLib)]//SystemUnitClass[@Name = string($SUClass)]/Attribute[(@Name!='color') and(@Name!='orientation')]">
	<xsl:variable name="ATTName" select="./@Name"/> 
	<xsl:variable name="DValue" select="./DefaultValue/text()"/>
	<!-- CREATE SystemUnitClass:localname InternalElement:name Attribute:color:value Attribute:orientation:value -->
	<xsl:variable name="PRM" select="concat('param ', $SUClass,' ',$ATTName,' ',$DValue)"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$PRM"/>
	</xsl:for-each>	
	</xsl:when>
	<xsl:otherwise>
		<!-- <xsl:text>&#xA;</xsl:text>
		<xsl:value-of select="$SUCPath"/>
		<xsl:value-of select="'=== SAME AS PRECIDING =='"/>
		-->
	</xsl:otherwise>
	</xsl:choose>
	
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