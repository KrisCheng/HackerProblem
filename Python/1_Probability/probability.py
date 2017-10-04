__author__ = 'Kris Peng'

import random

list1 = [0, 0, 0]
notChange = 0
change = 0
for i in range(1,1000000):
    temp1 = random.randint(0, 2)
    list1[temp1] = 1 #1代表有奖 0代表无奖
    temp2 = random.randint(0, 2) #选一个作为你初次选择的目标
    #从剩下两个中开一个没有奖的
    j = 0
    for j in range(0,2):
        if list1[j] == 0 and j != temp2:
            break
    if temp1 == temp2:
        notChange += 1
    else:
        change += 1
print("1000000次数值实验")
print("换的概率: " + str(change/1000000))
print("不换的概率: " + str(notChange/1000000))
