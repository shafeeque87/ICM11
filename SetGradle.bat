@ECHO OFF
doskey studio=E:\developer\ICM11\Studio\IntershopStudio.exe
doskey 1 =  gradlew startsolr startwebserver startas --debug-icm=true
doskey ! = gradlew stopas stopsolr stopwebserver
ECHO GRADLE_USER_HOME	%GRADLE_USER_HOME%
ECHO CONFIGDIR		%CONFIGDIR%
ECHO ----------------------------
ECHO studio
ECHO gradlew startwebserver		
ECHO gradlew startas
ECHO gradlew startas --debug-icm=true
ECHO gradlew startsolr
ECHO gradlew stopas
ECHO gradlew startsolr startwebserver startas --debug-icm=true
ECHO gradlew stopas stopsolr stopwebserver  
ECHO ----------------------------