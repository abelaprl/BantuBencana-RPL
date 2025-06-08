@echo off
setlocal

rem --- Konfigurasi ---
set JAVAFX_SDK_PATH=D:\javafx-sdk-24.0.1
set SRC_DIR=src
set CLASS_OUTPUT_DIR=src
rem -------------------

set MODULE_PATH="%JAVAFX_SDK_PATH%\lib"
set ADD_MODULES=javafx.controls,javafx.fxml,javafx.graphics

echo Mengkompilasi file JavaFX...

rem Hapus file .class yang sudah ada untuk memastikan kompilasi bersih
del "%CLASS_OUTPUT_DIR%\*.class" 2>nul
del "%CLASS_OUTPUT_DIR%\*.class" /s 2>nul

rem Kompilasi semua file .java di dalam SRC_DIR
javac --module-path %MODULE_PATH% --add-modules %ADD_MODULES% -d %CLASS_OUTPUT_DIR% ^
    %SRC_DIR%\Main.java ^
    %SRC_DIR%\User.java ^
    %SRC_DIR%\UserRepository.java ^
    %SRC_DIR%\LoginController.java ^
    %SRC_DIR%\RegisterController.java ^
    %SRC_DIR%\Dashboard.java ^
    %SRC_DIR%\Feedback.java ^
    %SRC_DIR%\FeedbackData.java ^
    %SRC_DIR%\LaporanBencana.java ^
    %SRC_DIR%\LaporanBencanaData.java ^
    %SRC_DIR%\DataManager.java ^
    %SRC_DIR%\DatabaseViewer.java

if %errorlevel% equ 0 (
    echo.
    echo Kompilasi BERHASIL!
) else (
    echo.
    echo Kompilasi GAGAL! Periksa error di atas.
)

endlocal
pause