#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Desc:  the preprocessing file of Abalone dataset, to generate the training and testing dataset
Author: Kris Peng
Copyright (c) 2017 - Kris Peng <kris.dacpc@gmail.com>
'''

# As logistic regression is used for binary classification,
# we abandon those data with attribute 'I'
# and divide the remainders in to train/test set (80%/20%) randomly

import os
import random

if os.path.isfile('dataset/training.txt'):
    os.remove('dataset/training.txt')
if os.path.isfile('dataset/testing.txt'):
    os.remove('dataset/testing.txt')

train = open('dataset/training.txt', 'w')
test = open('dataset/testing.txt', 'w')

#set the target repository
targetUrl = "dataset/dataset.txt"
data = open(targetUrl, 'r')

for line in data:
    row = str(line).strip().split(",")
    if row[0] is 'M':
        if random.randint(0, 9) < 8:
            train.write(",".join(row) + '\n')
        else:
            test.write(",".join(row) + '\n')
    elif row[0] is 'F':
        if random.randint(0, 9) < 8:
            train.write(",".join(row) + '\n')
        else:
            test.write(",".join(row) + '\n')
