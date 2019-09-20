import unittest

import pandas.testing as pdtesting
import pandas as pd

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
        sExpected = pd.Series(data=[pd.Timedelta('1d')])

        sTimeseries = pd.Series(data=[pd.Timestamp("2019-01-02T11:11:11Z"), pd.Timestamp("2019-01-01T11:11:11Z")])

        # when
        sut = DatetimeBuilder()

        # then
        sTimedelta = sut.diff_nplus1_and_n(sTimeseries.sort_values())

        pdtesting.assert_series_equal(sExpected, sTimedelta)


if __name__ == '__main__':
    unittest.main()
