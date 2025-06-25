#!/usr/bin/env bash

# For testing >> "Areca Backup - Target edition" window (Pre/Post processing) >> `Execute a script`:
# - `Script location` = `pre-post-processing.sh`
# - `Parameters` = PRE;%DATE%;%TIME%;%TARGET_NAME%;%TARGET_UID%;%BACKUP_TYPE%;%ARCHIVE%;%ARCHIVE_NAME%;%ARCHIVE_LOGICAL_PATH%;%COMPUTER_NAME%;%USER_NAME%;%HAS_WARNINGS%;%SUCCESS%
# - `Parameters` = POST;%DATE%;%TIME%;%TARGET_NAME%;%TARGET_UID%;%BACKUP_TYPE%;%ARCHIVE%;%ARCHIVE_NAME%;%ARCHIVE_LOGICAL_PATH%;%COMPUTER_NAME%;%USER_NAME%;%HAS_WARNINGS%;%SUCCESS%


PROGRAM_DIR=`dirname "$0"`
LOG_FILE="${PROGRAM_DIR}"/pre-post-processing.log
TRIGGER_TIME=N/A

shopt -s nocasematch
if [[ "$1" == "PRE" ]]; then
	TRIGGER_TIME=Pre
fi

if [[ "$1" == "POST" ]]; then
	TRIGGER_TIME=Post
fi
shopt -u nocasematch

DATE="$2"
TIME="$3"
TARGET_NAME="$4"
TARGET_UID="$5"
BACKUP_TYPE="$6"
ARCHIVE="$7"
ARCHIVE_NAME="$8"
ARCHIVE_LOGICAL_PATH="$9"
COMPUTER_NAME="${10}"
USER_NAME="${11}"
HAS_WARNINGS="${12}"
SUCCESS="${13}"


echo ${TRIGGER_TIME}-processing BEGIN >> $LOG_FILE
echo Call: pre-post-processing.sh $1 $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11} ${12} ${13} >> $LOG_FILE
date +%F >> $LOG_FILE
date +%T >> $LOG_FILE
echo USERNAME $USER >> $LOG_FILE
echo ----====================---- >> $LOG_FILE
echo 1.  DATE                 \(${DATE}\)                 >> $LOG_FILE
echo 2.  TIME                 \(${TIME}\)                 >> $LOG_FILE
echo 3.  TARGET_NAME          \(${TARGET_NAME}\)          >> $LOG_FILE
echo 4.  TARGET_UID           \(${TARGET_UID}\)           >> $LOG_FILE
echo 5.  BACKUP_TYPE          \(${BACKUP_TYPE}\)          >> $LOG_FILE
echo 6.  ARCHIVE              \(${ARCHIVE}\)              >> $LOG_FILE
echo 7.  ARCHIVE_NAME         \(${ARCHIVE_NAME}\)         >> $LOG_FILE
echo 8.  ARCHIVE_LOGICAL_PATH \(${ARCHIVE_LOGICAL_PATH}\) >> $LOG_FILE
echo 9.  COMPUTER_NAME        \(${COMPUTER_NAME}\)        >> $LOG_FILE
echo 10. USER_NAME            \(${USER_NAME}\)            >> $LOG_FILE
echo 11. HAS_WARNINGS         \(${HAS_WARNINGS}\)         >> $LOG_FILE
echo 12. SUCCESS              \(${SUCCESS}\)              >> $LOG_FILE
echo ----====================---- >> $LOG_FILE
echo ${TRIGGER_TIME}-processing END >> $LOG_FILE
echo >> $LOG_FILE

exit 0
