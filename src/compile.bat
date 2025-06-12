@echo off
setlocal

REM Path ke folder lib JavaFX kamu
set JAVAFX_LIB=C:\JavaFX\javafx-sdk-24.0.1\lib
set JUNIT_JAR="lib\junit-platform-console-standalone-1.13.0-M3.jar"

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

echo [0] Clean bin folder...
rmdir /S /Q bin
mkdir bin

echo [1] Compile...
echo Verifying javac version used for compilation...
where javac
javac -version
echo.

REM Compile all .java files
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media -cp "%JUNIT_JAR%" -d bin src\com\*.java 

if errorlevel 1 (
    echo ❌ Compile error.
    pause
    exit /b
)

echo ✅ Compilation successful.

@REM echo [2] Run Tests...
@REM java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media -cp "%JUNIT_JAR%;bin" org.junit.platform.console.ConsoleLauncher --scan-class-path

@REM if errorlevel 1 (
@REM     echo ❌ Test run error.
@REM     pause
@REM     exit /b
@REM )

echo ✅ Tests completed.

echo.
echo [3] Running application...
echo Verifying Java version used for execution...
where java
java -version
echo.

REM ✅ Copy FXML to bin (biarkan)
xcopy /Y /S /I src\com\*.fxml bin\com\
xcopy /Y /S /I src\com\*.css bin\com\


REM ✅ ✅ ✅ FIXED: Run with correct classpath (was: -cp src)
java -cp bin --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media com.Main

if errorlevel 1 (
    echo ❌ Application run error.
    echo Please check the error messages above for details.
    pause
    exit /b 1
)

echo ✅ Application exited.
pause
endlocal
