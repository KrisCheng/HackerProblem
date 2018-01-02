#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Desc: The K_Means Clustering of Titanic dataset, based on Python 3.6.1
DataSet: Titanic dataset (Link: biostat.mc.vanderbilt.edu/wiki/pub/Main/DataSets/titanic3.xls)
Author: Kris Peng
Copyright (c) 2017 - Kris Peng <kris.dacpc@gmail.com>
'''

# use pandas just for file read
import pandas as pd
# use numpy just for fill those data that is NaN(not a number)
import numpy as np
# use random just for generate initial centroids randomly
import random
# use sklern just for Standalsrdization, it's not necessary
from sklearn import preprocessing

def Get_Average(list):
    sum = 0
    for item in list:
        sum += item
    return sum / len(list)

class K_means(object):
    
    # k is the num of clusters, we consider survived or not, so k = 2
    # critical is the critical value for when to stop
    # max_iter is the maximum iteration number 
    def __init__(self, k = 2, critical = 0.001, max_iter = 1000):
        self.k = k
        self.critical = critical
        self.max_iter = max_iter
    
    def cluster(self, data):

        self.centroids = {}

        for i in range(self.k):
            # choose the initial 2 centroids randomly
            self.centroids[i] = data[i+random.randint(0,1000)]
        
        for i in range(self.max_iter):
            self.classifier = {}

            for j in range(len(self.centroids)):
                self.classifier[j] = []

            for val in data:
                distances = []
                for centroid in self.centroids:
                    distance = 0
                    for i in range(len(val)):
                        distance += ((val[i] - self.centroids[centroid][i])**2)
                    distances.append(distance)
                classification = distances.index(min(distances))
                self.classifier[classification].append(val)

            prev_centroids = dict(self.centroids)

            for classification in self.classifier:
                self.centroids[classification] = np.average(self.classifier[classification],axis = 0)
            # if the centroids have get optimal
            optimized = True

            for i in self.centroids:
                original_centroid = prev_centroids[i]
                current_centroid = self.centroids[i]
                if sum((current_centroid - original_centroid)) > self.critical:
                    # if not optimal
                    optimized = False

            if optimized or i is self.max_iter:
                print("Number of class one: %d" % len(self.classifier[0]))
                print("Number of class two: %d" % len(self.classifier[1]))
                break

    def classify(self, predict):
        distances = []
        for centroid in self.centroids:
            distance = 0
            for i in range(len(predict)):
                distance += ((predict[i] - self.centroids[centroid][i])**2)
            distances.append(distance)
        classification = distances.index(min(distances))
        return classification

# transfer the non-numerical data to numerical
def preprocess(data):
    columns = data.columns.values
    convert_dict = {}
    def convert_to_int(val):
        return convert_dict[val]
    for column in columns:
        identify = 0
        if(data[column].dtype != 'float64' and data[column].dtype != 'int64'):
            column_val = data[column].values.tolist()
            unique_val = set(column_val)
            for val in unique_val:
                convert_dict[val] = identify
                identify += 1
            data[column] = list(map(convert_to_int, column_val))
    return data

dataFile = pd.read_excel('dataset/titanic.xls')

# ground truth
y = np.array(dataFile['survived'])

# basic information of the raw dataset
# print(dataFile.columns.values)
# print(dataFile.info())

# drop the unnecessary columns
dataFile = dataFile.drop(['survived', 'name', 'ticket', 'body', 'home.dest'], 1)
dataFile = preprocess(dataFile)
data = np.array(dataFile)
# set those NaN value to 0
where_are_nan = np.isnan(data)  
where_are_inf = np.isinf(data)  
data[where_are_nan] = 0  
data[where_are_inf] = 0
kmeans = K_means()
kmeans.cluster(data)

correct = 0
accuracy = []

for i in range(len(data)):
    predict = data[i]
    prediction = kmeans.classify(predict)
    if prediction == y[i]:
        correct += 1
print("Accuracy without Standardization: %f %%" % (correct / len(data) * 100))
accuracy.append((correct / len(data) * 100))
correct = 0

# below are the code for standardilization (just for compare)
data = preprocessing.scale(data)
kmeans.cluster(data)
correct = 0
accuracy = []

for i in range(len(data)):
    predict = data[i]
    prediction = kmeans.classify(predict)
    if prediction == y[i]:
        correct += 1
accuracy.append((correct / len(data) * 100))
print("Accuracy with Standardization: %f %%" % (correct / len(data) * 100))
