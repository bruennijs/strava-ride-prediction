import unittest

import pandas.testing as pdtesting
from dateutil.parser.isoparser import isoparser
from pandas import Series
from pytz import UTC

from repository.builder import FeatureBuilder


class FeatureBuilderTest(unittest.TestCase):
    def test_convert_to_datetime(self):
        expectedDateTime = isoparser()\
            .isoparse("2019-11-01T13:14:15Z")\
            .astimezone(tz=UTC)
        expectedDateTime2 = isoparser() \
            .isoparse("2019-11-02T23:59:59Z") \
            .astimezone(tz=UTC)

        sExpected = Series(data=[expectedDateTime, expectedDateTime2])

        sInput = Series(data=["2019-11-01T13:14:15Z", "2019-11-02T23:59:59Z"], dtype=str)

        # when
        sut = FeatureBuilder()
        sConverted = sut.convert_to_datetime(sInput)

        # then
        pdtesting.assert_series_equal(sExpected, sConverted)

    def test_infer_isoweekday(self):
        # given
        tuesday = isoparser() \
            .isoparse("2019-09-17T13:14:15Z") \
            .astimezone(tz=UTC)

        monday = isoparser() \
            .isoparse("2019-09-16T23:59:59Z") \
            .astimezone(tz=UTC)

        sInput = Series(data=[monday, tuesday])

        sut = FeatureBuilder()
        sIsoweekday = sut.infer_isoweekday(sInput)

        pdtesting.assert_series_equal(Series(data=[1, 2], dtype=int), sIsoweekday)


if __name__ == '__main__':
    unittest.main()

