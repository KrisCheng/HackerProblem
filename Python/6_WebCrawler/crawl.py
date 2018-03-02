# -*- coding:utf-8 -*-

import sys
reload(sys)
sys.setdefaultencoding('utf-8')
import requests
import random
import json
import pymongo
import scrapy
import copy

client = pymongo.MongoClient()

HEADERS = {
    'Upgrade-Insecure-Requests':'1',
    'Host':"www.lagou.com",
    'User-agent':"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36",
}

def GetList(url, data_dict):

    global HEADERS 
    payload = {
        'px':'default',
        'gx':'全职',
        'gj':'3-5年',
        'jd':'B轮,C轮,D轮及以上,上市公司',
        'city':'全国',
    }
    r = requests.post(url, params=payload, data=data_dict, headers=HEADERS, timeout=5)
    with open('test.html', 'w') as html_file:
        print >> html_file, r.text.encode('utf-8')
    return r.text

'''
    keys of job dict:
    [u'companySize', u'appShow', u'pcShow', u'positionName', u'education', u'financeStage', u'city', u'companyLogo', u'score', u'district', u'companyId', u'explain', u'industryField', u'companyLabelList', u'positionLables', u'firstType', u'adWord', u'formatCreateTime', u'salary', u'workYear', u'lastLogin', u'jobNature', u'deliver', u'positionAdvantage', u'imState', u'companyFullName', u'createTime', u'positionId', u'companyShortName', u'approve', u'businessZones', u'plus', u'secondType', u'gradeDescription', u'publisherId', u'promotionScoreExplain']
'''
def GetDetail(job):

    global HEADERS
    prod = copy.copy(job) 
    print ' '.join([job['positionName'],job['city'],job['salary'],job['companyFullName']])
    prod['detailUrl'] = 'https://www.lagou.com/jobs/%s.html' % job['positionId']
    print prod['detailUrl']
    r = requests.get(prod['detailUrl'], headers=HEADERS, timeout=5)
    hxs = scrapy.Selector(text=r.text)
    text_list = hxs.xpath('//dd[@class="job_bt"]/div/p/text()|//dd[@class="job_bt"]/div/p/span/text()')
    prod['jdDetail'] = ''.join([text.extract() for text in text_list])
    print prod['jdDetail']
    global client
    try:
        client['lagou']['temp'].save(prod)
    except Exception as e:
        print e
    #import pdb;pdb.set_trace() 
 
def work():

    start_url = 'https://www.lagou.com/jobs/positionAjax.json'
    for i in range(1, 31):
        data_dict = {
            'first':'true',
            'kd':'算法',
            'pn':str(i),
        }
        json_str = GetList(start_url, data_dict)
        content_dict = json.loads(json_str)
        for job in content_dict['content']['positionResult']['result']:
            GetDetail(job)
        #import pdb;pdb.set_trace()

if __name__ == "__main__":

    work()
