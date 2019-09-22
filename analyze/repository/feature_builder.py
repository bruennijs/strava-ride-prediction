import pandas as pd
from numpy.core import timedelta64
from pandas.core.api import Timedelta
import numpy.core as npc
import numpy as np


class DatetimeBuilder(object):
    def __init__(self):
        pass;

    def infer_isoweekday(self, series) -> pd.Series:
        return series.apply(lambda dt: dt.isoweekday(), convert_dtype=True)

    def infer_timedeltas(self, array: np.array, pad: bool = True) -> pd.Series:

        ndDiff = np.diff(array)

        if pad:
            # pad NaT before to have
            return np.pad(ndDiff, (1,0), constant_values=pd.Timedelta('nat'))

        # Series contains len(series) -1
        return ndDiff

    def categorize_timedelta(self, sSeries: pd.Series) -> pd.Series:
        '''
        1. calculates max delta over all elements in days
        2. Categorizes with n categories , where n is equal max delta in days
        :param sSeries: Series of pandas Timedelta
        :return:
        '''
        series_min = sSeries.min(skipna=True)
        series_max: timedelta64 = sSeries.max(skipna=True)

        np.linspace(series_max)

        np.digitize()
        return



