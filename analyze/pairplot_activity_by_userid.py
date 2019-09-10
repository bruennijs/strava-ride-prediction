from elasticsearch import Elasticsearch

es = Elasticsearch()

searchBody  = "{ \"query\": { \"term\": {        \"athlete.id_as_string\": {\"value\": \"2416334\" } } } }"

params = {'_source': 'true' }

response = es.search(index="activity", body=searchBody, params=params)
print ("response={}".format(response))
