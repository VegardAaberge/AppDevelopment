package com.example.chat;

import com.example.chat.data.MessageRepository;
import com.example.chat.data.model.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(MessageRepository messageRepository){
		return args -> {
			messageRepository.save(new Message(
					"First message",
					"Admin",
					System.currentTimeMillis()
			));
		};
	}
}
