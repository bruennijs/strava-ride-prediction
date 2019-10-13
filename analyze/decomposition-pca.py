from argparse import ArgumentParser

import seaborn as sb
import matplotlib.pyplot as plt

import pandas as pd
import numpy as np
from sklearn.decomposition import PCA

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
X.loc[:, 'average_heartrate'].mask(lambda x: pd.isna(x), other=0.0, inplace=True)

sb.set()

pca = PCA(2)
X_decomposed = pca.fit_transform(X)

dfX_decomposed = pd.DataFrame(data=X_decomposed, index=X.index, columns=["A", "B"])

# pair plot all features of all activities with heart rate
dfX_decomposed_labeled = dfX_decomposed.assign(y=X.loc[:, 'start_date_delta'])
sb.pairplot(dfX_decomposed_labeled, hue='y', height=2.5)

plt.show()
