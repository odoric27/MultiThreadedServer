#!/bin/bash

# find and list all src files
find . -name "*.java" > sources.txt

# create bin if !exists
if [ ! -d "bin" ]; then
	mkdir bin
fi

# compile all src files to respective bin dirs
javac -d bin @sources.txt

# start server
java -cp bin servers.MultiThreadedServer

echo "Program ended."
