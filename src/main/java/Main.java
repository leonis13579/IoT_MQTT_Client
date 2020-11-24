import org.eclipse.paho.client.mqttv3.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Main {
    final static String BASE_URL = "tcp://dev.rightech.io:1883";

    final static String CHECKENGINE = "base/state/checkengine";
    final static String FUEL = "base/state/fuel";
    final static String OIL = "base/state/oil";
    final static String BATTERY = "base/error/battery";
    final static String LIGHT = "base/error/light";
    final static String SEATBELT = "base/error/seatbelt";
    final static String CONDITION = "base/error/condition";
    final static String SEATHEAT = "base/error/seatheat";

    static IMqttClient publisher;

    public static void main(String[] args) {
        String publisherString = "mqtt-leonis13579-0pvpos";

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setUserName("111");
        options.setPassword("111".toCharArray());

        try {
            publisher = new MqttClient(BASE_URL, publisherString);
            publisher.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (publisher.isConnected()) {
                        try {
                            publisher.publish(CHECKENGINE, getRandomMessage());
                            publisher.publish(FUEL, getRandomMessage());
                            publisher.publish(OIL, getRandomMessage());
                            publisher.publish(BATTERY, getRandomMessage());
                            publisher.publish(LIGHT, getRandomMessage());
                            publisher.publish(SEATBELT, getRandomMessage());
                            publisher.publish(CONDITION, getRandomMessage());
                            publisher.publish(SEATHEAT, getRandomMessage());
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    private static MqttMessage getRandomMessage() {
        MqttMessage msg = new MqttMessage(((Boolean) new Random().nextBoolean()).toString().getBytes());
        msg.setQos(0);
        msg.setRetained(false);

        return msg;
    }
}
