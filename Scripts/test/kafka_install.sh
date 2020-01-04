echo "[***] Downloading Kafka"
echo ""
wget http://it.apache.contactlab.it/kafka/2.4.0/kafka_2.12-2.4.0.tgz

echo "[***] Extracting Kafka"
tar xzf kafka_2.12-2.4.0.tgz
rm -f kafka_2.12-2.4.0.tgz

echo "[***] Installing Kafka"
sudo mv kafka_2.12-2.4.0 /usr/local/kafka

echo "[***] Launching Test Zookeeper Server"
konsole --hold --separate -e "/usr/local/kafka/bin/zookeeper-server-start.sh /usr/local/kafka/config/zookeeper.properties" &
sleep 7

echo "[***] Launching Test Kafka Server"
konsole --hold --separate -e "/usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties" &
sleep 5

echo "[***] Creating Kafka TestTopic"
/usr/local/kafka/bin//kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TestTopic

echo "[***] Creating a Test Producer"
konsole --hold --separate -e "/usr/local/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic TestTopic" &

echo "[***] Creating a Test Consumer"
konsole --hold --separate -e "/usr/local/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic TestTopic --from-beginning" &

echo ""
echo "Now write something in the Producer Konsole and the messages will appear in the Consumer Konsole" &
