package fr.emse.iot.local;

import org.eclipse.paho.client.mqttv3.*;


public class MqttManager {

    /*
        https://www.baeldung.com/java-mqtt-client
        https://www.eclipse.org/paho/files/javadoc/index.html
     */
    String serverURI = "tcp://max.isasecret.com:1723";
    String  clientId = "999888";
    int qualityOfService = 2;
    public static String  TOPIC_ON = "idrissi/switchOn";
    public static String  TOPIC_OFF = "idrissi/switchOff";
    public static String  TOPIC_UPDATE = "idrissi/update";
    private static MqttConnectOptions options;

/*
    public void publish() throws MqttException
    {
        MqttClient client = new MqttClient(serverURI,clientId,new MemoryPersistence());

        try{
            if(!client.isConnected()) client.connect(MqttManager.setOptions());
            System.out.println("Client Connected ! ");
            MqttMessage msg = new MqttMessage();
            msg.setQos(qualityOfService);
            msg.setPayload("Hello World ! we are testing our MqttClient".getBytes());
            System.out.println("Client is Publishing ....");
            client.publish(TOPIC,msg);
            System.out.println("Client is Published !");

        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }

 */
    public void subscribe() throws MqttException
    {
        MqttClient client = new MqttClient(serverURI,"898989");
        /**
         * We should First define the required methods for the client, before connecting it.
         */
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                    System.out.println("the client lost the connection to the broker");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message arrived ");
                    System.out.println("Switching lights ...." + topic);
                    if(topic.contains(TOPIC_ON)) {
                        System.out.println("TOPIC == TOPIC_ON");
                        HttpManager httpManager = new HttpManager();
                        httpManager.setLightOn(new String(message.getPayload()));
                    }
                    if(topic.contains(TOPIC_OFF)){
                        System.out.println("TOPIC == TOPIC_OFF");
                        HttpManager httpManager = new HttpManager();
                        httpManager.setLightOff(new String(message.getPayload()));
                    }
                    if(topic.contains(TOPIC_UPDATE)) {
                        System.out.println("TOPIC == TOPIC_UPDATE");
                        HttpManager httpManager = new HttpManager();
                        httpManager.setLightBrightnessOrHue(new String(message.getPayload()));
                    }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("outgoing publish is complete ");
            }
        });

        try
        {
            client.connect(MqttManager.setOptions());
            System.out.println("Client is Subscribing ....");
            client.subscribe(TOPIC_ON,qualityOfService);
            client.subscribe(TOPIC_OFF,qualityOfService);
            client.subscribe(TOPIC_UPDATE,qualityOfService);
            System.out.println("Client  Subscribed !");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static MqttConnectOptions setOptions()
    {
        if (options == null)
        { options= new MqttConnectOptions();
            options.setUserName("majinfo2019");
            options.setPassword("Y@_oK2".toCharArray());
        }
        return options;
    }

}
