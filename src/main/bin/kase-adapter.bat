@echo off

if "%OS%" == "Windows_NT" setlocal enabledelayedexpansion

rem Change working directory
cd /d %~dp0/../

set ADAPTER_HOME=%CD%

rem Define java runtime
if defined JAVA_HOME (
    set "JAVA=%JAVA_HOME%/bin/java"
) else (
    goto nojava
)

set OPTS=-Xms64m -Xmx256m

rem Define adapter classpath
set CLASSPATH=%ADAPTER_HOME%/conf
for /R "%ADAPTER_HOME%/lib" %%G in (*.jar) do (
   set CLASSPATH=!CLASSPATH!;%%G
)
set CLASSPATH=!CLASSPATH!

set MAINCLASS=ru.bis.integration.kase.adapter.App

rem Parse adapter parameters
if [%1]==[] goto usage

if /i "%1"=="start" set ARGS=start & goto run
if /i "%1"=="stop" set ARGS=stop & goto run
if /i "%1"=="status" set ARGS=status & goto run
goto usage

:run
rem Execute java application
"%JAVA%" %OPTS% -cp "%CLASSPATH%" %MAINCLASS% %ARGS%
goto end

:usage
    echo Usage: %~nx0 ^<option^>
    echo    where options include:
    echo        start       - start adapter
    echo        stop        - stop adapter
    echo        status      - show adapter status
goto end

:nojava
    echo JAVA_HOME environment variable is not defined.
goto end

:end
if "%OS%" == "Windows_NT" endlocal