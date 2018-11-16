package com.amazonaws.services.iot.client.sample.pubSub;

import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.amazonaws.services.iot.client.sample.pubSub.payload.EnergyConsumerGenerator;
import com.amazonaws.services.iot.client.sample.pubSub.payload.EnergyProducerGenerator;
import com.amazonaws.services.iot.client.sample.sampleUtil.CommandArguments;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil.KeyStorePasswordPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is an example that uses {@link AWSIotMqttClient} to subscribe to a topic and
 * publish messages to it. Both blocking and non-blocking publishing are
 * demonstrated in this example.
 */
public class PublishSubscribeSample {

    private static final String ProducerTopic = "sdk/test/java";
    private static final String ConsumerTopic = "sdk/test/Python";
//    private static final String ProducerTopic = "d12/producer/java";
//    private static final String ConsumerTopic = "d12/consumer/java";
    private static final AWSIotQos TestTopicQos = AWSIotQos.QOS0;

    private static AWSIotMqttClient awsIotClient;

    private static final EnergyConsumerGenerator consumerGenerator = new EnergyConsumerGenerator();
    private static final EnergyProducerGenerator producerGenerator = new EnergyProducerGenerator();

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void setClient(AWSIotMqttClient client) {
        awsIotClient = client;
    }

    public static class NonBlockingPublisher implements Runnable {
        private final AWSIotMqttClient awsIotClient;

        public NonBlockingPublisher(AWSIotMqttClient awsIotClient) {
            this.awsIotClient = awsIotClient;
        }

        @Override
        public void run() {
            while (true) {
                String payload = null;
                try {
                    payload = mapper.writeValueAsString(consumerGenerator.next());
                    AWSIotMessage message = new NonBlockingPublishListener(ConsumerTopic, TestTopicQos, payload);
                    awsIotClient.publish(message);
                    payload = mapper.writeValueAsString(producerGenerator.next());
                    message = new NonBlockingPublishListener(ProducerTopic, TestTopicQos, payload);
                    awsIotClient.publish(message);
                } catch (AWSIotException | JsonProcessingException e) {
                    System.out.println(System.currentTimeMillis() + ": publish failed for " + payload);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(System.currentTimeMillis() + ": NonBlockingPublisher was interrupted");
                    return;
                }
            }
        }
    }

    private static void initClient(CommandArguments arguments) {
        String clientEndpoint = arguments.getNotNull("clientEndpoint", SampleUtil.getConfig("clientEndpoint"));
        String clientId = arguments.getNotNull("clientId", SampleUtil.getConfig("clientId"));

        String certificateFile = arguments.get("certificateFile", SampleUtil.getConfig("certificateFile"));
        String privateKeyFile = arguments.get("privateKeyFile", SampleUtil.getConfig("privateKeyFile"));
        if (awsIotClient == null && certificateFile != null && privateKeyFile != null) {
            String algorithm = arguments.get("keyAlgorithm", SampleUtil.getConfig("keyAlgorithm"));

            KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);

            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        }

        if (awsIotClient == null) {
            String awsAccessKeyId = arguments.get("awsAccessKeyId", SampleUtil.getConfig("awsAccessKeyId"));
            String awsSecretAccessKey = arguments.get("awsSecretAccessKey", SampleUtil.getConfig("awsSecretAccessKey"));
            String sessionToken = arguments.get("sessionToken", SampleUtil.getConfig("sessionToken"));

            if (awsAccessKeyId != null && awsSecretAccessKey != null) {
                awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
                        sessionToken);
            }
        }

        if (awsIotClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
        }
    }

    public static void main(String args[]) throws InterruptedException, AWSIotException {
        CommandArguments arguments = CommandArguments.parse(args);
        initClient(arguments);

        awsIotClient.connect();

        AWSIotTopic topic = new TestTopicListener(ProducerTopic, TestTopicQos);
        awsIotClient.subscribe(topic, true);

        Thread nonBlockingPublishThread = new Thread(new NonBlockingPublisher(awsIotClient));

        nonBlockingPublishThread.start();

        nonBlockingPublishThread.join();
    }
}
