from pymongo import MongoClient

# Requires the PyMongo package.
# https://api.mongodb.com/python/current

client = MongoClient('mongodb://localhost:27017/')
result = client['jobspider_raw']['bytedance'].aggregate([
    {
        '$project': {
            'id': '$id', 
            'company': 'bytedance', 
            'name': '$title', 
            'categories': '$job_category.name', 
            'publishTime': {
                '$toDate': '$publish_time'
            }, 
            'workLocations': '$city_info.name', 
            'requirement': 1, 
            'description': 1, 
            'experience': None, 
            'degree': None, 
            'department': '$department_id', 
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