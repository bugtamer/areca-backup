#!/usr/bin/env bash

clear


BRANCH=$(git branch --show-current)

if [ "$BRANCH" != "develop" ]; then
    echo ERROR: This script can only be run from the develop branch. Current branch: "$BRANCH".
    exit 1
fi


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

cp docs/developer/history.md releases/README.md

cd releases
zip -9r hashes.zip . -i hashes/*
cd ..

rm --dir --force --recursive releases/hashes/
