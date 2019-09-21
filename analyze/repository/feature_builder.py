import pandas as pd
import numpy.core as npc
import numpy as np
from numpy.core import timedelta64


class DatetimeBuilder(object):
    def __init__(self):
        pass;

    def infer_isoweekday(self, series: pd.Series) -> pd.Series:
        return series.apply(lambda dt: dt.isoweekday(), convert_dtype=True)

    def diff_nplus1_and_n(self, sTimeseries: pd.Series, pad: bool = True) -> pd.Series:
        # Sort by value
        ndTimeseries: npc.array = sTimeseries.sort_values().to_numpy()

        ndDiff = np.diff(ndTimeseries)

        if pad:
            return pd.Series(np.pad(ndDiff, (1,0), constant_values=pd.Timedelta('nat')))
            # pad NaT before to have


        return pd.Series(ndDiff)



