@echo off
setlocal

REM Path ke folder lib JavaFX kamu
set JAVAFX_LIB=C:\Users\User\Downloads\openjfx-24.0.1_windows-x64_bin-sdk\javafx-sdk-24.0.1\lib

echo.
echo --- JavaFX Compilation and Run Script ---
echo Current directory: %cd%
echo JavaFX Library Path: %JAVAFX_LIB%
echo.

REM Check if JAVAFX_LIB exists
if not exist "%JAVAFX_LIB%" (
    echo ❌ ERROR: JavaFX library path does not exist: "%JAVAFX_LIB%"
    echo Please verify the JAVAFX_LIB variable in this batch file.
    pause
    exit /b 1
)

echo [1] Compile...
echo Verifying javac version used for compilation...
where javac
javac -version
echo.

REM --- CHANGE THIS LINE ---
REM Compile all .java files in the 'src' directory, adding javafx.media module
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media src\*.java
if errorlevel 1 (
    echo ❌ Compile error.
    pause
    exit /b
)

echo ✅ Compilation successful.

echo.
echo [2] Running application...
echo Verifying Java version used for execution...
where java
java -version
echo.

REM --- CHANGE THIS LINE ---
REM Specify the classpath using -cp or --class-path to include the 'src' directory, adding javafx.media module
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media -cp src Main
if errorlevel 1 (
    echo ❌ Application run error.
    echo Please check the error messages above for details.
    pause
    exit /b 1
)

echo ✅ Application exited.
pause
endlocal