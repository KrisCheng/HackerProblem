#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Desc: the analysis file of the project, based on Python 3.6.1
DataSet: Abalone Data Set from UCI
Link: http://archive.ics.uci.edu/ml/datasets/Abalone
Author: Kris Peng
Copyright (c) 2017 - Kris Peng <kris.dacpc@gmail.com>
'''

import numpy as np
import matplotlib.pyplot as plt

# load the dataset
trainingtPath = "dataset/training.txt"
testingPath = "dataset/testing.txt"
trainingData = open(trainingtPath, 'r')
testingData = open(testingPath, 'r')

trainingXList = []
trainingYList = []

testingXList = []
testingYList = []

for line in trainingData:
    row = str(line).strip().split(",")
    trainingXList.append(row[1:len(row)])
    # 1 for male 0 for female
    if row[0] is 'M':
        trainingYList.append(1)
    else:
        trainingYList.append(0)

for line in testingData:
    row = str(line).strip().split(",")
    testingXList.append(row[1:len(row)])
    if row[0] is 'M':
        testingYList.append(1)
    else:
        testingYList.append(0)

trainingXList = np.array(trainingXList, dtype=np.float64)
trainingYList = np.array(trainingYList, dtype=np.float64)
trainingYList = trainingYList.reshape(trainingYList.shape[0], 1)

testingXList = np.array(testingXList, dtype=np.float64)
testingYList = np.array(testingYList, dtype=np.float64)
testingYList = testingYList.reshape(testingYList.shape[0], 1)

# helper function
def sigmoid(z):
    s = 1 / (1 + np.exp(-z))
    return s

# init the weight and bias
def init(dim):
    w = np.zeros([dim, 1])
    b = 0
    return w, b

def propagate(w, b, X, Y):
    m = X.shape[0]
    A = sigmoid(np.dot(X, w) + b)

    # cost funtion
    cost = 0
    for i in range(len(A)):
        cost -= np.log(A[i][0]) * Y[i][0] + np.log(1 - A[i][0]) * (1 - Y[i][0])

    # update the parameters
    dw = (1 / m) * np.dot(X.T, (A - Y))
    db = (1 / m) * np.sum((A - Y), 1)
    grads = {"dw": dw,
             "db": db}

    return grads, cost

def optimize(w, b, X, Y, num_iter, learning_rate):
    costs = []
    axis = []
    for i in range(num_iter):
        grads, cost = propagate(w, b, X, Y)
        dw = grads["dw"]
        db = grads["db"]

        w = w - learning_rate * dw
        b = b - learning_rate * db

        if i % 5 == 0:
            # Print the cost every 100 training examples
            costs.append(cost)
            axis.append(i)
            print("Cost after iteration {0}: {1}".format(i, cost))

    plt.plot(axis, costs, linewidth=1.5, linestyle="-", label="cost")
    plt.xlabel('epochs')
    plt.ylabel('gradient')

    plt.show()

    params = {"w": w,
              "b": b}

    grads = {"dw": dw,
             "db": db}

    return params, grads, costs

def predict(w, b, X):
    m = X.shape[0]
    Y_prediction = np.zeros((m, 1))

    A = sigmoid(np.dot(X, w) + b)

    for i in range(A.shape[0]):
        # you can set the threshold here
        if A[i][0] > 0.5:
            Y_prediction[i] = 1
        else:
            Y_prediction[i] = 0
    return Y_prediction

def model(X_train, Y_train, X_test, Y_test, num_iter, learning_rate):
    w, b = init(X_train.shape[1])
    parameters, grads, costs = optimize(w, b, X_train, Y_train, num_iter, learning_rate)

    w = parameters["w"]
    b = parameters["b"]

    Y_prediction_test = predict(w, b, X_test)
    Y_prediction_train = predict(w, b, X_train)

    print("train accuracy: {} %".format(float(len(Y_prediction_train) - sum(abs(Y_prediction_train - Y_train)))/len(Y_prediction_train) * 100))
    print("test accuracy: {} %".format(float(len(Y_prediction_test) - sum(abs(Y_prediction_test - Y_test)))/len(Y_prediction_test) * 100))

    d = {"costs": costs,
         "Y_prediction_test": Y_prediction_test,
         "Y_prediction_train": Y_prediction_train,
         "w": w,
         "b": b,
         "learning_rate": learning_rate,
         "num_iterations": num_iter}

    return d

# you can update the epochs and learning rate as you wish
d = model(trainingXList, trainingYList, testingXList, testingYList, 40, 0.06)

