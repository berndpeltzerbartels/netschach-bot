@echo off
setlocal

set IMAGE_NAME=netschach-bot
set SCRIPT_DIR=%~dp0

echo Baue Image: %IMAGE_NAME% ...
docker build --no-cache -t %IMAGE_NAME% %SCRIPT_DIR%
if %ERRORLEVEL% neq 0 (
    echo Fehler beim Bauen des Images!
    exit /b %ERRORLEVEL%
)
echo Fertig! Image '%IMAGE_NAME%' wurde erstellt.
