#!/usr/bin/env bash
set -e

if [ ! -f ./root-CA.crt ]; then
  printf "\nDownloading AWS IoT Root CA certificate from AWS...\n"
  curl https://www.amazontrust.com/repository/AmazonRootCA1.pem > root-CA.crt
fi

printf "\nInstalling AWS SDK...\n"
mvn clean install -Dgpg.skip=true

printf "\nRunning pub/sub sample application...\n"
mvn exec:java -pl aws-iot-device-sdk-java-samples -Dexec.mainClass="com.amazonaws.services.iot.client.sample.pubSub.PublishSubscribeSample" -Dexec.args="-clientEndpoint a3ve3865weihdy-ats.iot.eu-west-1.amazonaws.com -clientId sdk-java -certificateFile dirtyDozenProducers.cert.pem -privateKeyFile dirtyDozenProducers.private.key"