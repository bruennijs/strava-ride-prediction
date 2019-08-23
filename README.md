# Introduction
This application shall be an testing environment for predicting whether an athelete will ride on a concrete day using maching learning algorithms.
There is an web applicatzion before managing loading of strava activities by authorization via oauth2 service provider of strava.
After the delegated authority has been applied to this app it loads activities for the current user.

Then machine learning algorithms try to train models using python scripts (like sklearn library).

The following use cases are planned:
1. Predidct rides of a strava user by learning from previous rides
2. Try to analyze whether a athlete rode a track multiple times or parts of previous rides by analyzing ther GPS coordinates of the tracks.
