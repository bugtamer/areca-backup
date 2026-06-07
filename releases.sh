#!/usr/bin/env bash

clear

rm -rf releases/
mkdir releases/


# WRONG BRANCH GUARD

TARGET_BRANCH=develop

ACTUAL_BRANCH=$(git branch --show-current)

if [ "$ACTUAL_BRANCH" != "${TARGET_BRANCH}" ]; then
    echo ERROR: This script can only be run from the ${TARGET_BRANCH} branch. Current branch: "$ACTUAL_BRANCH".
    exit 1
fi


# WRONG VERSION GUARD

CSV_VERSION=$(head -2 "docs/developer/builds.csv" | tail -1 | awk -F ';' '{print $2}')
CURRENT_VERSION=$(head -3 "version.xml" | tail -1 | awk -F '"' '{print $2}')

if [ "$CSV_VERSION" != "$CURRENT_VERSION" ]; then
    echo ERROR: Latest builds.csv version \($CSV_VERSION\) is not equal to local version.xml \($CURRENT_VERSION\).
    exit 1
fi

URL="https://areca-backup.sourceforge.io/version_xml.php"
VER="releases/site-version.xml"
wget --no-verbose -O "$VER" "$URL"
SITE_VERSION=$(head -3 "$VER" | tail -1 | awk -F '"' '{print $2}')
rm --force "${VER}"

if [ "$CURRENT_VERSION" == "$SITE_VERSION" ]; then
    echo ERROR: Local version.xml \($CURRENT_VERSION\) is equals to the site version.xml \($SITE_VERSION\).
    exit 1
fi

URL="https://raw.githubusercontent.com/bugtamer/areca-backup/refs/heads/${TARGET_BRANCH}/version.xml"
VER="releases/git-version.xml"
wget --no-verbose -O "$VER" "$URL"
GIT_VERSION=$(head -3 "$VER" | tail -1 | awk -F '"' '{print $2}')
rm --force "${VER}"

if [ "$CURRENT_VERSION" != "$GIT_VERSION" ]; then
    echo ERROR: Push local changes \($CURRENT_VERSION\) to the remote repo \($GIT_VERSION\).
    exit 1
fi


# MISSING JDK GUARD

javac -version

if [[ "$?" -ne 0 ]]; then
    echo ERROR: JDK is not installed or not found.
    exit 1
fi


# BUILD BUNDLES

ant clean

ant windows-x86-32
ant windows-x86-64

ant linux-x86-32
ant linux-x86-64
ant linux-ppc64
ant linux-ppc
ant linux-s390x
ant linux-s390

ant macos-x86-64
ant macos-x86-32

ant solaris-x86-32
ant solaris-sparc

ant aix-ppc64
ant aix-ppc

ant hpux-ia64


# DOWNLOAD SOURCE CODE

cd releases/

URL="https://github.com/bugtamer/areca-backup/archive/refs/heads/develop.zip"
SRC="areca-$CURRENT_VERSION-src.zip"
wget --no-verbose -O "$SRC" "$URL"


# HASHES

SRC_HASH="hashes/areca-$CURRENT_VERSION-src.txt"
sha512sum "$SRC"  > "$SRC_HASH"
sha1sum   "$SRC" >> "$SRC_HASH"
md5sum    "$SRC" >> "$SRC_HASH"

zip -9r hashes.zip hashes/
cd ..

rm --dir --force --recursive releases/hashes/


# CHANGELOG

HISTORY="docs/developer/history.md"
found=false
while IFS= read -r line; do
    if [[ $line =~ ^## ]]; then
        if [ "$found" = false ]; then
            found=true
        else
            break
        fi
    fi
    if [ "$found" = true ]; then
        echo "$line"
    fi
done < "$HISTORY" > releases/README.md


# UPLOADS

# https://sourceforge.net/p/forge/documentation/File%20Management/
# https://sourceforge.net/p/forge/documentation/SFTP/
# https://sourceforge.net/p/forge/documentation/rsync/
# https://sourceforge.net/p/forge/documentation/Using%20the%20Release%20API/

PROJECT_NAME=areca-backup
RELEASE_DIR=areca-backup-${CURRENT_VERSION}-DEPLOYMENT-TEST

# https://sourceforge.net/p/forge/documentation/SSH%20Keys/
sftp bugtamer@frs.sourceforge.net <<EOF
    cd /home/frs/project/areca-backup/areca-stable/
    mkdir ${RELEASE_DIR}
    cd  ${RELEASE_DIR}
    lcd releases/
    put README.md                                  README.md
    put hashes.zip                                 hashes.zip
    put areca-$CURRENT_VERSION-src.zip             areca-$CURRENT_VERSION-src.zip
    put areca-$CURRENT_VERSION-linux-x86-32.tar.gz areca-$CURRENT_VERSION-linux-x86-32.tar.gz
    put areca-$CURRENT_VERSION-linux-x86-64.tar.gz areca-$CURRENT_VERSION-linux-x86-64.tar.gz
    put areca-$CURRENT_VERSION-windows-x86-32.zip  areca-$CURRENT_VERSION-windows-x86-32.zip
    put areca-$CURRENT_VERSION-windows-x86-64.zip  areca-$CURRENT_VERSION-windows-x86-64.zip
    mkdir untested
    cd untested
    lcd ..
    put building/assets/untested-platforms.md        README.md
    lcd releases/
    put areca-$CURRENT_VERSION-hpux-ia64.tar.gz      areca-$CURRENT_VERSION-hpux-ia64.tar.gz
    put areca-$CURRENT_VERSION-aix-ppc.tar.gz        areca-$CURRENT_VERSION-aix-ppc.tar.gz
    put areca-$CURRENT_VERSION-aix-ppc64.tar.gz      areca-$CURRENT_VERSION-aix-ppc64.tar.gz
    put areca-$CURRENT_VERSION-solaris-x86-32.tar.gz areca-$CURRENT_VERSION-solaris-x86-32.tar.gz
    put areca-$CURRENT_VERSION-solaris-sparc.tar.gz  areca-$CURRENT_VERSION-solaris-sparc.tar.gz
    put areca-$CURRENT_VERSION-macos-x86-32.tar.gz   areca-$CURRENT_VERSION-macos-x86-32.tar.gz
    put areca-$CURRENT_VERSION-macos-x86-64.tar.gz   areca-$CURRENT_VERSION-macos-x86-64.tar.gz
    put areca-$CURRENT_VERSION-linux-s390.tar.gz     areca-$CURRENT_VERSION-linux-s390.tar.gz
    put areca-$CURRENT_VERSION-linux-s390x.tar.gz    areca-$CURRENT_VERSION-linux-s390x.tar.gz
    put areca-$CURRENT_VERSION-linux-ppc.tar.gz      areca-$CURRENT_VERSION-linux-ppc.tar.gz
    put areca-$CURRENT_VERSION-linux-ppc64.tar.gz    areca-$CURRENT_VERSION-linux-ppc64.tar.gz
    cd ..
    cd ..
    lcd ..
    put docs/developer/history.md README.md
    exit
EOF


RELEASE_URL=https://sourceforge.net/projects/${PROJECT_NAME}/files/areca-stable/${RELEASE_DIR}

echo
echo stage "${RELEASE_DIR}" folder, making it not listed for 3 days.
curl -H "Accept: application/json" \
     -X PUT \
     -d "stage=1" \
     -d "api_key=${SF_API_KEY}" ${RELEASE_URL}

echo
