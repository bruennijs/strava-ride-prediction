from elasticsearch import Elasticsearch
from argparse import ArgumentParser

# parse arguments
parser = ArgumentParser("pairplot_activity_by_userid")
parser.add_argument("--athlete_id", help="Unique strava athlete id to get activities for.", type=str)
args = parser.parse_args()

es = Elasticsearch()

print ("athlete_id={}".format(args.athlete_id))
searchBody = "{ \"query\": { \"term\": {        \"athlete.id_as_string\": {\"value\": \"{}\" } } } }".format(args.athlete_id)

params = {'_source': 'true' }

response = es.search(index="activity", body=searchBody, params=params)
print ("response={}".format(response))
