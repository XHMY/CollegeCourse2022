from pymongo import MongoClient

# Requires the PyMongo package.
# https://api.mongodb.com/python/current

client = MongoClient('mongodb://localhost:27017/')
result = client['jobspider_raw']['baidu'].aggregate([
    {
        '$project': {
            'id': '$jobId', 
            'company': 'baidu', 
            'name': 1, 
            'categories': '$postType', 
            'publishTime': {
                '$toDate': '$publishDate'
            }, 
            'workLocations': '$workPlace', 
            'requirement': '$serviceCondition', 
            'description': '$workContent', 
            'experience': {
                '$convert': {
                    'input': '$workYears', 
                    'to': 'int', 
                    'onError': None
                }
            }, 
            'degree': '$education', 
            'department': '$orgName', 
            'recruitNum': {
                '$cond': {
                    'if': {
                        '$eq': [
                            '$recruitNum', '若干'
                        ]
                    }, 
                    'then': -1, 
                    'else': {
                        '$toInt': '$recruitNum'
                    }
                }
            }
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