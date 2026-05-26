@echo off
REM Maven Wrapper script for Windows
setlocal

set "MAVEN_PROJECTBASEDIR=%~dp0"
set "MAVEN_WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"

set "MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.9"

if not exist "%MAVEN_HOME%" (
    echo Downloading Maven...
    mkdir "%MAVEN_HOME%"
    powershell -Command "Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip' -OutFile '%TEMP%\maven.zip'"
    powershell -Command "Expand-Archive -Path '%TEMP%\maven.zip' -DestinationPath '%MAVEN_HOME%' -Force"
    del "%TEMP%\maven.zip"
)

for /f "delims=" %%i in ('dir /s /b "%MAVEN_HOME%\mvn.cmd" 2^>nul') do set "MAVEN_BIN=%%i"
if not defined MAVEN_BIN (
    for /f "delims=" %%i in ('dir /s /b "%MAVEN_HOME%\mvn.cmd" 2^>nul') do set "MAVEN_BIN=%%i"
)

"%MAVEN_BIN%" %*
