#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Desc: see readme
Author: Kris Peng
Copyright (c) 2017 - Kris Peng <kris.dacpc@gmail.com>
'''

import random
import time

def Exchange(num):
    matrix = []
    for i in range(num): 
        matrix.append([]) 
        for j in range(num): 
                matrix[i].append(0) 
    for i in range(num):
        matrix[i][i] = 1
    # print(matrix)

    currentCount = 10000
    bestCount = 10000
    changeList = []
    for i in range(num):
        changeList.append(i) 
    count = 0
    while True:
        random.shuffle(changeList)
        count = count + 1
        # 根据排序两两配对
        for i in range(int(num/2)):
            tempList = []
            for j in range(num):
                if(matrix[changeList[i]][j] == 1 or matrix[changeList[i+int(len(changeList)/2)]][j] == 1):
                    tempList.append(1)
                else:
                    tempList.append(0)
            matrix[changeList[i]] = tempList
            matrix[changeList[i+int(len(changeList)/2)]] = tempList
            print("Change Log: %x , %x" % (changeList[i], changeList[i+int(len(changeList)/2)]) )

        # 判断是否满足终止条件
        isFinal = True
        print("Currten Stage:")
        for i in range(num):
            print(matrix[i])
            if(matrix[i].count(1) != num):
                isFinal = False
        if(isFinal == False):
            print("Current Num of iteration: %x" % count)
            continue
        print("Final Num of iteration: %x" % count)
        break

Exchange(9)

