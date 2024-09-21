#!/usr/bin/env bash


# locations of jni.h: `find /usr/lib/jvm -name jni.h`
# first location:     `head -n 1`
# remove '/jni.h':    `sed 's/\/jni\.h//g'`

find /usr/lib/jvm -name jni.h | head -n 1 | sed 's/\/jni\.h//g'