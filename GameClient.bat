
set PARAMS=-server
set CPPATH=./KnightProj/target/classes;./KnightProj/libs/*
set MAIN=com.backinfile.seam.CommandlineClientLauncher

java %PARAMS% -cp %CPPATH% %MAIN%
pause