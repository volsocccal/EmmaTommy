#!/bin/bash

SCRIPTS_FOLDER=.

# Kafka
KAFKA_FOLDER_NAME=kafka
KAFKA_FOLDER=${SCRIPTS_FOLDER}/${KAFKA_FOLDER_NAME}
echo "Entering Kafka Folder: ${KAFKA_FOLDER}"
cd ${KAFKA_FOLDER}
echo "Starting Kafka ..."
konsole --separate --hold -e "bash kafka_start.sh" &
echo "Started Kafka :)"
cd ..

# Mongo Paths
MONGO_FOLDER_NAME=mongo
MONGO_FOLDER=${SCRIPTS_FOLDER}/${MONGO_FOLDER_NAME}
echo "Entering Mongo Folder: ${MONGO_FOLDER}"
cd ${MONGO_FOLDER}
echo "Starting Mongo ..."
konsole --separate --hold -e "bash mongo_start.sh" &
sleep 3
echo "Started Mongo :)"
./mongo_status.sh
cd ..
