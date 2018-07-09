<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- 2018-07-06 TODO: Caex2AspFacts_PartFeedingSkills_1.xsl -->
<!-- BASED ON Caex2AspFacts.xsl version 1 -->
<!-- TODO: Machine number: $MacNr which element/function to use? -->
<!-- position() perhaps not good if there are other IE elements in the top layer -->
<!-- TODO: BETTER TO USE number element (see TEST NUMBER ELEMENT  below) -->
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
		<xsl:text>% Resource capability ASP facts (.db) generated from CAEX by ERAmlHandler (Caex2AspFacts_PartFeedingSkills_1.xsl)&#xA;</xsl:text>
	</xsl:variable>	
	<xsl:value-of select="$HeaderLine"/>
    <xsl:apply-templates select="./InstanceHierarchy"/>
</xsl:template>

<xsl:template match="InstanceHierarchy">
	<!-- ProductionResourcesIH1 -->
	<xsl:text>&#xA;% InstanceHierarchy: </xsl:text>
	<xsl:value-of select="./@Name"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:apply-templates select="./InternalElement"/>
	<!-- TEST NUMBER FUNC  http://www.java2s.com/Code/XML/XSLT-stylesheet/Createindexnumber.htm  -->
	<xsl:text>&#xA; TEST NUMBER ELEMENT &#xA;</xsl:text>
	<xsl:for-each select="./InternalElement">
			<xsl:text>&#xA;</xsl:text>
            <xsl:number format="1. "/>
            <xsl:value-of select="./@Name"/>
      </xsl:for-each>
	<!-- TEST NICE:  <xsl:variable name="GenID" select="generate-id()"/> 
	<xsl:value-of select="$GenID"/>
	-->
</xsl:template>




<xsl:template match="InternalElement">
	<xsl:variable name="IEguid" select="./@ID"/>
	<xsl:variable name="IEname" select="./@Name"/>
	<xsl:variable name="RBRCPath" select="./RoleRequirements/@RefBaseRoleClassPath"/>
	<!-- lowercase -->
	<xsl:variable name="iename" select="concat(translate(substring($IEname,1,1),$uppercase, $smallcase),substring($IEname,2))"/>
	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="BRClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($RBRCPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>
	
	<!-- IE may be of BaseRoleClass CapaEquipment (i.e. machine type in dlv program) -->
	<xsl:text>&#xA;% machine type ?: </xsl:text>
	<xsl:value-of select="$iename"/>
	<xsl:text>&#xA;</xsl:text>
	
	 <xsl:choose>
		<xsl:when test="string($BRClass)='CapaEquipment'">
			<!-- This IE truely is CapaEquipment type -->
			<xsl:variable name="EqType" select="$iename"/>
			<xsl:text>&#xA;% machine type in dlv (i.e. BaseRoleClass: </xsl:text>
			<xsl:value-of select="$BRClass"/>
			<xsl:text>&#xA;</xsl:text>		
			<!-- TODO: which function to use position() perhaps not good -->
			<!-- TODO: BETTER TO USE number element (see test above) -->
			<xsl:variable name="MacNr" select="position()"/>
			<!-- xsl:value-of select="$MacNr"/ -->
			
			<!-- Creating machine() and hasMacType() and machineType() predicates -->
			<xsl:variable name="CRMac" select="concat('machine(',$MacNr,').')"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$CRMac"/>			
			<xsl:variable name="phasMacType" select="concat('hasMacType(',$MacNr,',',$EqType,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$phasMacType"/>
			<xsl:text>&#xA;</xsl:text>			
			<xsl:variable name="pmacType" select="concat('machineType(',$EqType,').')"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$pmacType"/>
			
			<xsl:for-each select="./InternalElement">
				<xsl:call-template name="processMachineChildren">
                <xsl:with-param name="mactype" select="$EqType"/>
				<xsl:with-param name="iechild" select="."/>
				</xsl:call-template>
			</xsl:for-each>
			
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>&#xA;%????Unknown BaseRoleClass???</xsl:text>
			<xsl:value-of select="$BRClass"/>
		</xsl:otherwise>
	 </xsl:choose>
	
</xsl:template>

<!-- NEW process capaComponents -->
<xsl:template name="processMachineChildren">
	<xsl:param name="mactype"/>
	<xsl:param name="iechild"/>
	<xsl:text>&#xA;% processMachineChildren &#xA;</xsl:text>
	<!-- xsl:value-of select="$mactype"/ -->
	<!-- xsl:text>&#xA;</xsl:text -->
	<!-- xsl:value-of select="$iechild/@Name"/ -->
	
	<xsl:variable name="IEname" select="$iechild/@Name"/>
	<xsl:variable name="RBRCPath" select="./RoleRequirements/@RefBaseRoleClassPath"/>
	<!-- lowercase -->
	<xsl:variable name="iename" select="concat(translate(substring($IEname,1,1),$uppercase, $smallcase),substring($IEname,2))"/>
	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="BRClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($RBRCPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>	
	<!-- IE may be of BaseRoleClass CapaComponent (i.e. component type in dlv program) -->
	<xsl:text>&#xA;% component type ?: </xsl:text>
	<xsl:value-of select="$iename"/>
	<!-- xsl:text>&#xA;</xsl:text -->
	
	<xsl:choose>
		<xsl:when test="string($BRClass)='CapaComponent'">
			<!-- This IE truely is CapaComponent type-->
			<xsl:variable name="comType" select="$iename"/>	
			<!-- PREDICATES: compoType(baseunit_ff). hasCompoType(vibraBowlFeeder,bowl_vbf). -->
			<xsl:variable name="pcompoType" select="concat('compoType(',$comType,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$pcompoType"/>
			<xsl:variable name="phasCompoType" select="concat('hasCompoType(',$mactype,',',$comType,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$phasCompoType"/>
			
			<xsl:for-each select="$iechild/InternalElement">
				<xsl:call-template name="processComponentChildren">
                <xsl:with-param name="comtype" select="$comType"/>
				<xsl:with-param name="iechild" select="."/>
				</xsl:call-template>
			</xsl:for-each>			
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>&#xA;%????Unknown BaseRoleClass???</xsl:text>
			<xsl:value-of select="$BRClass"/>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text>&#xA;%  &#xA;</xsl:text>
	
</xsl:template>

<!-- NEW process ResourceSkills -->
<xsl:template name="processComponentChildren">
	<xsl:param name="comtype"/>
	<xsl:param name="iechild"/>
	<xsl:text>&#xA;%     processComponentChildren   &#xA;</xsl:text>
	<!-- xsl:value-of select="$comtype"/ -->
	<!-- xsl:text>&#xA;</xsl:text -->
	<!-- xsl:value-of select="$iechild/@Name"/ -->
	<!-- xsl:text>&#xA;</xsl:text -->
	<xsl:variable name="IEname" select="$iechild/@Name"/>
	<xsl:variable name="RBRCPath" select="./RoleRequirements/@RefBaseRoleClassPath"/>
	<!-- lowercase -->
	<xsl:variable name="iename" select="concat(translate(substring($IEname,1,1),$uppercase, $smallcase),substring($IEname,2))"/>
	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="BRClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($RBRCPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>	
	<xsl:text>&#xA;% capability ?: </xsl:text>
	<xsl:value-of select="$iename"/>
	<!-- xsl:text>&#xA;</xsl:text -->	
	
		<xsl:choose>
		<xsl:when test="string($BRClass)='ResourceSkill'">
			<!-- This IE truely is ResourceSkill type -->
			<xsl:variable name="reSkill" select="$iename"/>	
			<!-- PREDICATES: capability(vibrating). hasCapa(bowl_vbf,physicalOrient).-->
			<xsl:variable name="pcapa" select="concat('capability(',$reSkill,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$pcapa"/>
			<xsl:variable name="phasCapa" select="concat('hasCapa(',$comtype,',',$reSkill,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$phasCapa"/>
			
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>&#xA;%????Unknown BaseRoleClass???</xsl:text>
			<xsl:value-of select="$BRClass"/>
		</xsl:otherwise>
	</xsl:choose>

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

   <!-- OLD LEGO TEMPLATES -->
   
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
   
   
  <xsl:template name="specialfacts">
		<!-- Specifying #maxint value  -->
		<!-- REQ: #maxint needs to be AT LEAST 2xlegocount+2  -->
		<xsl:variable name="RLegos" select="//InternalElement[contains(string(./@RefBaseSystemUnitPath),'RectangleLego')]"/>
		<xsl:variable name="SLegos" select="//InternalElement[contains(string(./@RefBaseSystemUnitPath),'SquareLego')]"/>
		<xsl:variable name="RLNames" select="$RLegos/@Name"/>
		<xsl:variable name="SLNames" select="$SLegos/@Name"/>
		<xsl:variable name="RCount" select="count($RLegos)"/>
		<xsl:variable name="SCount" select="count($SLegos)"/>
		<xsl:variable name="MaxInt" select="2*($RCount + $SCount + 1)"/>
		<xsl:text>&#xA;</xsl:text>
		<xsl:value-of select="concat('#maxint=', string($MaxInt),'.')"/>
		<xsl:text>&#xA;</xsl:text>
		<!-- Specifying one predicate somelego(legoname).  -->
		<xsl:choose>
            <xsl:when test="$RCount">
				<xsl:value-of select="concat('somelego(',translate(string($RLNames), $uppercase, $smallcase),').')" />
			</xsl:when>
			<xsl:when test="$SCount">
				<xsl:value-of select="concat('somelego(',translate(string($SLNames), $uppercase, $smallcase),').')" />
			</xsl:when>
            <xsl:otherwise>
                 <xsl:text>&#xA;% NO LEGOS FOUND?? (InternalElements of classes RectangleLego or SquareLego)&#xA;</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
		<!--xsl:variable name="IEcolor" select="./Attribute[@Name='Color']/Value/text()"/-->
		
   </xsl:template>

</xsl:stylesheet>