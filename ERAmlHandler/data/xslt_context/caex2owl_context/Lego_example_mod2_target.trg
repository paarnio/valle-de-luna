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
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/InstanceHierarchy1">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InstanceHierarchy"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">InstanceHierarchy1</amlont:name>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_LegoPlatform"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_LegoPlatform">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">LegoPlatform</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">GUID6666</amlont:iD>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_Square1"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_Square2"/>
<amlont:internalElement rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_Rectangle1"/>
<amlont:internalLink rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_ILINK_Square1_bottom_Square2_top"/>
<amlont:internalLink rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_ILINK_InternalLink1"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_Square1">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Square1</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">c8d64b63-7f37-4353-8e66-9ef1bc72336e</amlont:iD>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_c8d64b63-7f37-4353-8e66-9ef1bc72336e:top"/>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_c8d64b63-7f37-4353-8e66-9ef1bc72336e:bottom"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_c8d64b63-7f37-4353-8e66-9ef1bc72336e:top">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_c8d64b63-7f37-4353-8e66-9ef1bc72336e:bottom">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_Square2">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Square2</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">81792d00-84f5-4776-ad8a-54167ffbdd47</amlont:iD>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_81792d00-84f5-4776-ad8a-54167ffbdd47:top"/>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_81792d00-84f5-4776-ad8a-54167ffbdd47:bottom"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_81792d00-84f5-4776-ad8a-54167ffbdd47:top">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_81792d00-84f5-4776-ad8a-54167ffbdd47:bottom">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_IE_Rectangle1">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalElement"/>
<amlont:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Rectangle1</amlont:name>
<amlont:iD rdf:datatype="http://www.w3.org/2001/XMLSchema#string">4f063b8e-70c0-4d72-bf4b-42bbd932eec8</amlont:iD>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:topA"/>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:topB"/>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:topC"/>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:bottomA"/>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:bottomB"/>
<amlont:externalInterface rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:bottomC"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:topA">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:topB">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:topC">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:bottomA">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:bottomB">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:bottomC">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#ExternalInterface"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_ILINK_Square1_bottom_Square2_top">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalLink"/>
<amlont:refPartnerSideA rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_c8d64b63-7f37-4353-8e66-9ef1bc72336e:bottom"/>
<amlont:refPartnerSideB rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_81792d00-84f5-4776-ad8a-54167ffbdd47:top"/>
</owl:NamedIndividual>
<owl:NamedIndividual rdf:about="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_ILINK_InternalLink1">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InternalLink"/>
<amlont:refPartnerSideA rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_4f063b8e-70c0-4d72-bf4b-42bbd932eec8:bottomA"/>
<amlont:refPartnerSideB rdf:resource="http://siima.net/ontologies/2017/caex/IH_InstanceHierarchy1_EXT_INTERFACE_c8d64b63-7f37-4353-8e66-9ef1bc72336e:top"/>
</owl:NamedIndividual>
</rdf:RDF>
