#!/usr/bin/env bash


# Run from:
# areca-backup/jni$ ./header.sh

if command -v javah &>/dev/null; then
    javah -jni -d . -classpath ../lib/areca.jar com.myJava.file.metadata.posix.jni.wrapper.FileAccessWrapper
else
    javac -h . \
    -classpath ../lib/areca.jar \
    -sourcepath ../src/com/myJava/file/metadata/posix/jni/wrapper \
    -d ../src/com/myJava/file/metadata/posix/jni/wrapper \
    ../src/com/myJava/file/metadata/posix/jni/wrapper/FileAccessWrapper.java
fi
