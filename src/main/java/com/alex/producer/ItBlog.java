package com.alex.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ItBlog {
    private static final String EXCHANGE_NAME = "DoubleDirect";
    private Channel channel;

    public void connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
    }

    public void sendMessage(String routingKey, String msg) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes("UTF-8"));
    }

    public void disconnect() throws IOException, TimeoutException {
        channel.close();
    }
}