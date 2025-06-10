@echo off
setlocal

rem --- Konfigurasi ---
set JAVAFX_SDK_PATH=D:\javafx-sdk-24.0.1
set CLASS_PATH=src
rem -------------------

set MODULE_PATH="%JAVAFX_SDK_PATH%\lib"
set ADD_MODULES=javafx.controls,javafx.fxml,javafx.graphics

echo Menjalankan aplikasi JavaFX...

rem Jalankan aplikasi Main class
java --module-path %MODULE_PATH% --add-modules %ADD_MODULES% -cp %CLASS_PATH% Main

if %errorlevel% equ 0 (
    echo.
    echo Aplikasi telah selesai.
) else (
    echo.
    echo Aplikasi berhenti dengan error!
)

endlocal
pause