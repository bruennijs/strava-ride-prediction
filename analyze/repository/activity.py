from pandas import DataFrame

from .client.elasticsearch import ActivityClient
from pandas.io.json import json_normalize

class ActivityRepository(object):
    def __init__(self) -> None:
        super().__init__()
        self.client = ActivityClient()

    def findAll(self, athleteId):
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

        # df = json_normalize(activtiesJson, ["distance", "total_elevation_gain", "average_speed", "start_date", "moving_time"])
        df = json_normalize(activtiesJson)

        dfSelected = DataFrame(data=df, columns=["distance", "moving_time", "total_elevation_gain", "average_speed", "start_date"])

        return dfSelected

