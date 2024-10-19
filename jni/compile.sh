#!/usr/bin/env bash
########################################################
# JNI Compilation script
# If you want to generate the .h file, use : "javah -jni -classpath "lib/areca.jar" com.myJava.file.metadata.posix.jni.wrapper.FileAccessWrapper"
########################################################

rm libarecafs.so

../building/scripts/install-c-header-dependencies-from-dep-packges.sh

H=$(../building/scripts/locate-jni-headers.sh)
./header.sh

gcc -c -fPIC -lacl com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.c -o com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o -I${H} -I${H}/linux
gcc -shared -lacl -Wl,-soname,libarecafs.so -o libarecafs.so  com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o

rm com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o
rm com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.h