from . import ELASTICSEARCH_CLIENT

INDEX_NAME = "activity"


class ActivityClient(object):

    def __init__(self) -> None:
        super().__init__()

    def findAll(self, athleteId) -> list:
        searchBody = {
            "query": {
                "term": {
                    "athlete.id_as_string": {
                        "value": athleteId
                    }
                }
            }
        }

        params = {'_source': 'true', 'size': 1000 }

        activityHits = ELASTICSEARCH_CLIENT.search(index=INDEX_NAME, body=searchBody, params=params)

        return list(map(lambda activity: activity["_source"], activityHits["hits"]["hits"]))
