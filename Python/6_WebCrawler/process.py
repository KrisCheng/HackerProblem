# -*- coding:utf-8 -*-

import jieba
jieba.enable_parallel(4)
import jieba.posseg as posseg
import pymongo
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

client = pymongo.MongoClient()
temp_table = client['lagou']['temp']

f = open('result.txt', 'a')
filters = ['机器','经验','技术','用户','团队','工作','职责','岗位', '运用', '能力', '基础','计算机']

for prod in temp_table.find({}):
    posseg_list = posseg.cut(prod['jdDetail'])
    word_list = [w.word for w in posseg_list if 'n' in w.flag and len(w.word) > 1]
    for word in word_list:
        if not word in filters:
            print >> f, word
