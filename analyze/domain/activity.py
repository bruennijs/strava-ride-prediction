import pandas as pd

from sklearn.preprocessing import StandardScaler, MinMaxScaler

class Activity:

    def __init__(self, a: pd.DataFrame):
        self.a = a

    def load_activity(self) -> tuple:
        samples = self.__scale_features(self.a)
        samples = self.__subset_samples(samples)
        y = self.__create_label_weekday(samples)
        return (samples, y)

    def __scale_features(self, a: pd.DataFrame) -> pd.DataFrame:
        # scale with std deviation
        scaler = MinMaxScaler(copy=True, feature_range=(0, 1))
        # scaler = StandardScaler()
        # df_activties_without_categorial_features = a.select_dtypes(exclude='category')

        df_to_scale = a.loc[:,
                     ['distance', 'moving_time', 'total_elevation_gain', 'average_speed', 'average_heartrate']]

        activities_scaled = scaler.fit_transform(df_to_scale)
        # build DF from scaled features
        dfActivtiesWithoutCategorialFeatures_scaled = pd.DataFrame(data=activities_scaled, index=a.index,
                                                                   columns=df_to_scale.columns)

        # add categorial features again
        return pd.concat([dfActivtiesWithoutCategorialFeatures_scaled, a.loc[:,
                                                                       ['start_date_delta', 'start_date_isoweekday']]], axis=1)

    def __create_label_weekday(self, a: pd.DataFrame) -> pd.Series:
        return a['start_date_isoweekday']

    def __subset_samples(self, a: pd.DataFrame) -> pd.DataFrame:
        return a.loc[lambda df: df['start_date_delta'] < 10]

