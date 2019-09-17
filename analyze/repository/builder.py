from pandas import to_datetime
from pandas import Series

class FeatureBuilder(object):
    def __init__(self):
        pass;

    def convert_to_datetime(self, series: Series) -> Series:
        return to_datetime(series, utc=True)

    def infer_isoweekday(self, series: Series):
        series.apply(lambda dt: dt.isoweekday())
