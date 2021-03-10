
set PARAMS=-server
set CPPATH=./KnightProj/target/classes;./KnightProj/libs/*
set MAIN=com.backinfile.core.net.GameServer

java %PARAMS% -cp %CPPATH% %MAIN%
pause