#!/bin/bash 
set -e
echo Build-Script for termasm using graalvm, target platform: linux {elf}. 
if [ -d bin ]; then  
	rm -rf bin 
fi 
mkdir bin 
echo Generating elf-file... 
mvn package 
mv target/termasm bin/termasm 
rm target/*.jar 
echo Done! 
