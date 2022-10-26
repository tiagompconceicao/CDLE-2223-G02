@echo off
echo off

set LibraryPath=..\NativeLibs

set JarFile=.\target\Demo02-OpenCV-IdentifyObjectsInPictures-2020.2021.SemInv.jar

set Input=..\..\..\input\imagens
set Output=..\..\..\output\imagens
set Classifiers=.\classifiers\faceClassifier.xml

set Arguments=%Input% %Output% %Classifiers%

set JavaBin=%JAVA_HOME%\bin\java

set JavaOptions=-Djava.library.path=%LibraryPath%

set Command=%JavaBin% %JavaOptions% -jar %JarFile% %Arguments%

echo.
echo %Command%
echo.
%Command%
echo.

pause