#! /bin/sh
#
# run.sh
# Copyright (C) 2019 weihao <weihao_dong@berkeley.edu>
#
# Distributed under terms of the MIT license.
#


java -cp "dependencies.jar:bin" org.junit.runner.JUnitCore InventoryAllocatorTest
