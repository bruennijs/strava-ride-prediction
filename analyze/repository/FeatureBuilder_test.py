import unittest

import pandas.testing as pdtesting
from dateutil.parser import parser
from pandas import Series

from repository.builder import FeatureBuilder

dateutilparser = parser()

class FeatureBuilderTest(unittest.TestCase):
    def test_something(self):
        expectedDateTime = dateutilparser.parse(timestr="2019-11-01T13:14:15Z")
        sExpected = Series(data=[expectedDateTime])

        sInput = Series(data=["2019-11-01T13:14:15Z"], dtype=str)

        # when
        sut = FeatureBuilder()
        sConverted = sut.convert_to_datetime(sInput)

        # then
        pdtesting.assert_series_equal(sExpected, sConverted)


if __name__ == '__main__':
    unittest.main()
