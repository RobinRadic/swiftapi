#!/bin/bash
rootdir=$PWD

echo "cleaning old files"
rm -rf $rootdir/builder/output
rm -rf $rootdir/builder/source

echo "unpack current good verison"
cd $rootdir/builder
mkdir output
cp orig.jar output/orig.jar
cd output
jar -xf orig.jar
rm orig.jar


echo "build source mvn"
cd $rootdir
mvn clean package

echo "unpack source jar"
cd builder
mkdir source
mv swiftapi.jar source/swiftapi.jar
cd source
jar -xf swiftapi.jar
rm swiftapi.jar

echo "Fixing directories and creating new jar"
rm -rf $rootdir/builder/output/org/phybros/minecraft
cp -r $rootdir/builder/source/org/phybros/minecraft $rootdir/builder/output/org/phybros/minecraft
cd $rootdir/builder/output
jar -cf SwiftApi.jar .

echo "Removing old jar from plugins dir and placing new"
rm $rootdir/../mcserver/craftbukkit/plugins/SwiftApi.jar
mv SwiftApi.jar $rootdir/../mcserver/craftbukkit/plugins/SwiftApi.jar