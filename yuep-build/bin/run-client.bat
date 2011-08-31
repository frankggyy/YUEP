set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
pushd %DIRNAME%\..

call "%DIRNAME%\run-inner.bat" yuep-client
