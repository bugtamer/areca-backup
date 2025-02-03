@ECHO OFF
SETLOCAL

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: This script launches Areca's Text user interface.
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


SET "SCRIPT_DIR=%~dp0%"
SET "PROGRAM_DIR=%SCRIPT_DIR:~0,-1%"

SET "ARECA_LAUNCHER=%PROGRAM_DIR%\areca_cl.exe"
SET "IS_EXE_LAUNCHER=1"

IF NOT EXIST "%ARECA_LAUNCHER%" (
    SET "ARECA_LAUNCHER=%PROGRAM_DIR%\bin\areca_run.bat"
    SET "IS_EXE_LAUNCHER=0"
)

SET "ARECA_LAUNCHER=%ARECA_LAUNCHER:/=\%"

IF "%IS_EXE_LAUNCHER%" == "1" (
    "%ARECA_LAUNCHER%" %*
) ELSE (
    CALL "%ARECA_LAUNCHER%" com.application.areca.launcher.tui.Launcher %*
)

ENDLOCAL