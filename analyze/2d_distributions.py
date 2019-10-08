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

# sb.swarmplot(x="start_date_delta", y="distance", hue="start_date_isoweekday", data=X)

X.loc[:, 'average_heartrate'].mask(lambda x: pd.isna(x), other=0.0, inplace=True)
sb.pairplot(X, kind='scatter', diag_kind='hist' , hue='start_date_delta' ,height=2.5)    # heart rate contains nan for almost all rides
# sb.pairplot(X, height=2.5)    # heart rate contains nan for almost all rides


plt.show()
