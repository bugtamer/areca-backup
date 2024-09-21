@ECHO OFF
SETLOCAL

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: This script launches Areca's Text user interface.
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


SET "PROGRAM_DIR=%cd%"
SET "ARECA_LAUNCHER=%PROGRAM_DIR%\areca_cl.exe"

IF EXIST "%ARECA_LAUNCHER%" (GOTO launch_areca) ELSE (SET "ARECA_LAUNCHER=%PROGRAM_DIR%\bin\areca_run.bat")

SET "BATCH_SCRIPT_PATHNAME=%0"
SET "SCRIPT_PATH=%BATCH_SCRIPT_PATHNAME:~1,-14%"

IF EXIST "%ARECA_LAUNCHER%" (GOTO launch_areca) ELSE (SET "ARECA_LAUNCHER=%SCRIPT_PATH%\areca_cl.exe")
IF EXIST "%ARECA_LAUNCHER%" (GOTO launch_areca) ELSE (SET "ARECA_LAUNCHER=%SCRIPT_PATH%\bin\areca_run.bat")


:launch_areca
SET "ARECA_LAUNCHER=%ARECA_LAUNCHER:/=\%"
CALL "%ARECA_LAUNCHER%" com.application.areca.launcher.tui.Launcher %*

ENDLOCAL