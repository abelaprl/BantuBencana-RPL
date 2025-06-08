@echo off
echo.
echo --- Starting Maven Clean Install ---
echo.

rem Change directory to where this script is located
rem cd /d "%~dp0"

rem Run Maven clean install
mvn clean install

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Maven Clean Install failed! Please check the output above.
    pause
    exit /b %errorlevel%
) else (
    echo.
    echo --- Maven Clean Install completed successfully! ---
)

echo.
echo Press any key to continue to run the application...
pause > nul