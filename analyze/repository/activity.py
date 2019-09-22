import datetime

import pandas as pd

from repository.feature_builder import DatetimeBuilder
from .client.elasticsearch import ActivityClient
from pandas.io.json import json_normalize


def inferWeekday(sDatetimes):
    sDatetimes.apply(lambda dt: datetime())


class ActivityRepository(object):
    def __init__(self) -> None:
        super().__init__()
        self.client = ActivityClient()
        self.datimeBuilder = DatetimeBuilder()

    def findAll(self, athleteId) -> pd.DataFrame:
        """
        Finds all activties from activity client and uses the following features
        in an pandas DataFrame:
        1.total_elevation_gain
        2.average_speed
        3.start_date
        4.distance (in meters)
        5.moving_time (in seconds)
        :param athleteId: id of the athlete to get activties for
        :return: DataFrame that can contain zeros for activties of given features
        """

        activtiesJson = self.client.findAll(athleteId)

        df = json_normalize(activtiesJson)

        dti: pd.DatetimeIndex =  pd.to_datetime(df["start_date"], utc=True)

        X = pd.DataFrame(data=df,
                               columns=["distance", "moving_time", "total_elevation_gain", "average_speed", "average_heartrate"],
                                  index=dti)

        X_sorted: pd.DataFrame = X.sort_index()

        X_sorted["start_date_delta"] = self.datimeBuilder.infer_timedeltas(X_sorted.index.to_numpy())
        X_sorted["start_isoweekday"] = self.datimeBuilder.infer_isoweekday(X_sorted.index.to_series())

        return X_sorted

