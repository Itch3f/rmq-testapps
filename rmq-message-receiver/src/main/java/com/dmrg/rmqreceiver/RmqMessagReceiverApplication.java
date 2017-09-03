package com.dmrg.rmqreceiver;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.*;

@SpringBootApplication
public class RmqMessagReceiverApplication implements CommandLineRunner {

	@Autowired
	AppConfig applicationConfig;

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) {

		SpringApplication.run(RmqMessagReceiverApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(applicationConfig.getRmqServer());

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(applicationConfig.getRmqServer());
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);

	}
}
