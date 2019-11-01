#! /bin/sh
#
# compile.sh
# Copyright (C) 2019 weihao <weihao_dong@berkeley.edu>
#
# Distributed under terms of the MIT license.
#


rm -rf ./bin
mkdir bin
find . -name "*.java" -print | xargs javac -d bin -cp ./dependencies.jar
