from elasticsearch import Elasticsearch
from argparse import ArgumentParser
import json

# parse arguments
parser = ArgumentParser("pairplot_activity_by_userid")
parser.add_argument("--athlete_id", help="Unique strava athlete id to get activities for.", type=str)
args = parser.parse_args()

if args.athlete_id is None:
    print("--athlete_id parameter is missing")
    exit(1)

# get activities from ActivityRepository

es = Elasticsearch()

print ("athlete_id={}".format(args.athlete_id))

searchBody = {
    "query": {
        "term": {
            "athlete.id_as_string": {
                "value": args.athlete_id
            }
        }
    }
}

params = {'_source': 'true', 'size': 1000 }

activityHits = es.search(index="activity", body=searchBody, params=params)

sources = list(map(lambda activity: activity["_source"], activityHits["hits"]["hits"]))
print ("{}".format(len(sources)))

# Filter only with heart rate
sourcesWithHeartrate = list(filter(lambda activity: activity["has_heartrate"], sources))
print ("{}".format(len(sourcesWithHeartrate)))
