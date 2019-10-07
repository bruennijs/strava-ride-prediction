import os
from elasticsearch import Elasticsearch

ELASTICSEARCH_CLIENT = Elasticsearch(hosts=[{"host": os.getenv("ELASTICSEARCH_HOST"), "port": os.getenv("ELASTICSEARCH_PORT")}])
