# spark-streaming

* Spark Streaming Application to identify any non existing / invalid card number from streaming card data.

![alt tag](https://github.com/valliappanr/spark-streaming/blob/master/find-fraudulent-ids.png)

# Pre-requisite
* Kafka as message broker
* Zookeeper to manage kafka
* Redis Cache - for storing the valid card numbers
* Elastic search / Kibana to store the output and render the output in dashboard


# Quick Start - Guide
* Start Zookeeper and Kafka
* Start Redis cache
* Start Elastic search / Kibana
* Run LoadValidIdsToRedis from scala app from the codebase, which will populate the 
  valid ids into redis cache (the app loads id range from 1 to 10,000 as valid ids).
* Run KafkaIdProducer app to generate random ids from 1 to 25,000, which sort of creating both ids within the
valid range and invalid range.
* Run the StreamingInvalidCardDeductor, which will pull the message from kafka broker and validates against
  redis cache and stores the invalid card to elastic search.
* The invalid cards can be seen on the kibana dashboard.
