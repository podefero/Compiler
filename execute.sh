#!/bin/bash
arg="$1"
filename_without_extension="${arg%.*}"  # Remove the extension

./CS4380-002/build/main_prog "$arg"
./CS4380-002/build/main_prog "$filename_without_extension.bin"
