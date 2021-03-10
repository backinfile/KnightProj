
set PARAMS=-server
set CPPATH=./KnightProj/target/classes;./KnightProj/libs/*
set MAIN=com.backinfile.core.net.GameClient

java %PARAMS% -cp %CPPATH% %MAIN%
pause