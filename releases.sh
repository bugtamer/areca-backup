#!/usr/bin/env bash

clear

javac -version

if [[ "$?" -ne 0 ]]; then
    exit 1
fi

rmdir releases/
mkdir releases/

ant clean
ant windows-x86-32
ant windows-x86-64
ant linux-x86-32
ant linux-x86-64

cp docs/developer/history.md releases/REAME.md

cd releases
zip -9r hashes.zip . -i hashes/*
cd ..

rm --dir --force --recursive releases/hashes/
