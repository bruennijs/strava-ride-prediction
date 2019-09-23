import unittest

import pandas.testing as pdtesting
import pandas as pd
import numpy as np

from repository.feature_builder import DatetimeBuilder


class FeatureBuilderTest(unittest.TestCase):
    def test_convert_to_datetime(self):
        # expectedDateTime = isoparser()\
        #     .isoparse("2019-11-01T13:14:15Z")\
        #     .astimezone(tz=UTC)
        # expectedDateTime2 = isoparser() \
        #     .isoparse("2019-11-02T23:59:59Z") \
        #     .astimezone(tz=UTC)

        sExpected = pd.Series(data=[pd.Timestamp("2019-11-01T13:14:15Z"), pd.Timestamp("2019-11-02T23:59:59Z")])

        sInput = pd.Series(data=["2019-11-01T13:14:15Z", "2019-11-02T23:59:59Z"], dtype=str)

        # when
        sut = DatetimeBuilder()
        sConverted = pd.to_datetime(sInput)

        # then
        pdtesting.assert_series_equal(sExpected, sConverted)

    def test_infer_isoweekday(self):
        # given
        tuesday = pd.Timestamp("2019-09-17T13:14:15Z")

        monday = pd.Timestamp("2019-09-16T23:59:59Z")

        sInput = pd.Series(data=[monday, tuesday])

        sut = DatetimeBuilder()
        sIsoweekday = sut.infer_isoweekday(sInput)

        pdtesting.assert_series_equal(pd.Series(data=[1, 2], dtype=int), sIsoweekday)

    def test_diff_timeseries_nplus1_and_n(self):
        npExpected = pd.to_timedelta(['nat', '-1D'])

        npTimeseries = np.array([pd.Timestamp("2019-01-02T11:11:11Z"), pd.Timestamp("2019-01-01T11:11:11Z")])

        # when
        sut = DatetimeBuilder()

        # then
        npTimedelta = sut.infer_timedeltas(npTimeseries)

        pdtesting.assert_series_equal(pd.Series(data=npExpected), pd.Series(data=npTimedelta))

    def test_round_up_timedelta(self):

        sExpected = pd.Series(data=[pd.to_timedelta('2D'), pd.to_timedelta('1D')])

        # when
        sut = DatetimeBuilder()

        # then
        sRounded = pd.Series(data=[pd.to_timedelta('1D14H'), pd.to_timedelta('1D2H')]).apply(lambda val: sut.round_timedelta_to_days(val))

        pdtesting.assert_series_equal(sExpected, sRounded)
        pdtesting.assert_series_equal(pd.Series(data=[2, 1]), sRounded.apply(lambda td: td.days))

    def test_sort_by_datetimeindex(self):

        sIndex: pd.DatetimeIndex =  pd.to_datetime(['2019-01-02T00:00:00Z', '2019-01-01T00:00:00Z'], utc=True)

        sUnsorted = pd.Series(data=np.linspace(1, 2, num=2), index=sIndex)
        sSorted = sUnsorted.sort_index()
        print(sSorted)

if __name__ == '__main__':
    unittest.main()
