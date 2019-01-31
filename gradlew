#!/usr/bin/env bash

    ##################################################################################  Gradle start up script for UN*X################################################################################

    # Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.DEFAULT_JVM_OPTS=""

    APP_NAME="Gradle"APP_BASE_NAME=`basename "$0"`

    # Use the maximum available, or set MAX_FD != -1 to use that value.MAX_FD="maximum"

    warn ( ) {echo "$*"}

    die ( ) {echoecho "$*"echoexit 1}

    # OS specific support (must be 'true' or 'false').cygwin=falsemsys=falsedarwin=falsecase "`uname`" inCYGWIN* )cygwin=true;;Darwin* )darwin=true;;MINGW* )msys=true;;esac

    # Attempt to set APP_HOME# Resolve links: $0 may be a linkPRG="$0"# Need this for relative symlinks.while [ -h "$PRG" ] ; dols=`ls -ld "$PRG"`link=`expr "$ls" : '.*-> \(.*\)$'`if expr "$link" : '/.*' > /dev/null; thenPRG="$link"elsePRG=`dirname "$PRG"`"/$link"fidoneSAVED="`pwd`"cd "`dirname \"$PRG\"`/" >/dev/nullAPP_HOME="`pwd -P`"cd "$SAVED" >/dev/null

    CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

    # Determine the Java command to use to start the JVM.if [ -n "$JAVA_HOME" ] ; thenif [ -x "$JAVA_HOME/jre/sh/java" ] ; then# IBM's JDK on AIX uses strange locations for the executablesJAVACMD="$JAVA_HOME/jre/sh/java"elseJAVACMD="$JAVA_HOME/bin/java"fiif [ ! -x "$JAVACMD" ] ; thendie "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

    Please set the JAVA_HOME variable in your environment to match thelocation of your Java installation."fielseJAVACMD="java"which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

    Please set the JAVA_HOME variable in your environment to match thelocation of your Java installation."fi

    # Increase the maximum file descriptors if we can.if [ "$cygwin" = "false" -a "$darwin" = "false" ] ; thenMAX_FD_LIMIT=`ulimit -H -n`if [ $? -eq 0 ] ; thenif [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; thenMAX_FD="$MAX_FD_LIMIT"fiulimit -n $MAX_FDif [ $? -ne 0 ] ; thenwarn "Could not set maximum file descriptor limit: $MAX_FD"fielsewarn "Could not query maximum file descriptor limit: $MAX_FD_LIMIT"fifi

    # For Darwin, add options to specify how the application appears in the dockif $darwin; thenGRADLE_OPTS="$GRADLE_OPTS \"-Xdock:name=$APP_NAME\" \"-Xdock:icon=$APP_HOME/media/gradle.icns\""fi

    # For Cygwin, switch paths to Windows format before running javaif $cygwin ; thenAPP_HOME=`cygpath --path --mixed "$APP_HOME"`CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`JAVACMD=`cygpath --unix "$JAVACMD"`

    # We build the pattern for arguments to be converted via cygpathROOTDIRSRAW=`find -L / -maxdepth 1 -mindepth 1 -type d 2>/dev/null`SEP=""for dir in $ROOTDIRSRAW ; doROOTDIRS="$ROOTDIRS$SEP$dir"SEP="|"doneOURCYGPATTERN="(^($ROOTDIRS))"# Add a user-defined pattern to the cygpath argumentsif [ "$GRADLE_CYGPATTERN" != "" ] ; thenOURCYGPATTERN="$OURCYGPATTERN|($GRADLE_CYGPATTERN)"fi# Now convert the arguments - kludge to limit ourselves to /bin/shi=0for arg in "$@" ; doCHECK=`echo "$arg"|egrep -c "$OURCYGPATTERN" -`CHECK2=`echo "$arg"|egrep -c "^-"`                                 ### Determine if an option

    if [ $CHECK -ne 0 ] && [ $CHECK2 -eq 0 ] ; then                    ### Added a conditioneval `echo args$i`=`cygpath --path --ignore --mixed "$arg"`elseeval `echo args$i`="\"$arg\""fii=$((i+1))donecase $i in(0) set -- ;;(1) set -- "$args0" ;;(2) set -- "$args0" "$args1" ;;(3) set -- "$args0" "$args1" "$args2" ;;(4) set -- "$args0" "$args1" "$args2" "$args3" ;;(5) set -- "$args0" "$args1" "$args2" "$args3" "$args4" ;;(6) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" ;;(7) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" ;;(8) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" ;;(9) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" "$args8" ;;esacfi

    # Split up the JVM_OPTS And GRADLE_OPTS values into an array, following the shell quoting and substitution rulesfunction splitJvmOpts() {JVM_OPTS=("$@")}eval splitJvmOpts $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTSJVM_OPTS[${#JVM_OPTS[*]}]="-Dorg.gradle.appname=$APP_BASE_NAME"

    exec "$JAVACMD" "${JVM_OPTS[@]}" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
