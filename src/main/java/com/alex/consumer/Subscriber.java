package com.alex.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class Subscriber {
    /*/*### Домашнее задание:
        1. Сделайте два консольных приложения (не Спринг):
        а. IT-блог, который публикует статьи по языкам программирования
        б. Подписчик, которого интересуют статьи по определенным языкам

        Детали a. Если IT-блог в консоли пишет 'php some message', то 'some message'
        отправляется в RabbitMQ с темой 'php', и это сообщение получают подписчики
        этой темы

        Детали b. Подписчик при запуске должен ввести команду 'set_topic php', после
        чего начнет получать сообщения из очереди с соответствующей темой 'php'

        2. * Сделайте возможность подписчикам подписываться и отписываться от статей по темам
        в процессе работы приложения */
    private static final String EXCHANGE_NAME = "DoubleDirect";
    private Channel channel;
    private String queueName;

    public void connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        queueName = channel.queueDeclare().getQueue();
    }

    public void subscribe(String theme) throws IOException {
        channel.queueBind(queueName, EXCHANGE_NAME, theme);
        System.out.println(" [*] Waiting for messages");
    }

    public void unsubscribe(String theme) throws IOException {
        channel.queueUnbind(queueName, EXCHANGE_NAME, theme);
        System.out.println(" [*] successfully unsubscribed " + theme);
    }

    public void start() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] New material published '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    public void disconnect() throws IOException, TimeoutException {
        channel.close();
    }
}