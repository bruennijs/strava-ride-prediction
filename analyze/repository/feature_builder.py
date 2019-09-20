import pandas as pd

class DatetimeBuilder(object):
    def __init__(self):
        pass;

    def infer_isoweekday(self, series: pd.Series) -> pd.Series:
        return series.apply(lambda dt: dt.isoweekday(), convert_dtype=True)

    def diff_nplus1_and_n(self, sTimeseries: pd.Series) -> pd.Series:
        # Sort by value
        sTimesSeries = sTimeseries.sort_values()

        summand2: pd.Series = sTimeseries.iloc[0:-1]
        summand1: pd.Series = sTimeseries.iloc[1:]

        # otherwise the index values in both Series correspond not to each other
        ndDiff = summand1.to_numpy() - summand2.to_numpy()
        return pd.Series(ndDiff)

