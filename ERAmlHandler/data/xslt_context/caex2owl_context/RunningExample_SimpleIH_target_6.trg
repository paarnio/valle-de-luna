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
<owl:Class rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#CAEXBasicObject"/>
<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#internalElement"/>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IExampleSystematIAF">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InstanceHierarchy"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/OvGU-Building10_Room445_FMS"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/OvGU-Building10_Room445_FMS">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/Machine"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/Conveyer"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/Turntable"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/Enclosure"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/Machine">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MachineBaseFrame"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MachineToolHead"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MachineTool1"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MachineTool2"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MachineTool3"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MachineHeadDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/UpperLimitSwitch"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/LowerLimitSwitch"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/ToolDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/ToolIDSensor"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MachineBaseFrame">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MachineToolHead">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MachineTool1">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MachineTool2">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MachineTool3">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MachineHeadDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/UpperLimitSwitch">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/LowerLimitSwitch">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/ToolDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/ToolIDSensor">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/Conveyer">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/ConveyerBaseFrame"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/ConveyerBelt"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/BeltDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MaterialIdentSensor"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/ConveyerBaseFrame">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/ConveyerBelt">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/BeltDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MaterialIdentSensor">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/Turntable">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/TurntableBaseFrame"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/TurntableHead"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/TurntableBelt"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/BeltDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/MaterialIdentSensor"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/HeadDrive"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/HeadPositionSensor0"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/HeadPositionSensor90"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/TurntableBaseFrame">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/TurntableHead">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/TurntableBelt">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/BeltDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/MaterialIdentSensor">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/HeadDrive">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/HeadPositionSensor0">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/HeadPositionSensor90">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/Enclosure">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/RaspberryPi"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/FieldIO"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/Switch"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/RaspberryPi">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/FieldIO">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/Switch">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
</owl:NamedIndividual>
</rdf:RDF>
