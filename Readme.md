The EmmaTommy software executes an ETL procedure for extracting EMT’s missions data from the Regional REST Service and load them in the local ERP.

The system is written using Java 11 as an ensemble of micro-services. 

Internally micro-services are composed by concurrent Actors via AKKA. 
At the global communication level between microservices, Akka is used to ask for data, for application logic or for orchestration, while Kafka is used a persistent data channel for producing micro-services’ output that other micro-services react on.

The chosen staging DB  is MySQL, where the data element is a decoration of a Servizio’s JSON to quickly analyze them for posting.

The persistence DB instead is MongoDB, where the JSONs are stored raw without any decoration.

Finally, the analytics service uses ELK stack linked to each micro-services’ logs (via FileBeat and LogStash) and to the persistence DB (via LogStash and Kafka, to add persistence to the data link).
