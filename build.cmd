@echo off 
setlocal 
echo. 
echo Build-Script for termasm using graalvm, target platform: windows {exe}. 
echo. 
if exist .\bin rmdir /s /q .\bin 
mkdir .\bin 
echo Generating exe-file... 
call mvn package 
move .\target\termasm.exe .\bin\termasm.exe 
del .\target\*.jar 
echo Done! 
endlocal 
