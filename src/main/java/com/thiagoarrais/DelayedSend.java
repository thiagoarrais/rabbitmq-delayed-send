package com.thiagoarrais;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP;

public class DelayedSend {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
	ConnectionFactory factory = new ConnectionFactory();
	Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
	byte[] messageBodyBytes = "delayed payload".getBytes();
	AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder();
	Map<String, Object> headers = new HashMap<String, Object>();
	headers.put("x-delay", 5000);
	props.headers(headers); //commenting this makes the message delivery work
	channel.basicPublish("delayed-ex-v2", "", props.build(), messageBodyBytes);
        conn.close();
    }
}
