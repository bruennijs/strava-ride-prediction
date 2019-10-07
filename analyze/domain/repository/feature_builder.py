import pandas as pd
from numpy.core import timedelta64
import numpy.core as npc
import numpy as np


class DatetimeBuilder(object):
    def __init__(self):
        pass;

    def infer_timedeltas(self, array: np.array, pad: bool = True) -> np.array:

        ndDiff = np.diff(array)

        if pad:
            # pad NaT before to have
            return np.pad(ndDiff, (1,0), constant_values=pd.Timedelta('nat'))

        # Series contains len(series) -1
        return ndDiff

    def bin_timedelta(self, a: np.array) -> np.array:
        '''
        1. calculates max delta over all elements in days
        2. Categorizes with n categories , where n is equal max delta in days
        :param sSeries: Series of pandas Timedelta
        :return:
        '''

        nPercentile80 = np.percentile(a, q=[80, 20], interpolation='nearest')[0]
        bins = np.linspace(np.min(a), nPercentile80, num=10)

        return np.digitize(a, bins=bins)

    def round_timedelta_to_days(self, value: pd.Timedelta):
        return value.round("D")



