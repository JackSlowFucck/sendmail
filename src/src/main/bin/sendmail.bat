@echo off

set "CURRENT_DIR=%cd%"
if not "%SENDMAIL_HOME%" == "" goto gotHome
set "SENDMAIL_HOME=%CURRENT_DIR%"
if exist "%SENDMAIL_HOME%\bin\sendmail.bat" goto gotHome
cd ..
set "SENDMAIL_HOME=%cd%"
cd "%CURRENT_DIR%"

echo "SENDMAIL_HOME:%SENDMAIL_HOME%"
:gotHome
:: ��ȡsendmail.jar��PID
for /f "tokens=1" %%a in ('jps ^| findStr MailSend') do  set PID=%%a

if ""%1"" == ""start"" goto doStart
if ""%1"" == ""restart"" goto doRestart
if ""%1"" == ""stop"" goto doStop
if ""%1"" == ""status"" goto doStatus
if ""%1"" == ""version"" goto doVersion
if ""%1"" == ""log"" goto doLog
if ""%1"" == """" goto doUsage


:: ��������
:doStart
	
	if "%PID%" == "" goto starting
	if not "%PID%" == "" goto started
	
	:starting
	
	
	start cmd /k java -Djava.ext.dirs="%SENDMAIL_HOME%/lib/"  com.sendmail.main.MailSend "%2" "%3" "%4" "%5"
	echo SendMail process start success!
	
	goto end
	
	:started
	echo SendMail Process has started!
	
	goto end

:: ��������
:doRestart

	if "%PID%" == "" goto notstart
	if not "%PID%" == "" goto restart
	
	:notstart
	echo SendMail process not start!
	
	:restart
	taskkill /f /t /pid %PID%
	echo SendMail process stop success!
	
	start cmd /k java -Djava.ext.dirs="%SENDMAIL_HOME%/lib/"  com.sendmail.main.MailSend "%2" "%3" "%4" "%5"
	
	echo SendMail process restart success!
	goto end

	


:: ֹͣ����
:doStop
	
	if not "%PID%" == "" goto stoping
	if "%PID%" == "" goto stoped
		
	:stoping
	taskkill /f /t /pid %PID%
	echo SendMail process stop success!
	goto end
	
	:stoped
	echo SendMail process had stoped!
	goto end
	
:: �鿴����״̬
:doStatus

	if not "%PID%" == "" goto Running
	if "%PID%" == "" goto Stoped

	:Running
	echo SendMail process is running!
	goto end
	:Stoped
	echo SendMail process has stoped!
	goto end

:: �鿴������־
:doLog
	tail -f ../logs/sendmail.log
	
:: �鿴����汾
:doVersion
	echo   SendMail version 0.0.1
	goto end

:: ���ʹ�ó���
:doUsage
	echo Usage:  sendmail ( commands ... )
	echo commands:

	echo   start             start send mail tool
	echo   restart           restart send mail tool
	echo   stop              stop send mail tool
	echo   status            get send mail tool status
	echo   version           get send mail tool version

	echo sendmail:
	echo   pid               %PID%

	goto end

:end
	set PID=<nul

