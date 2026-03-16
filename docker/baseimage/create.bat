@echo off
setlocal

set IMAGE_NAME=netschach-baseimage
set SCRIPT_DIR=%~dp0

echo Baue Baseimage: %IMAGE_NAME% ...
docker build -t %IMAGE_NAME% %SCRIPT_DIR%
if %ERRORLEVEL% neq 0 (
    echo Fehler beim Bauen des Images!
    exit /b %ERRORLEVEL%
)
echo Fertig! Image '%IMAGE_NAME%' wurde erstellt.
