#!/bin/bash  
#Command substitution allows us to take the output of a command 
#or program (what would normally be printed to the screen) and save it 
#as the value of a variable. To do this we place it within brackets, 
#preceded by a $ sign.
getClasspath() {
local first="yes";
local CLASSPATH=""
for i in $1 
do
    if [ $first = "yes" ]
    then
        CLASSPATH=$CLASSPATH$i
    else    
        CLASSPATH=$CLASSPATH:$i
    fi
    first="no";
done
echo $CLASSPATH
}
class="net.sf.arbocdi.Main"
classpath=" -cp "$( getClasspath "build/libs/*.jar" )
jmx_port="10100"
jmx="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$jmx_port -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=true -Dcom.sun.management.jmxremote.password.file=jmx.password  -Dcom.sun.management.jmxremote.access.file=jmx.access -Dcom.sun.management.jmxremote.ssl=false"
opts="-Djava.net.preferIPv4Stack=true -Xmx1024m"
cmd="java $opts $jmx $classpath $class"
nohup $cmd > output.txt 2>&1 &
#$! gives pis of the last executed command
echo $! > pid.txt
