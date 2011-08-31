@echo off
REM Verify if JAVA_HOME is well defined
if not exist "%JAVA_HOME%\bin\java.exe" goto nokJava

set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
pushd %DIRNAME%

cd joram\run
set JORAM_HOME=..

set CONFIG_DIR=%JORAM_HOME%\config
set JORAM_LIBS=%JORAM_HOME%\libs
set RUN_DIR=%JORAM_HOME%\run

REM  Building the Classpath
set CLASSPATH=%JORAM_LIBS%\joram-mom.jar
set CLASSPATH=%CLASSPATH%;%JORAM_LIBS%\joram-shared.jar
set CLASSPATH=%CLASSPATH%;%JORAM_LIBS%\JCup.jar
set CLASSPATH=%CLASSPATH%;%JORAM_LIBS%\jakarta-regexp-1.2.jar
set CLASSPATH=%CLASSPATH%;%JORAM_LIBS%\ow_monolog.jar
set CLASSPATH=%CLASSPATH%;%JORAM_LIBS%\jmxri.jar
set CLASSPATH=%CLASSPATH%;%RUN_DIR%

mkdir %RUN_DIR%
copy /Y %CONFIG_DIR%\a3config.dtd %RUN_DIR%\a3config.dtd
copy /Y %CONFIG_DIR%\a3debug.cfg %RUN_DIR%\a3debug.cfg
copy /Y %CONFIG_DIR%\centralized_a3servers.xml %RUN_DIR%\a3servers.xml
copy /Y %CONFIG_DIR%\jndi.properties %RUN_DIR%\jndi.properties

REM dir %JORAM_LIBS%\joram-mom.jar
REM %JAVA_HOME%\bin\java -classpath %CLASSPATH% fr.dyade.aaa.agent.AgentServer 0 ./s0

echo == Launching a non persistent server#0 ==
start /D "%RUN_DIR%" /B java -classpath %CLASSPATH% fr.dyade.aaa.agent.AgentServer 0 ./s0

goto end

:nokJava
echo The JAVA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end

:end
