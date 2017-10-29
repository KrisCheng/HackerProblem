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
import pandas as pd
from pandas import DataFrame
import matplotlib.pyplot as plot
from math import exp

# create a file to record the data analysis result
if os.path.isfile('dataAnalysis.txt'):
    os.remove('dataAnalysis.txt')
f = open('dataAnalysis.txt', 'w')

#set the target repository
targetPath = "dataset/dataset.txt"
data = open(targetPath, 'r')

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
labelRow = 0
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

# 3. show the distribution of dataset
dataDistribute = pd.read_csv(targetPath, header = None)

# set the x label and y label
dataDistribute.columns = ['Sex', 'Length', 'Diameter', 'Height',
                   'Whole', 'Shucked',
                   'Viscera', 'Shell', 'Rings']

summary = dataDistribute.describe()
minRings = summary.iloc[3, 7]
maxRings = summary.iloc[7, 7]
nrows = len(dataDistribute.index)

for i in range(nrows):
    #plot rows of data
    dataRow = dataDistribute.iloc[i, 1:8]
    # set the color of line
    labelColor = (dataDistribute.iloc[i, 8] - minRings) / (maxRings - minRings)
    dataRow.plot(color = plot.cm.RdYlBu(labelColor), alpha = 0.4, linewidth = 0.7)

plot.xlabel("Attribute")
plot.ylabel(("Value"))
plot.show()

f.close()
data.close()
