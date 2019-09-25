from argparse import ArgumentParser

import seaborn as sb
import matplotlib.pyplot as plt
import pandas as pd

from sklearn.preprocessing import StandardScaler

from repository.activity import ActivityRepository

parser = ArgumentParser("pairplot_activity_by_userid")
parser.add_argument("--athlete_id", help="Unique strava athlete id to get activities for.", type=str)
args = parser.parse_args()

if args.athlete_id is None:
    print("--athlete_id parameter is missing")
    exit(1)

# get activities from ActivityRepository

print ("athlete_id={}".format(args.athlete_id))

repo = ActivityRepository()
dfActivities: pd.DataFrame = repo.findAll(args.athlete_id)

# scale with std deviation
scaler = StandardScaler()
activities_scaled = scaler.fit_transform(dfActivities.to_numpy())
dfActivities = dfActivities.from_records(data=activities_scaled, index=dfActivities.index, columns=dfActivities.columns)
print(dfActivities)

# pair plot all features of all activities with heart rate
sb.set()
sb.pairplot(dfActivities, height=2.5)
plt.show()

# Filter only with heart rate
# sourcesWithHeartrate = list(filter(lambda activity: activity["has_heartrate"], activities))
# print ("{}".format(len(sourcesWithHeartrate)))


