from unittest import TestCase
import pandas as pd
from pandas.core.resample import Resampler


class TestActivityRepository(TestCase):

    def test_timeseries_index_window_with_highest_frequency(self):
        series = pd.Series(range(6), index=pd.to_datetime(
            ['2017-01-01T00:00:00Z',
             '2017-02-02T00:30:00Z',
             '2017-02-03T00:31:00Z',
             '2017-02-17T01:00:00Z',
             '2017-03-10T03:00:00Z',
             '2017-03-12T03:05:00Z']))

        # sGroups = series.rolling(window=pd.Timedelta(30, unit='D'), min_periods=2)
        resampler: Resampler = series.resample(rule=pd.Timedelta(30, unit='D'))

        print(resampler)

    def test_findAll(self):
        self.fail()
