@echo off
echo.
echo --- Starting JavaFX Application ---
echo.

rem Change directory to where this script is located
rem cd /d "%~dp0"

rem Run JavaFX application using Maven
mvn javafx:run

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Failed to run JavaFX application! Please check the output above.
    pause
    exit /b %errorlevel%
) else (
    echo.
    echo --- JavaFX Application finished. ---
)

echo.
pause