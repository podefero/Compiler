#!/bin/bash
mvn clean install -q -e -DskipTests
./buildVM.sh

