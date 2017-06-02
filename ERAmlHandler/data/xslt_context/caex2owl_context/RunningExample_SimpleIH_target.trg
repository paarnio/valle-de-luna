<?xml version="1.0" encoding="UTF-8"?><rdf:RDF xml:base="http://siima.net/ontologies/2017/caex/" xmlns="http://siima.net/ontologies/2017/caex/" xmlns:siima="http://siima.net/ontologies/2017/caex/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:xsd="http://www.w3.org/2001/XMLSchema#" xmlns:html="http://www.w3.org/1999/xhtml" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:amlont="http://data.ifs.tuwien.ac.at/aml/ontology#" xmlns:foaf="http://xmlns.com/foaf/spec/">
<!--VPA: CAEX InstanceHierarchy Translation to caex OWL ontology -->
<!--VPA: CAEX InstanceHierarchy source FileName="RunningExample_SimpleIH.aml" -->
<owl:Ontology rdf:about="http://siima.net/ontologies/2017/caex/"/>
<!-- owl:Classes -->
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
<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXBasicObject"/>
<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#internalElement"/>
<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#externalInterface"/>
<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#internalLink"/>
<owl:DatatypeProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#name">
<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#string"/>
</owl:DatatypeProperty>
<owl:DatatypeProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#iD">
<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#string"/>
</owl:DatatypeProperty>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IExampleSystematIAF">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InstanceHierarchy"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">IExampleSystematIAF</amlont:name>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_OvGU-Building10_Room445_FMS"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_OvGU-Building10_Room445_FMS">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">OvGU-Building10_Room445_FMS</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">7b59a576-92ee-4ae1-ba96-82b80202606e</amlont:iD>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Machine"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Conveyer"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Turntable"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Enclosure"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Machine">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Machine</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">27ba8da8-e6c2-4ff5-90a1-08a31d83f308</amlont:iD>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineBaseFrame"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineToolHead"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineTool1"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineTool2"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineTool3"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineHeadDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_UpperLimitSwitch"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_LowerLimitSwitch"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ToolDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ToolIDSensor"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineBaseFrame">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MachineBaseFrame</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">44dca432-27d3-4342-b05f-d26bf0a41fef</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineToolHead">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MachineToolHead</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">807dd246-8abd-4570-8704-e0cfb3cd9558</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineTool1">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MachineTool1</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">1fb9cdb3-ec98-4826-b725-65c4ceb338cf</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineTool2">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MachineTool2</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">0c9389f5-2328-4504-992b-348063cede89</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineTool3">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MachineTool3</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">3bec5736-e7e6-41b8-a54d-a2133b4144a2</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MachineHeadDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MachineHeadDrive</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">4e814208-1b6b-4cfc-a0cd-3bf303bb2add</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_UpperLimitSwitch">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">UpperLimitSwitch</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">e8f3efe6-063e-4c9a-a47a-e7cafd1aedf3</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_LowerLimitSwitch">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">LowerLimitSwitch</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">52297502-6b37-412e-b83d-6dd3f599406b</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ToolDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">ToolDrive</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">061398b5-2d17-45fe-a347-4476aaf7dee7</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ToolIDSensor">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">ToolIDSensor</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">be8f6982-a2fe-4d13-a864-a3c7d5910653</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Conveyer">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Conveyer</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">4214d17c-2773-466a-b9c0-61a358be2dec</amlont:iD>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ConveyerBaseFrame"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ConveyerBelt"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_BeltDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MaterialIdentSensor"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ConveyerBaseFrame">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">ConveyerBaseFrame</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">62e42cee-a870-44f9-a1f3-3aa520f2d205</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_ConveyerBelt">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">ConveyerBelt</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">05c501ca-25df-4dc8-9e48-21f1c05cb05e</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_BeltDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">BeltDrive</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">0355a984-1e8c-46d5-9818-e0409c45057f</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MaterialIdentSensor">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MaterialIdentSensor</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">e96d3874-83d3-44d5-92ac-70080d8a66e2</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Turntable">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Turntable</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">c9017299-6247-49e3-9c3a-bc41b70f9e9f</amlont:iD>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_TurntableBaseFrame"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_TurntableHead"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_TurntableBelt"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_BeltDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MaterialIdentSensor"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_HeadDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_HeadPositionSensor0"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_HeadPositionSensor90"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_TurntableBaseFrame">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">TurntableBaseFrame</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">723129c3-1be2-44d3-9418-9e4e2e676940</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_TurntableHead">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">TurntableHead</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">a30e18f7-97eb-47f3-bc02-b3cb0e2cd286</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_TurntableBelt">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">TurntableBelt</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">b21196fb-f3fa-4c3d-8933-66d15d83c993</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_BeltDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">BeltDrive</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">d99a2ba9-0538-4546-b5bf-e5a4b46ad76f</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_MaterialIdentSensor">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">MaterialIdentSensor</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">1fcb6fa1-1509-4aac-8b1a-f770b27d192c</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_HeadDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">HeadDrive</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">e7828d36-43d9-43c3-9805-698015f2c764</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_HeadPositionSensor0">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">HeadPositionSensor0</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">52dcf8cf-2ed0-47df-9f32-f545928dc152</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_HeadPositionSensor90">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">HeadPositionSensor90</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">eeb25c06-463c-405a-9bb1-2381fc34ae5c</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Enclosure">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Enclosure</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">f076dc9a-6118-4ec2-beff-f78371fc8580</amlont:iD>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_RaspberryPi"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_FieldIO"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Switch"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_RaspberryPi">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">RaspberryPi</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">8be5be58-6c00-405e-8d05-5cb1f6063dd6</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_FieldIO">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">FieldIO</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">7522f89a-11bb-4af6-9373-c642b249ced3</amlont:iD>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_IExampleSystematIAF_IE_Switch">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Switch</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">2a298cc4-b45a-453a-a53a-ed8843d0af7c</amlont:iD>
</owl:NamedIndividual>
</rdf:RDF>
