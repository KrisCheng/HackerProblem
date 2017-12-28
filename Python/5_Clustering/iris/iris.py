#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Desc: The K_Means Clustering of Iris dataset, based on Python 3.6.1
DataSet: Iris dataset (Link: https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data)
Author: Kris Peng
Copyright (c) 2017 - Kris Peng <kris.dacpc@gmail.com>
'''
import matplotlib.pyplot as plt
from matplotlib import style
# just use numpy for list operation add and subtract
import numpy as np
style.use('ggplot')

class K_means(object):
    
    # k is the num of clusters, we consider survived or not, so k = 2
    # critical is the critical value for when to stop
    # max_iter is the maximum iteration number 
    def __init__(self, k = 3, critical = 0.1, max_iter = 10000):
        self.k = k
        self.critical = critical
        self.max_iter = max_iter
    
    def cluster(self, data):

        self.centroids = {}

        for i in range(self.k):
            # choose the initial 3 centroids randomly
            self.centroids[i] = data[i*50+18]
        
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
                self.centroids[classification] = np.average(self.classifier[classification], axis = 0)
            # if the centroids have get optimal
            optimized = True

            for i in self.centroids:
                original_centroid = prev_centroids[i]
                current_centroid = self.centroids[i]
                if sum((current_centroid - original_centroid)) > self.critical:
                    # if not optimal
                    optimized = False

            if optimized or i is self.max_iter:
                print("Number of Iris-setosa: %d" % len(self.classifier[0]))
                print("Number of Iris-versicolor: %d" % len(self.classifier[1]))
                print("Number of class Iris-virginica: %d" % len(self.classifier[2]))
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
data = []
with open("dataset/iris.txt", 'r') as file:  
    data = file.readlines() 
# ground truth
label = []

for i in range(len(data)):
    data[i] = data[i].split(',')
    if  "Iris-setosa" in data[i][-1]:
        label.append(0)
    elif "Iris-versicolor" in data[i][-1]:
        label.append(1)
    elif "Iris-virginica" in data[i][-1]:
        label.append(2)
    data[i].pop()
    data[i] = [float(j) for j in data[i]]

    # uer the setal length and width
    # data[i].pop()
    # data[i].pop()

    # uer the petal length and width
    del data[i][0]
    del data[i][1]

kmeans = K_means()
kmeans.cluster(data)

for centroid in kmeans.centroids:
    plt.scatter(kmeans.centroids[centroid][0], kmeans.centroids[centroid][1],
                marker="o", color="k")

colors = 10 * ["g","r","c","b","k"]
iris = []
for classify in kmeans.classifier:
    color = colors[classify]
    iris.append(plt.scatter(kmeans.classifier[classify][0], kmeans.classifier[classify][1], marker="x", color=color))
    for featureset in kmeans.classifier[classify]:
        # print(featureset)
        plt.scatter(featureset[0], featureset[1], marker="x", color=color)
        
correct = 0
for i in range(len(data)):
    predict = data[i]
    prediction = kmeans.classify(predict)
    if prediction == label[i]:
        correct += 1

print("Accuracy: %f %%" % (correct / len(data) * 100))

plt.title('Iris Clustering')
plt.xlabel(("Length (cm)"))
plt.ylabel(("Width (cm)"))
plt.legend((iris[0], iris[1], iris[2]),
           ('Iris-setosa', 'Iris-versicolor', 'Iris-virginica'),
           scatterpoints=1,
           loc='upper left',
           ncol=1,
           fontsize=8)
plt.show()