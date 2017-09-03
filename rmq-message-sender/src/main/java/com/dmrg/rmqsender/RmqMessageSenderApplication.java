package com.dmrg.rmqsender;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rabbitmq.client.*;


@SpringBootApplication
public class RmqMessageSenderApplication implements CommandLineRunner {

	@Autowired
	AppConfig applicationConfig;

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) {

		SpringApplication.run(RmqMessageSenderApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {


		System.out.println(applicationConfig.getRmqServer());

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(applicationConfig.getRmqServer());
		com.rabbitmq.client.Connection connection = factory.newConnection();
		com.rabbitmq.client.Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		int count = 1;
		String input;
		Scanner sc = new Scanner(System.in);
		try{
			do{

				String message = "Hello World - " + count;
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
					
				System.out.println(" [x] - " + count + " Sent '" + message + "'");

				System.out.println(" Please hit enter to continue sending messages and q  to Exit");
				
				count++;
				input = sc.nextLine();
			}while(!input.equals("q"));
		}
		finally{
			sc.close();
			channel.close();
			connection.close();
		}

	}
}
