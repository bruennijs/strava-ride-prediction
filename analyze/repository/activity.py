import datetime

import pandas as pd
import numpy as np

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

        dFeatures = {'distance': df['distance'].to_numpy(),
                     'moving_time': df['moving_time'].to_numpy(),
                     'total_elevation_gain': df['total_elevation_gain'].to_numpy(),
                     'average_speed': df['average_speed'].to_numpy(),
                     'average_heartrate': df['average_heartrate'].to_numpy()}
        X = pd.DataFrame(data=dFeatures, index=dti)

        X_sorted: pd.DataFrame = X.sort_index()
        X_sorted_index: pd.Series = X_sorted.index.to_series()

        # infer days between DatetimeIndex keys
        npTimedeltas: np.array = self.datimeBuilder.infer_timedeltas(X_sorted.index.to_numpy())
        sTimedeltas = pd.Series(data=npTimedeltas, index=dti)
        sTimedeltas = sTimedeltas\
            .apply(lambda timedelta: self.datimeBuilder.round_timedelta_to_days(timedelta))\
            .apply(lambda timedelta: timedelta.days)
        # lDays = list(map(lambda timedelta: {
        #     self.datimeBuilder.round_timedelta_to_days(timedelta).days
        # }, npTimedeltas))
        X_sorted = X_sorted.assign(start_date_delta=sTimedeltas)

        # infer weekdays from datetimeindex
        sIsoWeekDays = X_sorted_index.apply(lambda dt: dt.isoweekday(), convert_dtype=True)
        X_sorted = X_sorted.assign(start_date_isoweekday=sIsoWeekDays)


        return X_sorted

