import pandas as pd

from sklearn.preprocessing import StandardScaler, MinMaxScaler

class Activity:

    def __init__(self, a: pd.DataFrame):
        self.a = a

    def load_activity(self) -> tuple:
        features_scaled = self.__scale_features(self.a)
        y = self.__create_label_weekday(self.a)
        return (features_scaled, y)

    def __scale_features(self, a: pd.DataFrame) -> pd.DataFrame:
        # scale with std deviation
        scaler = MinMaxScaler(copy=True, feature_range=(0, 1))
        # scaler = StandardScaler()
        dfActivtiesWithoutCategorialFeatures = a.select_dtypes(exclude='category')

        activities_scaled = scaler.fit_transform(dfActivtiesWithoutCategorialFeatures)
        # build DF from scaled features
        dfActivtiesWithoutCategorialFeatures_scaled = pd.DataFrame(data=activities_scaled, index=a.index,
                                                                   columns=dfActivtiesWithoutCategorialFeatures.columns)

        # add categorial features again
        return pd.concat([dfActivtiesWithoutCategorialFeatures_scaled, a.select_dtypes(include='category')], axis=1)

    def __create_label_weekday(self, a: pd.DataFrame) -> pd.Series:
        return a['start_date_isoweekday']
