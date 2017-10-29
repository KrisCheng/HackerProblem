#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Desc: the analysis file of the project, based on Python 3.6.1
DataSet: Abalone Data Set from UCI
Link: http://archive.ics.uci.edu/ml/datasets/Abalone
Author: Kris Peng
Copyright (c) 2017 - Kris Peng <kris.dacpc@gmail.com>
'''

import os
import numpy as np

# create a file to record the data analysis result
if os.path.isfile('dataAnalysis.txt'):
    os.remove('dataAnalysis.txt')
f = open('dataAnalysis.txt', 'w')

#set the target repository
targetUrl = "dataset/dataset.txt"
data = open(targetUrl, 'r')

xList = []
yList = []

# 1.get the size of the dataset
for line in data:
    row = str(line).strip().split(",")
    xList.append(row)

f.write("The Size of the Dataset:" + '\n')
f.write("Number of Rows of Data = " + str(len(xList)) + '\n')
f.write("Number of Columns of Data = " + str(len(xList[0])) + '\n')
nrow = len(xList)
ncol = len(xList[0])

# 2.figure out the distribution and categories
col = 3
colData = []
for row in xList:
    colData.append(float(row[col]))
colArray = np.array(colData)
colMean = np.mean(colArray)
colsd = np.std(colArray)
f.write("\nThe Distribution of the Dataset:" + '\n')
f.write("Mean = " + '\t' + str(colMean) + '\n' + "Standard Deviation = " + str(colsd) + '\n')
labels = []
labelRow = 8
for row in xList:
    labels.append(row[labelRow])
numLabel = np.unique(labels)
labelCount = len(numLabel)
countNumber = {}
for i in numLabel:
    countNumber.update({i: 0})
for row in xList:
    countNumber[row[labelRow]] += 1
f.write("The Type and Number of Labels in the Dataset:" + '\n')
for i in countNumber:
    f.write("{0} = {1}".format(i, countNumber[i]) + '\n')

f.close()
data.close()
