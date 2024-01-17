#!/bin/bash
echo "Building..
"
mvn clean install -q -e -DskipTests
echo "Done! to run program: ./run.sh [options]"


