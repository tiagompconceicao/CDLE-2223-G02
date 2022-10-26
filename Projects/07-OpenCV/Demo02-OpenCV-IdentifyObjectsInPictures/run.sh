#!/bin/bash

LibraryPath="../NativeLibs"

JarFile="./target/Demo02-OpenCV-IdentifyObjectsInPictures-2020.2021.SemInv.jar"

Input="../../../input/imagens/"
Output="../../../output/imagens"
Classifiers="./classifiers/faceClassifier.xml"

mkdir -p ${Output}

Arguments="${Input} ${Output} ${Classifiers}"

JavaBin="${JAVA_HOME}/bin/java"

JavaOptions="-Djava.library.path=${LibraryPath}"

JavaAccessOptions="-Djavax.accessibility.assistive_technologies=java.lang.Object"

Command="${JavaBin} ${JavaOptions} ${JavaAccessOptions} -jar ${JarFile} ${Arguments}"

echo ""
echo "${Command}"
echo ""

${Command}

echo ""
