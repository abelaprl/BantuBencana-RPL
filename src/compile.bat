@echo off
REM Path ke folder lib JavaFX kamu
set JAVAFX_LIB=C:\Java\javafx-sdk-24.0.1\lib

echo [1] Compile...
javac --module-path %JAVAFX_LIB% --add-modules javafx.controls Dashboard.java
if errorlevel 1 (
    echo ❌ Compile error.
    pause
    exit /b
)

echo [2] Jalankan aplikasi...
java --module-path %JAVAFX_LIB% --add-modules javafx.controls Dashboard
pause
