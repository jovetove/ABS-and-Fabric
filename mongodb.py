# -*- coding:utf-8 -*- 
'''
Created on 2020年1月30日
@Group: waditu.com
@author: JM
'''
import pandas as pd
import tushare as ts
from pymongo import MongoClient

client = MongoClient(host='localhost',
                     port=27017,
                      username='root',
                      password='mima123',
                      authSource='admin',
                      authMechanism='SCRAM-SHA-1')

def readfrom_mongo(exchange=None):
    db = client.demos
    collection = db.stock_basic
    rows = collection.find({})
    df = pd.DataFrame([basic for basic in rows])
    return df


def insert_mongo(df):
    db = client['demos']
    collection = db['stock_basic']
    print(df)
    collection.insert_many(df.to_dict('records'))



def get_basic():
    pro = ts.pro_api()
    df = pro.daily(trade_date='20200123')
    insert_mongo(df)


if __name__ == '__main__':
    get_basic()