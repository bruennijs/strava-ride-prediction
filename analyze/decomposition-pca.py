from argparse import ArgumentParser

import seaborn as sb
import matplotlib.pyplot as plt

import pandas as pd
import numpy as np

from domain.activity import Activity

from domain.repository.activity import ActivityRepository




parser = ArgumentParser("pairplot_activity_by_userid")
parser.add_argument("--athlete_id", help="Unique strava athlete id to get activities for.", type=str)

args = parser.parse_args()

# get activities from ActivityRepository

if args.athlete_id is None:
    print("--athlete_id parameter is missing")
    exit(1)

print ("athlete_id={}".format(args.athlete_id))
repo = ActivityRepository()

dfActivities: pd.DataFrame = repo.findAll(args.athlete_id)

activity = Activity(dfActivities)
X, y = activity.load_activity()

sb.set()
# pair plot all features of all activities with heart rate
nSamples, nFeatures = X.shape
fig, ax = plt.subplots(nrows=nFeatures, ncols=1, squeeze=True, figsize=(5.0, 25.0))
# sb.pairplot(dfActivities, height=2.5)



plt.show()
