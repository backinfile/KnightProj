
set PARAMS=-server
set CPPATH=./KnightProj/target/classes;./KnightProj/libs/*;C:/Users/Administrator/.m2/repository/*
set MAIN=com.backinfile.knightProj.seam.CommandlineClientLauncher

java %PARAMS% -cp %CPPATH% %MAIN%
pause