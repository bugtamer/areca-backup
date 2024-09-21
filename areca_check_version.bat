@ECHO OFF
SETLOCAL

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: This script launches Areca's version check module
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


SET "PROGRAM_DIR=%cd%"

IF EXIST "%PROGRAM_DIR%\areca_check_version.exe" (
    SET "ARECA_LAUNCHER=%PROGRAM_DIR%\areca_check_version.exe"
) ELSE (
    SET "ARECA_LAUNCHER=%PROGRAM_DIR%\bin\areca_run.bat"
)

START "Areca Backup Launcher" /B "%ARECA_LAUNCHER%" com.application.areca.version.VersionCheckLauncher %*

ENDLOCAL