from argparse import ArgumentParser
import seaborn as sb
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
activities = repo.findAll(args.athlete_id)
print(activities)

# pair plot all features of all activities with heart rate
sb.set()
sb.pairplot(activities)

# Filter only with heart rate
# sourcesWithHeartrate = list(filter(lambda activity: activity["has_heartrate"], activities))
# print ("{}".format(len(sourcesWithHeartrate)))


