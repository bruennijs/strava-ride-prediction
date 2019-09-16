from elasticsearch import Elasticsearch
from argparse import ArgumentParser
import json

# parse arguments
from repository.client.elasticsearch import ActivityClient

parser = ArgumentParser("pairplot_activity_by_userid")
parser.add_argument("--athlete_id", help="Unique strava athlete id to get activities for.", type=str)
args = parser.parse_args()

if args.athlete_id is None:
    print("--athlete_id parameter is missing")
    exit(1)

# get activities from ActivityRepository

es = Elasticsearch()

print ("athlete_id={}".format(args.athlete_id))

client = ActivityClient()
activities = client.findAll(args.athlete_id)

# Filter only with heart rate
sourcesWithHeartrate = list(filter(lambda activity: activity["has_heartrate"], activities))
print ("{}".format(len(sourcesWithHeartrate)))


