@echo off

set PRODUCT=%1
if not "%PRODUCT%"=="" goto title1
echo "have no product parameter,will exit"
goto end

:title1
title %PRODUCT% for YUEP Console

setlocal
echo set DIRNAME=.\
echo if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
echo pushd %DIRNAME%\..

if exist "jre\bin\java.exe" (
	GOTO SET_JAVA_HOME
)
GOTO CONTINUE2
:SET_JAVA_HOME
set JAVA_HOME=jre

:CONTINUE2
set JAVA=%JAVA_HOME%\bin\java.exe

if not "%JAVA%" == "" goto okJava
echo "The JAVA_HOME environment variable is not defined."
echo "This environment variable is needed to run this program."
goto end

:okJava

set SERVER_HOME=.

if exist "bin\setclasspath.bat" goto okSetclasspath
echo Cannot find bin\setclasspath.bat
echo This file is needed to run this program
goto end
:okSetclasspath
set CLASSPATH=
for %%1 IN (bootstrap\lib\jakarta-commons\*.jar) DO call "bin\setclasspath.bat" %%1
for %%1 IN (bootstrap\lib\jdom\*.jar) DO call "bin\setclasspath.bat" %%1
for %%1 IN (bootstrap\lib\log4j\*.jar) DO call "bin\setclasspath.bat" %%1
for %%1 IN (bootstrap\lib\logback\*.jar) DO call "bin\setclasspath.bat" %%1
for %%1 IN (bootstrap\lib\yuep\*.jar) DO call "bin\setclasspath.bat" %%1

set CLASSPATH=%CLASSPATH%;

set REMOTEDEBUGPORT=%2
if "%REMOTEDEBUGPORT%"=="" goto NOTREMOTE
set JAVA_OPTS=%JAVA_OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,address=%REMOTEDEBUGPORT%,server=y,suspend=y
goto NOTREMOTE_GOON

:NOTREMOTE
set JAVA_OPTS=%JAVA_OPTS%

:NOTREMOTE_GOON
echo ===============================================================================
echo.
echo   Server Bootstrap Environment
echo.
echo   SERVER_HOME: %CD%
echo.
echo   JAVA: %JAVA%
echo.
echo   JAVA_OPTS: %JAVA_OPTS%
echo.
echo ===============================================================================
echo.

"%JAVA%" %JAVA_OPTS% -Xms128m -Xmx512m com.yuep.core.bootstrap.impl.CoreContainer -p %1

:end
endlocal
@echo on