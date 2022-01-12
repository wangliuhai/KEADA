#!/usr/bin/env bash

name=Argouml
classname=guitar.replayer.ArgoumlReplayer

classpath="."
for jar in `ls lib`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":./lib/"$jar
    fi
done

for jar in `ls aut`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":./aut/"$jar
    fi
done

for jar in `ls softwares/argouml`
do
    if [[ $jar == *.jar ]]
    then
        classpath=$classpath":./softwares/argouml/"$jar
    fi
done

rm -rf net/$name
mkdir net/$name


Cmd="java -javaagent:./aut/agent.jar -cp $classpath $classname"

#获取静态call graph
#Cmd="java -jar ./lib/javacg-0.1-SNAPSHOT-static.jar  ./aut/studentinfo.jar"

echo $Cmd
eval $Cmd

mv net.txt net/$name

Cmd="java -cp $classpath transfrom.ToNet net/$name/net.txt net/$name/$name.net"
echo $Cmd
eval $Cmd



