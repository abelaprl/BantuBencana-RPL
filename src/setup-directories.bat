@echo off
echo Creating required directories...

if not exist "img" mkdir img
if not exist "img\bencana_photos" mkdir img\bencana_photos
if not exist "img\bencana_videos" mkdir img\bencana_videos

echo Directories created successfully!
echo.
echo Directory structure:
echo img/
echo   bencana_photos/
echo   bencana_videos/
echo.
pause