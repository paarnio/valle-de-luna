CAEX JAXB
-----
(2018-07-03 some more xs:anyType to xs:string changes for CAEX 3.0)
Building java classes from CAEX_ClassModel_V3.0_byVPA18.xsd schema
1)First, delete old siima folder in gen-src/
2)then run >ant compile
3)Remember to ADD: AppInfoEXTRAContentType.java (containing WriterHeader inner class)
and ko. object creations added also to ObjectFactory
PLUS ADD: TEMP_Helpper.java into the folder 

>ant compile
------------
C:\Users\paarnio>cd C:\javaopenlab\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex3vpa3java_2018-07-03

C:\javaopenlab\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex3vpa3java_2018-07-03>ant compile
Buildfile: C:\javaopenlab\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex3vpa3java_2018-07-03\build.xml

compile:
     [echo] Compiling the schema...
      [xjc] C:\javaopenlab\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex3vpa3java_2018-07
-03\gen-src\siima.models.jaxb.caex3 is not found and thus excluded from the depe
ndency check
      [xjc] Compiling file:/C:/javaopenlab/jaxb-ri-2.2.11/jaxb-ri/vpa_work/caex3
vpa3java_2018-07-03/CAEX_ClassModel_V3.0_byVPA18.xsd
      [xjc] Writing output to C:\javaopenlab\jaxb-ri-2.2.11\jaxb-ri\vpa_work\cae
x3vpa3java_2018-07-03\gen-src

BUILD SUCCESSFUL
Total time: 3 seconds
C:\javaopenlab\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex3vpa3java_2018-07-03>

