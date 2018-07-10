<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- 2018-07-06 TODO: Caex2AspFacts_PartFeedingSkills_1.xsl -->
<!-- (BASED ON Caex2AspFacts.xsl version 1) -->
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
		<xsl:text>% Resource capability ASP facts (.db) generated from CAEX file:</xsl:text>
		<xsl:value-of select="./@FileName"/>
		<xsl:text>&#xA;% by ERAmlHandler (Caex2AspFacts_PartFeedingSkills_1.xsl)&#xA;</xsl:text>
	</xsl:variable>	
	<xsl:value-of select="$HeaderLine"/>
	<xsl:apply-templates select="./InstanceHierarchy"/>
	<xsl:apply-templates select="./RoleClassLib"/>   
</xsl:template>


<xsl:template match="InstanceHierarchy">
	<!-- TEST NUMBER FUNC  http://www.java2s.com/Code/XML/XSLT-stylesheet/Createindexnumber.htm  -->
	<xsl:text>&#xA;% TEST NUMBER ELEMENT</xsl:text>
	<xsl:for-each select="./InternalElement">
			<xsl:text>&#xA;%</xsl:text>
            <xsl:number format="1. "/>
            <xsl:value-of select="./@Name"/>
      </xsl:for-each>
	<!-- TEST NICE:  <xsl:variable name="GenID" select="generate-id()"/> 
	<xsl:value-of select="$GenID"/>
	-->	
	<!-- ProductionResourcesIH1 -->
	<xsl:text>&#xA;%</xsl:text>
	<xsl:text>&#xA;% ********************************** </xsl:text>
	<xsl:text>&#xA;% InstanceHierarchy: </xsl:text>
	<xsl:value-of select="./@Name"/>
	<xsl:text>&#xA;% ********************************** </xsl:text>
	<xsl:text>&#xA;%</xsl:text>
	<xsl:apply-templates select="./InternalElement"/>
</xsl:template>


<xsl:template match="InternalElement">
	<xsl:variable name="IEguid" select="./@ID"/>
	<xsl:variable name="IEname" select="./@Name"/>
	<xsl:variable name="RBRCPath" select="./RoleRequirements/@RefBaseRoleClassPath"/>
	<!-- lowercase first char -->
	<xsl:variable name="iename" select="concat(translate(substring($IEname,1,1),$uppercase, $smallcase),substring($IEname,2))"/>	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="BRClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($RBRCPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>
	
	<!-- IE expected tp be of BaseRoleClass CapaEquipment (i.e. machine type in dlv program) -->
	<xsl:text>&#xA;% ******************</xsl:text>
	<xsl:text>&#xA;% machine type name: </xsl:text>
	<xsl:value-of select="$iename"/>
	
	 <xsl:choose>
		<xsl:when test="string($BRClass)='CapaEquipment'">
			<!-- This IE truely is CapaEquipment type -->
			<xsl:variable name="EqType" select="$iename"/>
			<xsl:text>&#xA;% machine type (i.e. in IH: BaseRoleClass: </xsl:text>
			<xsl:value-of select="$BRClass"/>
			<xsl:text>)</xsl:text>
			<xsl:text>&#xA;% ******************</xsl:text>			
			<!-- TODO: which function to use position() perhaps not good -->
			<!-- TODO: position() OK if all top level IE are CapaEquipment types -->
			<!-- TODO: Maybe BETTER TO USE number element (see test above) -->
			<xsl:variable name="MacNr" select="position()"/>
			<!-- xsl:value-of select="$MacNr"/ -->			
			<!-- Creating machine() and hasMacType() and machineType() predicates -->
			<xsl:variable name="CRMac" select="concat('machine(',$MacNr,').')"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$CRMac"/>			
			<xsl:variable name="phasMacType" select="concat('hasMacType(',$MacNr,',',$EqType,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$phasMacType"/>		
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

<!-- process capaComponents children of CapaEquipment -->
<xsl:template name="processMachineChildren">
	<xsl:param name="mactype"/>
	<xsl:param name="iechild"/>
	<!-- xsl:value-of select="$mactype"/ -->
	<!-- xsl:text>&#xA;</xsl:text -->
	<!-- xsl:value-of select="$iechild/@Name"/ -->
	
	<xsl:variable name="IEname" select="$iechild/@Name"/>
	<xsl:variable name="RBRCPath" select="./RoleRequirements/@RefBaseRoleClassPath"/>
	<!-- lowercase first char -->
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
	<!--xsl:text>&#xA;% component type name: </xsl:text>
	<xsl:value-of select="$iename"/-->
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
	
</xsl:template>

<!-- process ResourceSkills -->
<xsl:template name="processComponentChildren">
	<xsl:param name="comtype"/>
	<xsl:param name="iechild"/>
	<!-- xsl:value-of select="$comtype"/ -->
	<!-- xsl:text>&#xA;</xsl:text -->
	<!-- xsl:value-of select="$iechild/@Name"/ -->
	<!-- xsl:text>&#xA;</xsl:text -->
	<xsl:variable name="IEname" select="$iechild/@Name"/>
	<xsl:variable name="RBRCPath" select="./RoleRequirements/@RefBaseRoleClassPath"/>
	<!-- lowercase first char -->
	<xsl:variable name="iename" select="concat(translate(substring($IEname,1,1),$uppercase, $smallcase),substring($IEname,2))"/>	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="BRClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($RBRCPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>	
	<!--xsl:text>&#xA;% capability name: </xsl:text>
	<xsl:value-of select="$iename"/-->
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

<!--
 ******************************
 * RoleClassLib TEMPLATE 
 * 
 ****************************** 
 -->

<xsl:template match="RoleClassLib">	
	<!-- TemplateSkillsRCLib1 -->
	<xsl:text>&#xA;%</xsl:text>
	<xsl:text>&#xA;% ********************************** </xsl:text>
	<xsl:text>&#xA;% RoleClassLib: </xsl:text>
	<xsl:value-of select="./@Name"/>
	<xsl:text>&#xA;% ********************************** </xsl:text>
	<xsl:text>&#xA;%</xsl:text>
	<xsl:apply-templates select="./RoleClass[./@RefBaseClassPath='AutomationMLBaseRoleClassLib/AutomationMLBaseRole/Process']"/>
	
	<!-- Processing Skill CONSTRAINTS: Attribute Name="ReqDependenceSkill" -->
	<xsl:apply-templates select=".//Attribute[./@Name='ReqDependenceSkill']"/>
	
	
	
</xsl:template>

<!-- Processing Skill CONSTRAINTS: Attribute Name="ReqDependenceSkill" -->
<xsl:template match="Attribute[./@Name='ReqDependenceSkill']">
	<xsl:text>&#xA;% ************</xsl:text>
	<xsl:text>&#xA;% CONSTRAINT Attribute Value: </xsl:text>
	<xsl:value-of select="./Value/text()"/>
	
	
	<!-- TEST GENERATE-ID() : -->
	<xsl:variable name="GenConstraintID" select="generate-id()"/> 
	<!--xsl:value-of select="$GenConstraintID"/-->
	<!-- lowercase first char -->
	<xsl:variable name="genId" select="concat(translate(substring($GenConstraintID,1,1),$uppercase, $smallcase),substring($GenConstraintID,2))"/>
	<xsl:text>&#xA;% CONSTRAINT ID: </xsl:text>
	<xsl:value-of select="$genId"/>	
	
	
	<xsl:variable name="CapaParent" select="./parent::RoleClass/@Name"/>
	<!-- lowercase first char -->
	<xsl:variable name="source" select="concat(translate(substring($CapaParent,1,1),$uppercase, $smallcase),substring($CapaParent,2))"/>
	<xsl:text>&#xA;% SOURCE Capability: </xsl:text>
	<xsl:value-of select="$source"/>
	
	<xsl:variable name="PathDepTarget" select="./Value/text()"/>
	<!-- Parsing the target local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="DepTarget">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($PathDepTarget)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>
	<!-- lowercase first char -->
	<xsl:variable name="depTarget" select="concat(translate(substring($DepTarget,1,1),$uppercase, $smallcase),substring($DepTarget,2))"/>
	<xsl:text>&#xA;% TARGET Capability: </xsl:text>
	<xsl:value-of select="$depTarget"/>
	
	<!-- GENERATING CONSTRAINTS i.e. capability dependencies between capabilities 
		% CONSTRAINT EXAMPLE 5: "OrientationRecog  requires InformationalSingu "
		constr5(X) :- in(informationalSingu),capability(informationalSingu),capability(X), X=orientationRecog.
		cf(5,X) :- capability(X), X=orientationRecog, in(X), not constr5(orientationRecog).
		cff :- cf(5,X), capability(X), X=orientationRecog.
		% FORCE: As a hard constraint
		:- cf(5,X), capability(X), X=orientationRecog.
	-->

	<xsl:variable name="comLine" select="concat('% CONSTRAINT: ',$genId,': ',$source,' requires ',$depTarget)"/>	
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$comLine"/>
	
	<xsl:variable name="cline1" select="concat('constr',$genId,'(X) :- in(',$depTarget,'),capability(',$depTarget,'),capability(X), X=',$source,'.')"/>	
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$cline1"/>
	
	<xsl:variable name="cline2" select="concat('cf(',$genId,',X) :- in(X),capability(X), X=',$source,', not constr',$genId,'(',$source,').')"/>	
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$cline2"/>
	
	<xsl:variable name="cline3" select="concat('cff :- cf(',$genId,',X),capability(X), X=',$source,'.')"/>	
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$cline3"/>
	
	<xsl:variable name="comLine2" select="concat('% FORCE: As a hard constraint by uncommenting next line:')"/>	
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$comLine2"/>
	
	<xsl:variable name="cline4" select="concat(' :- cf(',$genId,',X),capability(X), X=',$source,'.')"/>	
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$cline4"/>
	
	<xsl:text>&#xA;% ************</xsl:text>
	
</xsl:template>

<xsl:template match="RoleClass[./@RefBaseClassPath='AutomationMLBaseRoleClassLib/AutomationMLBaseRole/Process']">
	<xsl:variable name="RCguid" select="./@ID"/>
	<xsl:variable name="RCname" select="./@Name"/>
	<xsl:variable name="RBClassPath" select="./@RefBaseClassPath"/>
	<!-- lowercase first char -->
	<xsl:variable name="rcname" select="concat(translate(substring($RCname,1,1),$uppercase, $smallcase),substring($RCname,2))"/>	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="RBClass">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($RBClassPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>
	
	<!-- RC expected to be of RefBaseClassPath Process (i.e. machine type in dlv program) -->
	<xsl:text>&#xA;% ************</xsl:text>
	<xsl:text>&#xA;% TOP LEVEL Process name: </xsl:text>
	<xsl:value-of select="$rcname"/>
	<xsl:text>&#xA;% ************</xsl:text>
	<!-- PREDICATES: capability(physicalOrient).-->
	<xsl:variable name="pcapa" select="concat('capability(',$rcname,').')"/>			
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$pcapa"/>
	<!-- LOOP: Capability children are  CapabilityAssociations -->
	<xsl:for-each select="./RoleClass">
		<xsl:call-template name="recursiveCapaAssoc">
            <xsl:with-param name="capaName" select="$rcname"/>
			<xsl:with-param name="assocChild" select="."/>
		</xsl:call-template>
	</xsl:for-each>
	
</xsl:template>


<!-- ***********************************
 * 
 * Recursive CapabilityAssociation Processing  
 * 
 * ***************************************-->
<xsl:template name="recursiveCapaAssoc">
	<xsl:param name="capaName"/>
	<xsl:param name="assocChild"/>
	<!--xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$capaName"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$assocChild/@Name"/-->
	
	<xsl:variable name="ASname" select="$assocChild/@Name"/>
	<xsl:variable name="CASPath" select="$assocChild/@RefBaseClassPath"/>
	<!-- lowercase first char -->
	<xsl:variable name="asname" select="concat(translate(substring($ASname,1,1),$uppercase, $smallcase),substring($ASname,2))"/>	
	<!-- Parsing the class local name from the path -->
	<xsl:variable name="find" select="string('/')"/>
	<xsl:variable name="RBCPath">
		<xsl:call-template name="lastpart">
                <xsl:with-param name="string" select="string($CASPath)"/>
                <xsl:with-param name="search" select="string($find)"/>
         </xsl:call-template>
	</xsl:variable>	
	<!--xsl:text>&#xA;% capability name: </xsl:text>
	<xsl:value-of select="$asname"/-->
	<xsl:choose>
		<xsl:when test="string($RBCPath)='CapabilityAssociation'">
			<!-- This IE truely is CapabilityAssociation type -->
			<xsl:variable name="reS" select="$asname"/>	
			<!-- PREDICATES: association(orientingAssoc). req_assoc(structuring,singulatingAssoc).-->
			<xsl:variable name="passoc" select="concat('association(',$asname,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$passoc"/>
			<xsl:variable name="preqassoc" select="concat('req_assoc(',$capaName,',',$asname,').')"/>			
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="$preqassoc"/>	

			<!-- LOOP: CapaAssociation children are Capabilities (i.e. Process) -->
			<xsl:for-each select="$assocChild/RoleClass">				
				<xsl:variable name="RCguid" select="./@ID"/>
				<xsl:variable name="RCname" select="./@Name"/>
				<xsl:variable name="RBClassPath" select="./@RefBaseClassPath"/>
				<!-- lowercase first char -->
				<xsl:variable name="rcname" select="concat(translate(substring($RCname,1,1),$uppercase, $smallcase),substring($RCname,2))"/>	
				<!-- Parsing the class local name from the path -->
				<xsl:variable name="find" select="string('/')"/>
				<xsl:variable name="RBClass">
				<xsl:call-template name="lastpart">
					<xsl:with-param name="string" select="string($RBClassPath)"/>
					<xsl:with-param name="search" select="string($find)"/>
				</xsl:call-template>
				</xsl:variable>
				
				<xsl:choose>
					<xsl:when test="string($RBClass)='Process'">
					<!-- PREDICATES: capability(physicalOrient).-->
					<xsl:variable name="pcapa" select="concat('capability(',$rcname,').')"/>			
					<xsl:text>&#xA;</xsl:text>
					<xsl:value-of select="$pcapa"/>
					
					<!-- PREDICATES: pro_assoc(physicalSingu,singulatingAssoc).-->
					<xsl:variable name="pproassoc" select="concat('pro_assoc(',$rcname,',',$asname,').')"/>			
					<xsl:text>&#xA;</xsl:text>
					<xsl:value-of select="$pproassoc"/>	
					
					<!-- LOOP: Capability children are  CapabilityAssociations -->
					<xsl:for-each select="./RoleClass">
					<!-- RECURSIVE CALL -->
						<xsl:call-template name="recursiveCapaAssoc">
							<xsl:with-param name="capaName" select="$rcname"/>
							<xsl:with-param name="assocChild" select="."/>
						</xsl:call-template>
					</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>&#xA;%%  </xsl:text>
						<xsl:value-of select="$RBClass"/>
						<xsl:text>&#xA;%%  </xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
							
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>&#xA;%%  </xsl:text>
			<xsl:value-of select="$RBCPath"/>
			<xsl:text>&#xA;%%  </xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>





<!--
 ******************************
 * LASTPART TEMPLATE 
 * Modified from: http://p2p.wrox.com/xslt/31664-functions-replace-tokenize-not-found.html
 ****************************** 
 -->

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