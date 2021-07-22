@echo off
set "ezyclasspath=lib\*;common\*;apps\common\*;apps\resources\*"
for /D %%d in (plugins\*) do (
    call set "ezyclasspath=%%ezyclasspath%%;%%d\*
)
echo classpath = %ezyclasspath%

java %1 -cp %ezyclasspath% com.tvd12.ezyfoxserver.nio.EzyNioRunner settings/config.properties