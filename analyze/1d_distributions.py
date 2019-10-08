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

labels = np.unique(y)

for i in range(nFeatures):

    X_feature: np.array = X.iloc[:, i].to_numpy()
    X_feature = np.nan_to_num(X_feature)
    freq_per_bin, bins = np.histogram(a=X_feature, bins=25)
    for label in labels:
         X_feature_by_label = X_feature[y == label]
         ax[i].hist(X_feature_by_label, histtype='bar', alpha=0.3, bins=bins, density=False) #https://matplotlib.org/3.1.1/api/_as_gen/matplotlib.pyplot.hist.html
    ax[i].set_xlabel(X.columns[i])

    # ax[i].hist2d(X_feature, y, bins=bins)
    # sb.distplot(dfActivities.iloc[:, i].to_numpy(), bins=25, norm_hist=True, ax=ax[i], axlabel=dfActivities.columns[i])
    # joinplot

# sb.jointplot("start_date_isoweekday", "distance", data=dfActivities_scaled)
# sb.catplot("start_date_isoweekday", "distance", data=X, kind='swarm')


# Filter only with heart rate
# sourcesWithHeartrate = list(filter(lambda activity: activity["has_heartrate"], activities))
# print ("{}".format(len(sourcesWithHeartrate)))


plt.show()
