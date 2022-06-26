from pymongo import MongoClient

# Requires the PyMongo package.
# https://api.mongodb.com/python/current

client = MongoClient('mongodb://localhost:27017/')
result = client['jobspider_raw']['alibaba'].aggregate([
    {
        '$project': {
            'id': 1, 
            'company': 'alibaba', 
            'name': 1, 
            'categories': {
                '$arrayElemAt': [
                    '$categories', 0
                ]
            }, 
            'publishTime': {
                '$toDate': '$publishTime'
            }, 
            'workLocations': {
                '$arrayElemAt': [
                    '$workLocations', 0
                ]
            }, 
            'requirement': 1, 
            'description': 1, 
            'experience': '$experience.from', 
            'degree': 1, 
            'department': 1
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