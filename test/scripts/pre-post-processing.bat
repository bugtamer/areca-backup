@ECHO OFF

:: For testing >> "Areca Backup - Target edition" window (Pre/Post processing) >> `Execute a script`:
:: - `Script location` = `pre-post-processing.bat`
:: - `Parameters` = PRE;%DATE%;%TIME%;%TARGET_NAME%;%TARGET_UID%;%BACKUP_TYPE%;%ARCHIVE%;%ARCHIVE_NAME%;%ARCHIVE_LOGICAL_PATH%;%COMPUTER_NAME%;%USER_NAME%;%HAS_WARNINGS%;%SUCCESS%
:: - `Parameters` = POST;%DATE%;%TIME%;%TARGET_NAME%;%TARGET_UID%;%BACKUP_TYPE%;%ARCHIVE%;%ARCHIVE_NAME%;%ARCHIVE_LOGICAL_PATH%;%COMPUTER_NAME%;%USER_NAME%;%HAS_WARNINGS%;%SUCCESS%


SET "LOG_FILE=C:\areca\pre-post-processing.log"
SET "TRIGGER_TIME=N/A"

IF /i "%1" == "PRE" (
	SET "TRIGGER_TIME=Pre"
)

IF /i "%1" == "POST" (
	SET "TRIGGER_TIME=Post"
)

SET "DATE=%2"
SET "TIME=%3"
SET "TARGET_NAME=%4"
SET "TARGET_UID=%5"
SET "BACKUP_TYPE=%6"
SET "ARCHIVE=%7"
SET "ARCHIVE_NAME=%8"
SET "ARCHIVE_LOGICAL_PATH=%9"
SHIFT
SET "COMPUTER_NAME=%9"
SHIFT
SET "USER_NAME=%9"
SHIFT
SET "HAS_WARNINGS=%9"
SHIFT
SET "SUCCESS=%9"



ECHO %TRIGGER_TIME%-processing BEGIN >> %LOG_FILE%
ECHO Call: pre-post-processing.bat %TRIGGER_TIME% %DATE% %TIME%^
     %TARGET_NAME% %TARGET_UID% %BACKUP_TYPE% %ARCHIVE% %ARCHIVE_NAME%^
	 %ARCHIVE_LOGICAL_PATH% %COMPUTER_NAME% %USER_NAME% %HAS_WARNINGS%^
	 %SUCCESS% >> $LOG_FILE
DATE /T >> %LOG_FILE%
TIME /T >> %LOG_FILE%
ECHO USERNAME %USERNAME% >> %LOG_FILE%
ECHO ----====================---- >> %LOG_FILE%
ECHO 1.  DATE                 (%DATE%)                 >> %LOG_FILE%
ECHO 2.  TIME                 (%TIME%)                 >> %LOG_FILE%
ECHO 3.  TARGET_NAME          (%TARGET_NAME%)          >> %LOG_FILE%
ECHO 4.  TARGET_UID           (%TARGET_UID%)           >> %LOG_FILE%
ECHO 5.  BACKUP_TYPE          (%BACKUP_TYPE%)          >> %LOG_FILE%
ECHO 6.  ARCHIVE              (%ARCHIVE%)              >> %LOG_FILE%
ECHO 7.  ARCHIVE_NAME         (%ARCHIVE_NAME%)         >> %LOG_FILE%
ECHO 8.  ARCHIVE_LOGICAL_PATH (%ARCHIVE_LOGICAL_PATH%) >> %LOG_FILE%
ECHO 9.  COMPUTER_NAME        (%COMPUTER_NAME%)        >> %LOG_FILE%
ECHO 10. USER_NAME            (%USER_NAME%)            >> %LOG_FILE%
ECHO 11. HAS_WARNINGS         (%HAS_WARNINGS%)         >> %LOG_FILE%
ECHO 12. SUCCESS              (%SUCCESS%)              >> %LOG_FILE%
ECHO ----====================---- >> %LOG_FILE%
ECHO %TRIGGER_TIME%-processing END >> %LOG_FILE%
ECHO. >> %LOG_FILE%

EXIT 0
