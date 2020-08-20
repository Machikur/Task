call runcrud.bat
if "%ERRORLEVEL%" =="0" goto browser
echo.
echo Server ERROR
goto fail

:browser
call start chrome.exe http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo Something went Wrong
goto end

:end
echo.
echo Work is finished