@ECHO OFF
SETLOCAL

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: This script launches Areca's Graphical user interface.
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


SET "SCRIPT_DIR=%~dp0%"
SET "PROGRAM_DIR=%SCRIPT_DIR:~0,-1%"

SET "ARECA_LAUNCHER=%PROGRAM_DIR%\areca.exe"
SET "IS_EXE_LAUNCHER=1"

IF NOT EXIST "%ARECA_LAUNCHER%" (
    SET "ARECA_LAUNCHER=%PROGRAM_DIR%\bin\areca_run.bat"
    SET "IS_EXE_LAUNCHER=0"
)

IF NOT EXIST "%PROGRAM_DIR%\logs" (
    MKDIR "%PROGRAM_DIR%\logs"
)

:: Logging
SET "ARECA_LOG=%PROGRAM_DIR%\logs\areca.log"

ECHO %DATE%                        >  "%ARECA_LOG%"
ECHO %TIME%                        >> "%ARECA_LOG%"
VER                                >> "%ARECA_LOG%"
ECHO.                              >> "%ARECA_LOG%"
ECHO SCRIPT_DIR:  %SCRIPT_DIR%     >> "%ARECA_LOG%"
ECHO PROGRAM_DIR: %PROGRAM_DIR%    >> "%ARECA_LOG%"
ECHO SCRIPT:      %0               >> "%ARECA_LOG%"
ECHO LAUNCHER:    %ARECA_LAUNCHER% >> "%ARECA_LOG%"
ECHO ARGUMENTS:   %*               >> "%ARECA_LOG%"
ECHO.                              >> "%ARECA_LOG%"

ECHO.
ECHO Areca Backup will continue running if you close this window.
ECHO.


IF "%IS_EXE_LAUNCHER%" == "1" (
    "%ARECA_LAUNCHER%" %* 2>&1 >> "%ARECA_LOG%"
) ELSE (
    "%ARECA_LAUNCHER%" com.application.areca.launcher.gui.Launcher %* 2>&1 >> "%ARECA_LOG%"
)


IF NOT ERRORLEVEL 0 (
    ECHO.
    ECHO An Error has happened:
    ECHO.
    TYPE "%ARECA_LOG%"
    ECHO.
    ECHO See "%ARECA_LOG%"
    ECHO.
    PAUSE
     EXIT /B 1
)

ENDLOCAL