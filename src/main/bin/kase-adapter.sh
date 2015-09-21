#!/bin/bash

die() {
    echo -e $1
    exit 1
}

# Change working directory
BASEDIR=$(dirname $0)
cd $BASEDIR/.. > /dev/null

ADAPTER_HOME=$(pwd)

# Define java runtime
[ -n "$JAVA_HOME" ] || 
    die "Define JAVA_HOME environment variable."    

JAVA="${JAVA_HOME}/bin/java"

OPTS="-Xms64m -Xmx256m"

# Define adapter classpath
CLASSPATH="$ADAPTER_HOME/conf"
CLASSPATH="$CLASSPATH:$(echo $ADAPTER_HOME/lib/*.jar | tr ' ' ':')"

MAINCLASS="ru.bis.integration.kase.adapter.App"

usage="Usage: $0 <option> \n
        \t where options include: \n
        \t\t start \t\t     - start adapter \n
        \t\t stop   \t\t    - stop adapter \n
        \t\t status \t      - show adapter status"

# Parse adapter parameters
[ $# -eq 1 ] || die "$usage"
 
case $1 in
    start)
        ARGS="start";;
    stop)
        ARGS="stop";;
    status)
        ARGS="status";;
    *)
        die "$usage";;
esac

# Execute java application
exec "$JAVA" $OPTS -cp "$CLASSPATH" $MAINCLASS $ARGS