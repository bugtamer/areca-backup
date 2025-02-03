@ECHO OFF
SETLOCAL

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: Generic script to launch Areca-backup's executables
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


:: Absolute script path
SET "SCRIPT_DIR=%~dp0%"

:: Logging data
SET "BATCH_SCRIPT_PATHNAME=%0"
SET "SCRIPT_PATH=%BATCH_SCRIPT_PATHNAME:~1,-19%"
SET "SCRIPT_PATH=%SCRIPT_PATH:/=\%"

:: Command-line arguments
SET "P1_REQUIRED_LAUNCHER_CLASS=%1"
SET "P2_OPTIONAL=%2"
SET "P3_OPTIONAL=%3"
SET "P4_OPTIONAL=%4"
SET "P5_OPTIONAL=%5"
SET "P6_OPTIONAL=%6"
SET "P7_OPTIONAL=%7"
SET "P8_OPTIONAL=%8"
SET "P9_OPTIONAL=%9"
SET "P10_OPTIONAL=%9"
SHIFT
SET "P10_OPTIONAL=%9"
SHIFT
SET "P11_OPTIONAL=%9"
SHIFT
SET "P12_OPTIONAL=%9"


:: Getting Areca's directory
SET "ARECA_DIR=%SCRIPT_DIR:~0,-5%"


:: Getting Java directory
                CALL :find_java "%ARECA_DIR%\jdk"  "" "Embedded jdk"
IF ERRORLEVEL 1 CALL :find_java "%ARECA_DIR%\jre"  "" "Embedded jre"
IF ERRORLEVEL 1 CALL :find_java "%ARECA_HOME%\jdk" "" "ARECA_HOME\jdk"
IF ERRORLEVEL 1 CALL :find_java "%ARECA_HOME%\jre" "" "ARECA_HOME\jre"
IF ERRORLEVEL 1 CALL :find_java "%JAVA_HOME%"      "" "JAVA_HOME"

IF ERRORLEVEL 1 CALL :find_java_resursively "C:\Java"
IF ERRORLEVEL 1 CALL :find_java_resursively "C:\Program Files (X86)\Java"
IF ERRORLEVEL 1 CALL :find_java_resursively "C:\Program Files\Java"

IF ERRORLEVEL 1 CALL :find_java "C:\Program Files (X86)\Common Files\Oracle\Java\javapath" "\java.exe"
IF ERRORLEVEL 1 CALL :find_java "C:\Program Files\Common Files\Oracle\Java\javapath"       "\java.exe"

IF NOT EXIST "%JAVA_EXE%" (
    ECHO ERROR: Java not found.
    EXIT /B 1
)

GOTO areca_settings


:: function find_java(location: string, bin_java?: string, description?: string);
:find_java
    SET "LOCATION=%~1"
    SET "BIN_JAVA=bin\java.exe"
    SET "BIN_JAVAW=bin\javaw.exe"
    IF NOT "%~2"=="" SET "BIN_JAVA=%~2"
    SET "DESCRIPTION="%~1"
    IF NOT "%~3"=="" SET "DESCRIPTION=%~3"
    SET "JAVA_PATH=%LOCATION%"
    SET "JAVA_EXE=%LOCATION%\%BIN_JAVA%"
    SET "JAVAW_EXE=%LOCATION%\%BIN_JAVAW%"
    IF EXIST "%JAVA_EXE%" EXIT /B 0
    ECHO ERROR 2: "java.exe" was not found in "%DESCRIPTION%"
    EXIT /B 2


:: function find_java_resursively(location: string, binary?: string);
:find_java_resursively
    SET "LOCATION=%~1"
    SET "BINARY=bin\java.exe"
    IF NOT "%~2"=="" SET "BINARY=%~2"
    FOR /r "%LOCATION%" %%i IN (%BINARY%) DO (
        IF EXIST "%%i" (
            SET "JAVA_PATH=%%~dpi"
            SET "JAVA_EXE=%%i"
            SET "JAVAW_EXE=%%~dpi\javaw.exe"
            EXIT /B 0
        )
    )
    ECHO ERROR 3: "java.exe" was not found in "%LOCATION%"
    EXIT /B 3


:areca_settings


:: Configured directories
SET "LICENSE_PATH=%ARECA_DIR%"
SET "LIB_PATH=%ARECA_DIR%\lib\"
SET "TRANSLATION_PATH=%ARECA_DIR%\translations"
SET "CONFIG_PATH=%ARECA_DIR%\config"


:: Building Areca's classpath
SET "CLASSPATH=%LICENSE_PATH%"
SET "CLASSPATH=%CLASSPATH%;%CONFIG_PATH%"
SET "CLASSPATH=%CLASSPATH%;%TRANSLATION_PATH%"

SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%areca.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%mail.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%activation.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%commons-net-1.4.1.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%jakarta-oro-2.0.8.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%jsch.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%org.eclipse.core.commands_3.2.0.I20060605-1400.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%org.eclipse.equinox.common_3.2.0.v20060603.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%org.eclipse.jface_3.2.0.I20060605-1400.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%swt.jar"
SET "CLASSPATH=%CLASSPATH%;%LIB_PATH%commons-codec-1.4.jar"


set "LIBRARY_PATH=%LIB_PATH%;%JAVA_PATH%\lib;%JAVA_PATH%\jni"


:: See https://bugs.launchpad.net/gtk/+bug/442078
SET "GDK_NATIVE_WINDOWS=true"


SET "JAVA=%JAVA_EXE%"
IF "%P1_REQUIRED_LAUNCHER_CLASS%"=="com.application.areca.launcher.gui.Launcher" (
    SET "JAVA=%JAVAW_EXE%"

    :: Logging
    SET "ARECA_LOG=%PROGRAM_DIR%\logs\areca_run.bat.log"
    ECHO SCRIPT:                    areca_run.bat
    ECHO SCRIPT_DIR:                %SCRIPT_DIR%
    ECHO SCRIPT_PATH:               %SCRIPT_PATH%
    ECHO.
    ECHO ARECA_HOME:                %ARECA_HOME%
    ECHO JAVA_HOME:                 %JAVA_HOME%
    ECHO JAVA_PATH:                 %JAVA_PATH%
    ECHO JAVA_EXE:                  %JAVA_EXE%
    ECHO JAVAW_EXE:                 %JAVAW_EXE%
    ECHO GDK_NATIVE_WINDOWS:        %GDK_NATIVE_WINDOWS%
    ECHO.
    ECHO JAVA:                      %JAVA%
    ECHO CLASSPATH:                 %CLASSPATH%
    ECHO ARECA_DIR:                 %ARECA_DIR%
    ECHO LIBRARY_PATH:              %LIBRARY_PATH%
    ECHO.
    ECHO P0:                         %BATCH_SCRIPT_PATHNAME%
    ECHO P1_REQUIRED_LAUNCHER_CLASS: %P1_REQUIRED_LAUNCHER_CLASS%
    ECHO P2_OPTIONAL:                %P2_OPTIONAL%
    ECHO P3_OPTIONAL:                %P3_OPTIONAL%
    ECHO P4_OPTIONAL:                %P4_OPTIONAL%
    ECHO P5_OPTIONAL:                %P5_OPTIONAL%
    ECHO P6_OPTIONAL:                %P6_OPTIONAL%
    ECHO P7_OPTIONAL:                %P7_OPTIONAL%
    ECHO P8_OPTIONAL:                %P8_OPTIONAL%
    ECHO P9_OPTIONAL:                %P9_OPTIONAL%
    ECHO P10_OPTIONAL:               %P10_OPTIONAL%
    ECHO P11_OPTIONAL:               %P11_OPTIONAL%
    ECHO P12_OPTIONAL:               %P12_OPTIONAL%
    ECHO.
)


:: Launching Areca
"%JAVA_EXE%" -version 2>&1

IF ERRORLEVEL 1 EXIT /B 4

"%JAVA%"^
    -Xmx1024m^
    -Xms64m^
    -cp "%CLASSPATH%"^
    -Duser.dir="%ARECA_DIR%"^
    -Djava.library.path="%LIBRARY_PATH%"^
    -Djava.system.class.loader=com.application.areca.impl.tools.ArecaClassLoader %P1_REQUIRED_LAUNCHER_CLASS%^
    "%P2_OPTIONAL%"^
    "%P3_OPTIONAL%"^
    "%P4_OPTIONAL%"^
    "%P5_OPTIONAL%"^
    "%P6_OPTIONAL%"^
    "%P7_OPTIONAL%"^
    "%P8_OPTIONAL%"^
    "%P9_OPTIONAL%"^
    "%P10_OPTIONAL%"^
    "%P11_OPTIONAL%"^
    "%P12_OPTIONAL%"^
    2>&1

IF ERRORLEVEL 1 EXIT /B 5

ENDLOCAL