<?xml version="1.0" encoding="UTF-8"?><rdf:RDF xml:base="http://data.ifs.tuwien.ac.at/aml/ontology" xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:siima="http://siima.net/ontologies/2017/caex/" xmlns:foaf="http://xmlns.com/foaf/spec/" xmlns:html="http://www.w3.org/1999/xhtml" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xsd="http://www.w3.org/2001/XMLSchema#" xmlns:amlont="http://data.ifs.tuwien.ac.at/aml/ontology#" xmlns="http://data.ifs.tuwien.ac.at/aml/ontology#">
<!--VPA: CAEX InstanceHierarchy Translation to caex OWL ontology -->
<!--VPA: CAEX InstanceHierarchy source FileName="RunningExample_SimpleIH.aml" -->
<owl:Ontology rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology"/>
<!--- - - -owl:Classes- - - - - -->
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
<!--- - - -Instances/NamedIndividuals- - - - - -->
<owl:NamedIndividual rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#IExampleSystematIAF">
<rdf:type rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InstanceHierarchy"/>
<amlont:internalElement resource="http://siima.net/ontologies/2017/caex/OvGU-Building10_Room445_FMS"/>
</owl:NamedIndividual>
<!--- - - -owl:Properties- - - - - -->
<owl:ObjectProperty rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology#internalElement">
<rdfs:domain rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#InstanceHierarchy"/>
<rdfs:domain rdf:resource="http://data.ifs.tuwien.ac.at/aml/ontology#SystemUnitClass"/>
</owl:ObjectProperty>
</rdf:RDF>
