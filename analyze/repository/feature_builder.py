import pandas as pd

class DatetimeBuilder(object):
    def __init__(self):
        pass;

    def convert_to_datetime(self, series: pd.Series) -> pd.Series:
        return pd.to_datetime(series, utc=True)

    def infer_isoweekday(self, series: pd.Series) -> pd.Series:
        return series.apply(lambda dt: dt.isoweekday(), convert_dtype=True)

    def diff_nplus1_and_n(self, sTimeseries: pd.Series) -> pd.Series:
        summand2 = sTimeseries.iloc[0:-1]
        summand1: pd.Series = pd.Series(data=sTimeseries.iloc[1:].to_numpy())   # create from numy to reindex starting from 0 cause subtraction uses index to find corrrespondng items to subtract
        return summand1 - summand2

