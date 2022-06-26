from pymongo import MongoClient

# Requires the PyMongo package.
# https://api.mongodb.com/python/current

client = MongoClient('mongodb://localhost:27017/')
result = client['jobspider_raw']['tencent'].aggregate([
    {
        '$project': {
            'id': '$Id', 
            'company': 'tencent', 
            'name': '$RecruitPostName', 
            'categories': '$CategoryName', 
            'publishTime': {
                '$dateFromString': {
                    'dateString': '$LastUpdateTime', 
                    'format': '%Y年%m月%d日'
                }
            }, 
            'workLocations': '$LocationName', 
            'requirement': None, 
            'description': '$Responsibility', 
            'experience': None, 
            'degree': None, 
            'department': '$BGName', 
            'recruitNum': None
        }
    }, {
        '$merge': {
            'into': {
                'db': 'jobspider_DW', 
                'coll': 'dwd'
            }
        }
    }
])