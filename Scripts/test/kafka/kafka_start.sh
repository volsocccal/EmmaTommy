echo "Starting Zookeper Server"
konsole --separate --hold -e "/usr/local/kafka/bin/zookeeper-server-start.sh ./zookeeper_test.properties" &
sleep 10
echo "Starting Kafka Server 1"
konsole --separate --hold -e "/usr/local/kafka/bin/kafka-server-start.sh ./kafka_server_1_test.properties" &
echo "Starting Kafka Server 2"
konsole --separate --hold -e "/usr/local/kafka/bin/kafka-server-start.sh ./kafka_server_2_test.properties" &
echo "Starting Kafka Server 3"
konsole --separate --hold -e "/usr/local/kafka/bin/kafka-server-start.sh ./kafka_server_3_test.properties" &
