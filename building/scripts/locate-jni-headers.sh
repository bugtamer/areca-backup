#!/usr/bin/env bash


# locations of jni.h: `find /usr/lib/jvm -name jni.h`
# first location:     `head -n 1`
# remove '/jni.h':    `sed 's/\/jni\.h//g'`


# C Headers to find
JNI=include/jni.h
JNI_MD=include/linux/jni_md.h

# Getting Areca's directory
ARECA_DIR=`dirname "$0"`
ARECA_DIR=`cd "$ARECA_DIR/../.."; pwd`


if [[ -e "${ARECA_DIR}/jdk/${JNI}" ]] && [[ -e "${ARECA_DIR}/jdk/${JNI_MD}" ]]; then
    HEADERS=$ARECA_DIR
elif [[ -e "${JAVA_HOME}/${JNI}" ]] && [[ -e "${JAVA_HOME}/${JNI_MD}" ]]; then
    HEADERS=$JAVA_HOME
else
    HEADERS=/usr/lib/jvm
fi


find $HEADERS -name jni.h | head -n 1 | sed 's/\/jni\.h//g'