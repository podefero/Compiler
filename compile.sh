#!/bin/bash
mvn clean install -q -e -DskipTests

mvn -q -e exec:java -Dexec.args="$*"


