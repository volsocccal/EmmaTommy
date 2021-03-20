echo "Starting Zookeper Server"
konsole --separate --hold -e "sudo /opt/kafka/bin/zookeeper-server-start.sh ./zookeeper_test.properties" &
sleep 10
echo "Starting Kafka Server 1"
konsole --separate --hold -e "sudo /opt/kafka/bin/kafka-server-start.sh ./kafka_server_1_test.properties" &
sleep 10
echo "Starting Kafka Server 2"
konsole --separate --hold -e "sudo /opt/kafka/bin/kafka-server-start.sh ./kafka_server_2_test.properties" &
sleep 10
echo "Starting Kafka Server 3"
konsole --separate --hold -e "sudo /opt/kafka/bin/kafka-server-start.sh ./kafka_server_3_test.properties" &
sleep 10
