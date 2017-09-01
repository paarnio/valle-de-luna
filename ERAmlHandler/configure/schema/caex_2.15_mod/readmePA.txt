CAEX JAXB
----------
(2017-05-10) Modifying caex schema: Attribute/Value type="xs:string"
(prev. type="xs:anyType" which was binded to java Object (no use))

CAEX_V2.15_modified.xsd 

>ant compile

PS C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java_schema_mod> ant compile
Buildfile: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java_schema_mod\build.xml

compile:
     [echo] Compiling the schema...
    [mkdir] Created dir: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java_schema_mod\gen-src

      [xjc] C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java_schema_mod\gen-src\siima.models
.jaxb.caex is not found and thus excluded from the dependency check
      [xjc] Compiling file:/C:/D-PA/programs/java/2016/JAXB_ri/jaxb-ri-2.2.11/jaxb-ri/vpa_work/caex2java_schema_mod/CAEX
_V2.15_modified.xsd
      [xjc] Writing output to C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java_schema_mod\ge
n-src

BUILD SUCCESSFUL
Total time: 4 seconds





CUSTOMIZING EI TOIMI
-----
(2017-05-08) Using direct command line options:
https://docs.oracle.com/javase/tutorial/jaxb/intro/examples.html

xjc [-options ...] <schema file/URL/dir/jar>... [-b >bindinfo<] ...


bin>xjc -d ../vpa_work/caex2java_custom/gen-src ../vpa_work/caex2java_custom/CAEX_ClassModel_V2.15.xsd -b ../vpa_work/caex2java_custom/siima_custom_binding.xjb



See: https://docs.oracle.com/javase/tutorial/jaxb/intro/custom.html 



(2017-04-18)
Building java classes from caex_2.15.xsd schema

>ant compile

---------
PS C:\Users\paarnio> cd C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java
PS C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java> ant compile
Buildfile: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java\build.xml

compile:
     [echo] Compiling the schema...
    [mkdir] Created dir: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java\gen-src
      [xjc] C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java\gen-src\siima.models.jaxb.caex
is not found and thus excluded from the dependency check
      [xjc] Compiling file:/C:/D-PA/programs/java/2016/JAXB_ri/jaxb-ri-2.2.11/jaxb-ri/vpa_work/caex2java/CAEX_ClassMode
_V2.15.xsd
      [xjc] Writing output to C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java\gen-src

BUILD SUCCESSFUL
Total time: 5 seconds
PS C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\caex2java>


